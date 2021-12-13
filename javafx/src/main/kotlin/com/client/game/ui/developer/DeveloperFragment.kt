package com.client.game.ui.developer

import com.client.game.ui.software.SoftwareFragment
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.layout.AnchorPane
import javafx.stage.StageStyle
import org.controlsfx.control.Notifications
import tornadofx.Fragment

class DeveloperFragment : Fragment("Developer") {

    override val root: AnchorPane by fxml("developer.fxml")

    val notif: Button by fxid()
    val osNotif: Button by fxid()
    val detachStorage: Button by fxid()

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

    }

}