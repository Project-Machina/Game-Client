package com.client.game.model.processes

class ProcessData(
    val name: String,
    val pid: Int,
    val isPaused: Boolean,
    val elapsedTime: Long,
    val time: Long,
    val remove: Boolean = false,
    val isIndeterminate: Boolean = false,
    val isRemote: Boolean = false
)