package ru.tinkoff.fintech.meowle.domain.repository

import android.net.Uri
import ru.tinkoff.fintech.meowle.domain.BackendError
import ru.tinkoff.fintech.meowle.domain.FileError
import ru.tinkoff.fintech.meowle.domain.MeowleResult

/**
 * @author Ruslan Ganeev
 */
interface PhotosRepository {

    suspend fun getCatPhotos(id: Long): MeowleResult<List<Uri>, BackendError>

    suspend fun uploadCatPhoto(id: Long, photoUri: Uri): MeowleResult<Unit, BackendError>

    suspend fun saveCatPhoto(id: Long, catImageUrl: Uri?): MeowleResult<Unit, FileError>

    suspend fun getCatPhoto(id: Long): MeowleResult<Uri?, FileError>

    suspend fun deleteCatPhoto(id: Long): MeowleResult<Unit, FileError>
}
