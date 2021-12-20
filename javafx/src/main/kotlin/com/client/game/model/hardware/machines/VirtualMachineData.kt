package com.client.game.model.hardware.machines

class VirtualMachineData(
    val id: String,
    val name: String,
    val address: String,
    val domain: String,
    val ramCapacity: Long,
    val availableRam: Long,
    val storageCapacity: Long,
    val availableStorage: Long,
    val threadCapacity: Int,
    val availableThreads: Int,
    val networkCardCapacity: Int,
    val availableNetwork: Int,
    val mbWatts: Int,
    val rackWatts: Int,
    val networkWatts: Int,
    val isLinked: Boolean = false
)