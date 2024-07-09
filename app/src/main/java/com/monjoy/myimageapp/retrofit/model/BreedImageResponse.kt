package com.monjoy.myimageapp.retrofit.model

import kotlinx.serialization.Serializable

@Serializable
data class BreedImageResponse(
    val message: String,
    val status: String
)
