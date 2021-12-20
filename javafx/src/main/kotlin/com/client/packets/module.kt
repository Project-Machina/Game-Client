package com.client.packets

import com.client.network.channel.packets.PacketEncoder
import com.client.packets.outgoing.LogoutMessage
import com.client.packets.outgoing.PingMessage
import com.client.packets.outgoing.VmCommandMessage
import com.client.packets.outgoing.WidgetChangeMessage
import org.koin.core.module.Module
import org.koin.dsl.module

val outgoingPackets = module {
    MessageEncoder(PingMessage)
    MessageEncoder(VmCommandMessage)
    MessageEncoder(LogoutMessage)
    MessageEncoder(WidgetChangeMessage)
}

inline fun <reified M : Any> Module.MessageEncoder(encoder: PacketEncoder<M>) {
    scope<M> {
        scoped { encoder }
        factory { (msg: M) ->
            encoder.encode(msg)
        }
    }
}