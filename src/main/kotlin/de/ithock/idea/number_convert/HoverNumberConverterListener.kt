package de.ithock.idea.number_convert

import com.intellij.codeInsight.hint.HintManager
import com.intellij.codeInsight.hints.InlayParameterHintsProvider
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.event.EditorMouseEvent
import com.intellij.openapi.editor.event.EditorMouseMotionListener
import com.intellij.openapi.fileEditor.FileDocumentManagerListener
import com.intellij.ui.awt.RelativePoint
import com.intellij.util.concurrency.annotations.RequiresEdt
import de.ithock.idea.number_convert.utils.*
import java.awt.Point

class HoverNumberConverterListener
    : EditorMouseMotionListener {
    private var lastTextSequenceHovered: String = ""
    override fun mouseMoved(event: EditorMouseEvent) {
        if (!HoverNumberConverterState.instance.isEnabled) {
            return
        }

        val editor = event.editor
        val offset = event.offset

        val number = getNumberAtCaret(editor, offset)
        if(number != null && number == lastTextSequenceHovered) {
            return
        }

        lastTextSequenceHovered = number ?: ""
        if (number != null) {
            createAndShowHint(editor, event, number)
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

    // Maybe in the future we can make these configurable, but I doubt it.
    private val showHex: Boolean
        get() = true
    private val showBinary: Boolean
        get() = true
    private val showOctal: Boolean
        get() = true

    private val displayTime: Int = 60000

    @RequiresEdt
    private fun createAndShowHint(editor: Editor, event: EditorMouseEvent, numberStr: String) {
        val number = numberStr.toNumber()

        var hint = "<h1>$number</h1>\n"
        if (showHex) {
            hint += "<b>Hex</b>: <code>${number.toHex()}</code><br/>"
        }
        if (showBinary) {
            hint += "<b>Binary</b>: <code>${number.toBinary()}</code><br/>"
        }
        if (showOctal) {
            hint += "<b>Octal</b>: <code>${number.toOctal()}</code><br/>"
        }

        val cursorAbsoluteLocation = event.mouseEvent.getPoint()
        val point = RelativePoint.fromScreen(
            Point(
                editor.contentComponent.locationOnScreen.x + cursorAbsoluteLocation.x,
                editor.component.locationOnScreen.y + cursorAbsoluteLocation.y - editor.scrollingModel.verticalScrollOffset
            )
        )

        showHint(
            """<html>
                <body>
                    $hint
                </body>
                </html>""".trimIndent(),
            point,
            HintManager.HIDE_BY_TEXT_CHANGE or HintManager.HIDE_BY_ESCAPE or HintManager.HIDE_BY_SCROLLING,
            displayTime
        )
    }
}