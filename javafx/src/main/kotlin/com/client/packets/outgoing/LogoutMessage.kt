package com.client.packets.outgoing

import com.client.network.channel.packets.Packet
import com.client.network.channel.packets.PacketEncoder
import io.netty.buffer.Unpooled

class LogoutMessage {
    companion object : PacketEncoder<LogoutMessage> {
        override fun encode(message: LogoutMessage): Packet {
            return Packet(3, Unpooled.EMPTY_BUFFER)
        }
    }
}