package com.tusjak.aifin.data

import androidx.annotation.DrawableRes
import com.tusjak.aifin.common.D

data class Category(val name: String, @DrawableRes val drawableRes: Int)

val categories = listOf(
    Category("Food", D.ic_food),
    Category("Transport", D.ic_transport),
    Category("Medicine", D.ic_medicine),
    Category("Groceries", D.ic_groceries),
    Category("Rent", D.ic_rent),
    Category("Gifts", D.ic_gift),
    Category("Savings", D.ic_saving),
    Category("Entertainment", D.ic_entertainment),
    Category("Jewelry", D.ic_jewelry),
    Category("Salary", D.ic_salary),
    Category("Car", D.ic_car),
    Category("Other", D.ic_more)
)