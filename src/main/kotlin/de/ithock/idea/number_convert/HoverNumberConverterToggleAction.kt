package de.ithock.idea.number_convert

import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.ToggleAction

class HoverNumberConverterToggleAction
    : ToggleAction() {
    override fun isSelected(e: AnActionEvent): Boolean {
        return HoverNumberConverterState.instance.isEnabled
    }

    override fun setSelected(e: AnActionEvent, state: Boolean) {
        HoverNumberConverterState.instance.isEnabled = state
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }
}
