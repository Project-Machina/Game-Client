package com.client.network.channel

import com.client.network.channel.packets.PacketChannelHandler
import com.client.network.channel.packets.codec.PacketCodec
import com.client.network.channel.packets.decoder.PacketDecoder
import com.client.network.channel.packets.encoders.PacketLengthEncoder
import com.client.network.session.NetworkSession
import com.client.network.session.NetworkSession.Companion.ATTRIBUTE_KEY
import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.codec.LengthFieldBasedFrameDecoder

class ClientChannelInitializer(val username: String, val password: String) : ChannelInitializer<NioSocketChannel>() {
    override fun initChannel(ch: NioSocketChannel) {
        ch.attr(ATTRIBUTE_KEY).set(NetworkSession(ch))

        ch.pipeline().addLast(LengthFieldBasedFrameDecoder(
            8192,
            0,
            4,
            0,
            4
        ))
        ch.pipeline().addLast(PacketDecoder())
        ch.pipeline().addLast(PacketLengthEncoder())
        ch.pipeline().addLast("handler", PacketChannelHandler(username, password))
    }
}