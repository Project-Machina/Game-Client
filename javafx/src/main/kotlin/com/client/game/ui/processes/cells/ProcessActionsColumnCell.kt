package com.client.game.ui.processes.cells

import com.client.game.model.processes.ProcessDataModel
import com.client.game.ui.processes.ProcessActionsColumnFragment
import javafx.scene.control.ContentDisplay
import javafx.scene.control.TableCell
import tornadofx.rowItem

class ProcessActionsColumnCell(val fragment: ProcessActionsColumnFragment) : TableCell<ProcessDataModel, String>() {

    init {
        contentDisplay = ContentDisplay.GRAPHIC_ONLY
    }

    override fun updateItem(item: String?, empty: Boolean) {
        super.updateItem(item, empty)
        graphic = if(item != null && !empty) {
            fragment.bind(rowItem)
            fragment.root
        } else null
    }
}