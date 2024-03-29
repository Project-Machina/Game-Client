package com.client.packets.incoming

import com.client.game.model.internet.InternetModel
import com.client.game.model.software.SoftwareData
import com.client.game.model.software.SoftwareDataModel
import com.client.game.model.software.SoftwareModel
import com.client.game.ui.software.SoftwareFragment
import com.client.network.channel.packets.Packet
import com.client.network.channel.packets.handlers.PacketHandler
import com.client.network.readSimpleString
import com.client.scripting.Extensions.get
import tornadofx.find
import tornadofx.runLater

class VirtualSoftwareUpdate(override val opcode: Int = 4) : PacketHandler<VirtualSoftwareUpdate.SoftwareUpdate, Unit> {
    override fun decode(packet: Packet): SoftwareUpdate {
        val buf = packet.content

        val softSize = buf.readUnsignedShort()
        val isRemote = buf.readBoolean()
        val list = mutableListOf<SoftwareData>()
        repeat(softSize) {
            val id = buf.readSimpleString(true)
            val name = buf.readSimpleString()
            val extension = buf.readSimpleString()
            val isHidden = buf.readBoolean()
            val version = buf.readDouble()
            val size = buf.readLong()
            val pid = buf.readInt()
            list.add(SoftwareData(id, name, extension, version, size, pid, remote = isRemote, isHidden = isHidden))
        }
        return SoftwareUpdate(list, isRemote)
    }

    override fun handle(message: SoftwareUpdate) {
        val softs = message.softwares
        println(message.isRemote)
        if(message.isRemote) {
            val model: InternetModel = get()
            runLater {
                model.softwares.clear()
                if(softs.isNotEmpty()) {
                    model.softwares.putAll(softs.map { SoftwareDataModel(it) }.associateBy { it.id.get() })
                }
            }
        } else {
            val model: SoftwareModel = get()
            runLater {
                model.softwares.clear()
                if (softs.isNotEmpty()) {
                    model.softwares.putAll(softs.map { SoftwareDataModel(it) }.associateBy { it.id.get() })
                }
            }
        }
    }

    class SoftwareUpdate(val softwares: List<SoftwareData>, val isRemote: Boolean)
}