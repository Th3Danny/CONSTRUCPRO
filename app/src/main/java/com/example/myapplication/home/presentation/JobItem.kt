import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myapplication.home.data.model.Job
@Composable
fun JobItem(job: Job) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = job.title, style = MaterialTheme.typography.titleMedium, color = Color.White)
            Text(text = job.description, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
            Text(text = "Ubicación: ${job.location}", style = MaterialTheme.typography.labelSmall, color = Color.White)
            Text(text = "Salario: $${job.salary}", style = MaterialTheme.typography.bodyMedium, color = Color(0xFFFF9800))
        }
    }
}

