package com.client.game.ui.logs

import com.client.game.model.internet.InternetModel
import com.client.game.model.logs.LogDataModel
import com.client.game.model.logs.LogsModel
import com.client.game.model.processes.ProcessesModel
import com.client.game.model.software.SoftwareModel
import com.client.game.ui.logs.actions.HideLogFragment
import com.client.javafx.nodes.GameActionIconButton
import com.client.packets.outgoing.VmCommandMessage
import com.client.scripting.Extensions
import javafx.beans.binding.Bindings
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.control.MenuItem
import javafx.scene.layout.AnchorPane
import javafx.stage.StageStyle
import tornadofx.Fragment
import tornadofx.hide
import tornadofx.visibleWhen

class LogActionsFragment(val isRemote: Boolean = false) : Fragment() {

    val softwareModel: SoftwareModel by inject()
    val logsModel: LogsModel by inject()
    val internetModel: InternetModel by di()
    val processModel: ProcessesModel by di()

    override val root: AnchorPane by fxml("logs-actions.fxml")

    val editBtn: GameActionIconButton by fxid()
    val hideBtn: GameActionIconButton by fxid()
    val deleteBtn: GameActionIconButton by fxid()

    val hideMenuBtn: MenuItem by fxid()
    val hideQuickMenuBtn: MenuItem by fxid()

    fun bind(data: LogDataModel) {

        hideBtn.visibleWhen(Bindings.createBooleanBinding({
            softwareModel.softwares.values.any {
                (it.extension.get() == "skr" || it.extension.get() == "hdr") && it.pid.get() != -1
            }
        }, internetModel.softwares, softwareModel.softwares, processModel.processes, logsModel.logs))

        hideBtn.iconToggleProperty().bind(Bindings.createBooleanBinding({
            data.isHidden.get()
        }, internetModel.softwares, softwareModel.softwares, processModel.processes, data.isHidden, logsModel.logs))

        val action = EventHandler<ActionEvent> {
            find<HideLogFragment>("data" to data, "isRemote" to isRemote)
                .openModal(StageStyle.UNDECORATED)
        }

        hideBtn.onAction = action
        hideMenuBtn.onAction = action

        hideQuickMenuBtn.setOnAction {
            val session = Extensions.session
            session?.sendMessage(VmCommandMessage("hidelg -i ${data.id.get()}", isRemote))
        }

        editBtn.setOnAction {
            val editor = find<LogEditorFragment>()
            editor.bind(data, isRemote)
            editor.openModal(StageStyle.UNDECORATED)
        }

        if (isRemote)
            deleteBtn.hide()

        deleteBtn.setOnAction {
            val session = Extensions.session
            val logId = data.id.get()
            session?.sendMessage(VmCommandMessage("rmlg -i $logId", isRemote))
        }

    }

}