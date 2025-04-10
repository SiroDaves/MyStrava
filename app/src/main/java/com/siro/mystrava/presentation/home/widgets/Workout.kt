package com.siro.mystrava.presentation.home.widgets

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.IconButton
import com.siro.mystrava.core.utils.calculatePace
import com.siro.mystrava.core.utils.formatElapsedTime
import com.siro.mystrava.data.models.activites.ActivityItem
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("DefaultLocale")
@Composable
fun Workout(
    activity: ActivityItem,
    onActivityClick: (ActivityItem) -> Unit = {},
) {
    Card(
        modifier = Modifier.fillMaxWidth()
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

                    val dateFormat =
                        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
                    val date = try {
                        dateFormat.parse(activity.start_date_local)
                    } catch (e: Exception) {
                        null
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "${
                                SimpleDateFormat(
                                    "MMMM d, yyyy 'at' h:mm a ",
                                    Locale.getDefault()
                                ).format(
                                    date ?: Date()
                                )
                            }",
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
                val distance = "%.1f".format(activity.distance / 1000)
                Column {
                    Text(
                        text = "Distance",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "$distance km",
                        fontSize = 20.sp,
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
                            text = calculatePace(activity.moving_time, activity.distance),
                            fontSize = 20.sp,
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
                            fontSize = 20.sp,
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
                        text = formatElapsedTime(activity.elapsed_time),
                        fontSize = 20.sp,
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
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier.size(48.dp)
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
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

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
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

            //HorizontalDivider(thickness = 2.dp)
        }
    }
}