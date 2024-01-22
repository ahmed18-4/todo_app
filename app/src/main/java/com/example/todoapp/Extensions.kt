package com.example.todoapp

import java.util.Calendar

fun Calendar.clearTime():Calendar{
    this.clear(Calendar.MINUTE)
    this.clear(Calendar.HOUR)
    this.clear(Calendar.MILLISECOND)
    this.clear(Calendar.SECOND)
    return this
}
fun android.icu.util.Calendar.clearTime(): android.icu.util.Calendar {
    this.clear(Calendar.MINUTE)
    this.clear(Calendar.HOUR)
    this.clear(Calendar.MILLISECOND)
    this.clear(Calendar.SECOND)
    return this
}