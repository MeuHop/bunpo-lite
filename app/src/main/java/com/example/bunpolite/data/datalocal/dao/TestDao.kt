package com.example.bunpolite.data.datalocal.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bunpolite.data.datalocal.entities.TestEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TestDao {

    @Query("SELECT * FROM Test ORDER BY idx ASC")
    fun getAllTests(): Flow<List<TestEntity>>

    @Query("SELECT * FROM Test WHERE id = :id")
    suspend fun getTestById(id: String): TestEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTests(tests: List<TestEntity>)

    @Query("DELETE FROM Test")
    suspend fun clearAllTests()

}