package com.client.network.channel.packets.codec

import com.client.network.channel.packets.Packet
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageCodec

class PacketCodec : ByteToMessageCodec<Packet>() {
    override fun decode(ctx: ChannelHandlerContext, buf: ByteBuf, out: MutableList<Any>) {
        if (buf.readableBytes() >= 4) {
            val opcode = buf.readUnsignedShort()
            val frameLength = buf.readUnsignedShort()
            out.add(Packet(opcode, -1, buf.readBytes(frameLength)))

        }
        if(buf.readableBytes() >= buf.writerIndex()) {
            buf.release(buf.refCnt())
        }
    }

    override fun encode(ctx: ChannelHandlerContext, msg: Packet, out: ByteBuf) {
        out.writeShort(msg.opcode)
        out.writeShort(msg.content.writerIndex())
        out.writeBytes(msg.content)
    }
}