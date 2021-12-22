package com.client.game.ui.logs

import com.client.game.model.logs.LogDataModel
import com.client.game.model.software.SoftwareModel
import com.client.javafx.nodes.GameActionIconButton
import com.client.packets.outgoing.VmCommandMessage
import com.client.scripting.Extensions
import javafx.beans.binding.Bindings
import javafx.scene.layout.AnchorPane
import javafx.stage.StageStyle
import tornadofx.Fragment
import tornadofx.hiddenWhen
import tornadofx.visibleWhen

class LogActionsFragment : Fragment() {

    val softwareModel: SoftwareModel by inject()

    override val root: AnchorPane by fxml("logs-actions.fxml")

    val editBtn: GameActionIconButton by fxid()
    val hideBtn: GameActionIconButton by fxid()
    val deleteBtn: GameActionIconButton by fxid()

    fun bind(data: LogDataModel) {

        hideBtn.visibleWhen(Bindings.createBooleanBinding({
            softwareModel.softwares.values.any { it.pid.get() != -1 && it.extension.get() == "skr" }
        }, softwareModel.softwares))

        editBtn.setOnAction {
            val editor = find<LogEditorFragment>()
            editor.bind(data)
            editor.openModal(StageStyle.UNDECORATED)
        }

        deleteBtn.setOnAction {
            val session = Extensions.session
            val logId = data.id.get()
            session?.sendMessage(VmCommandMessage("rmlg -i $logId", false))
        }

    }

}