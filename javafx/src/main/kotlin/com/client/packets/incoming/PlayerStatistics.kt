package com.client.packets.incoming

import com.client.game.model.player.PlayerStatisticsModel
import com.client.game.ui.gameframe.GameFrameView
import com.client.network.channel.packets.Packet
import com.client.network.channel.packets.handlers.PacketHandler
import com.client.network.readSimpleString
import com.client.packets.message.PlayerStatisticsMessage
import com.client.scripting.Extensions.get
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

        val storageRackName = buf.readSimpleString()
        val availableDiskSpace = buf.readLong()
        val driveUsage = buf.readLong()

        val motherboardName = buf.readSimpleString()
        val availableRam = buf.readLong()
        val ramUsage = buf.readLong()

        val availableThreads = buf.readInt()
        val threadUsage = buf.readInt()
        val processCount = buf.readInt()

        return PlayerStatisticsMessage(
            linkIP,
            remoteIP,
            time,
            rank,
            nextRankProgress,
            experienceForNextRank,
            storageRackName,
            availableDiskSpace,
            driveUsage,
            motherboardName,
            availableRam,
            ramUsage,
            availableThreads,
            threadUsage,
            processCount
        )
    }

    override fun handle(message: PlayerStatisticsMessage) {
        val gameframe = find<GameFrameView>()
        val model = gameframe.gameframeModel

        val player: PlayerStatisticsModel = get()

        runLater {
            model.linkIP.set(message.linkIP)
            model.remoteIP.set(message.remoteIP)
            model.rank.set(message.rank)
            model.time.set(message.time)

            player.storageRackName.set(message.storageRackName)
            player.motherboardName.set(message.motherboardName)
            player.availableDiskSpace.set(message.availableDiskSpace)
            player.driveUsage.set(message.driveUsage)
            player.availableRam.set(message.availableRam)
            player.ramUsage.set(message.ramUsage)
            player.availableThreads.set(message.availableThreads)
            player.threadUsage.set(message.threadUsage)
            player.processCount.set(message.processCount)
        }

    }
}