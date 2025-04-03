package com.valhallatech.civibridge.core.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.valhallatech.civibridge.job.presentation.JobViewModel


class JobUpdateReceiver(private val viewModel: JobViewModel) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val type = intent?.getStringExtra("job_update_type")
        android.util.Log.d("📡 JobUpdateReceiver", " Recibido tipo: $type")

        when (type) {
            "NEW_JOB" -> viewModel.refreshJobs()
            "JOB_APPLIED" -> viewModel.refreshPendingJobs()
            "JOB_ACCEPTED" -> viewModel.refreshAcceptedJobs()
            "JOB_Apply" -> viewModel.refreshApplyJobs()
            "REFRESH_ALL" -> {
                viewModel.refreshJobs()
                viewModel.refreshPendingJobs()
                viewModel.refreshAcceptedJobs()
            }
        }

    }

}
