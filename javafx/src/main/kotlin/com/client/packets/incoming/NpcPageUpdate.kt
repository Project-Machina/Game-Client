package com.client.packets.incoming

import com.client.game.model.internet.InternetModel
import com.client.game.npcs.NpcPageLoader
import com.client.game.ui.internet.InternetFragment
import com.client.network.channel.packets.Packet
import com.client.network.channel.packets.handlers.PacketHandler
import com.client.scripting.Extensions.get
import javafx.scene.control.Alert
import tornadofx.add
import tornadofx.clear
import tornadofx.find
import tornadofx.runLater

class NpcPageUpdate(override val opcode: Int = 9) : PacketHandler<ByteArray, Unit> {
    override fun decode(packet: Packet): ByteArray {
        val content = packet.content
        val data = ByteArray(content.readableBytes())
        content.readBytes(data)
        return data
    }

    override fun handle(message: ByteArray) {
        val pane = NpcPageLoader.loadNpcPage(message)
        val model: InternetModel = get()
        runLater {
            model.remoteHomePageNode.set(pane)
        }
    }
}