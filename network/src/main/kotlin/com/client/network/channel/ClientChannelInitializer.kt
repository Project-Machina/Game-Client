package com.client.network.channel

import com.client.network.channel.packets.PacketChannelHandler
import com.client.network.channel.packets.codec.PacketCodec
import com.client.network.session.NetworkSession
import com.client.network.session.NetworkSession.Companion.ATTRIBUTE_KEY
import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.nio.NioSocketChannel

class ClientChannelInitializer(val username: String, val password: String) : ChannelInitializer<NioSocketChannel>() {
    override fun initChannel(ch: NioSocketChannel) {
        ch.attr(ATTRIBUTE_KEY).set(NetworkSession(ch))
        ch.pipeline().addLast("codec", PacketCodec())
        ch.pipeline().addLast("handler", PacketChannelHandler(username, password))
    }
}