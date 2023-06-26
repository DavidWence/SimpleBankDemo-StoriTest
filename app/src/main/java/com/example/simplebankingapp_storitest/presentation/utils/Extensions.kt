package com.example.simplebankingapp_storitest.presentation.utils

import android.annotation.SuppressLint
import android.content.Context
import java.io.File
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object NumberExtensions {
    val currencyFormat = DecimalFormat(
        "$#,###,##0.00",
        DecimalFormatSymbols.getInstance(Locale.ENGLISH))
}

fun Double.asCurrency(showMinusSymbol: Boolean = true): String =
    NumberExtensions.currencyFormat.format(this).replace(if (showMinusSymbol) "" else "-", "")

@SuppressLint("SimpleDateFormat")
fun Context.createImageFile(): File {
    // Create an image file name
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    return File.createTempFile(imageFileName, ".jpg", externalCacheDir)
}