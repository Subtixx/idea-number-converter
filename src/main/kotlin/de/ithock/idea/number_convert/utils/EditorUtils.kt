package de.ithock.idea.number_convert.utils

import com.intellij.codeInsight.hint.HintManager
import com.intellij.codeInsight.hint.HintUtil;
import com.intellij.openapi.editor.Editor
import com.intellij.ui.awt.RelativePoint
import java.awt.Point
import javax.swing.JLabel

const val NO_ITEMS_HINT_TIMEOUT_MS = 2000

fun Editor.getHintPos(mouseLocation: Point = Point(0, 0)): RelativePoint {
    return RelativePoint(this.component, mouseLocation)
}

fun showErrorHint(title: String, relativePoint: RelativePoint, flags: Int = 0, displayTime: Int = NO_ITEMS_HINT_TIMEOUT_MS) {
    val label = JLabel(title)
    label.border = HintUtil.createHintBorder()
    label.background = HintUtil.getErrorColor()
    label.isOpaque = true
    HintManager.getInstance().showHint(label, relativePoint, flags,  displayTime)
}

fun showHint(title: String, relativePoint: RelativePoint, flags: Int = 0, displayTime: Int = NO_ITEMS_HINT_TIMEOUT_MS) {
    val label = JLabel(title)
    label.border = HintUtil.createHintBorder()
    label.background = HintUtil.getInformationColor()
    label.isOpaque = true

    HintManager.getInstance().showHint(label, relativePoint, flags,  displayTime)
}