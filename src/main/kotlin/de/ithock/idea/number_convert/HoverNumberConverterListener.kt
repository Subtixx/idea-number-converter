package de.ithock.idea.number_convert

import com.intellij.codeInsight.hint.HintManager
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.event.EditorMouseEvent
import com.intellij.openapi.editor.event.EditorMouseMotionListener
import com.intellij.ui.awt.RelativePoint
import de.ithock.idea.number_convert.utils.*
import java.awt.Point
import javax.swing.BorderFactory
import javax.swing.JLabel


class HoverNumberConverterListener

    : EditorMouseMotionListener {
    override fun mouseMoved(event: EditorMouseEvent) {
        if (!HoverNumberConverterState.instance.isEnabled) {
            return
        }

        val editor = event.editor
        val offset = event.offset

        val number = getNumberAtCaret(editor, offset)
        if (number != null) {
            createAndShowHint(event.mouseEvent.point, number)
        } else {
            HintManager.getInstance().hideAllHints()
        }
    }

    private fun getNumberAtCaret(editor: Editor, offset: Int): String? {
        val text = editor.document.charsSequence
        var start = offset
        var end = offset

        if (start == 0 || end == text.length || !text[start - 1].isNumeric()) {
            return null
        }

        // Iterate backwards to find the start of the number
        while (start > 0 && (text[start - 1].isNumeric())) {
            start--
        }

        // Iterate forwards to find the end of the number
        while (end < text.length && text[end].isNumeric()) {
            end++
        }

        val number = text.subSequence(start, end).toString()
        return if (number.isValidNumber()) number else null
    }

    private val showHex: Boolean
        get() = true
    private val showBinary: Boolean
        get() = true
    private val showOctal: Boolean
        get() = true

    private val offset: Point = Point(20, -35)
    private val displayTime:Int = 60000

    private fun createAndShowHint(point: Point, numberStr: String) {
        val number = numberStr.toNumber()

        var hint = "<h1>$number</h1>\n"
        if (showHex) {
            hint += "<b>Hex</b>: <pre>${number.toHex()}</pre><br/>"
        }
        if (showBinary) {
            hint += "<b>Binary</b>: <pre>${number.toBinary()}</pre><br/>"
        }
        if (showOctal) {
            hint += "<b>Octal</b>: <pre>${number.toOctal()}</pre><br/>"
        }

        val component = JLabel(
            """<html>
                <body>
                    $hint
                </body>
                </html>""".trimIndent()
        ).apply {
            verticalAlignment = JLabel.CENTER
            horizontalAlignment = JLabel.CENTER
            border = BorderFactory.createEmptyBorder(10, 10, 10, 10)
        }
        point.translate(offset.x, offset.y)

        HintManager.getInstance()
            .showHint(component, RelativePoint(point), HintManager.HIDE_BY_OTHER_HINT, displayTime)
    }
}