package com.client.game.ui.software.cells

import com.client.game.model.software.SoftwareDataModel
import javafx.beans.binding.Bindings
import javafx.scene.control.Label
import javafx.scene.control.TableRow

class SoftwareTableRowCell : TableRow<SoftwareDataModel>() {
    override fun updateItem(sdm: SoftwareDataModel?, empty: Boolean) {
        super.updateItem(sdm, empty)
        if(sdm != null && !empty) {
            opacityProperty().bind(Bindings.createDoubleBinding({
                if(sdm.isHidden.get()) 0.5 else 1.0
            }, sdm.isHidden))
        }
    }
}