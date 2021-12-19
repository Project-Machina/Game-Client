package com.client.game.model.software.coloring

import javafx.beans.DefaultProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList

@DefaultProperty("list")
class SoftwareColorSizeRangeList(val list: ObservableList<SoftwareSizeColorModel> = FXCollections.observableArrayList()) :
    ObservableList<SoftwareSizeColorModel> by list