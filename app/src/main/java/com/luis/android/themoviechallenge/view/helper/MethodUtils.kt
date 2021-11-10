package com.luis.android.themoviechallenge.view.helper

import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Environment
import android.view.View
import androidx.exifinterface.media.ExifInterface
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class MethodUtils {

    companion object {
        fun reduceImageSizeAndRotate( path: String ): File {
            val file = File( path )

            return try {
                // BitmapFactory options to downsize the image
                val o = BitmapFactory.Options()
                o.inJustDecodeBounds = true
                o.inSampleSize = 6
                // factor of downsizing the image
                var inputStream = FileInputStream(file)
                //Bitmap selectedBitmap = null;
                BitmapFactory.decodeStream(inputStream, null, o)
                inputStream.close()

                // The new size we want to scale to
                val REQUIRED_SIZE = 75

                // Find the correct scale value. It should be the power of 2.
                var scale = 1
                while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE
                ) {
                    scale *= 2
                }
                val o2 = BitmapFactory.Options()
                o2.inSampleSize = scale
                inputStream = FileInputStream(file)
                var selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2)
                inputStream.close()

                // here i override the original image file
                file.createNewFile()
                val outputStream = FileOutputStream(Environment.getExternalStorageDirectory().path + "/" +file.name)

                val matrix = rotateImage( path )
                selectedBitmap = Bitmap.createBitmap(
                    selectedBitmap!!,
                    0,
                    0,
                    selectedBitmap.width,
                    selectedBitmap.height,
                    matrix,
                    true
                )

                selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
                file
            } catch (e: Exception) {
                file
            }
        }

        private fun rotateImage (path: String): Matrix {
            val exif = ExifInterface(path)
            val orientation =
                exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED)

            val matrix = Matrix()
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> matrix.preRotate(90f)
                ExifInterface.ORIENTATION_ROTATE_180 -> matrix.preRotate(180f)
                ExifInterface.ORIENTATION_ROTATE_270 -> matrix.preRotate(270f)
                else -> matrix.preRotate(0f)
            }

            return matrix
        }

    }

}