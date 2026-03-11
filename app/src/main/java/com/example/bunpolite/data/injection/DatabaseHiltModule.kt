package com.example.bunpolite.data.injection

import android.content.Context
import androidx.room.Room
import com.example.bunpolite.data.datalocal.LiteLocalDatabase
import com.example.bunpolite.data.datalocal.dao.MainLessonDao
import com.example.bunpolite.data.datalocal.dao.TestDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseHiltModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): LiteLocalDatabase {
        return Room.databaseBuilder(
            context,
            LiteLocalDatabase::class.java,
            "bunpo_lite_database"
        ).fallbackToDestructiveMigration(true).build()
    }

    @Provides
    fun provideMainLessonDao(database: LiteLocalDatabase): MainLessonDao = database.mainLessonDao()

    @Provides
    fun provideTestDao(database: LiteLocalDatabase): TestDao = database.testDao()

}