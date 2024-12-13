package com.geisyanne.goapp.util

import android.view.View
import java.text.SimpleDateFormat
import java.util.Locale

fun View.setDebouncedOnClickListener(debounceTime: Long = 1000L, onClick: (View) -> Unit) {
    this.setOnClickListener {
        this.isEnabled = false
        onClick(this)
        this.postDelayed({
            this.isEnabled = true
        }, debounceTime)
    }
}

fun String.formatDate(): String {
    return try {
        val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val targetFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("pt", "BR"))
        val date = originalFormat.parse(this)
        date?.let { targetFormat.format(it) } ?: "Data inválida"
    } catch (e: Exception) {
        "Data inválida"
    }
}

fun Double.formatToBRL(): String {
    return "R$ %.2f".format(this)
}