package com.tusjak.aifin.ui.common

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tusjak.aifin.common.D
import com.tusjak.aifin.common.M
import com.tusjak.aifin.common.RS
import com.tusjak.aifin.common.mutable
import com.tusjak.aifin.common.string
import com.tusjak.aifin.common.toFormattedDate
import com.tusjak.aifin.common.toFormattedDateYear
import com.tusjak.aifin.theme.body1
import com.tusjak.aifin.theme.body2Bold
import com.tusjak.aifin.theme.lightGreen
import com.tusjak.aifin.theme.radius5
import com.tusjak.aifin.theme.textColor
import com.tusjak.aifin.theme.value
import ui.common.AfText
import java.util.Calendar
import java.util.Date

@Composable
fun AfDatePicker(date: Date = Date(), onDateSelected: (Date) -> Unit) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val selectedDate = mutable(date.toFormattedDateYear())

    Column(
        modifier = M
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        AfText(
            text = RS.date.string(),
            maxLines = 1,
            color = textColor.value,
            style = body2Bold
        )

        Box(
            modifier = M
                .fillMaxWidth()
                .clickable {
                    showDatePickerDialog(
                        context = context,
                        calendar = calendar,
                        selectedDate = selectedDate,
                        onDateSelected = { date ->
                            onDateSelected(date)
                        }
                    )
                }
        ) {
            OutlinedTextField(
                value         = selectedDate.value.ifEmpty { calendar.toFormattedDate() },
                onValueChange = { },
                modifier      = M
                    .fillMaxWidth()
                    .background(color = lightGreen, shape = radius5),
                trailingIcon  = {
                    Image(
                        painter = painterResource(id = D.ic_calendar),
                        contentDescription = RS.select_date.string(),
                        modifier = M.padding(end = 8.dp)
                    )
                },
                readOnly = true,
                enabled  = false,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor   = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedLabelColor    = Color.Black,
                    unfocusedLabelColor  = Color.Gray,
                    disabledBorderColor  = Color.Transparent,
                    disabledTextColor    = Color.Black
                ),
                textStyle = body1
            )
        }
    }
}

fun showDatePickerDialog(
    context: Context,
    calendar: Calendar,
    selectedDate: MutableState<String>? = null,
    onDateSelected: (Date) -> Unit
) {
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
            calendar.set(selectedYear, selectedMonth, selectedDay)
            selectedDate?.value = calendar.toFormattedDate()
            onDateSelected(calendar.time)
        },
        year,
        month,
        day
    )
    datePickerDialog.show()

    datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE)
        ?.setTextColor(android.graphics.Color.parseColor("#FF0068FF"))
    datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE)
        ?.setTextColor(android.graphics.Color.parseColor("#FF0068FF"))
}


@Preview
@Composable
private fun AfDatePickerPreview() {
    AfDatePicker { }
}