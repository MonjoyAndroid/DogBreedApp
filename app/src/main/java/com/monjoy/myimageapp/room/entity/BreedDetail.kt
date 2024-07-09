package com.monjoy.myimageapp.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "BreedDetail")
data class BreedDetail(
    @PrimaryKey(autoGenerate = true) val id : Long,
    @ColumnInfo(name = "Title") var breedName: String
)
