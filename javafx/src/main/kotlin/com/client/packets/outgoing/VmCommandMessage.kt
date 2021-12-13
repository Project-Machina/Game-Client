package com.client.packets.outgoing

import com.client.network.channel.packets.Packet
import com.client.network.channel.packets.PacketEncoder
import com.client.network.writeSimpleString
import io.netty.buffer.Unpooled

class VmCommandMessage(val command: String, val remote: Boolean) {
    companion object : PacketEncoder<VmCommandMessage> {
        override fun encode(message: VmCommandMessage): Packet {
            val content = Unpooled.buffer()
            content.writeSimpleString(message.command)
            content.writeBoolean(message.remote)
            return Packet(2, 0, content)
        }
    }
}