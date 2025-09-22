package com.example.dailyplanet.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer

@Composable
fun ShimmerList() {
    // The shimmer modifier wraps the entire list
    LazyColumn(modifier = Modifier.shimmer()) {
        items(7) { // Display 7 placeholder cards
            ShimmerNewsCard()
        }
    }
}

@Composable
private fun ShimmerNewsCard() {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Image Placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(Color.Gray)
            )
            // Text Placeholders
            Column(modifier = Modifier.padding(16.dp)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(fraction = 0.4f)
                        .height(20.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Gray)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth(fraction = 0.9f)
                        .height(20.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Gray)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth(fraction = 0.7f)
                        .height(20.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Gray)
                )
            }
        }
    }
}