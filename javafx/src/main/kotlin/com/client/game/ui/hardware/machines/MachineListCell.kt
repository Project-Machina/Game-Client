package com.client.game.ui.hardware.machines

import com.client.game.model.hardware.machines.MachineDataModel
import javafx.scene.control.ContentDisplay
import javafx.scene.control.ListCell

class MachineListCell(val fragment: MachineListCellFragment) : ListCell<MachineDataModel>() {

    init {
        contentDisplay = ContentDisplay.GRAPHIC_ONLY
    }

    override fun updateItem(item: MachineDataModel?, empty: Boolean) {
        super.updateItem(item, empty)
        graphic = if(item != null && !empty) {
            fragment.bind(item)
            fragment.root
        } else {
            null
        }
    }
}