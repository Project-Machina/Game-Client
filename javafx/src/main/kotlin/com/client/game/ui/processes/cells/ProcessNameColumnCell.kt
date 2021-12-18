package com.client.game.ui.processes.cells

import com.client.game.model.processes.ProcessDataModel
import com.client.game.ui.processes.ProcessNameColumnFragment
import javafx.scene.control.ContentDisplay
import javafx.scene.control.TableCell
import tornadofx.rowItem

class ProcessNameColumnCell(val fragment: ProcessNameColumnFragment) : TableCell<ProcessDataModel, String>() {

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