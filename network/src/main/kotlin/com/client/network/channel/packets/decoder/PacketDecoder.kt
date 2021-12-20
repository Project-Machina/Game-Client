package com.client.network.channel.packets.decoder

import com.client.network.channel.packets.Packet
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToMessageDecoder

class PacketDecoder : MessageToMessageDecoder<ByteBuf>() {
    override fun decode(ctx: ChannelHandlerContext, frame: ByteBuf, out: MutableList<Any>) {
        val opcode = frame.readUnsignedShort()
        val contentFrame = frame.copy()
        out.add(Packet(opcode, contentFrame))
    }
}