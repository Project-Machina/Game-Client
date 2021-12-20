package com.client.packets.incoming

import com.client.game.model.software.SoftwareData
import com.client.game.model.software.SoftwareDataModel
import com.client.game.ui.software.SoftwareFragment
import com.client.network.channel.packets.Packet
import com.client.network.channel.packets.handlers.PacketHandler
import com.client.network.readSimpleString
import tornadofx.find
import tornadofx.runLater

class VirtualSoftwareUpdate(override val opcode: Int = 4) : PacketHandler<SoftwareData, Unit> {
    override fun decode(packet: Packet): SoftwareData {
        val buf = packet.content
        val id = buf.readSimpleString(true)
        val name = buf.readSimpleString()
        val extension = buf.readSimpleString()
        val version = buf.readDouble()
        val size = buf.readLong()
        val pid = buf.readInt()
        return SoftwareData(id, name, extension, version, size, pid)
    }

    override fun handle(message: SoftwareData) {
        val softwares = find<SoftwareFragment>()
        val model = softwares.model

        if (model.softwares.containsKey(message.id)) {
            val soft = model.softwares[message.id]!!
            runLater {
                soft.name.set(message.name)
                soft.extension.set(message.extension)
                soft.version.set(message.version)
                soft.size.set(message.size)
            }
        } else {
            runLater {
                model.softwares[message.id] = SoftwareDataModel(message)
            }
        }
    }
}