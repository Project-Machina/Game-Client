package com.client.network.channel.packets

fun interface PacketEncoder<T> {

    fun encode(message: T) : Packet

}