package de.ithock.idea.number_convert.utils

fun Char.isNumeric(): Boolean {
    return (
            Character.isDigit(this) ||
                    this.lowercaseChar() == 'x' ||
                    this.lowercaseChar() == 'b' ||
                    this.lowercaseChar() == 'o' ||
                    (this.lowercaseChar() in 'a'..'f')
            )
}
