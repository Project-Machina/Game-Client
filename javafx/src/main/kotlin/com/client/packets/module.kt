package com.client.packets

import com.client.network.channel.packets.PacketEncoder
import com.client.packets.outgoing.PingMessage
import org.koin.core.module.Module
import org.koin.dsl.module

val outgoingPackets = module {
    MessageEncoder(PingMessage)
}

inline fun <reified M : Any> Module.MessageEncoder(encoder: PacketEncoder<M>) {
    scope<M> {
        scoped { encoder }
        factory { (msg: M) ->
            encoder.encode(msg)
        }
    }
}