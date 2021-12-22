package com.client.packets.incoming

import com.client.game.model.logs.LogData
import com.client.game.model.logs.LogDataModel
import com.client.game.ui.logs.LogsFragment
import com.client.network.channel.packets.Packet
import com.client.network.channel.packets.handlers.PacketHandler
import com.client.network.readSimpleString
import tornadofx.find
import tornadofx.runLater

class SystemLogUpdate(override val opcode: Int = 7) : PacketHandler<List<LogData>, Unit> {
    override fun decode(packet: Packet): List<LogData> {
        val buf = packet.content

        val size = buf.readUnsignedShort()

        val list = mutableListOf<LogData>()
        repeat(size) {
            val logId = buf.readInt()
            val source = buf.readSimpleString()
            val message = buf.readSimpleString()
            val time = buf.readLong()
            val isHidden = buf.readBoolean()
            list.add(LogData(logId, time, source, message, isHidden))
        }
        return list
    }

    override fun handle(message: List<LogData>) {
        val frag = find<LogsFragment>()
        val model = frag.logModel
        runLater {
            model.logs.clear()
            if (message.isNotEmpty()) {
                model.logs.setAll(message.map { LogDataModel(it) })
            }
        }
    }
}