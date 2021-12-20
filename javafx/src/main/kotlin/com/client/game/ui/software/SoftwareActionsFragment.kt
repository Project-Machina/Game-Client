package com.client.game.ui.software

import com.client.game.model.PreferencesModel
import com.client.game.model.software.SoftwareDataModel
import com.client.game.ui.software.installing.InstallSoftwareFragment
import com.client.javafx.nodes.GameActionIconButton
import javafx.beans.binding.Bindings
import javafx.scene.control.MenuItem
import javafx.scene.control.Tooltip
import javafx.scene.layout.AnchorPane
import javafx.stage.Modality
import javafx.stage.StageStyle
import tornadofx.FX
import tornadofx.Fragment

class SoftwareActionsFragment : Fragment() {

    private val prefModel: PreferencesModel by di()

    override val root: AnchorPane by fxml("software-actions-cell.fxml")

    val installTooltip: Tooltip by fxid()
    val installMenuBtn: MenuItem by fxid()
    val quickInstallBtn: MenuItem by fxid()
    val installBtn: GameActionIconButton by fxid()

    fun bind(data: SoftwareDataModel) {

        installBtn.iconToggleProperty().bind(Bindings.createBooleanBinding({
            data.pid.get() != -1
        }, data.pid))

        installTooltip.textProperty().bind(Bindings.createStringBinding({
            if(prefModel.SOFTWARE_EXTENSION_SUB_MODE.and(prefModel.HIGH_MODE).get()) {
                "Install ${data.name.get()}"
            } else "Install ${data.name.get()}.${data.extension.get()}"
        }, data.name, data.extension, prefModel.SOFTWARE_EXTENSION_SUB_MODE, prefModel.HIGH_MODE))

        installMenuBtn.textProperty().bind(Bindings.createStringBinding({
            if(prefModel.SOFTWARE_EXTENSION_SUB_MODE.and(prefModel.HIGH_MODE).get()) {
                "Install ${data.name.get()}"
            } else "Install ${data.name.get()}.${data.extension.get()}"
        }, data.name, data.extension, prefModel.SOFTWARE_EXTENSION_SUB_MODE, prefModel.HIGH_MODE))

        installMenuBtn.setOnAction {
            find<InstallSoftwareFragment>("data" to data)
                .openModal(StageStyle.UNDECORATED)
        }

        installBtn.setOnAction {
            find<InstallSoftwareFragment>("data" to data)
                .openModal(StageStyle.UNDECORATED)
        }

        quickInstallBtn.textProperty().bind(Bindings.createStringBinding({
            if(prefModel.SOFTWARE_EXTENSION_SUB_MODE.and(prefModel.HIGH_MODE).get()) {
                "Quick Install ${data.name.get()}"
            } else "Quick Install ${data.name.get()}.${data.extension.get()}"
        }, data.name, data.extension, prefModel.SOFTWARE_EXTENSION_SUB_MODE, prefModel.HIGH_MODE))

    }

}