package com.client.scripting

private const val LOWER = "abcdefghijklmnopqrstuvwxyz"
private const val UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
private const val DIGITS = "0123456789"
private val types = arrayOf(LOWER.toCharArray(), UPPER.toCharArray(), DIGITS.toCharArray())

fun generatePassword(length: Int = 16) : String {
    val builder = StringBuilder()
    repeat(length) {
        builder.append(types.random().random())
    }
    return builder.toString()
}