package com.alican.network

import com.alican.ExerciseApiServiceImp
import com.alican.ExerciseResponseItem
import com.alican.NetworkResult


class FakeExerciseApiService : ExerciseApiServiceImp {

    var shouldReturnError = false

    override suspend fun getExerciseList(limit: Int): NetworkResult<List<ExerciseResponseItem>> {
        return if (shouldReturnError) {
            NetworkResult.Error(message = "Test error")
        } else {
            NetworkResult.Success(
                listOf(
                    ExerciseResponseItem(
                        id = "1",
                        name = "Push-up",
                        bodyPart = "Chest",
                        equipment = "Body weight",
                        target = "Pectorals",
                        gifUrl = "https://example.com/pushup.gif"
                    ),
                    ExerciseResponseItem(
                        id = "2",
                        name = "Squat",
                        bodyPart = "Legs",
                        equipment = "Body weight",
                        target = "Quadriceps",
                        gifUrl = "https://example.com/squat.gif"
                    )
                )
            )
        }
    }
}
