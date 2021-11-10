package com.luis.android.themoviechallenge.view.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.luis.android.themoviechallenge.R
import com.luis.android.themoviechallenge.domain.model.PopularMovies
import com.luis.android.themoviechallenge.view.helper.MethodUtils
import com.luis.android.themoviechallenge.view.helper.StringUtils
import com.luis.android.themoviechallenge.view.model.PopularMoviesData
import com.luis.android.themoviechallenge.view.viewmodel.ImageViewModel
import com.luis.android.themoviechallenge.view.viewmodel.MoviesViewModel
import com.lyft.android.themoviedbtest.view.adapter.ViewPagerAdapter
import com.lyft.android.themoviedbtest.view.fragment.LocationFragment
import com.lyft.android.themoviedbtest.view.fragment.MoviesFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.util.ArrayList

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mTag = "MovieActivityError"
    private lateinit var mMoviesViesModel: MoviesViewModel
    private lateinit var mImageViewModel: ImageViewModel


    private val mMoviesFragment:MoviesFragment = MoviesFragment.getInstance()
    private val mLocationFragment : LocationFragment = LocationFragment.getInstance()

    private val fragmentList = listOf(mMoviesFragment, mLocationFragment)

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mMoviesViesModel = ViewModelProvider(this).get(MoviesViewModel::class.java)
        mImageViewModel = ViewModelProvider(this).get(ImageViewModel::class.java)

        initViewPagerView()
        initBottomAppBar()
        getPopularMoviesResponse()
        getPopularMoviesError()

    }

    private fun getPopularMoviesResponse() {
        mMoviesViesModel.popularMoviesResult.observe(this, {
            it.map {
                mMoviesFragment.setMoviesView(toPopularMoviesData(it.popularMovies))
            }
        })

        mMoviesViesModel.getLocalPopularMovie().observe(this, {
            if (it.isNotEmpty()) {
                mMoviesFragment.setMoviesView(toPopularMoviesData(it))
            } else {
                getPopularMoviesRequest()
            }
        })

    }

    private fun getPopularMoviesError() {
        mMoviesViesModel.popularMoviesError.observe(this, {
            Log.e(mTag, it)
        })
    }

    private fun getPopularMoviesRequest(pageNumber:String = "1") {
        mMoviesViesModel.getPopularMoviesRequest(pageNumber)
    }

    private fun initViewPagerView() {
        val pagerAdapter = ViewPagerAdapter(fragmentList, this)
        menu_viewPager.adapter = pagerAdapter

        menu_viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled( position: Int,
                                         positionOffset: Float,
                                         positionOffsetPixels: Int) {

                when (position){

                    0 -> {
                        getPopularMoviesResponse()
                    } 1-> {

                    }
                }

            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> bottomNavigationView.selectedItemId = R.id.bottom_app_movies
                    1 -> bottomNavigationView.selectedItemId = R.id.bottom_app_location
                    else -> bottomNavigationView.selectedItemId = R.id.bottom_app_movies
                }
            }
        })

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initBottomAppBar() {
        fabMenu.setOnClickListener {
            selectPhoFromGallery()
        }

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId){
                R.id.bottom_app_movies -> {
                    menu_viewPager.currentItem = 0
                    true
                }
                R.id.bottom_app_location -> {
                    menu_viewPager.currentItem = 1
                    true
                }
                else -> false
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun selectPhoFromGallery() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE),
                StringUtils.READ_EXTERNAL_PERMISSION
            )
            return
        }

        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, StringUtils.READ_EXTERNAL_PERMISSION)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val uri: Uri
        if (resultCode == Activity.RESULT_OK && requestCode == StringUtils.READ_EXTERNAL_PERMISSION) {
            uri = Uri.parse(data?.data.toString())
            loadImageToServer(uri)
        }

    }

    private fun loadImageToServer(uri: Uri) {
        Log.i("ImagePathh", "Uri image: $uri")

        val file = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(uri, file, null, null, null)

        if (cursor != null) {
            cursor.moveToFirst()
            val imageIndex = cursor.getColumnIndex(file[0])
            val photoPath = cursor.getString(imageIndex)

            lateinit var fileImage: File
                if (photoPath != null) {
                    if (File(photoPath).exists()) {
                        fileImage = File(
                            Environment.getExternalStorageDirectory().path + "/" + MethodUtils.reduceImageSizeAndRotate(photoPath).name
                        )
                    }
                }


            try {
                uploadFile(fileImage, cursor)
            } catch (e: UninitializedPropertyAccessException) {
                Log.e("UploadImageError", e.stackTrace.toString())
            }
        }
    }

    private fun uploadFile(fileImage: File, cursor: Cursor) {
        Log.e("ImagePathh", fileImage.absolutePath)
        mImageViewModel.uploadImage(fileImage.toURI().toString())
        Toast.makeText(this, getString(R.string.image_uploading), Toast.LENGTH_SHORT).show()
        cursor.close()
    }


    override fun onBackPressed() {
        if (menu_viewPager.currentItem == 0) {
            super.onBackPressed()
        } else if (menu_viewPager.currentItem == 1) {
            menu_viewPager.setCurrentItem(0)
        }
    }

}

private fun toPopularMoviesData(moviesList: List<PopularMovies>): ArrayList<PopularMoviesData> {
    val moviesDataList = ArrayList<PopularMoviesData>()

    moviesList.map {
        moviesDataList.add(PopularMoviesData(it.movieId.toInt(),it.title,it.posterImagePath))
    }

    return moviesDataList
}


