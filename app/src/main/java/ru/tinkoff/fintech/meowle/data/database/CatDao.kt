package ru.tinkoff.fintech.meowle.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * @author Ruslan Ganeev
 */
@Dao
interface CatDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCat(cat: CatEntity)

    @Query("SELECT * FROM cats WHERE id = :id")
    suspend fun getCat(id: Long): CatEntity?

    @Query("SELECT * FROM cats")
    suspend fun getCats(): List<CatEntity>

    @Query("DELETE FROM cats WHERE id = :id")
    suspend fun deleteCat(id: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveVote(vote: VoteEntity)

    @Query("SELECT * FROM votes WHERE id = :id")
    suspend fun getVote(id: Long): VoteEntity?
}
