package de.ithock.idea.number_convert.utils

fun String.isValidNumber(): Boolean {
    return when {
        this.isValidHexNumber() -> true
        this.isValidBinaryNumber() -> true
        this.isValidOctalNumber() -> true
        this.isValidDecimalNumber() -> true
        else -> false
    }
}

fun String.isValidHexNumber(): Boolean {
    return this.matches("0[xX][0-9a-fA-F_]+".toRegex())
}

fun String.isValidBinaryNumber(): Boolean {
    return this.matches("0[bB][01_]+".toRegex())
}

fun String.isValidOctalNumber(): Boolean {
    return this.matches("0[oO][0-7_]+".toRegex())
}

fun String.isValidDecimalNumber(): Boolean {
    return this.matches("[0-9_]+".toRegex())
}


fun String.fromHex(): Int {
    val matches = "0[xX]([0-9a-fA-F_]+)".toRegex().find(this)
    return matches?.groupValues?.get(1)?.replace("_", "")?.toInt(16) ?: 0
}

fun String.fromBinary(): Int {
    val matches = "0[bB]([01_]+)".toRegex().find(this)
    return matches?.groupValues?.get(1)?.replace("_", "")?.toInt(2) ?: 0
}

fun String.fromOctal(): Int {
    val matches = "0[oO]([0-7_]+)".toRegex().find(this)
    return matches?.groupValues?.get(1)?.replace("_", "")?.toInt(8) ?: 0
}

fun String.fromDecimal(): Int {
    val matches = "[0-9_]+".toRegex().find(this)
    return matches?.groupValues?.get(0)?.replace("_", "")?.toInt() ?: 0
}

fun String.toNumber(): Int {
    if (this.isValidHexNumber()) {
        return this.fromHex()
    }
    if (this.isValidBinaryNumber()) {
        return this.fromBinary()
    }
    if (this.isValidOctalNumber()) {
        return this.fromOctal()
    }
    if (this.isValidDecimalNumber()) {
        return this.fromDecimal()
    }
    return 0
}