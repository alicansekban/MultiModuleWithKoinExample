package com.alican.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alican.ExerciseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: ExerciseRepository
) : ViewModel() {

   private val _exercises = MutableStateFlow<List<String>>(emptyList())
    val exercises = _exercises.asStateFlow().onStart {
        getExercies()
    }

    private fun getExercies() {
        viewModelScope.launch {
            _exercises.emit(repository.getExerciseList(10))
        }
    }

}