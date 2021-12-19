package com.client.game.ui.software

import com.client.game.model.PreferencesModel
import com.client.game.model.software.SoftwareData
import com.client.game.model.software.SoftwareDataModel
import com.client.game.model.software.SoftwareModel
import javafx.beans.binding.Bindings
import javafx.scene.control.MenuItem
import javafx.scene.control.Tooltip
import javafx.scene.layout.AnchorPane
import tornadofx.Fragment

class SoftwareActionsFragment : Fragment() {

    private val prefModel: PreferencesModel by di()

    override val root: AnchorPane by fxml("software-actions-cell.fxml")

    val installTooltip: Tooltip by fxid()
    val installBtn: MenuItem by fxid()
    val quickInstallBtn: MenuItem by fxid()

    fun bind(data: SoftwareDataModel) {

        installTooltip.textProperty().bind(Bindings.createStringBinding({
            if(prefModel.SOFTWARE_EXTENSION_SUB_MODE.and(prefModel.HIGH_MODE).get()) {
                "Install ${data.name.get()}"
            } else "Install ${data.name.get()}.${data.extension.get()}"
        }, data.name, data.extension, prefModel.SOFTWARE_EXTENSION_SUB_MODE, prefModel.HIGH_MODE))

        installBtn.textProperty().bind(Bindings.createStringBinding({
            if(prefModel.SOFTWARE_EXTENSION_SUB_MODE.and(prefModel.HIGH_MODE).get()) {
                "Install ${data.name.get()}"
            } else "Install ${data.name.get()}.${data.extension.get()}"
        }, data.name, data.extension, prefModel.SOFTWARE_EXTENSION_SUB_MODE, prefModel.HIGH_MODE))

        quickInstallBtn.textProperty().bind(Bindings.createStringBinding({
            if(prefModel.SOFTWARE_EXTENSION_SUB_MODE.and(prefModel.HIGH_MODE).get()) {
                "Quick Install ${data.name.get()}"
            } else "Quick Install ${data.name.get()}.${data.extension.get()}"
        }, data.name, data.extension, prefModel.SOFTWARE_EXTENSION_SUB_MODE, prefModel.HIGH_MODE))

    }

}