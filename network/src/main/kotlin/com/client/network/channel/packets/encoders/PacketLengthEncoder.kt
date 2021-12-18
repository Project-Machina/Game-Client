package com.client.network.channel.packets.encoders

import com.client.network.channel.packets.Packet
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder

class PacketLengthEncoder : MessageToByteEncoder<Packet>() {
    override fun encode(ctx: ChannelHandlerContext, msg: Packet, out: ByteBuf) {
        val length = msg.content.readableBytes()
        out.writeInt(length + 2)
        out.writeShort(msg.opcode)
        out.writeBytes(msg.content)
    }
}