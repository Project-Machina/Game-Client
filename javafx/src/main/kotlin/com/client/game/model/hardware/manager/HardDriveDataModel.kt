package com.client.game.model.hardware.manager

import javafx.beans.property.SimpleStringProperty
import tornadofx.ItemViewModel

class HardDriveDataModel(name: String, sizeDesc: String) : ItemViewModel<HardDriveData>(HardDriveData(name, sizeDesc)) {

    val driveName = bind { SimpleStringProperty(this, "drive_name", name) }
    val sizeDesc = bind { SimpleStringProperty(this, "size_desc", sizeDesc) }


    override fun onCommit() {
        item = HardDriveData(driveName.get(), sizeDesc.get())
    }
}