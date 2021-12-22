package com.client.scripting

import com.client.packets.outgoing.VmCommandMessage

object Actions {

    @JvmStatic
    fun connect(address: String) {
        val session = Extensions.session
        session?.sendMessage(VmCommandMessage("connect $address", false))
    }

}