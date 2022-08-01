package com.example.memelist.database

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

interface MemeDao {

    @Query("SELECT * FROM meme_table")
    suspend fun getAllSavedMemes(): Flow<MemeEntity>

    @Query("DELETE FROM meme_table")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(memeEntity: MemeEntity)
}