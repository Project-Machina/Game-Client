package com.client.network.channel.packets.handlers

import com.client.network.channel.packets.PacketDecoder

interface SimplePacketHandler<M> : PacketHandler<M, Unit>, PacketDecoder<M>