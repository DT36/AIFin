package com.tusjak.aifin.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.tusjak.aifin.common.D
import com.tusjak.aifin.common.RS

data class Category(
    val id                      : Int,
    @StringRes val name         : Int,
    val type                    : TransactionType,
    @DrawableRes val drawableRes: Int
)

val categories = listOf(
    Category(id = 0, name = RS.food, type = TransactionType.EXPENSE, drawableRes = D.ic_food),
    Category(id = 1, name = RS.transport, type = TransactionType.EXPENSE, drawableRes = D.ic_transport),
    Category(id = 2, name = RS.medicine, type = TransactionType.EXPENSE, drawableRes = D.ic_medicine),
    Category(id = 3, name = RS.groceries, type = TransactionType.EXPENSE, drawableRes = D.ic_groceries),
    Category(id = 4, name = RS.rent, type = TransactionType.EXPENSE, drawableRes = D.ic_rent),
    Category(id = 5, name = RS.gift, type = TransactionType.EXPENSE, drawableRes = D.ic_gift),
    Category(id = 6, name = RS.entertainment, type = TransactionType.EXPENSE, drawableRes = D.ic_entertainment),
    Category(id = 7, name = RS.luxury, type = TransactionType.EXPENSE, drawableRes = D.ic_jewelry),
    Category(id = 8, name = RS.car, type = TransactionType.EXPENSE, drawableRes = D.ic_car),
    Category(id = 9, name = RS.other, type = TransactionType.EXPENSE, drawableRes = D.ic_more),
    Category(id = 10, name = RS.utilities, type = TransactionType.EXPENSE, drawableRes = D.ic_utilities),
    Category(id = 11, name = RS.insurance, type = TransactionType.EXPENSE, drawableRes = D.ic_insurance),
    Category(id = 12, name = RS.education, type = TransactionType.EXPENSE, drawableRes = D.ic_education),
    Category(id = 13, name = RS.health_fitness, type = TransactionType.EXPENSE, drawableRes = D.ic_fitness),
    Category(id = 14, name = RS.travel, type = TransactionType.EXPENSE, drawableRes = D.ic_travel),
    Category(id = 15, name = RS.hobbies, type = TransactionType.EXPENSE, drawableRes = D.ic_hobbies),
    Category(id = 16, name = RS.savings, type = TransactionType.INCOME, drawableRes = D.ic_saving),
    Category(id = 17, name = RS.salary, type = TransactionType.INCOME, drawableRes = D.ic_salary),
    Category(id = 18, name = RS.side_hustle, type = TransactionType.INCOME, drawableRes = D.ic_side_hustle),
    Category(id = 19, name = RS.investments, type = TransactionType.INCOME, drawableRes = D.ic_investment)
)