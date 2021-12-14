package com.client.packets.incoming

import com.client.game.ui.gameframe.GameFrameView
import com.client.network.channel.packets.Packet
import com.client.network.channel.packets.handlers.PacketHandler
import com.client.network.readSimpleString
import com.client.packets.message.PlayerStatisticsMessage
import tornadofx.find
import tornadofx.runLater

class PlayerStatistics(override val opcode: Int = 2) : PacketHandler<PlayerStatisticsMessage, Unit> {
    override fun decode(packet: Packet): PlayerStatisticsMessage {
        val buf = packet.content
        val linkIP = buf.readSimpleString()
        val remoteIP = buf.readSimpleString()
        val time = buf.readLong()
        val rank = buf.readInt()
        val nextRankProgress = buf.readInt()
        val experienceForNextRank = buf.readInt()
        return PlayerStatisticsMessage(linkIP, remoteIP, time, rank, nextRankProgress, experienceForNextRank)
    }

    override fun handle(message: PlayerStatisticsMessage) {
        val gameframe = find<GameFrameView>()
        val model = gameframe.gameframeModel

        runLater {
            model.linkIP.set(message.linkIP)
            model.remoteIP.set(message.remoteIP)
            model.rank.set(message.rank)
            model.time.set(message.time)
        }

    }
}