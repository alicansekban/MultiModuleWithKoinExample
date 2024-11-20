package com.alican.multimodulewithkoin.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alican.ExerciseApiService
import com.alican.NetworkResult
import com.alican.di.AwesomeSdk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {

    private val repository: ExerciseApiService = AwesomeSdk.getInstance().get()

   private val _exercises = MutableStateFlow<List<String>>(emptyList())
    val exercises = _exercises.asStateFlow().onStart {
        getExercies()
    }

    private fun getExercies() {
        viewModelScope.launch {
            when (val response = repository.getExerciseList(100)) {
                is NetworkResult.GenericError -> {}
                is NetworkResult.Success -> {
                    val list = response.value.map { it.name ?: "" }
                    _exercises.emit(list)
                }

                NetworkResult.NetworkError -> {}
            }
        }
    }

}