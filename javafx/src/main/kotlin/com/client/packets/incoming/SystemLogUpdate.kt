package com.client.packets.incoming

import com.client.game.model.internet.InternetModel
import com.client.game.model.logs.LogData
import com.client.game.model.logs.LogDataModel
import com.client.game.ui.logs.LogsFragment
import com.client.network.channel.packets.Packet
import com.client.network.channel.packets.handlers.PacketHandler
import com.client.network.readSimpleString
import com.client.scripting.Extensions.get
import tornadofx.find
import tornadofx.runLater

class SystemLogUpdate(override val opcode: Int = 7) : PacketHandler<SystemLogUpdate.LogUpdate, Unit> {
    override fun decode(packet: Packet): LogUpdate {
        val buf = packet.content

        val size = buf.readUnsignedShort()
        val isRemote = buf.readBoolean()

        val list = mutableListOf<LogData>()
        repeat(size) {
            val logId = buf.readInt()
            val source = buf.readSimpleString()
            val message = buf.readSimpleString()
            val time = buf.readLong()
            val isHidden = buf.readBoolean()
            list.add(LogData(logId, time, source, message, isHidden))
        }
        return LogUpdate(list, isRemote)
    }

    override fun handle(message: LogUpdate) {
        if(message.isRemote) {
            val model: InternetModel = get()
            runLater {
                model.logs.clear()
                if (message.logs.isNotEmpty()) {
                    model.logs.setAll(message.logs.map { LogDataModel(it) })
                }
            }
        } else {
            val frag = find<LogsFragment>()
            val model = frag.logModel
            val logs = message.logs
            runLater {
                model.logs.clear()
                if (logs.isNotEmpty()) {
                    model.logs.setAll(logs.map { LogDataModel(it) })
                }
            }
        }


    }

    class LogUpdate(val logs: List<LogData>, val isRemote: Boolean)
}