package com.client.packets.incoming

import com.client.game.model.logs.LogData
import com.client.game.model.logs.LogDataModel
import com.client.game.ui.logs.LogsFragment
import com.client.network.channel.packets.Packet
import com.client.network.channel.packets.handlers.PacketHandler
import com.client.network.readSimpleString
import tornadofx.find
import tornadofx.runLater

class VirtualEventUpdate(override val opcode: Int = 6) : PacketHandler<LogData, Unit> {
    override fun decode(packet: Packet): LogData {
        val buf = packet.content

        val eventId = buf.readInt()
        val timestamp = buf.readLong()
        val source = buf.readSimpleString()
        val message = buf.readSimpleString()

        return LogData(eventId, timestamp, source, message)
    }

    override fun handle(message: LogData) {
        val frag = find<LogsFragment>()
        val model = frag.logModel
        runLater {
            if(model.logs.containsKey(message.id)) {
                val data = model.logs[message.id]!!
                data.id.set(message.id)
                data.time.set(message.time)
                data.source.set(message.source)
                data.message.set(message.message)
            } else {
                model.logs[message.id] = LogDataModel(message)
            }
        }

    }
}