package com.client.game.model.software.coloring

import javafx.beans.DefaultProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList

@DefaultProperty("list")
class SoftwareVersionColorRangeList(val list: ObservableList<SoftwareVersionColorModel> = FXCollections.observableArrayList()) :
    ObservableList<SoftwareVersionColorModel> by list