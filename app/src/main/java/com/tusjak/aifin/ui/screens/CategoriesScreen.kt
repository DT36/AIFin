package com.tusjak.aifin.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tusjak.aifin.R
import com.tusjak.aifin.common.D
import com.tusjak.aifin.common.M
import com.tusjak.aifin.common.RS
import com.tusjak.aifin.common.mutable
import com.tusjak.aifin.common.string
import com.tusjak.aifin.common.toEuroAmount
import com.tusjak.aifin.common.toFormattedDateYear
import com.tusjak.aifin.data.Category
import com.tusjak.aifin.data.Transaction
import com.tusjak.aifin.data.TransactionType
import com.tusjak.aifin.data.categories
import com.tusjak.aifin.theme.background
import com.tusjak.aifin.theme.body2
import com.tusjak.aifin.theme.caption1
import com.tusjak.aifin.theme.caribbeanGreen
import com.tusjak.aifin.theme.oceanBlue
import com.tusjak.aifin.theme.radius4
import com.tusjak.aifin.theme.spacedBy4
import com.tusjak.aifin.theme.textColor
import com.tusjak.aifin.theme.value
import com.tusjak.aifin.theme.vividBlue
import com.tusjak.aifin.ui.common.CenteredColumn
import com.tusjak.aifin.ui.common.CenteredRow
import com.tusjak.aifin.ui.common.PieChartWithLegend
import com.tusjak.aifin.ui.common.ShowDateRangePickerDialog
import com.tusjak.aifin.ui.common.TwoColorBackgroundScreen
import com.tusjak.aifin.ui.viewModels.CategoryViewModel
import kotlinx.coroutines.flow.StateFlow
import java.util.Date

@Composable
fun CategoriesScreen(transactions: StateFlow<List<Transaction>>, onCategoryClick: (Int) -> Unit) {
    val transactionList by transactions.collectAsState()

    val viewModel: CategoryViewModel = viewModel()
    val selectedTab by viewModel.selectedTransactionType
    var dateRange   by mutable<Pair<Date, Date>?>(null)

    val filteredTransactions = dateRange?.let { (start, end) ->
        transactionList.filter {
            it.date.time in start.time..end.time
        }
    } ?: transactionList

    val incomeSum          = filteredTransactions.filter { it.type == TransactionType.INCOME }.sumOf { it.amount }
    val expenseSum         = filteredTransactions.filter { it.type == TransactionType.EXPENSE }.sumOf { it.amount }
    val filteredCategories = processCategories(categories, filteredTransactions, selectedTab)

    TwoColorBackgroundScreen(
        contentOnGreen = {


            CenteredColumn {
                Row(M.fillMaxWidth().padding(horizontal = 32.dp, vertical = 16.dp), horizontalArrangement = Arrangement.spacedBy(32.dp)) {

                    CenteredColumn(
                        M
                            .weight(1f)
                            .clip(radius4)
                            .background(if (selectedTab == TransactionType.INCOME) vividBlue else background.value)
                            .clickable { viewModel.setSelectedTransactionType(TransactionType.INCOME) }
                            .padding(horizontal = 8.dp, vertical = 16.dp),
                        vertical = spacedBy4
                    ) {
                        Image(
                            modifier = M.size(20.dp),
                            painter = painterResource(R.drawable.income),
                            colorFilter = ColorFilter.tint(caribbeanGreen),
                            contentDescription = RS.income.string(),
                        )
                        Text(RS.income.string(), color = textColor.value, style = body2)
                        Text(
                            text = incomeSum.toEuroAmount(),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = textColor.value
                        )
                    }

                    CenteredColumn(
                        M
                            .weight(1f)
                            .clip(radius4)
                            .background(if (selectedTab == TransactionType.EXPENSE) vividBlue else background.value)
                            .clickable { viewModel.setSelectedTransactionType(TransactionType.EXPENSE) }
                            .padding(horizontal = 8.dp, vertical = 16.dp),
                        vertical = spacedBy4
                    ) {
                        Image(
                            modifier = M.size(20.dp),
                            painter = painterResource(R.drawable.expense),
                            colorFilter = ColorFilter.tint(oceanBlue),
                            contentDescription = RS.expenses.string(),
                        )
                        Text(RS.expenses.string(), color = textColor.value, style = body2)
                        Text(
                            text = expenseSum.toEuroAmount(),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = textColor.value
                        )
                    }
                }

                if (filteredCategories.isNotEmpty()) {
                    PieChartWithLegend(filteredCategories)
                }

                dateRange?.let {
                    Text(
                        modifier = M.padding(vertical = 8.dp),
                        text = stringResource(id = R.string.date_filter, it.first.toFormattedDateYear(), it.second.toFormattedDateYear()),
                        style = body2,
                        color = textColor.value
                    )
                }
            }
        },
        contentOnWhite = {
            var showDialog by remember { mutableStateOf(false) }

            Image(
                painter            = painterResource(id = D.ic_calendar),
                contentDescription = RS.select_date.string(),
                modifier           = Modifier
                    .align(Alignment.TopEnd)
                    .padding(24.dp)
                    .zIndex(1f)
                    .clickable { showDialog = true }
            )

            if (showDialog) {
                ShowDateRangePickerDialog(
                    onDateRangeSelected = { startDate, endDate ->
                        dateRange = startDate to endDate
                    },
                    onDismiss = { showDialog = false }
                )
            }

            if (transactionList.isEmpty()) {
                EmptyTransactionList(message = RS.empty_category_list.string())
            } else {
                CategoriesGrid(filteredCategories, onCategoryClick)
            }
        }
    )
}

@Composable
fun CategoriesGrid(categories: List<Category>, onCategoryClick: (Int) -> Unit) {
    LazyVerticalGrid(
        modifier              = M.fillMaxWidth(),
        columns               = GridCells.Fixed(4),
        contentPadding        = PaddingValues(start = 16.dp, top = 56.dp, end = 16.dp, bottom = 100.dp),
        verticalArrangement   = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(categories.size) { index ->
            CategoryItem(category = categories.sortedByDescending { it.name }[index], onCategoryClick = onCategoryClick)
        }
    }
}

@Composable
fun CategoryItem(category: Category, onCategoryClick: (Int) -> Unit) {
    CenteredColumn {
        Image(
            modifier           = M.size(80.dp).clickable { onCategoryClick(category.id) },
            painter            = painterResource(category.drawableRes),
            contentDescription = category.name.string(),
        )

        Text(
            text  = category.name.string(),
            color = textColor.value,
            style = caption1
        )
    }
}

private fun processCategories(
    categories  : List<Category>,
    transactions: List<Transaction>,
    selectedType: TransactionType
): List<Category> {
    val filteredTransactions = transactions.filter { it.type == selectedType }

    val sumsByCategory = filteredTransactions
        .groupBy { it.category }
        .mapValues { entry -> entry.value.sumOf { it.amount } }

    return categories
        .filter { it.id in sumsByCategory.keys }
        .map { category ->
            category.copy(
                transactionSum = sumsByCategory[category.id] ?: 0.0
            )
        }.sortedBy { it.transactionSum }
}