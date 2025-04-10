package com.siro.mystrava.presentation.screens.home.widgets

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.foundation.layout.Arrangement
import com.siro.mystrava.core.utils.*
import com.siro.mystrava.data.models.activites.ActivityItem

@SuppressLint("DefaultLocale")
@Composable
fun Workout(
    activity: ActivityItem,
    onActivityClick: (ActivityItem) -> Unit = {},
) {
    val distance = activity.distance / 1000
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onActivityClick(activity) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF121212))
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = activity.name,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = formatActivityDate(activity.start_date_local),
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = " • Nairobi County",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                }
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More options",
                        tint = Color.White
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Distance",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "${"%.1f".format(distance)} km",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                if (activity.type == "Run") {
                    Column {
                        Text(
                            text = "Pace",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = calculatePace(activity.moving_time, distance),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                } else {
                    Column {
                        Text(
                            text = "Elev Gain",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )

                        Text(
                            text = "${activity.total_elevation_gain} m",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }

                Column() {
                    Text(
                        text = "Time",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = formatElapsedTime(activity.moving_time),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                if (activity.achievement_count != 0) {
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "Achievements",
                            fontSize = 12.sp,
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
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                } else {
                    Box(modifier = Modifier.size(48.dp))
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Outlined.ThumbUp,
                            contentDescription = "Like",
                            tint = Color.White
                        )
                    }
                    Text(
                        text = "${activity.kudos_count} kudos",
                        color = Color.Gray,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Outlined.Email,
                            contentDescription = "Comment",
                            tint = Color.White
                        )
                    }
                    Text(
                        text = "${activity.comment_count} comments",
                        color = Color.Gray,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}