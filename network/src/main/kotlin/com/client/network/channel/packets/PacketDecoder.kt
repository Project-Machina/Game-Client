package com.client.network.channel.packets

fun interface PacketDecoder<T> {

    fun decode(packet: Packet) : T

}