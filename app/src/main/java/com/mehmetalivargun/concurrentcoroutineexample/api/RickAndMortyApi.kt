package com.mehmetalivargun.concurrentcoroutineexample.api

import com.mehmetalivargun.concurrentcoroutineexample.data.CharacterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApi {
    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id :Int):Response<CharacterResponse>
}