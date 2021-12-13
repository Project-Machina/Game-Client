package com.client.network

import com.client.network.channel.ClientChannelInitializer
import com.client.network.session.NetworkSession
import com.client.network.session.NetworkSession.Companion.session
import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelFuture
import io.netty.channel.EventLoopGroup
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioSocketChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.util.concurrent.ThreadFactory

class NetworkClient(val address: String, val port: Int) {

    fun connect(username: String, password: String) : Flow<ChannelFuture> {
        try {
            val bootstrap = Bootstrap()
            val workerGroup: EventLoopGroup = NioEventLoopGroup(1, ThreadFactory { Thread(it).also { th -> th.isDaemon = true } })
            bootstrap.group(workerGroup)
                .channel(NioSocketChannel::class.java)
                .remoteAddress(address, port)
                .handler(ClientChannelInitializer(username, password))
            val future = bootstrap.connect().sync()
            return flowOf(future)
        } catch(e: Exception) {
            e.printStackTrace()
        }
        error("Failed to connect to game server.")
    }

}