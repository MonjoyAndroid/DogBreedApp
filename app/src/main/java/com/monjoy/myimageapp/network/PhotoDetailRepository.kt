package com.monjoy.myimageapp.network

import android.util.Log
import com.monjoy.myimageapp.retrofit.ApiService
import com.monjoy.myimageapp.room.dao.BreedDetailDao
import com.monjoy.myimageapp.room.entity.BreedDetail


class PhotoDetailRepository(private val apiService: ApiService, private val breedDetailDao: BreedDetailDao) {

    suspend fun fetchBreeds() {
        try {
            val response = apiService.getBreedList()
            if (response.status == "success") {
                val breedList = response.message.keys.toList()
                if(breedList.isNotEmpty()){
                    for(breed in breedList){
                        val breedDetails = BreedDetail(0,breed)
                        breedDetailDao.insertBreed(breedDetails)
                    }

                }

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }


    }

    suspend fun getBreedsFromDb(): List<BreedDetail> {
        return breedDetailDao.getBreeds()
    }



    suspend fun fetchBreedDetail(breedName:String): String? {
        return try {
            val response = apiService.getBreedPic(breedName)
            if (response.status == "success") {
                response.message
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


}

