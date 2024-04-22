package ru.tinkoff.fintech.meowle.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Ruslan Ganeev
 */
@Entity(tableName = "votes")
data class VoteEntity(
    @PrimaryKey
    val id: Long,
    val vote: Boolean
)
