package com.example.dnd.ui.screens

import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.CalendarContract
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.example.dnd.R
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AgendaScreen(context: Context) {
    val modifier = Modifier
        .fillMaxWidth()

    val items = listOf("Campaign 1", "Campaign 2", "Campaign 3")
    val startDates = listOf(LocalDateTime.of(2023, 10, 18, 10, 0), LocalDateTime.of(2023, 11, 24, 14, 30), LocalDateTime.of(2023, 12, 5,6, 5))
    val endDates = listOf(LocalDateTime.of(2023, 10, 18, 14, 0), LocalDateTime.of(2023, 11, 24, 17, 0), LocalDateTime.of(2023, 12, 5,11, 5))
    val allDates = getDateList(startDates, endDates)

    var campaign by remember { mutableStateOf(items[0]) }
    var startDate by remember { mutableStateOf(startDates[0]) }
    var endDate by remember { mutableStateOf(startDates[0]) }

    Box {
        Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top) {
            CustomDropdownMenu(
                items = items,
                label = R.string.campaign,
                onValueChanged = { campaign = it },
                value = campaign
            )

            Spacer(modifier = Modifier.height(15.dp))

            CustomDropdownMenu(
                items = allDates,
                label = R.string.session,
                onValueChanged = { startDate = startDates[it.toInt()]
                                    endDate = endDates[it.toInt()]},
                value = "$startDate : $endDate",
                returnInt = true
            )
        }

        Button(onClick = { exportToCalendar(startDate, endDate, campaign, context) },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(top = 170.dp)) {
            Text(text = "Export", color = MaterialTheme.colors.secondary)
        }
    }
}

fun getDateList(startDates: List<LocalDateTime>, endDates: List<LocalDateTime>): List<String> {
    val dateList = ArrayList<String>()

    startDates.forEachIndexed { i, _ -> dateList.add("${startDates[i]} : ${endDates[i]}") }

    return dateList
}

@RequiresApi(Build.VERSION_CODES.O)
fun exportToCalendar(startDate: LocalDateTime, endDate: LocalDateTime, campaign: String, context: Context) {
    val startDateAsDate: Date = Date.from(startDate.atZone(ZoneId.systemDefault()).toInstant())
    val endDateAsDate: Date = Date.from(endDate.atZone(ZoneId.systemDefault()).toInstant())
    val intent = Intent(Intent.ACTION_INSERT)

    intent.data = CalendarContract.Events.CONTENT_URI
    intent.putExtra(CalendarContract.Events.TITLE, campaign)
    intent.putExtra(CalendarContract.Events.DESCRIPTION, "Next session for $campaign")
    intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startDateAsDate.time)
    intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endDateAsDate.time)
    startActivity(context, intent, null)
}
