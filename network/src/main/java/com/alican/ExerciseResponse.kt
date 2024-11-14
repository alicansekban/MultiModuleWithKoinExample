package com.alican

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
@Serializable
data class ExerciseResponseItem(

	@SerialName("gifUrl")
	val gifUrl: String? = null,

	@SerialName("instructions")
	val instructions: List<String>? = null,

	@SerialName("secondaryMuscles")
	val secondaryMuscles: List<String>? = null,

	@SerialName("name")
	val name: String? = null,

	@SerialName("equipment")
	val equipment: String? = null,

	@SerialName("id")
	val id: String? = null,

	@SerialName("bodyPart")
	val bodyPart: String? = null,

	@SerialName("target")
	val target: String? = null
)
