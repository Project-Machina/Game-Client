package com.client.packets.incoming

import com.client.game.model.remote.SystemAccountModel
import com.client.network.channel.packets.Packet
import com.client.network.channel.packets.handlers.PacketHandler
import com.client.network.readSimpleString
import com.client.scripting.Extensions.get
import tornadofx.runLater

class SystemAccountUpdate(override val opcode: Int = 10) : PacketHandler<SystemAccount, Unit> {
    override fun decode(packet: Packet): SystemAccount {
        val buf = packet.content
        val logout = buf.readBoolean()
        if(!logout) {
            val username = buf.readSimpleString()
            val permsSize = buf.readUnsignedByte().toInt()
            val perms = mutableListOf<String>()
            repeat(permsSize) {
                perms.add(buf.readSimpleString())
            }
            return SystemAccount(username, perms)
        }
        return SystemAccount("", listOf(), true)
    }

    override fun handle(message: SystemAccount) {
        val model: SystemAccountModel = get()
        if(message.logout) {
            runLater {
                model.username.set(null)
                model.permissions.clear()
            }
        } else {
            runLater {
                model.username.set(message.username)
                model.permissions.setAll(message.perms)
            }
        }
    }
}