package ru.aklem.customimageloader.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import javax.inject.Inject

class CustomImageLoader @Inject constructor(
    @ApplicationContext private val context: Context,
    private val baseUrl: String
) {

    fun getImage(urn: String): Bitmap {
        try {
            val url = URL(baseUrl + PATH + urn)
            val image = File(context.filesDir, urn)
            return if (image.exists()) {
                getImageFromInternalStorage(image)
            } else {
                val connection = url.openConnection() as HttpURLConnection
                connection.connect()
                val inputStream: InputStream = connection.inputStream
                val bufferedInputStream = BufferedInputStream(inputStream)
                val bitmap = BitmapFactory.decodeStream(bufferedInputStream)
                saveImageToInternalStorage(bitmap, image)
                bitmap
            }
        } catch (e: IOException) {
            // todo wrap to appException
            throw e
        } catch (e: MalformedURLException) {
            // todo wrap to appException
            throw e
        } catch (e: Exception) {
            // todo wrap other exceptions
            throw e
        }
    }

    private fun saveImageToInternalStorage(bitmap: Bitmap, image: File) {
        FileOutputStream(image).use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            Log.d("IMAGE_LOADER", "Saved $image")
        }
    }

    private fun getImageFromInternalStorage(image: File): Bitmap {
        FileInputStream(image).use {
            Log.d("IMAGE_LOADER", "Loaded $image")
            return BitmapFactory.decodeStream(it)
        }
    }

    companion object {
        private const val PATH = "images/"
    }
}