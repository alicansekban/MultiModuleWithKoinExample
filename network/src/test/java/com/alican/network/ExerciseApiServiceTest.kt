package com.alican.network

import com.alican.NetworkResult
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ExerciseApiServiceTest {

    private lateinit var fakeApiService: FakeExerciseApiService

    @Before
    fun setup() {
        fakeApiService = FakeExerciseApiService()
    }

    @Test
    fun `getExerciseList returns success`() = runBlocking {

        fakeApiService.shouldReturnError = false


        val result = fakeApiService.getExerciseList(2)


        assert(result is NetworkResult.Success)
        val exercises = (result as NetworkResult.Success).data
        assertEquals(2, exercises.size)
        assertEquals("Push-up", exercises[0].name)
        assertEquals("Squat", exercises[1].name)
    }

    @Test
    fun `getExerciseList returns error`() = runBlocking {

        fakeApiService.shouldReturnError = true


        val result = fakeApiService.getExerciseList(2)


        assert(result is NetworkResult.Error)
        val error = result as NetworkResult.Error
        assertEquals("Test error", error.message)
    }
}
