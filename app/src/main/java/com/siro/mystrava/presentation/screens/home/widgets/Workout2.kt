package com.siro.mystrava.presentation.screens.home.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.IconButton
import com.siro.mystrava.data.models.activites.ActivityItem
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun Workout2(activity: ActivityItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF121212))
            .padding(16.dp)
    ) {
        // Header section
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Profile photo would go here
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Color.Gray, shape = RoundedCornerShape(24.dp))
            )

            // Name and time
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp)
            ) {
                Text(
                    text = "K Muriuki",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.White
                )

                val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
                val date = try {
                    dateFormat.parse(activity.start_date_local)
                } catch (e: Exception) {
                    null
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Today at ${SimpleDateFormat("h:mm a", Locale.getDefault()).format(date ?: Date())} • Nairobi County",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }

            // Menu icon
            IconButton(onClick = { /* Menu options */ }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More options",
                    tint = Color.White
                )
            }
        }

        // Workout title
        Text(
            text = activity.name,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
        )

        // Workout stats
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Distance column
            Column {
                Text(
                    text = "Distance",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Text(
                    text = "${activity.distance} km",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            // Pace column
            Column {
                Text(
                    text = "Pace",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                // Calculate pace from distance and time
                val paceMinutes = activity.moving_time / 60 / activity.distance
                val paceSeconds = ((activity.moving_time / 60 / activity.distance) - paceMinutes) * 60
                Text(
                    text = "${paceMinutes.toInt()}:${String.format("%02d", paceSeconds.toInt())} /km",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            // Achievements column
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "Achievements",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Gold medal",
                        tint = Color(0xFFFFD700),
                        modifier = Modifier.size(24.dp)
                    )
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Silver medal",
                        tint = Color(0xFFC0C0C0),
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "${activity.achievement_count}",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }

        // Achievement card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF1E1E1E)
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Silver medal",
                    tint = Color(0xFFC0C0C0),
                    modifier = Modifier.size(36.dp)
                )
                Text(
                    text = "This was K's 2nd fastest time in the 5K!",
                    color = Color.White,
                    modifier = Modifier.padding(start = 16.dp),
                    fontSize = 16.sp
                )
            }
        }

        // Image would go here - placeholder
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color(0xFF2A2A2A), RoundedCornerShape(8.dp))
        )

        // Kudos section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatars would go here
            Row {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(Color.Gray, shape = RoundedCornerShape(12.dp))
                )
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .offset(x = (-8).dp)
                        .background(Color.DarkGray, shape = RoundedCornerShape(12.dp))
                )
            }

            Text(
                text = "${activity.kudos_count} gave kudos",
                color = Color.Gray,
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        // Action buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Like button
            IconButton(onClick = { /* Like action */ }) {
                Icon(
                    imageVector = Icons.Outlined.ThumbUp,
                    contentDescription = "Like",
                    tint = Color.White
                )
            }

            // Comment button
            IconButton(onClick = { /* Comment action */ }) {
                Icon(
                    imageVector = Icons.Outlined.Email,
                    contentDescription = "Comment",
                    tint = Color.White
                )
            }

            // Share button
            IconButton(onClick = { /* Share action */ }) {
                Icon(
                    imageVector = Icons.Outlined.Share,
                    contentDescription = "Share",
                    tint = Color.White
                )
            }
        }
    }
}