package com.tusjak.aifin.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tusjak.aifin.common.M
import com.tusjak.aifin.common.RS
import com.tusjak.aifin.common.string
import com.tusjak.aifin.data.Category
import com.tusjak.aifin.data.Transaction
import com.tusjak.aifin.data.TransactionType
import com.tusjak.aifin.data.categories
import com.tusjak.aifin.theme.caption1
import com.tusjak.aifin.theme.textColor
import com.tusjak.aifin.theme.value
import com.tusjak.aifin.ui.common.CenteredColumn
import com.tusjak.aifin.ui.common.TwoColorBackgroundScreen
import kotlinx.coroutines.flow.StateFlow

@Composable
fun CategoriesScreen(transactions: StateFlow<List<Transaction>>, onCategoryClick: (Int) -> Unit) {
    val transactionList by transactions.collectAsState()
    val incomeSum  = transactionList.filter { it.type == TransactionType.INCOME }.sumOf { it.amount }
    val expenseSum = transactionList.filter { it.type == TransactionType.EXPENSE }.sumOf { it.amount }
    val totalSum   = incomeSum.minus(expenseSum)

    TwoColorBackgroundScreen(
        contentOnGreen = {
            BalanceOverview(totalSum, expenseSum, showGoal = false)
        },
        contentOnWhite = {
            if (transactionList.isEmpty()) {
                EmptyTransactionList(message = RS.empty_category_list.string())
            } else {
                CategoriesGrid(filterCategories(categories, transactionList), onCategoryClick)
            }
        }
    )
}

@Composable
fun CategoriesGrid(categories: List<Category>, onCategoryClick: (Int) -> Unit) {
    LazyVerticalGrid(
        modifier              = M.fillMaxWidth(),
        columns               = GridCells.Fixed(3),
        contentPadding        = PaddingValues(start = 16.dp, top = 32.dp, end = 16.dp, bottom = 100.dp),
        verticalArrangement   = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(categories.size) { index ->
            CategoryItem(category = categories.sortedBy { it.name }[index], onCategoryClick = onCategoryClick)
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

private fun filterCategories(categories: List<Category>, transactions: List<Transaction>): List<Category> {
    val categoryIdsWithTransactions = transactions.map { it.category }.toSet()
    return categories.filter { it.id in categoryIdsWithTransactions }
}