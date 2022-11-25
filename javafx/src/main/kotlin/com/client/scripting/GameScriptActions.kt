package com.client.scripting

import com.client.game.ui.gameframe.GameFrameView
import com.client.packets.outgoing.VmCommandMessage
import javafx.geometry.Pos
import org.controlsfx.control.Notifications
import tornadofx.FX.Companion.find

object GameScriptActions {
    fun connect(address: String) {
        val session = Extensions.session
        session?.sendMessage(VmCommandMessage("connect $address", false))
    }

    fun showAlert(title: String, msg: String) {
        val gameFrame = find<GameFrameView>()
        val nots = Notifications.create().position(Pos.TOP_CENTER).owner(gameFrame.root)
            .title(title)
            .text(msg)
        nots.show()
    }
}