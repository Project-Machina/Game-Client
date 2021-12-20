package com.client.network.channel.packets

import com.client.network.session.NetworkSession.Companion.session
import com.client.network.writeSimpleString
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler

class PacketChannelHandler(val username: String, val password: String) : SimpleChannelInboundHandler<Packet>() {

    override fun channelActive(ctx: ChannelHandlerContext) {
        val content = Unpooled.buffer()
        content.writeSimpleString(username)
        content.writeSimpleString(password)
        ctx.writeAndFlush(Packet(0, content))
    }

    override fun channelRead0(ctx: ChannelHandlerContext, msg: Packet) {
        val session = ctx.channel().session
        session.receivePacket(msg)
    }
}