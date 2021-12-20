package com.client.game.model.hardware.machines

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleLongProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.ItemViewModel

class MachineDataModel(vm: VirtualMachineData) : ItemViewModel<VirtualMachineData>(vm) {

    val name = bind { SimpleStringProperty(this, "name", vm.name) }
    val address = bind { SimpleStringProperty(this, "address", vm.address) }

    val ramCapacity = bind { SimpleLongProperty(this, "ram_capacity", vm.ramCapacity) }
    val availableRam = bind { SimpleLongProperty(this, "available_ram", vm.availableRam) }

    val storageCapacity = bind { SimpleLongProperty(this, "storage_capacity", vm.storageCapacity) }
    val availableStorage = bind { SimpleLongProperty(this, "available_storage", vm.availableStorage) }

    val threadCapacity = bind { SimpleIntegerProperty(this, "thread_capacity", vm.threadCapacity) }
    val availableThreads = bind { SimpleIntegerProperty(this, "available_threads", vm.availableThreads) }

    val networkCardCapacity = bind { SimpleIntegerProperty(this, "network_card_capacity", vm.networkCardCapacity) }
    val availableNetwork = bind { SimpleIntegerProperty(this, "available_network", vm.availableNetwork) }

    val mbWatts = bind { SimpleIntegerProperty(this, "available_network", vm.mbWatts) }
    val rackWatts = bind { SimpleIntegerProperty(this, "available_network", vm.rackWatts) }
    val networkWatts = bind { SimpleIntegerProperty(this, "available_network", vm.networkWatts) }

    val isLinked = bind { SimpleBooleanProperty(this, "is_linked", vm.isLinked) }

    val domain = bind { SimpleStringProperty(this, "domain", vm.domain) }

    override fun onCommit() {
        val id = item.id
        item = VirtualMachineData(
            id,
            name.get(),
            address.get(),
            domain.get(),
            ramCapacity.get(),
            availableRam.get(),
            storageCapacity.get(),
            availableStorage.get(),
            threadCapacity.get(),
            availableThreads.get(),
            networkCardCapacity.get(),
            availableNetwork.get(),
            mbWatts.get(),
            rackWatts.get(),
            networkWatts.get(),
            isLinked.get()
        )
    }
}