package com.example.memelist.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meme_table")
data class MemeEntity(
    @PrimaryKey val id: Int,
    val bottomText: String? = null,
    val image: String? = null,
    val name: String? = null,
    val rank: Int? = null,
    val tags: String? = null,
    val topText: String? = null
)
