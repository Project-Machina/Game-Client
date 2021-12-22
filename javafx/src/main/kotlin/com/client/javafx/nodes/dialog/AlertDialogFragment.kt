package com.client.javafx.nodes.dialog

import com.client.javafx.models.dialogs.AlertDialogModel
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.AnchorPane
import javafx.stage.StageStyle
import tornadofx.Fieldset
import tornadofx.Fragment

class AlertDialogFragment : Fragment() {

    override val root: AnchorPane by fxml("alert-dialog.fxml")

    val container: Fieldset by fxid()
    val messageLabel: Label by fxid()
    val okBtn: Button by fxid()

    fun bind(data: AlertDialogModel) {
        container.textProperty.bind(data.title)
        messageLabel.textProperty().bind(data.message)
        okBtn.setOnAction {
            close()
        }
    }

    companion object {

        fun AlertDialogModel.show(wait: Boolean = true) {
            val alert = AlertDialogFragment()
            alert.bind(this)
            alert.openModal(StageStyle.UNDECORATED, block = wait)
        }
    }

}