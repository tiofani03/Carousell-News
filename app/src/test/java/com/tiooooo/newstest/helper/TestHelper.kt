package com.tiooooo.newstest.helper

import kotlin.math.abs
import kotlin.random.Random

fun randomLong(absolute: Boolean = false): Long {
    val value = Random.nextLong()
    return if (absolute) abs(value) else value
}

fun randomInt(absolute: Boolean = false): Int {
    val value = Random.nextInt()
    return if (absolute) abs(value) else value
}

fun randomString(length: Int = 10): String {
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}