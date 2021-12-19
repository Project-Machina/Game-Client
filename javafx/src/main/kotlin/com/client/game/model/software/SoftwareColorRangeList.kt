package com.client.game.model.software

import javafx.beans.DefaultProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList

@DefaultProperty("list")
class SoftwareColorRangeList(val list: ObservableList<SoftwareColorModel> = FXCollections.observableArrayList()) :
    ObservableList<SoftwareColorModel> by list