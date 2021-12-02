package com.mehmetalivargun.concurrentcoroutineexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.mehmetalivargun.concurrentcoroutineexample.data.CharacterResponse
import com.mehmetalivargun.concurrentcoroutineexample.databinding.ActivityMainBinding
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private val binding by lazy {
        ActivityMainBinding.inflate(
            layoutInflater
        )
    }

    private var operationStartTime = 0L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.uiState().observe(this,{
            if (it!=null){
                updateUi(it)
            }
        })

        binding.seqButton.setOnClickListener {
            viewModel.getRandomCharactersSequentilay()
        }
        binding.concurButton.setOnClickListener {
            viewModel.getRandomCharactersConcurrently()
        }
    }

    private fun  updateUi(uiState:UiState){
        when(uiState){
            is UiState.Loading->{loading()}
            is UiState.Success->{onSucces(uiState)}
            is UiState.Error->{onError()}
        }
    }

    private fun loading(){
        operationStartTime=System.currentTimeMillis()
    }

    private fun onSucces(uiState: UiState.Success){
       binding.apply {
           uiState.characters[0]?.name?.let { Log.e("character", it) }
           firstCharacterName.text= uiState.characters[0]?.name.toString()
           secondCharacterName.text= uiState.characters[1]?.name.toString()
           thirdCharacterName.text= uiState.characters[2]?.name.toString()
       }
        val duration=System.currentTimeMillis()-operationStartTime
        binding.timeElapsedText.text=duration.toString()
    }

    private fun onError(){

    }
}

sealed class UiState {
    object Loading : UiState()
    data class Success(
        val characters: List<CharacterResponse?>
    ) : UiState()

    data class Error(val message: String) : UiState()
}