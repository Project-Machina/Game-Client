package com.client.game.ui.software

import com.client.game.model.software.SoftwareColorRangeList
import com.client.game.model.software.SoftwareDataModel
import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleListProperty
import javafx.scene.control.Label
import javafx.scene.layout.AnchorPane
import javafx.scene.paint.Color
import tornadofx.Fragment
import tornadofx.c

class SoftwareVersionFragment : Fragment() {

    override val root : AnchorPane by fxml("software-version-cell.fxml")

    val versionLabel: Label by fxid()

    val colorRange: SoftwareColorRangeList by fxid()

    fun bind(data: SoftwareDataModel) {

        versionLabel.textFillProperty().bind(Bindings.createObjectBinding({
            val version = data.version.get()
            for (scm in colorRange) {
                if(version in scm.getMinValue()..scm.getMaxValue()) {
                    return@createObjectBinding c(scm.getColor())
                }
            }
            c("90EE90FF")
        }, data.version))

        versionLabel.textProperty().bind(Bindings.createStringBinding({
            data.version.get().toString()
        }, data.version))
    }

}