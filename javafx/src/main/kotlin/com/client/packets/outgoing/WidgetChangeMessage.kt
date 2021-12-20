package com.client.packets.outgoing

import com.client.network.channel.packets.Packet
import com.client.network.channel.packets.PacketEncoder
import com.client.network.writeSimpleString
import io.netty.buffer.Unpooled

class WidgetChangeMessage(val widgetId: String) {
    companion object : PacketEncoder<WidgetChangeMessage> {
        override fun encode(message: WidgetChangeMessage): Packet {
            val buf = Unpooled.buffer()
            buf.writeSimpleString(message.widgetId)
            return Packet(4, buf)
        }
    }
}