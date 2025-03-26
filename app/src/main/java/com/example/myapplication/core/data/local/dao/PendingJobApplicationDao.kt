package com.example.myapplication.core.data.local.dao

import androidx.room.*
import com.example.myapplication.core.data.local.entities.PendingJobApplicationEntity

@Dao
interface PendingJobApplicationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPendingApplication(application: PendingJobApplicationEntity)

    @Query("SELECT * FROM pending_job_applications")
    suspend fun getAllPendingApplications(): List<PendingJobApplicationEntity>

    @Query("DELETE FROM pending_job_applications WHERE id = :id")
    suspend fun deletePendingApplication(id: Int)
}
