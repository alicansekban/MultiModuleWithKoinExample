package com.alican

class ExerciseRepository(
    private val apiService: ExerciseApiService
) {
    //private val apiService = ExampleManuelDependencyInjection.exerciseApiService

    suspend fun getExerciseList(limit: Int): List<String>{
       return when (val response = apiService.getExerciseList(limit)) {
            is NetworkResult.GenericError -> {
                emptyList()
            }
            is NetworkResult.Success -> {
                response.value.map { it.name ?: "" }
            }
        }
    }
}