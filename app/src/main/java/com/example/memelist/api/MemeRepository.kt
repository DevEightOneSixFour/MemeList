package com.example.memelist.api

import com.example.memelist.model.UIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface MemeRepository {
    suspend fun getMemesByPageNum(num: Int): Flow<UIState>
    suspend fun getMemeById(id: Int): Flow<UIState>
}

class MemeRepositoryImpl(private val service: MemeService): MemeRepository {
    override suspend fun getMemesByPageNum(num: Int): Flow<UIState> =
        flow {
//            emit(UIState.Loading)
            try {
                val response = service.getMemesByPageNum(num)
                if (response.isSuccessful) {
                    emit(response.body()?.let { memeResponse ->
                        UIState.Success(memeResponse)
                    } ?: throw Exception("Empty response"))
                } else throw Exception("Failed network call")
            } catch (e: Exception) {
                emit(UIState.Error(e))
            }
        }

    override suspend fun getMemeById(id: Int): Flow<UIState> =
        flow {
            try {
                val response = service.getMemeById(id)
                if (response.isSuccessful) {
                    emit(response.body()?.let { idResponse ->
                        UIState.Success(idResponse)
                    } ?: throw Exception("Empty response"))
                } else throw Exception("Failed network call")
            } catch (e: Exception) {
                emit(UIState.Error(e))
            }
        }
}