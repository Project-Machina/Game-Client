package com.client.packets.message

class PlayerStatisticsMessage(
    val linkIP: String,
    val remoteIP: String,
    val time: Long,
    val rank: Int,
    val nextRankProgress: Int,
    val experienceForNextRank: Int
)