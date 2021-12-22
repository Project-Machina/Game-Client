package com.client.game.model.logs

data class LogData(val id: Int, val time: Long, val source: String, val message: String, val isHidden: Boolean = false)