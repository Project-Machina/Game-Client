package com.client.packets.outgoing

import com.client.network.channel.packets.Packet
import com.client.network.channel.packets.PacketEncoder
import com.client.network.writeSimpleString
import io.netty.buffer.Unpooled

class PingMessage(val message: String) {
    companion object : PacketEncoder<PingMessage> {
        override fun encode(message: PingMessage): Packet {
            val buffer = Unpooled.buffer()
            buffer.writeSimpleString(message.message)
            return Packet(1, buffer)
        }
    }

}