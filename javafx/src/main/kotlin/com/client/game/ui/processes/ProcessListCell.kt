package com.client.game.ui.processes

import com.client.game.model.processes.ProcessDataModel
import javafx.scene.control.ContentDisplay
import javafx.scene.control.ListCell

class ProcessListCell(val content: ProcessContentFragment) : ListCell<ProcessDataModel>() {

    init {
        contentDisplay = ContentDisplay.GRAPHIC_ONLY
    }

    override fun updateItem(item: ProcessDataModel?, empty: Boolean) {
        super.updateItem(item, empty)
        graphic = if(item != null && !empty) {
            content.bind(item)
            content.root
        } else {
            null
        }
    }
}