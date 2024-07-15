package de.ithock.idea.number_convert.utils

fun Int.toHex(): String {
    return Integer.toHexString(this)
}

fun Int.toBinary(): String {
    return Integer.toBinaryString(this)
}

fun Int.toOctal(): String {
    return Integer.toOctalString(this)
}

fun Int.toDecimal(): String {
    return this.toString()
}

fun Int.isValidNumber(): Boolean {
    return this >= 0
}

fun Int.isNegative(): Boolean {
    return this < 0
}
