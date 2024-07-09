package com.monjoy.myimageapp.retrofit

import com.monjoy.myimageapp.retrofit.model.BreedImageResponse
import com.monjoy.myimageapp.retrofit.model.DogBreedsResponse
import com.monjoy.myimageapp.utils.Constant
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET(Constant.breedEnd)
    suspend fun getBreedList(): DogBreedsResponse


    @GET(Constant.breed +"{breed}/"+Constant.imgRandEnd)
    suspend fun getBreedPic(@Path("breed") breed: String): BreedImageResponse


}