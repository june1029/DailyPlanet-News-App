package com.example.dailyplanet.ui.composables

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image // <-- Make sure Image is imported
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.dailyplanet.R

@Composable
fun AboutDialog(onDismiss: () -> Unit) {
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.surface,
        title = { Text("About DailyPlanet", color = MaterialTheme.colorScheme.onSurface) },
        text = {
            Column {
                Text("Version 1.0", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
                Spacer(modifier = Modifier.height(16.dp))

                Text("Developed by Mohammed Hamil P R", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    // GitHub Button
                    TextButton(onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/hamilwt")) // Replace with your GitHub URL
                        context.startActivity(intent)
                    }) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            // THIS IS THE FIX: Changed from Icon to Image
                            Image(
                                painter = painterResource(id = R.drawable.ic_github_logo),
                                contentDescription = "GitHub Logo",
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("GitHub", color = MaterialTheme.colorScheme.secondary)
                        }
                    }

                    // LinkedIn Button
                    TextButton(onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/mohammed-hamil-pr/")) // Replace with your LinkedIn URL
                        context.startActivity(intent)
                    }) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            // THIS IS THE FIX: Changed from Icon to Image
                            Image(
                                painter = painterResource(id = R.drawable.ic_linkedin_logo),
                                contentDescription = "LinkedIn Logo",
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("LinkedIn", color = MaterialTheme.colorScheme.secondary)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text("Powered by NewsAPI.org", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        },
        confirmButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text("OK")
            }
        }
    )
}