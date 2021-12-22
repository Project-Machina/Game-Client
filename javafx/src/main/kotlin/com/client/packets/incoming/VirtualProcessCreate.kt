package com.client.packets.incoming

import com.client.game.model.processes.ProcessData
import com.client.game.model.processes.ProcessDataModel
import com.client.game.model.processes.ProcessesModel
import com.client.game.ui.processes.ProcessDialogFragment.Companion.showProcess
import com.client.network.channel.packets.Packet
import com.client.network.channel.packets.handlers.PacketHandler
import com.client.network.readSimpleString
import com.client.scripting.Extensions
import tornadofx.runLater

class VirtualProcessCreate(override val opcode: Int = 8) : PacketHandler<ProcessData, Unit> {

    override fun decode(packet: Packet): ProcessData {
        val buf = packet.content

        val immediate = buf.readBoolean()
        if (!immediate) {
            val pid = buf.readInt()
            val isPaused = buf.readBoolean()
            val isIndeterminate = buf.readBoolean()
            val remove = buf.readBoolean()
            val name = buf.readSimpleString()
            val elapsedTime = buf.readLong()
            val preferredRunningTime = buf.readLong()
            return ProcessData(name, pid, isPaused, elapsedTime, preferredRunningTime, remove, isIndeterminate)
        }
        return ProcessData("", -1, false, 0, 0)
    }

    override fun handle(message: ProcessData) {
        if(message.pid != -1) {
            val model: ProcessesModel = Extensions.get()
            runLater {
                val m = ProcessDataModel(message)
                model.processes[message.pid] = m
                if (!message.isIndeterminate) {
                    m.showProcess()
                }
            }
        }
    }
}