package com.client.packets.incoming

import com.client.game.model.processes.ProcessData
import com.client.game.model.processes.ProcessDataModel
import com.client.game.model.processes.ProcessesModel
import com.client.network.channel.packets.Packet
import com.client.network.channel.packets.handlers.PacketHandler
import com.client.network.readSimpleString
import com.client.scripting.Extensions.get
import tornadofx.runLater

class VirtualProcessUpdate(override val opcode: Int = 3) : PacketHandler<ProcessData, Unit> {

    override fun decode(packet: Packet): ProcessData {
        val buf = packet.content

        val immediate = buf.readBoolean()
        if (!immediate) {
            val pid = buf.readInt()
            val isPaused = buf.readBoolean()
            val remove = buf.readBoolean()
            val name = buf.readSimpleString()
            val elapsedTime = buf.readLong()
            val preferredRunningTime = buf.readLong()
            return ProcessData(name, pid, isPaused, elapsedTime, preferredRunningTime, remove)
        }
        return ProcessData("", -1, false, 0, 0)
    }

    override fun handle(message: ProcessData) {
        if(message.pid != -1) {
            val model: ProcessesModel = get()
            runLater {
                if(model.processes.containsKey(message.pid)) {
                    if(message.remove) {
                        model.processes.remove(message.pid)
                    } else {
                        val data = model.processes[message.pid]!!
                        data.pid.set(message.pid)
                        data.name.set(message.name)
                        data.isPaused.set(message.isPaused)
                        data.timeElapsed.set(message.elapsedTime)
                        data.time.set(message.time)
                    }
                } else {
                    model.processes[message.pid] = ProcessDataModel(message)
                }
            }
        }
    }
}