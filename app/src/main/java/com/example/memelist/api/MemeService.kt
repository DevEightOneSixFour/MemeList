package com.example.memelist.api

import com.example.memelist.model.IdResponse
import com.example.memelist.model.MemeItem
import com.example.memelist.model.MemeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MemeService {
    // "https://alpha-meme-maker.herokuapp.com/3"
    @GET("{pageNum}")
    suspend fun getMemesByPageNum(
        @Path("pageNum") num: Int
    ): Response<MemeResponse>

    // http://alpha-meme-maker.herokuapp.com/memes/:id
    @GET("memes/{id}")
    suspend fun getMemeById(
        @Path("id") id: Int? = null
    ): Response<IdResponse>
}