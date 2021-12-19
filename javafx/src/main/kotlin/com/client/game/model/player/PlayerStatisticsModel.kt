package com.client.game.model.player

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleLongProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.ViewModel

class PlayerStatisticsModel : ViewModel() {

    val nextRank = bind { SimpleIntegerProperty(this, "next_rank", 2) }
    val rankProgress = bind { SimpleIntegerProperty(this, "rank_progress", 0) }

    val storageRackName = bind { SimpleStringProperty(this, "storage_rank_name", "") }
    val motherboardName = bind { SimpleStringProperty(this, "motherboard_name", "") }

    val availableDiskSpace = bind { SimpleLongProperty(this, "available_disk_space", 0) }
    val driveUsage = bind { SimpleLongProperty(this, "available_disk_space", 0) }

    val availableRam = bind { SimpleLongProperty(this, "available_ram", 0) }
    val ramUsage = bind { SimpleLongProperty(this, "ram_usage", 0) }

    val availableThreads = bind { SimpleIntegerProperty(this, "available_threads", 0) }
    val threadUsage = bind { SimpleIntegerProperty(this, "thread_usage", 0) }
    val processCount = bind { SimpleIntegerProperty(this, "process_count", 0) }

}