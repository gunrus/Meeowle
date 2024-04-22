package ru.tinkoff.fintech.meowle.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.tinkoff.fintech.meowle.domain.cat.Gender

/**
 * @author Ruslan Ganeev
 */
@Entity(tableName = "cats")
data class CatEntity(
    @PrimaryKey
    val id: Long,
    val name: String,
    val description: String,
    val gender: Gender,
    val likes: Int,
    val dislikes: Int
)
