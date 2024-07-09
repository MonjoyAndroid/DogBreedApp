package com.monjoy.myimageapp.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.monjoy.myimageapp.room.entity.BreedDetail

@Dao
interface BreedDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(breedList: List<BreedDetail>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBreed(breedDetail :BreedDetail)

    @Delete
    suspend fun deleteBreed(breedDetail :BreedDetail)

    @Query("SELECT * FROM BreedDetail")
    suspend fun getBreeds(): List<BreedDetail>

    @Query("Select * from BreedDetail")
    fun getAllBreeds(): LiveData<List<BreedDetail>>

    @Update
    suspend fun updateBreed(breedDetail :BreedDetail)


}