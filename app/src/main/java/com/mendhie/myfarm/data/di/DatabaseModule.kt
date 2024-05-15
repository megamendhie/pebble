package com.mendhie.myfarm.data.di

import android.app.Application
import androidx.room.Room
import com.mendhie.myfarm.data.database.FarmerDao
import com.mendhie.myfarm.data.database.MyFarmDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun providesDatabase(app: Application): MyFarmDb {
        return Room.databaseBuilder(app, MyFarmDb::class.java, "myfarm.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providesMoviesDao(db: MyFarmDb): FarmerDao {
        return db.farmerDao()
    }
}