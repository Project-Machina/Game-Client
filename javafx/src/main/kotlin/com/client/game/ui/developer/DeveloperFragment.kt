package com.client.game.ui.developer

import com.client.game.model.developer.DeveloperModel
import com.client.game.ui.software.SoftwareFragment
import com.client.packets.outgoing.PingMessage
import com.client.packets.outgoing.VmCommandMessage
import com.client.scope.GameScope
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.layout.AnchorPane
import org.controlsfx.control.Notifications
import tornadofx.Fragment

class DeveloperFragment : Fragment("Developer") {

    val model: DeveloperModel by di()

    override val scope: GameScope = super.scope as GameScope

    override val root: AnchorPane by fxml("developer.fxml")

    val notif: Button by fxid()
    val osNotif: Button by fxid()
    val detachStorage: Button by fxid()

    val commandBtn: Button by fxid()
    val commandInput: TextField by fxid()
    val commandOutput: TextArea by fxid()

    init {

        notif.setOnAction {
            val nots = Notifications.create().position(Pos.TOP_CENTER).owner(root)
                .title("Test Notification")
            nots.show()
        }

        osNotif.setOnAction {
            val nots = Notifications.create().position(Pos.BOTTOM_RIGHT)
                .title("Test OS Like Notification")
            nots.show()
        }

        detachStorage.setOnAction {
            val frag = find<SoftwareFragment>()
            frag.openWindow()
        }

        commandBtn.setOnAction {
            val cmd = commandInput.text
            scope.session?.sendMessage(VmCommandMessage(cmd, false))
        }

        commandOutput.textProperty().bind(model.output)

    }

}