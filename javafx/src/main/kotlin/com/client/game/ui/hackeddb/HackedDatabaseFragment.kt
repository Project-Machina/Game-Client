package com.client.game.ui.hackeddb

import com.client.game.model.hackeddb.HackedDatabaseModel
import com.client.game.model.hackeddb.HackedMachineDataModel
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.layout.AnchorPane
import tornadofx.Fragment
import tornadofx.SmartResize

class HackedDatabaseFragment : Fragment("Hacked Database") {

    val model: HackedDatabaseModel by inject()

    override val root: AnchorPane by fxml("hackeddb.fxml")

    val hackedTable: TableView<HackedDatabaseModel> by fxid()

    val addressColumn: TableColumn<HackedMachineDataModel, String> by fxid()
    val accountsColumn: TableColumn<HackedMachineDataModel, String> by fxid()
    val malwareColumn: TableColumn<HackedMachineDataModel, String> by fxid()
    val actionsColumn: TableColumn<HackedMachineDataModel, String> by fxid()

    init {
        hackedTable.columnResizePolicy = SmartResize.POLICY

        addressColumn.setCellValueFactory { it.value.address }

    }

}