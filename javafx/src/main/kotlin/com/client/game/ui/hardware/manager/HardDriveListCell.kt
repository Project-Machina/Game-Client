package com.client.game.ui.hardware.manager

import com.client.game.model.hardware.manager.HardDriveDataModel
import javafx.scene.control.ContentDisplay
import javafx.scene.control.ListCell

class HardDriveListCell(val fragment: HardDriveCellFragment) : ListCell<HardDriveDataModel>() {

    init {
        contentDisplay = ContentDisplay.GRAPHIC_ONLY
    }

    override fun updateItem(item: HardDriveDataModel?, empty: Boolean) {
        super.updateItem(item, empty)
        graphic = if(item != null && !empty) {
            fragment.bind(item)
            fragment.root
        } else {
            null
        }
    }
}