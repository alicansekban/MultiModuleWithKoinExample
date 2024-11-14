package com.alican

class ExerciseRepository(
    private val apiService: ExerciseApiService
) {
    suspend fun getExerciseList(limit: Int): List<String>{
       return when (val response = apiService.getExerciseList(limit)) {
            is com.alican.ResultWrapper.GenericError -> {
                emptyList()
            }
            is com.alican.ResultWrapper.Success -> {
                response.value.map { it.name ?: "" }
            }
        }
    }
}