package ru.tinkoff.fintech.meowle.domain.repository

import ru.tinkoff.fintech.meowle.domain.BackendError
import ru.tinkoff.fintech.meowle.domain.DatabaseError
import ru.tinkoff.fintech.meowle.domain.MeowleResult
import ru.tinkoff.fintech.meowle.domain.cat.Cat
import ru.tinkoff.fintech.meowle.domain.cat.Vote

/**
 * @author Ruslan Ganeev
 */
interface CatRepository {

    suspend fun getCats(
        name: String,
        order: String,
        gender: String?
    ): MeowleResult<List<Cat>, BackendError>

    suspend fun getCatById(id: Long): MeowleResult<Cat, BackendError>

    suspend fun saveDescription(id: Long, description: String): MeowleResult<Cat, BackendError>

    suspend fun addCat(name: String, gender: String, description: String): MeowleResult<Cat, BackendError>

    suspend fun getRating() : MeowleResult<Map<Vote, List<Cat>>, BackendError>

    suspend fun vote(id: Long, vote: Boolean): MeowleResult<Cat, BackendError>

    suspend fun isVoted(id: Long): MeowleResult<Boolean?, DatabaseError>

    suspend fun isFavourite(id: Long): MeowleResult<Boolean, DatabaseError>

    suspend fun getFavoriteCats(): MeowleResult<List<Cat>, DatabaseError>

    suspend fun addToFavourites(cat: Cat): MeowleResult<Unit, DatabaseError>

    suspend fun removeFromFavourites(id: Long): MeowleResult<Unit, DatabaseError>
}
