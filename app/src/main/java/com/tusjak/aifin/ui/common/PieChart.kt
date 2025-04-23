package com.tusjak.aifin.ui.common

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.tusjak.aifin.data.Category
import com.tusjak.aifin.data.categories
import com.tusjak.aifin.theme.textColor
import com.tusjak.aifin.theme.value
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

data class PieChartSlice(
    val category: Category,
    val percentage: Float,
    val startAngle: Float,
    val sweepAngle: Float
)

fun createImageBitmapFromDrawable(context: Context, drawableResId: Int, width: Int, height: Int): ImageBitmap? {
    val drawable: Drawable? = ContextCompat.getDrawable(context, drawableResId)
    drawable?.let {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = android.graphics.Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap.asImageBitmap()
    }
    return null
}

@Composable
fun PieChartWithLegend(
    categories: List<Category>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val totalSum = categories.sumOf { it.transactionSum }
    val slices = mutableListOf<PieChartSlice>()
    var currentAngle = 0f

    categories.forEach { category ->
        val percentage = if (totalSum > 0) (category.transactionSum / totalSum * 100).toFloat() else 0f
        val sweepAngle = percentage * 360 / 100
        slices.add(PieChartSlice(category, percentage, currentAngle, sweepAngle))
        currentAngle += sweepAngle
    }

    val iconSize = 50
    val imageBitmaps = remember(categories) {
        categories.associate { category ->
            category.drawableRes to createImageBitmapFromDrawable(context, category.drawableRes, iconSize, iconSize)
        }
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Canvas(
            modifier = Modifier
                .size(200.dp)
                .padding(24.dp)
        ) {
            val canvasSize = min(size.width, size.height)
            val radius = canvasSize / 2
            val center = Offset(size.width / 2, size.height / 2)

            slices.forEach { slice ->
                drawArc(
                    color = slice.category.color,
                    startAngle = slice.startAngle,
                    sweepAngle = slice.sweepAngle,
                    useCenter = true,
                    topLeft = Offset(center.x - radius, center.y - radius),
                    size = Size(radius * 2, radius * 2)
                )

                drawArc(
                    color = textColor.value,
                    startAngle = slice.startAngle,
                    sweepAngle = slice.sweepAngle,
                    useCenter = true,
                    topLeft = Offset(center.x - radius, center.y - radius),
                    size = Size(radius * 2, radius * 2),
                    style = androidx.compose.ui.graphics.drawscope.Stroke(
                        width = 3f
                    )
                )
            }

            slices.forEach { slice ->
                if (slice.percentage >= 5f) {
                    val midAngle = slice.startAngle + slice.sweepAngle / 2
                    val midAngleRad = Math.toRadians(midAngle.toDouble())
                    val labelRadius = radius * 0.6f
                    val labelX = center.x + labelRadius * cos(midAngleRad).toFloat()
                    val labelY = center.y + labelRadius * sin(midAngleRad).toFloat()

                    imageBitmaps[slice.category.drawableRes]?.let { imageBitmap ->
                        drawImage(
                            image = imageBitmap,
                            topLeft = Offset(labelX - iconSize - 30, labelY - iconSize + 15),
                            alpha = 1f,
                            colorFilter = null
                        )
                    }

                    drawContext.canvas.nativeCanvas.drawText(
                        "${slice.percentage.toInt()}%",
                        labelX,
                        labelY,
                        Paint().apply {
                            color     = textColor.value.hashCode()
                            textSize  = 30f
                            textAlign = Paint.Align.CENTER
                            typeface  = Typeface.create("roboto_bold", Typeface.BOLD)
                        }
                    )
                }
            }

            slices.forEach { slice ->
                if (slice.percentage in 0f..5f && slice.percentage > 0f) {
                    val midAngle = slice.startAngle + slice.sweepAngle / 2
                    val midAngleRad = Math.toRadians(midAngle.toDouble())
                    val edgeRadius = radius * 0.99f
                    val lineEndRadius = radius * 1.2f
                    val startX = center.x + edgeRadius * cos(midAngleRad).toFloat()
                    val startY = center.y + edgeRadius * sin(midAngleRad).toFloat()
                    val endX = center.x + lineEndRadius * cos(midAngleRad).toFloat()
                    val endY = center.y + lineEndRadius * sin(midAngleRad).toFloat()
                    val labelX = center.x + lineEndRadius * cos(midAngleRad).toFloat()
                    val labelY = center.y + lineEndRadius * sin(midAngleRad).toFloat()

                    drawLine(
                        color = textColor.value,
                        start = Offset(startX, startY),
                        end = Offset(endX, endY),
                        strokeWidth = 2f
                    )

                    imageBitmaps[slice.category.drawableRes]?.let { imageBitmap ->
                        drawImage(
                            image = imageBitmap,
                            topLeft = Offset(labelX - iconSize / 2, labelY - iconSize + 10),
                            alpha = 1f,
                            colorFilter = null
                        )
                    }

                    drawContext.canvas.nativeCanvas.drawText(
                        "${slice.percentage.toInt()}%",
                            labelX + iconSize,
                            labelY,
                        Paint().apply {
                            color     = textColor.value.hashCode()
                            textSize  = 30f
                            textAlign = Paint.Align.CENTER
                            typeface  = Typeface.create("roboto_bold", Typeface.BOLD)
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PieChartPreview() {
    PieChartWithLegend(categories)
}