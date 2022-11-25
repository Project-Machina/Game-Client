package com.client.packets.incoming

import com.client.game.model.internet.InternetModel
import com.client.game.npcs.NpcPageLoader
import com.client.network.channel.packets.Packet
import com.client.network.channel.packets.handlers.PacketHandler
import com.client.network.readSimpleString
import com.client.scripting.Extensions.get
import tornadofx.runLater

class NpcPageUpdate(override val opcode: Int = 9) : PacketHandler<String, Unit> {
    override fun decode(packet: Packet): String {
        val content = packet.content
        val useDefault = content.readBoolean()
        return if (useDefault) "" else content.readSimpleString(true)
    }

    override fun handle(message: String) {
        runLater {
            val model: InternetModel = get()
            model.loadPage(message)
        }
    }
}