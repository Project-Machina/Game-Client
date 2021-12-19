package com.client.game.ui.software

import com.client.game.formatSize
import com.client.game.model.software.SoftwareDataModel
import com.client.game.model.software.coloring.SoftwareColorSizeRangeList
import javafx.beans.binding.Bindings
import javafx.scene.control.Label
import javafx.scene.layout.AnchorPane
import tornadofx.Fragment
import tornadofx.c

class SoftwareSizeFragment : Fragment() {

    override val root : AnchorPane by fxml("software-size-cell.fxml")

    val sizeLabel: Label by fxid()

    val colorRange: SoftwareColorSizeRangeList by fxid()

    fun bind(data: SoftwareDataModel) {

        sizeLabel.textFillProperty().bind(Bindings.createObjectBinding({
            val version = data.size.get().toLong()
            for (scm in colorRange) {
                if(version in scm.getMinValue()..scm.getMaxValue()) {
                    return@createObjectBinding c(scm.getColor())
                }
            }
            c("90EE90FF")
        }, data.size))

        sizeLabel.textProperty().bind(Bindings.createStringBinding({
            formatSize(data.size.get())
        }, data.size))
    }

}