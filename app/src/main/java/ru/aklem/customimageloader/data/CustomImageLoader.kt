package ru.aklem.customimageloader.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.aklem.customimageloader.util.Result
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.random.Random

class CustomImageLoader @Inject constructor(
    @ApplicationContext private val context: Context,
    private val baseUrl: String,
    private val workManager: WorkManager
) {

    fun getImage(urn: String): Result<Bitmap> {
        val result = try {
            if (Random.nextBoolean()) throw IllegalArgumentException()
            val url = URL(baseUrl + PATH + urn)
            val image = File(context.filesDir, urn)
            Result.Success(
                if (image.exists()) {
                    getImageFromInternalStorage(image)
                } else {
                    val connection = url.openConnection() as HttpURLConnection
                    connection.connect()
                    val inputStream: InputStream = connection.inputStream
                    val bufferedInputStream = BufferedInputStream(inputStream)
                    val bitmap = BitmapFactory.decodeStream(bufferedInputStream)
                    saveImageToInternalStorage(bitmap, image)
                    scheduleDeleteFile(image)
                    bitmap
                }
            )
        } catch (e: IOException) {
            Result.Error(e)
        } catch (e: MalformedURLException) {
            Result.Error(e)
        } catch (e: Exception) {
            Result.Error(e)
        }
        return result
    }

    private fun saveImageToInternalStorage(bitmap: Bitmap, image: File) {
        FileOutputStream(image).use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            Log.d("IMAGE_LOADER", "saveImageToInternalStorage() $image")
        }
    }

    private fun getImageFromInternalStorage(image: File): Bitmap {
        FileInputStream(image).use {
            Log.d("IMAGE_LOADER", "getImageFromInternalStorage() $image")
            return BitmapFactory.decodeStream(it)
        }
    }

    private fun scheduleDeleteFile(image: File) {
        val data = createInputData(image.path)
        Log.d("IMAGE_LOADER", "scheduleDeleteFile()")
        val worker = OneTimeWorkRequestBuilder<ImageDeleteWorker>()
            .setInitialDelay(10, TimeUnit.MINUTES)
            .setInputData(data)
            .build()
        workManager.enqueueUniqueWork(
            WORK_NAME,
            ExistingWorkPolicy.APPEND_OR_REPLACE,
            mutableListOf(worker)
        )
    }

    private fun createInputData(path: String): Data {
        val builder = Data.Builder()
        builder.putString(IMAGE_PATH_KEY, path)
        return builder.build()
    }

    companion object {
        private const val PATH = "images/"
        private const val WORK_NAME = "delayed_image_delete"
        const val IMAGE_PATH_KEY = "image_path_key"
    }

}