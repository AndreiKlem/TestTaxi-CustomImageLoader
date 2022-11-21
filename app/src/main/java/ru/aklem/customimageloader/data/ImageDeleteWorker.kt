package ru.aklem.customimageloader.data

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.io.File

@HiltWorker
class ImageDeleteWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        return try {
            val path = inputData.getString(CustomImageLoader.IMAGE_PATH_KEY)
                ?: throw IllegalArgumentException()
            val file = File(path)
            if (file.exists()) file.delete()
            Log.wtf("DELETE_WORKER", "doWork() image $path successfully deleted")
            Result.success()
        } catch (e: Exception) {
            Log.wtf("DELETE_WORKER", "doWork() image delete failure")
            Result.failure()
        }
    }
}