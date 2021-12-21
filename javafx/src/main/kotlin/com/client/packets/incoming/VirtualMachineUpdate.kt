package com.client.packets.incoming

import com.client.game.formatSize
import com.client.game.model.hardware.machines.MachineDataModel
import com.client.game.model.hardware.machines.VirtualMachineData
import com.client.game.ui.hardware.HardwareFragment
import com.client.network.channel.packets.Packet
import com.client.network.channel.packets.handlers.PacketHandler
import com.client.network.readSimpleString
import tornadofx.find
import tornadofx.runLater

class VirtualMachineUpdate(override val opcode: Int = 5) : PacketHandler<VirtualMachineData, Unit> {
    override fun decode(packet: Packet): VirtualMachineData {
        val buf = packet.content

        val id = buf.readSimpleString(true)
        val name = buf.readSimpleString()
        val ip = buf.readSimpleString()
        val domain = buf.readSimpleString()
        val isLinked = buf.readBoolean()

        val ramCapacity = buf.readLong()
        val availableRam = buf.readLong()

        val storageCapacity = buf.readLong()
        val availableStorage = buf.readLong()

        val threadCapacity = buf.readInt()
        val availableThreads = buf.readInt()

        val networkCardCapacity = buf.readInt()
        val availableNetwork = buf.readInt()

        val mbWatts = buf.readInt()
        val rackWatts = buf.readInt()
        val networkWatts = buf.readInt()
        return VirtualMachineData(
            id,
            name,
            ip,
            domain,
            ramCapacity,
            availableRam,
            storageCapacity,
            availableStorage,
            threadCapacity,
            availableThreads,
            networkCardCapacity,
            availableNetwork,
            mbWatts,
            rackWatts,
            networkWatts,
            isLinked
        )
    }

    override fun handle(message: VirtualMachineData) {
        /*if(model.virtualMachines.containsKey(message.id)) {
            runLater {
                val vm = model.virtualMachines[message.id]!!
                vm.name.set(message.name)
                vm.address.set(message.address)
                vm.ramCapacity.set(message.ramCapacity)
                vm.availableRam.set(message.availableRam)
                vm.storageCapacity.set(message.storageCapacity)
                vm.availableStorage.set(message.availableStorage)
                vm.threadCapacity.set(message.threadCapacity)
                vm.availableThreads.set(message.availableThreads)
                vm.networkCardCapacity.set(message.networkCardCapacity)
                vm.availableNetwork.set(message.availableNetwork)
                vm.mbWatts.set(message.mbWatts)
                vm.rackWatts.set(message.rackWatts)
                vm.networkWatts.set(message.networkWatts)

                vm.isLinked.set(message.isLinked)
            }
        } else {
        }*/
        runLater {
            val hardware = find<HardwareFragment>()
            val model = hardware.model
            model.virtualMachines[message.id] = MachineDataModel(message)
        }
    }
}