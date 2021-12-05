package com.client.network

import com.client.network.channel.ClientChannelInitializer
import com.client.network.session.NetworkSession
import com.client.network.session.NetworkSession.Companion.session
import io.netty.bootstrap.Bootstrap
import io.netty.channel.EventLoopGroup
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioSocketChannel
import java.util.concurrent.ThreadFactory

class NetworkClient(val address: String, val port: Int) {

    val bootstrap = Bootstrap()
    private val workerGroup: EventLoopGroup = NioEventLoopGroup(1, ThreadFactory { Thread(it).also { th -> th.isDaemon = true } })

    fun connect(username: String, password: String) : NetworkSession {
        try {
            bootstrap.group(workerGroup)
                .channel(NioSocketChannel::class.java)
                .remoteAddress(address, port)
                .handler(ClientChannelInitializer(username, password))
            val future = bootstrap.connect().sync()
            return future.channel().session
        } catch(e: Exception) {
            e.printStackTrace()
        }
        error("Failed to connect to game server.")
    }

}