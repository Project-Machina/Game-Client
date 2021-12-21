package com.client.packets.incoming

import com.client.game.model.software.SoftwareData
import com.client.game.model.software.SoftwareDataModel
import com.client.game.ui.software.SoftwareFragment
import com.client.network.channel.packets.Packet
import com.client.network.channel.packets.handlers.PacketHandler
import com.client.network.readSimpleString
import tornadofx.find
import tornadofx.runLater

class VirtualSoftwareUpdate(override val opcode: Int = 4) : PacketHandler<List<SoftwareData>, Unit> {
    override fun decode(packet: Packet): List<SoftwareData> {
        val buf = packet.content

        val softSize = buf.readUnsignedShort()
        val list = mutableListOf<SoftwareData>()
        repeat(softSize) {
            val id = buf.readSimpleString(true)
            val name = buf.readSimpleString()
            val extension = buf.readSimpleString()
            val isHidden = buf.readBoolean()
            val version = buf.readDouble()
            val size = buf.readLong()
            val pid = buf.readInt()
            list.add(SoftwareData(id, name, extension, version, size, pid, isHidden = isHidden))
        }
        return list
    }

    override fun handle(message: List<SoftwareData>) {
        val softwares = find<SoftwareFragment>()
        val model = softwares.model
        runLater {
            model.softwares.clear()
            model.softwares.putAll(message.map { SoftwareDataModel(it) }.associateBy { it.id.get() })
        }
    }
}