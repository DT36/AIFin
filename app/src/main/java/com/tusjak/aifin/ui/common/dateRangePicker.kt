package com.tusjak.aifin.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.tusjak.aifin.common.M
import com.tusjak.aifin.common.RS
import com.tusjak.aifin.common.string
import com.tusjak.aifin.theme.background
import com.tusjak.aifin.theme.button
import com.tusjak.aifin.theme.lightBlue
import com.tusjak.aifin.theme.oceanBlue
import com.tusjak.aifin.theme.stateDisabled
import com.tusjak.aifin.theme.textColor
import com.tusjak.aifin.theme.value
import com.tusjak.aifin.theme.vividBlue
import kotlinx.coroutines.launch
import java.time.format.TextStyle
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowDateRangePickerDialog(
    onDateRangeSelected: (Date, Date) -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val coroutineScope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState       = sheetState,
        containerColor   = background.value
    ) {
        val state         = rememberDateRangePickerState()
        val dateFormatter = DatePickerDefaults.dateFormatter()

        Column(modifier = M.fillMaxSize(), verticalArrangement = Arrangement.Top) {
            Row(
                modifier = M
                    .fillMaxWidth()
                    .padding(start = 12.dp, end = 12.dp),
                verticalAlignment     = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = {
                    coroutineScope.launch {
                        sheetState.hide()
                        onDismiss()
                    }
                }) {
                    Icon(Icons.Filled.Close, contentDescription = RS.close.string())
                }
                TextButton(
                    onClick = {
                        val startMillis = state.selectedStartDateMillis
                        val endMillis   = state.selectedEndDateMillis

                        if (startMillis != null && endMillis != null) {
                            val startDate = Date(startMillis)
                            val endDate   = Date(endMillis)

                            onDateRangeSelected(startDate, endDate)

                            coroutineScope.launch {
                                sheetState.hide()
                                onDismiss()
                            }
                        }
                    },
                    enabled = state.selectedEndDateMillis != null,
                    colors  = ButtonDefaults.buttonColors(containerColor = lightBlue)
                ) {
                    Text(
                        text  = RS.save.string(),
                        style = button,
                        color = if (state.selectedEndDateMillis != null) textColor.value else stateDisabled.value
                    )
                }
            }
            DateRangePicker(
                state    = state,
                modifier = M.weight(1f),
                title = {
                    Text(
                        text = RS.pick_dates.string(),
                        modifier = M.padding(horizontal = 32.dp),
                    )
                },
                headline = {
                    val startDateText = state.selectedStartDateMillis?.let { millis ->
                        dateFormatter.formatDate(millis, Locale.getDefault())
                    } ?: RS.from.string()
                    val endDateText = state.selectedEndDateMillis?.let { millis ->
                        dateFormatter.formatDate(millis, Locale.getDefault())
                    } ?: RS.to.string()

                    Text(
                        text = "$startDateText - $endDateText",
                        modifier = M.padding(horizontal = 32.dp),
                    )
                },
                colors   = DatePickerDefaults.colors(
                    containerColor                    = background.value,
                    selectedDayContentColor           = oceanBlue,
                    selectedDayContainerColor         = vividBlue,
                    dayInSelectionRangeContentColor   = vividBlue,
                    dayInSelectionRangeContainerColor = lightBlue,
                    dividerColor                      = lightBlue,
                    todayDateBorderColor              = lightBlue
                )
            )
        }
    }
}