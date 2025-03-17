package com.example.myapplication.core.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.core.data.local.dao.JobDao
import com.example.myapplication.core.data.local.dao.PendingJobApplicationDao
import com.example.myapplication.core.data.local.entities.JobEntity
import com.example.myapplication.core.data.local.entities.PendingJobApplicationEntity

@Database(entities = [JobEntity::class, PendingJobApplicationEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun jobDao(): JobDao
    abstract fun pendingJobApplicationDao(): PendingJobApplicationDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "job_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
