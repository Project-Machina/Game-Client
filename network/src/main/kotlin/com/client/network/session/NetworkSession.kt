package com.client.network.session

import com.client.network.channel.packets.Packet
import com.client.network.channel.packets.handlers.PacketHandler
import io.netty.channel.Channel
import io.netty.channel.ChannelFuture
import io.netty.util.AttributeKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.flow.*
import org.koin.core.context.GlobalContext
import org.koin.core.parameter.parametersOf
import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext

class NetworkSession(
    val channel: Channel,
) {

    lateinit var channelFuture: ChannelFuture

    val incomingPackets = MutableSharedFlow<Packet>(extraBufferCapacity = 255)

    val incomingHandlerJobs = mutableListOf<Job>()

    fun receivePacket(packet: Packet): Boolean {
        return incomingPackets.tryEmit(packet)
    }

    fun sendPacket(packet: Packet, flush: Boolean = true) {
        channel.write(packet)
        if (flush) {
            channel.flush()
        }
    }

    inline fun <reified T> sendMessage(message: T) {
        if (message != null) {
            sendPacket(message.toPacket())
        }
    }

    inline fun <reified M : Any, reified R : Any> handlePacket(handler: PacketHandler<M, R>) {
        incomingHandlerJobs.add(incomingPackets
            .filter { it.opcode == handler.opcode }
            .onEach {
                handler.handle(handler.decode(it))
                it.content.release()
            }
            .launchIn(NetworkSession))
    }

    fun shutdownGracefully() {
        incomingHandlerJobs.forEach { it.cancel() }
    }

    companion object : CoroutineScope {

        inline fun <reified M : Any> M.toPacket(): Packet {
            println(M::class.java.simpleName)
            val scope = GlobalContext.get().createScope<M>()
            return scope.get { parametersOf(this) }
        }

        val ATTRIBUTE_KEY = AttributeKey.valueOf<NetworkSession>("session")
        val Channel.session: NetworkSession get() = attr(ATTRIBUTE_KEY).get()
        override val coroutineContext: CoroutineContext = Executors.newSingleThreadExecutor().asCoroutineDispatcher()
    }
}