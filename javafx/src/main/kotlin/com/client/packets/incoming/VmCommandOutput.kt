package com.client.packets.incoming

import com.client.game.model.developer.DeveloperModel
import com.client.network.channel.packets.Packet
import com.client.network.channel.packets.handlers.PacketHandler
import com.client.network.readSimpleString
import com.client.packets.message.VmCommandOutputMessage
import com.client.scripting.Extensions.inject

class VmCommandOutput(override val opcode: Int = 1) : PacketHandler<VmCommandOutputMessage, Unit> {

    val devMode: DeveloperModel by inject()

    override fun decode(packet: Packet): VmCommandOutputMessage {
        val con = packet.content
        return VmCommandOutputMessage(con.readSimpleString(), con.readBoolean())
    }

    override fun handle(message: VmCommandOutputMessage) {
        devMode.output.set(message.output)
    }
}