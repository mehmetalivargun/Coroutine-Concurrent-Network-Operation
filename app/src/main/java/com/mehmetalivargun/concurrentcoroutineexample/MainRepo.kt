package com.mehmetalivargun.concurrentcoroutineexample

import com.mehmetalivargun.concurrentcoroutineexample.api.api
import com.mehmetalivargun.concurrentcoroutineexample.data.CharacterResponse
import java.lang.Exception

class MainRepo {
    suspend fun getCharacter(id: Int): CharacterResponse? {
        val response = try {
            api.getCharacter(id)
        } catch (e: Exception) {
            null
        }
        return response!!.body()
    }

}