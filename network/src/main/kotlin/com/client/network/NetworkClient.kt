package com.client.network

import com.client.network.channel.ClientChannelInitializer
import com.client.network.session.NetworkSession
import com.client.network.session.NetworkSession.Companion.session
import io.netty.bootstrap.Bootstrap
import io.netty.channel.Channel
import io.netty.channel.ChannelFuture
import io.netty.channel.EventLoopGroup
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioSocketChannel
import org.jetbrains.kotlin.utils.addToStdlib.cast
import java.util.concurrent.ThreadFactory

class NetworkClient(val address: String, val port: Int) {

    lateinit var channel: Channel
    lateinit var workerGroup: EventLoopGroup

    fun connect(username: String, password: String) : NetworkSession? {
        try {
            val bootstrap = Bootstrap()
            workerGroup = NioEventLoopGroup(1, ThreadFactory { Thread(it).also { th -> th.isDaemon = true } })
            bootstrap.group(workerGroup)
                .channel(NioSocketChannel::class.java)
                .remoteAddress(address, port)
                .handler(ClientChannelInitializer(username, password))
            channel = bootstrap.connect().sync().channel()
            return channel.session
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun shutdown() : ChannelFuture {
        channel.disconnect()
        val future = channel.close().awaitUninterruptibly()
        workerGroup.shutdownGracefully()
        return future
    }

}