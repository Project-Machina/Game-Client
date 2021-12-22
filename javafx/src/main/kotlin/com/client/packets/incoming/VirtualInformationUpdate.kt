package com.client.packets.incoming

import com.client.game.ui.gameframe.GameFrameView
import com.client.network.channel.packets.Packet
import com.client.network.channel.packets.handlers.PacketHandler
import com.client.network.readSimpleString
import com.client.packets.message.VirtualInfoMessage
import javafx.geometry.Pos
import org.controlsfx.control.Notifications
import tornadofx.find
import tornadofx.runLater

class VirtualInformationUpdate(override val opcode: Int = 1) : PacketHandler<VirtualInfoMessage, Unit> {
    override fun decode(packet: Packet): VirtualInfoMessage {
        val con = packet.content
        return VirtualInfoMessage(con.readSimpleString(), con.readSimpleString())
    }

    override fun handle(message: VirtualInfoMessage) {
        runLater {
            val gameFrame = find<GameFrameView>()
            val nots = Notifications.create().position(Pos.TOP_CENTER).owner(gameFrame.root)
                .title(message.title)
                .text(message.message)
            nots.show()
        }
    }
}