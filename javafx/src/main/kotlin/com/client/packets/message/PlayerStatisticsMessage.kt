package com.client.packets.message

class PlayerStatisticsMessage(
    val linkIP: String,
    val remoteIP: String,
    val time: Long,
    val rank: Int,
    val nextRankProgress: Int,
    val experienceForNextRank: Int,
    val storageRackName: String,
    val availableDiskSpace: Long,
    val driveUsage: Long,
    val motherboardName: String,
    val availableRam: Long,
    val ramUsage: Long,
    val availableThreads: Int,
    val threadUsage: Int,
    val processCount: Int
)