package ru.tinkoff.fintech.meowle.data.repository

import ru.tinkoff.fintech.meowle.data.api.CatsApi
import ru.tinkoff.fintech.meowle.data.api.dto.request.AddCatDto
import ru.tinkoff.fintech.meowle.data.api.dto.request.AddCatRequestDto
import ru.tinkoff.fintech.meowle.data.api.dto.request.SaveDescriptionRequestDto
import ru.tinkoff.fintech.meowle.data.api.dto.request.SearchRequestDto
import ru.tinkoff.fintech.meowle.data.api.dto.request.VoteRequestDto
import ru.tinkoff.fintech.meowle.data.database.CatDao
import ru.tinkoff.fintech.meowle.data.database.VoteEntity
import ru.tinkoff.fintech.meowle.data.entityToDomain
import ru.tinkoff.fintech.meowle.data.toDomain
import ru.tinkoff.fintech.meowle.data.toEntity
import ru.tinkoff.fintech.meowle.domain.BackendError
import ru.tinkoff.fintech.meowle.domain.DatabaseError
import ru.tinkoff.fintech.meowle.domain.MeowleResult
import ru.tinkoff.fintech.meowle.domain.cat.Cat
import ru.tinkoff.fintech.meowle.domain.cat.Vote
import ru.tinkoff.fintech.meowle.domain.repository.CatRepository
import javax.inject.Inject

/**
 * @author Ruslan Ganeev
 */
class CatsRepositoryImpl @Inject constructor(
    private val catsApi: CatsApi,
    private val catDao: CatDao
) : CatRepository {

    override suspend fun getCats(name: String, order: String, gender: String?): MeowleResult<List<Cat>, BackendError> {
        return try {
            val cats = catsApi.getCats(SearchRequestDto(name, order, gender)).toDomain()
            MeowleResult.Success(cats)
        } catch (e: Exception) {
            MeowleResult.Error(BackendError.Network.UNKNOWN)
        }
    }

    override suspend fun getCatById(id: Long): MeowleResult<Cat, BackendError> {
        return try {
            MeowleResult.Success(catsApi.getCatById(id).cat.toDomain())
        } catch (e: Exception) {
            MeowleResult.Error(BackendError.Network.UNKNOWN)
        }
    }

    override suspend fun saveDescription(id: Long, description: String): MeowleResult<Cat, BackendError> {
        return try {
            val result = catsApi.saveDescription(SaveDescriptionRequestDto(id, description))
            MeowleResult.Success(result.toDomain())
        } catch (e: Exception) {
            MeowleResult.Error(BackendError.Network.UNKNOWN)
        }
    }

    override suspend fun addCat(name: String, gender: String, description: String): MeowleResult<Cat, BackendError> {
        return try {
            val result = catsApi.addCat(AddCatRequestDto(listOf(AddCatDto(name, gender, description))))

            val errorDescription = result.cats.first().errorDescription
            if (errorDescription == null) {
                return MeowleResult.Success(result.toDomain())
            } else {
                return MeowleResult.Error(BackendError.Business.Unknown(errorDescription))
            }
        } catch (e: Exception) {
            MeowleResult.Error(BackendError.Network.UNKNOWN)
        }
    }

    override suspend fun getRating(): MeowleResult<Map<Vote, List<Cat>>, BackendError> {
        return try {
            MeowleResult.Success(catsApi.getRating().toDomain())
        } catch (e: Exception) {
            MeowleResult.Error(BackendError.Network.UNKNOWN)
        }
    }

    override suspend fun vote(id: Long, vote: Boolean): MeowleResult<Cat, BackendError> {
        return try {
            val result = catsApi.vote(id, VoteRequestDto(like = vote, dislike = !vote))
            catDao.saveVote(VoteEntity(id, vote))
            MeowleResult.Success(result.toDomain())
        } catch (e: Exception) {
            MeowleResult.Error(BackendError.Network.UNKNOWN)
        }
    }

    override suspend fun isVoted(id: Long): MeowleResult<Boolean?, DatabaseError> {
        return try {
            MeowleResult.Success(catDao.getVote(id)?.vote)
        } catch (e: Exception) {
            MeowleResult.Error(DatabaseError.UNKNOWN)
        }
    }

    override suspend fun isFavourite(id: Long): MeowleResult<Boolean, DatabaseError> {
        return try {
            MeowleResult.Success(catDao.getCat(id) != null)
        } catch (e: Exception) {
            MeowleResult.Error(DatabaseError.UNKNOWN)
        }
    }

    override suspend fun getFavoriteCats(): MeowleResult<List<Cat>, DatabaseError> {
        return try {
            MeowleResult.Success(catDao.getCats().entityToDomain())
        } catch (e: Exception) {
            MeowleResult.Error(DatabaseError.UNKNOWN)
        }
    }

    override suspend fun addToFavourites(cat: Cat): MeowleResult<Unit, DatabaseError> {
        return try {
            catDao.saveCat(cat.toEntity())
            MeowleResult.Success(Unit)
        } catch (e: Exception) {
            MeowleResult.Error(DatabaseError.UNKNOWN)
        }
    }

    override suspend fun removeFromFavourites(id: Long): MeowleResult<Unit, DatabaseError> {
        return try {
            catDao.deleteCat(id)
            MeowleResult.Success(Unit)
        } catch (e: Exception) {
            MeowleResult.Error(DatabaseError.UNKNOWN)
        }
    }
}
