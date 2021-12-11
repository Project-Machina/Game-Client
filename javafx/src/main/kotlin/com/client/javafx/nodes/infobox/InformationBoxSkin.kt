package com.client.javafx.nodes.infobox

import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.control.Label
import javafx.scene.control.SkinBase
import javafx.scene.layout.AnchorPane
import tornadofx.bindChildren

class InformationBoxSkin(control: InformationBox) : SkinBase<InformationBox>(control) {

    @FXML
    lateinit var infoTitle: Label

    @FXML
    lateinit var contentContainer: AnchorPane

    init {
        val loader = FXMLLoader(this::class.java.getResource("infobox.fxml"))
        loader.setController(this)
        val parent = loader.load<AnchorPane>()
        children.setAll(parent)

        infoTitle.textProperty().bind(control.titleProperty())
        contentContainer.bindChildren(control.getContent()) { it }
    }

}