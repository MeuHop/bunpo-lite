package com.example.bunpolite.data.repository

import com.example.bunpolite.data.datalocal.dao.TestDao
import com.example.bunpolite.data.dataremote.TestRemoteDataSource
import com.example.bunpolite.data.dataremote.externalmodel.TestNetwork
import com.example.bunpolite.data.dataremote.externalmodel.asEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TestRepository @Inject constructor(
    private val testRemoteDataSource: TestRemoteDataSource,
    private val testDao: TestDao
) {

    suspend fun refreshTestList() {
        testDao.clearAllTests()
        val tests = testRemoteDataSource.getTestList().map(TestNetwork::asEntity)
        testDao.insertAllTests(tests)
    }
}