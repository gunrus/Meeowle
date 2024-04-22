package ru.tinkoff.fintech.meowle.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import ru.tinkoff.fintech.meowle.data.api.CatsApi
import ru.tinkoff.fintech.meowle.di.AppModule
import ru.tinkoff.fintech.meowle.domain.BackendError
import ru.tinkoff.fintech.meowle.domain.FileError
import ru.tinkoff.fintech.meowle.domain.MeowleResult
import ru.tinkoff.fintech.meowle.domain.repository.PhotosRepository
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import javax.inject.Inject

/**
 * @author Ruslan Ganeev
 */
class PhotosRepositoryImpl @Inject constructor(
    private val catsApi: CatsApi,
    @ApplicationContext
    private val context: Context
) : PhotosRepository {

    override suspend fun getCatPhotos(id: Long): MeowleResult<List<Uri>, BackendError> {
        return try {
            val photos = catsApi.getCatPhotos(id).images.map {
                Uri.parse("${AppModule.BASE_URL}$it")
            }

            MeowleResult.Success(photos)
        } catch (e: Exception) {
            MeowleResult.Error(BackendError.Network.UNKNOWN)
        }
    }

    override suspend fun uploadCatPhoto(id: Long, photoUri: Uri): MeowleResult<Unit, BackendError> {
        return try {
            val file = uriToFile(photoUri)
            val filePart = MultipartBody.Part.createFormData(
                name = "file",
                filename = file.name,
                body = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            )
            catsApi.uploadCatPhoto(id, filePart)
            return MeowleResult.Success(Unit)
        } catch (e: Exception) {
            MeowleResult.Error(BackendError.Network.UNKNOWN)
        }
    }

    override suspend fun saveCatPhoto(id: Long, catImageUrl: Uri?): MeowleResult<Unit, FileError> {
        return try {
            val photoDir = context.filesDir.resolve(PHOTOS_DIRECTORY)
            val url = URL(catImageUrl.toString())
            val bitmap = BitmapFactory.decodeStream(withContext(Dispatchers.IO) {
                url.openStream()
            })
            if (bitmap!= null) {
                if (!photoDir.exists()) {
                    photoDir.mkdir()
                }
                val photoFile = photoDir.resolve(id.toPhotoName())
                withContext(Dispatchers.IO) {
                    FileOutputStream(photoFile).use {
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
                    }
                }
            }
            MeowleResult.Success(Unit)
        } catch (e: Exception) {
            return MeowleResult.Error(FileError.UNKNOWN)
        }
    }

    override suspend fun getCatPhoto(id: Long): MeowleResult<Uri?, FileError> {
        return try {
            val catPhotoUri = Uri.fromFile(context.filesDir.resolve(PHOTOS_DIRECTORY).resolve(id.toPhotoName()))
            MeowleResult.Success(catPhotoUri)
        } catch (e: Exception) {
            return MeowleResult.Error(FileError.UNKNOWN)
        }
    }

    override suspend fun deleteCatPhoto(id: Long): MeowleResult<Unit, FileError> {
        return try {
            context.filesDir.resolve(PHOTOS_DIRECTORY).resolve(id.toPhotoName()).delete()
            MeowleResult.Success(Unit)
        } catch (e: Exception) {
            return MeowleResult.Error(FileError.UNKNOWN)
        }
    }

    private fun uriToFile(uri: Uri): File {
        var file: File? = null
        context.contentResolver.openInputStream(uri)?.let {
            it.use {
                file = File(context.cacheDir, "file.jpg")
                val outputStream = FileOutputStream(file)
                outputStream.use { output ->
                    val buffer = ByteArray(4 * 1024)
                    while (true) {
                        val byteCount = it.read(buffer)
                        if (byteCount < 0) break
                        output.write(buffer, 0, byteCount)
                    }
                    output.flush()
                }
            }
        }
        return file!!
    }

    private fun Long.toPhotoName(): String {
        return "$this.jpg"
    }

    private companion object {
        private const val PHOTOS_DIRECTORY = "photos"
    }
}
