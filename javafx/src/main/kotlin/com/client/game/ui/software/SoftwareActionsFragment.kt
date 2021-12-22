package com.client.game.ui.software

import com.client.game.model.PreferencesModel
import com.client.game.model.processes.ProcessesModel
import com.client.game.model.software.SoftwareDataModel
import com.client.game.model.software.SoftwareModel
import com.client.game.ui.software.actions.HideSoftwareFragment
import com.client.game.ui.software.actions.InstallSoftwareFragment
import com.client.javafx.nodes.GameActionIconButton
import com.client.packets.outgoing.VmCommandMessage
import com.client.scripting.Extensions
import javafx.beans.binding.Bindings
import javafx.scene.control.MenuItem
import javafx.scene.control.Tooltip
import javafx.scene.layout.AnchorPane
import javafx.stage.Modality
import javafx.stage.StageStyle
import tornadofx.*

class SoftwareActionsFragment : Fragment() {

    private val prefModel: PreferencesModel by di()
    private val processModel: ProcessesModel by di()
    private val softwareModel: SoftwareModel by inject()

    override val root: AnchorPane by fxml("software-actions-cell.fxml")

    val installTooltip: Tooltip by fxid()
    val hideTooltip: Tooltip by fxid()
    val installMenuBtn: MenuItem by fxid()
    val quickInstallBtn: MenuItem by fxid()
    val installBtn: GameActionIconButton by fxid()
    val hideBtn: GameActionIconButton by fxid()
    val hideMenuBtn: MenuItem by fxid()
    val quickHideBtn: MenuItem by fxid()


    fun bind(data: SoftwareDataModel) {

        installBtn.iconToggleProperty().bind(Bindings.createBooleanBinding({
            data.pid.get() != -1
        }, data.pid))

        hideBtn.iconToggleProperty().bind(Bindings.createBooleanBinding({
            softwareModel.softwares.value.any { it.value.extension.get() == "hdr" && data.isHidden.not().get() }
        }, softwareModel.softwares, processModel.processes))

        hideBtn.visibleWhen(Bindings.createBooleanBinding({
            softwareModel.softwares.value
                .any { (it.value.extension.get() == "hdr" && it.value.pid.get() != -1 && data.pid.get() == -1) }
                    || data.isHidden.get()
        }, softwareModel.softwares, processModel.processes))

        installTooltip.textProperty().bind(Bindings.createStringBinding({
            if (prefModel.SOFTWARE_EXTENSION_SUB_MODE.and(prefModel.HIGH_MODE).get()) {
                "Install ${data.name.get()}"
            } else "Install ${data.name.get()}.${data.extension.get()}"
        }, data.name, data.extension, prefModel.SOFTWARE_EXTENSION_SUB_MODE, prefModel.HIGH_MODE))

        hideTooltip.textProperty().bind(Bindings.createStringBinding({
            if (prefModel.SOFTWARE_EXTENSION_SUB_MODE.and(prefModel.HIGH_MODE).get()) {
                "Hide ${data.name.get()}"
            } else "Hide ${data.name.get()}.${data.extension.get()}"
        }, data.name, data.extension, prefModel.SOFTWARE_EXTENSION_SUB_MODE, prefModel.HIGH_MODE))

        installMenuBtn.textProperty().bind(Bindings.createStringBinding({
            if (prefModel.SOFTWARE_EXTENSION_SUB_MODE.and(prefModel.HIGH_MODE).get()) {
                "Install ${data.name.get()}"
            } else "Install ${data.name.get()}.${data.extension.get()}"
        }, data.name, data.extension, prefModel.SOFTWARE_EXTENSION_SUB_MODE, prefModel.HIGH_MODE))

        hideMenuBtn.textProperty().bind(Bindings.createStringBinding({
            if (prefModel.SOFTWARE_EXTENSION_SUB_MODE.and(prefModel.HIGH_MODE).get()) {
                "Hide ${data.name.get()}"
            } else "Hide ${data.name.get()}.${data.extension.get()}"
        }, data.name, data.extension, prefModel.SOFTWARE_EXTENSION_SUB_MODE, prefModel.HIGH_MODE))

        installMenuBtn.setOnAction {
            find<InstallSoftwareFragment>("data" to data)
                .openModal(StageStyle.UNDECORATED)
        }

        hideMenuBtn.setOnAction {

            if(data.isHidden.get()) {
                val session = Extensions.session
                val softwareName = data.name.concat(".").concat(data.extension).get().replace(' ', '_')
                session?.sendMessage(
                    VmCommandMessage(
                        "seek -n $softwareName -v ${data.version.get()}",
                        softwareModel.isRemote.get()
                    )
                )
            } else {
                find<HideSoftwareFragment>("data" to data)
                    .openModal(StageStyle.UNDECORATED)
            }

        }

        hideBtn.setOnAction {
            if(data.isHidden.get()) {
                val session = Extensions.session
                val softwareName = data.name.concat(".").concat(data.extension).get().replace(' ', '_')
                session?.sendMessage(
                    VmCommandMessage(
                        "seek -n $softwareName -v ${data.version.get()}",
                        softwareModel.isRemote.get()
                    )
                )
            } else {
                find<HideSoftwareFragment>("data" to data)
                    .openModal(StageStyle.UNDECORATED)
            }
        }

        quickHideBtn.setOnAction {

            val command = if(data.isHidden.get()) "seek" else "hide"

            val session = Extensions.session
            val softwareName = data.name.concat(".").concat(data.extension).get().replace(' ', '_')
            session?.sendMessage(
                VmCommandMessage(
                    "$command -n $softwareName -v ${data.version.get()}",
                    softwareModel.isRemote.get()
                )
            )
        }

        installBtn.setOnAction {
            val pid = data.pid.get()
            if (pid == -1) {
                find<InstallSoftwareFragment>("data" to data)
                    .openModal(StageStyle.UNDECORATED)
            } else {
                val session = Extensions.session
                session?.sendMessage(VmCommandMessage("killproc ${data.pid.get()}", softwareModel.isRemote.get()))
            }
        }

        quickInstallBtn.setOnAction {
            val session = Extensions.session
            val softwareName = data.name.concat(".").concat(data.extension).get()
            softwareName.replace(' ', '_')
            session?.sendMessage(
                VmCommandMessage(
                    "install -n $softwareName -v ${data.version.get()} -E",
                    softwareModel.isRemote.get()
                )
            )
        }

        quickInstallBtn.disableWhen(Bindings.createBooleanBinding({
            data.pid.get() != -1
        }, data.pid))

        quickInstallBtn.textProperty().bind(Bindings.createStringBinding({
            if (prefModel.SOFTWARE_EXTENSION_SUB_MODE.and(prefModel.HIGH_MODE).get()) {
                "Quick Install ${data.name.get()}"
            } else "Quick Install ${data.name.get()}.${data.extension.get()}"
        }, data.name, data.extension, prefModel.SOFTWARE_EXTENSION_SUB_MODE, prefModel.HIGH_MODE))

        quickHideBtn.textProperty().bind(Bindings.createStringBinding({
            if (prefModel.SOFTWARE_EXTENSION_SUB_MODE.and(prefModel.HIGH_MODE).get()) {
                "Quick Hide ${data.name.get()}"
            } else "Quick Hide ${data.name.get()}.${data.extension.get()}"
        }, data.name, data.extension, prefModel.SOFTWARE_EXTENSION_SUB_MODE, prefModel.HIGH_MODE))

    }

}