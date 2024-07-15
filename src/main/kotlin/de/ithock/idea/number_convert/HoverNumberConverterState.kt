package de.ithock.idea.number_convert

import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


class HoverNumberConverterState
private constructor() {
    var isEnabled: Boolean = false

    companion object {
        @get:Synchronized
        val instance: HoverNumberConverterState = HoverNumberConverterState()
    }
}
