package com.valhallatech.civibridge.core.data.local.dao

import androidx.room.*
import com.valhallatech.civibridge.core.data.local.entities.PendingJobApplicationEntity

@Dao
interface PendingJobApplicationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPendingApplication(application: PendingJobApplicationEntity)

    @Query("SELECT * FROM pending_job_applications")
    suspend fun getAllPendingApplications(): List<PendingJobApplicationEntity>

    @Query("DELETE FROM pending_job_applications WHERE id = :id")
    suspend fun deletePendingApplication(id: Int)
}
