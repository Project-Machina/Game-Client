package com.client.packets.incoming

import com.client.game.model.animations.AnimationModel
import com.client.game.model.processes.ProcessData
import com.client.game.model.processes.ProcessDataModel
import com.client.game.model.processes.ProcessesModel
import com.client.game.ui.processes.ProcessDialogFragment.Companion.showProcess
import com.client.network.channel.packets.Packet
import com.client.network.channel.packets.handlers.PacketHandler
import com.client.network.readSimpleString
import com.client.scripting.Extensions
import tornadofx.runAsync
import tornadofx.runLater
import tornadofx.ui

class VirtualProcessCreate(override val opcode: Int = 8) : PacketHandler<ProcessData, Unit> {

    override fun decode(packet: Packet): ProcessData {
        val buf = packet.content
        val isRemote = buf.readBoolean()
        val immediate = buf.readBoolean()
        if (!immediate) {
            val pid = buf.readInt()
            val isPaused = buf.readBoolean()
            val isIndeterminate = buf.readBoolean()
            val remove = buf.readBoolean()
            val name = buf.readSimpleString()
            val elapsedTime = buf.readLong()
            val preferredRunningTime = buf.readLong()
            return ProcessData(
                name,
                pid,
                isPaused,
                elapsedTime,
                preferredRunningTime,
                remove,
                isIndeterminate,
                isRemote
            )
        }
        return ProcessData("", -1, false, 0, 0)
    }

    override fun handle(message: ProcessData) {
        if(message.pid != -1) {
            val model: ProcessesModel = Extensions.get()
            val animModel: AnimationModel = Extensions.get()
            runLater {
                val m = ProcessDataModel(message)
                model.processes[message.pid] = m
                if (!message.isIndeterminate) {
                    val anim = animModel.currentAnim
                    runAsync {
                        if(anim.get() != null) {
                            anim.get().play()
                        }
                    }
                    m.showProcess(message.isRemote) {
                        if(anim.get() != null)
                            anim.get().stop()
                    }
                }
            }
        }
    }
}