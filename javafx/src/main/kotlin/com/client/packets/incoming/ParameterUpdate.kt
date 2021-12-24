package com.client.packets.incoming

import com.client.game.model.ParameterModel
import com.client.network.channel.packets.Packet
import com.client.network.channel.packets.handlers.PacketHandler
import com.client.network.readSimpleString
import com.client.scripting.Extensions.get

class ParameterUpdate(override val opcode: Int = 11) : PacketHandler<Pair<Map<String, ParameterUpdate.StringParameter>, Map<String, ParameterUpdate.IntParameter>>, Unit> {
    override fun decode(packet: Packet): Pair<Map<String, StringParameter>, Map<String, IntParameter>> {
        val intParams = mutableMapOf<String, IntParameter>()
        val stringParams = mutableMapOf<String, StringParameter>()
        val buf = packet.content
        val clear = buf.readBoolean()
        if (!clear) {
            val size = buf.readUnsignedShort()
            repeat(size) {
                val key = buf.readSimpleString()
                val remove = buf.readBoolean()
                val isString = buf.readBoolean()
                if(remove) {
                    if (isString) {
                        stringParams[key] = StringParameter("", true)
                    } else {
                        intParams[key] = IntParameter(0, true)
                    }
                } else if(isString) {
                    stringParams[key] = StringParameter(buf.readSimpleString())
                } else {
                    intParams[key] = IntParameter(buf.readInt())
                }
            }
        }
        println(stringParams.size)
        println(intParams.size)
        return stringParams to intParams
    }

    override fun handle(message: Pair<Map<String, StringParameter>, Map<String, IntParameter>>) {
        val (stringParams, intParams) = message
        val model: ParameterModel = get()
        stringParams.forEach { (t, u) ->
            if(u.remove)
                model.stringParams.remove(t)
            else
                model.stringParams[t] = u.value
        }

        intParams.forEach { (t, u) ->
            if(u.remove)
                model.integerParams.remove(t)
            else
                model.integerParams[t] = u.value
        }
    }

    class StringParameter(val value: String, val remove: Boolean = false)
    class IntParameter(val value: Int, val remove: Boolean = false)
}