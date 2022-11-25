package com.client.game.ui.software

import com.client.game.model.PreferencesModel
import com.client.game.model.internet.InternetModel
import com.client.game.model.processes.ProcessesModel
import com.client.game.model.remote.SystemAccountModel
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
import javafx.scene.web.WebEngine
import javafx.stage.StageStyle
import tornadofx.Fragment
import tornadofx.disableWhen
import tornadofx.visibleWhen

class SoftwareActionsFragment(val isRemote: Boolean = false) : Fragment() {

    private val prefModel: PreferencesModel by di()
    private val processModel: ProcessesModel by di()
    private val internetModel: InternetModel by di()
    private val softwareModel: SoftwareModel by di()
    private val accmanModel: SystemAccountModel by di()

    override val root: AnchorPane by fxml("software-actions-cell.fxml")

    val installTooltip: Tooltip by fxid()
    val hideTooltip: Tooltip by fxid()
    val installMenuBtn: MenuItem by fxid()
    val quickInstallBtn: MenuItem by fxid()
    val installBtn: GameActionIconButton by fxid()
    val hideBtn: GameActionIconButton by fxid()
    val hideMenuBtn: MenuItem by fxid()
    val quickHideBtn: MenuItem by fxid()
    val downloadBtn: GameActionIconButton by fxid()

    fun bind(data: SoftwareDataModel) {

        installBtn.visibleWhen(Bindings.createBooleanBinding({
           true
        }, softwareModel.softwares, internetModel.softwares))

        installBtn.iconToggleProperty().bind(Bindings.createBooleanBinding({
            data.pid.get() != -1
        }, data.pid))

        installBtn.visibleWhen(Bindings.createBooleanBinding({
            if(isRemote) {
                accmanModel.permissions.contains("ssh")
            } else true
        }, accmanModel.permissions, softwareModel.softwares, internetModel.softwares))

        hideBtn.iconToggleProperty().bind(Bindings.createBooleanBinding({
            softwareModel.softwares.value.any { it.value.extension.get() == "hdr" && data.isHidden.not().get() }
        }, softwareModel.softwares, internetModel.softwares, processModel.processes))

        hideBtn.visibleWhen(Bindings.createBooleanBinding({
            softwareModel.softwares.value
                .any {
                    (it.value.extension.get() == "hdr" && it.value.pid.get() != -1 && data.pid.get() == -1)
                            || (it.value.extension.get() == "skr" && it.value.pid.get() != -1 && data.pid.get() == -1)
                }
                    || data.isHidden.get()
        }, softwareModel.softwares, internetModel.softwares, processModel.processes))

        downloadBtn.visibleWhen(
            Bindings.createBooleanBinding({
                isRemote && accmanModel.permissions.contains("ftp")
            }, accmanModel.permissions, softwareModel.isRemote, internetModel.softwares)
        )

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
            find<InstallSoftwareFragment>("data" to data, "isRemote" to isRemote)
                .openModal(StageStyle.UNDECORATED)
        }

        hideMenuBtn.setOnAction {

            if (data.isHidden.get()) {
                val session = Extensions.session
                val softwareName = data.name.concat(".").concat(data.extension).get().replace(' ', '_')
                session?.sendMessage(
                    VmCommandMessage(
                        "seek -n $softwareName -v ${data.version.get()}",
                        isRemote
                    )
                )
            } else {
                find<HideSoftwareFragment>("data" to data, "isRemote" to isRemote)
                    .openModal(StageStyle.UNDECORATED)
            }

        }

        hideBtn.setOnAction {
            if (data.isHidden.get()) {
                val session = Extensions.session
                val softwareName = data.name.concat(".").concat(data.extension).get().replace(' ', '_')
                session?.sendMessage(
                    VmCommandMessage(
                        "seek -n $softwareName -v ${data.version.get()}",
                        isRemote
                    )
                )
            } else {
                find<HideSoftwareFragment>("data" to data, "isRemote" to isRemote)
                    .openModal(StageStyle.UNDECORATED)
            }
        }

        quickHideBtn.setOnAction {

            val command = if (data.isHidden.get()) "seek" else "hide"
            val session = Extensions.session
            val softwareName = data.name.concat(".").concat(data.extension).get().replace(' ', '_')
            session?.sendMessage(
                VmCommandMessage(
                    "$command -n $softwareName -v ${data.version.get()}",
                    isRemote
                )
            )
        }

        installBtn.setOnAction {
            val pid = data.pid.get()
            if (pid == -1) {
                find<InstallSoftwareFragment>("data" to data, "isRemote" to isRemote)
                    .openModal(StageStyle.UNDECORATED)
            } else {
                val session = Extensions.session
                session?.sendMessage(VmCommandMessage("killproc ${data.pid.get()}", isRemote))
            }
        }


        downloadBtn.setOnAction {
            if(isRemote) {
                Extensions.session?.let {
                    val softwareName = data.name.concat(".").concat(data.extension).get().replace(' ', '_')
                    it.sendMessage(VmCommandMessage("download -n $softwareName -v ${data.version.get()}", isRemote))
                }
            }
        }

        quickInstallBtn.setOnAction {
            val session = Extensions.session
            val softwareName = data.name.concat(".").concat(data.extension).get()
            softwareName.replace(' ', '_')
            session?.sendMessage(
                VmCommandMessage(
                    "install -n $softwareName -v ${data.version.get()} -E",
                    isRemote
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