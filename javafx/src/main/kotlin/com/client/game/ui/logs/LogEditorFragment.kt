package com.client.game.ui.logs

import com.client.game.model.logs.LogDataModel
import com.client.packets.outgoing.VmCommandMessage
import com.client.scripting.Extensions
import com.jfoenix.controls.JFXTimePicker
import javafx.scene.control.Button
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.layout.AnchorPane
import tornadofx.Fragment
import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset

class LogEditorFragment : Fragment() {

    override val root: AnchorPane by fxml("log-editor.fxml")

    val timeStamp: JFXTimePicker by fxid()
    val sourceField: TextField by fxid()
    val messageArea: TextArea by fxid()
    val saveBtn: Button by fxid()
    val cancelBtn: Button by fxid()

    fun bind(data: LogDataModel, isRemote: Boolean) {
        val offset = Instant.ofEpochSecond(data.time.get()).atOffset(ZoneOffset.UTC)

        timeStamp.is24HourView = true
        timeStamp.value = offset.toLocalTime()
        sourceField.text = data.source.get()
        messageArea.text = data.message.get()

        saveBtn.setOnAction {
            val time = timeStamp.value.atOffset(ZoneOffset.UTC).toEpochSecond(offset.toLocalDate())
            val source = sourceField.text.replace(' ', '_')
            val message = messageArea.text.replace(' ', '_')
            val logID = data.id.get()
            val session = Extensions.session
            session?.sendMessage(VmCommandMessage("elog -i $logID -s $source -m $message -t $time", isRemote))
        }

        cancelBtn.setOnAction {
            close()
        }
    }
}