package ru.tinkoff.fintech.meowle.data.api

import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import ru.tinkoff.fintech.meowle.data.api.dto.CatDto
import ru.tinkoff.fintech.meowle.data.api.dto.request.AddCatRequestDto
import ru.tinkoff.fintech.meowle.data.api.dto.request.SaveDescriptionRequestDto
import ru.tinkoff.fintech.meowle.data.api.dto.request.SearchRequestDto
import ru.tinkoff.fintech.meowle.data.api.dto.request.VoteRequestDto
import ru.tinkoff.fintech.meowle.data.api.dto.response.AddCatResponseDto
import ru.tinkoff.fintech.meowle.data.api.dto.response.CatByIdResponseDto
import ru.tinkoff.fintech.meowle.data.api.dto.response.CatPhotosDto
import ru.tinkoff.fintech.meowle.data.api.dto.response.RatingResponseDto
import ru.tinkoff.fintech.meowle.data.api.dto.response.SearchResponseDto
import ru.tinkoff.fintech.meowle.data.api.dto.response.UploadResponseDto


/**
 * @author Ruslan Ganeev
 */
interface CatsApi {

    @POST("/api/core/cats/search")
    suspend fun getCats(@Body requestBody: SearchRequestDto): SearchResponseDto

    @GET("/api/core/cats/get-by-id")
    suspend fun getCatById(@Query("id")id: Long): CatByIdResponseDto

    @GET("/api/photos/cats/{id}/photos")
    suspend fun getCatPhotos(@Path("id")id: Long): CatPhotosDto

    @POST("/api/core/cats/save-description")
    suspend fun saveDescription(@Body requestBody: SaveDescriptionRequestDto): CatDto

    @POST("/api/core/cats/add")
    suspend fun addCat(@Body requestBody: AddCatRequestDto): AddCatResponseDto

    @GET("/api/likes/cats/rating")
    suspend fun getRating(): RatingResponseDto

    @POST("/api/likes/cats/{id}/likes")
    suspend fun vote(@Path("id")id: Long, @Body requestBody: VoteRequestDto): CatDto

    @Multipart
    @POST("api/photos/cats/{id}/upload")
    suspend fun uploadCatPhoto(@Path("id")id: Long, @Part filePart: MultipartBody.Part): UploadResponseDto
}
