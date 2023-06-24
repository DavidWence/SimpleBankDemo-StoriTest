package com.example.simplebankingapp_storitest.presentation.utils

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

object NumberExtensions {
    val currencyFormat = DecimalFormat(
        "$#,###,##0.00",
        DecimalFormatSymbols.getInstance(Locale.ENGLISH))
}

fun Double.asCurrency(showMinusSymbol: Boolean = true): String =
    NumberExtensions.currencyFormat.format(this).replace(if (showMinusSymbol) "" else "-", "")