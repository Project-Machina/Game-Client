package com.client.network.channel.packets

import io.netty.buffer.ByteBuf

data class Packet(val opcode: Int, val content: ByteBuf)