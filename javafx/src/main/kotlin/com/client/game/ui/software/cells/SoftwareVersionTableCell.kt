package com.client.game.ui.software.cells

import com.client.game.model.software.SoftwareDataModel
import com.client.game.ui.software.SoftwareVersionFragment
import javafx.scene.control.ContentDisplay
import javafx.scene.control.TableCell
import tornadofx.rowItem

class SoftwareVersionTableCell(val fragment: SoftwareVersionFragment) : TableCell<SoftwareDataModel, String>() {

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