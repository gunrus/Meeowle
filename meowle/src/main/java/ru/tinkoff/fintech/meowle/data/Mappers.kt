package ru.tinkoff.fintech.meowle.data

import ru.tinkoff.fintech.meowle.data.api.dto.CatDto
import ru.tinkoff.fintech.meowle.data.api.dto.response.AddCatResponseDto
import ru.tinkoff.fintech.meowle.data.api.dto.response.RatingResponseDto
import ru.tinkoff.fintech.meowle.data.api.dto.response.SearchResponseDto
import ru.tinkoff.fintech.meowle.data.database.CatEntity
import ru.tinkoff.fintech.meowle.domain.cat.Cat
import ru.tinkoff.fintech.meowle.domain.cat.Gender
import ru.tinkoff.fintech.meowle.domain.cat.Vote

/**
 * @author Ruslan Ganeev
 */
fun List<CatEntity>.entityToDomain(): List<Cat> {
    return this.map {
        Cat(
            id = it.id,
            name = it.name,
            description = it.description,
            gender = it.gender,
            likes = it.likes,
            dislikes = it.dislikes
        )
    }
}

fun Cat.toEntity(): CatEntity {
    return CatEntity(
        id = this.id,
        name = this.name,
        description = this.description,
        gender = this.gender,
        likes = this.likes,
        dislikes = this.dislikes
    )
}

fun CatDto.toDomain(): Cat {
    return Cat(
        id = this.id,
        name = this.name,
        description = this.description ?: "",
        gender = gender.toGender(),
        likes = this.likes,
        dislikes = this.dislikes
    )
}

fun CatDto.toEntity(): CatEntity {
    return CatEntity(
        id = this.id,
        name = this.name,
        description = this.description ?: "",
        gender = gender.toGender(),
        likes = this.likes,
        dislikes = this.dislikes
    )
}

fun List<CatDto>.toDomain(): List<Cat> {
    return this.map { it.toDomain() }
}

fun List<CatDto>.toEntity(): List<CatEntity> {
    return this.map { it.toEntity() }
}

fun SearchResponseDto.toDomain(): List<Cat> {
    val result = mutableListOf<Cat>()
    this.groups.forEach {
        result.addAll(it.cats.toDomain())
    }
    return result
}

fun SearchResponseDto.toDomainMap(): Map<String, List<Cat>> {
    val result = mutableMapOf<String, List<Cat>>()
    this.groups.forEach {
       result[it.title] = it.cats.toDomain()
    }
    return result
}

fun SearchResponseDto.toEntity(): List<CatEntity> {
    val result = mutableListOf<CatEntity>()
    this.groups.forEach {
        result.addAll(it.cats.toEntity())
    }
    return result
}

fun RatingResponseDto.toDomain(): Map<Vote, List<Cat>> {
    return mapOf(Vote.LIKES to this.likes.toDomain(), Vote.DISLIKES to this.dislikes.toDomain())
}

fun AddCatResponseDto.toDomain(): Cat {
    val first = this.cats.first()
    return Cat(
        id = first.id!!,
        name = first.name!!,
        description = first.description ?: "",
        gender = first.gender.toGender(),
        likes = first.likes!!,
        dislikes = first.dislikes!!
    )
}

private fun String?.toGender(): Gender {
    return when (this) {
        Gender.MALE.gender -> Gender.MALE
        Gender.FEMALE.gender -> Gender.FEMALE
        Gender.UNISEX.gender -> Gender.UNISEX
        else -> Gender.UNISEX
    }
}
