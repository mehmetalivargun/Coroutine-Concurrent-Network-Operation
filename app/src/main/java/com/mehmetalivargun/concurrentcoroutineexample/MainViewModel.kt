package com.mehmetalivargun.concurrentcoroutineexample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mehmetalivargun.concurrentcoroutineexample.api.api
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel : ViewModel() {
    private val repository = MainRepo()
    fun uiState(): LiveData<UiState> = uiState
    protected val uiState: MutableLiveData<UiState> = MutableLiveData()


    fun getRandomCharactersSequentilay() = viewModelScope.launch {
        uiState.value = UiState.Loading
        val firtsChar = repository.getCharacter((1 until 100).random())
        val secondChar = repository.getCharacter((1 until 100).random())
        val thirdChar = repository.getCharacter((1 until 100).random())
        uiState.value = UiState.Success(listOf(firtsChar, secondChar, thirdChar))

    }


    fun getRandomCharactersConcurrently() {
        uiState.value = UiState.Loading
        val firtsChar = viewModelScope.async { repository.getCharacter((1 until 100).random()) }
        val secondChar = viewModelScope.async { repository.getCharacter((1 until 100).random()) }
        val thirdChar = viewModelScope.async { repository.getCharacter((1 until 100).random()) }


        viewModelScope.launch {
            try {
                val charaters = awaitAll(firtsChar, secondChar, thirdChar)
                uiState.value = UiState.Success(charaters)
            } catch (e: Exception) {

            }
        }
    }


}