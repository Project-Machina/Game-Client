package com.client.game.ui.logs

import com.client.game.model.internet.InternetModel
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
import tornadofx.hide
import tornadofx.visibleWhen

class LogActionsFragment(val isRemote: Boolean = false) : Fragment() {

    val softwareModel: SoftwareModel by inject()
    val internetModel: InternetModel by di()

    override val root: AnchorPane by fxml("logs-actions.fxml")

    val editBtn: GameActionIconButton by fxid()
    val hideBtn: GameActionIconButton by fxid()
    val deleteBtn: GameActionIconButton by fxid()

    fun bind(data: LogDataModel) {

        hideBtn.visibleWhen(Bindings.createBooleanBinding({
            if(isRemote) {
                internetModel.softwares.values.any { it.pid.get() != -1 && it.extension.get() == "skr" }
            } else {
                softwareModel.softwares.values.any { it.pid.get() != -1 && it.extension.get() == "skr" }
            }
        }, if(isRemote) internetModel.softwares else softwareModel.softwares))

        editBtn.setOnAction {
            val editor = find<LogEditorFragment>()
            editor.bind(data, isRemote)
            editor.openModal(StageStyle.UNDECORATED)
        }

        if(isRemote)
            deleteBtn.hide()

        deleteBtn.setOnAction {
            val session = Extensions.session
            val logId = data.id.get()
            session?.sendMessage(VmCommandMessage("rmlg -i $logId", isRemote))
        }

    }

}