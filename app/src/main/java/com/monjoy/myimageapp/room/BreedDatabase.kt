package com.monjoy.myimageapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.monjoy.myimageapp.room.dao.BreedDetailDao
import com.monjoy.myimageapp.room.entity.BreedDetail
import com.monjoy.myimageapp.utils.Constant

@Database(entities = [BreedDetail::class], version = Constant.DATABASE_VERSION, exportSchema = false)
abstract class BreedDatabase: RoomDatabase() {

    abstract fun mPhotoDetailDao(): BreedDetailDao

    companion object {
        @Volatile
        private var database: BreedDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): BreedDatabase? {
            if (database == null) {
                synchronized(BreedDatabase::class.java) {
                    if (database == null) {
                        database = Room.databaseBuilder(context.applicationContext, BreedDatabase::class.java, "Breed_DB")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return database
        }
    }
}