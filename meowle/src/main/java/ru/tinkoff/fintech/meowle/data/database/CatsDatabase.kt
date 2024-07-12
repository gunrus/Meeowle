package ru.tinkoff.fintech.meowle.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * @author Ruslan Ganeev
 */
@Database(entities = [CatEntity::class, VoteEntity::class], version = 1, exportSchema = false)
abstract class CatsDatabase : RoomDatabase() {
    abstract fun catsDao(): CatDao
}
