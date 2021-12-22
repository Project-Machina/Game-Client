package com.client.game.ui.logs.cells

import com.client.game.model.logs.LogDataModel
import com.client.game.ui.logs.LogActionsFragment
import javafx.scene.control.ContentDisplay
import javafx.scene.control.TableCell
import tornadofx.rowItem

class LogActionsTableCell(val frag: LogActionsFragment) : TableCell<LogDataModel, String>() {

    init {
        contentDisplay = ContentDisplay.GRAPHIC_ONLY
    }

    override fun updateItem(item: String?, empty: Boolean) {
        super.updateItem(item, empty)
        graphic = if(item != null && !empty) {
            frag.bind(rowItem)
            frag.root
        } else null
    }
}