package com.valhallatech.civibridge.core.data.local.dao

import androidx.room.*
import com.valhallatech.civibridge.core.data.local.entities.JobEntity

@Dao
interface JobDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJob(job: JobEntity)

    @Query("SELECT * FROM jobs ORDER BY id DESC")
    suspend fun getAllJobs(): List<JobEntity>

    @Delete
    suspend fun deleteJob(job: JobEntity)
}
