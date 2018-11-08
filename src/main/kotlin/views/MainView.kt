package views

import controllers.InventFancyNameController
import javafx.collections.FXCollections
import javafx.scene.layout.VBox
import model.GrainGrowth
import tornadofx.*
import views.components.addMenu

class MainView: View() {
    val controller: InventFancyNameController by inject()

    override val root = borderpane{
        top{
            addMenu()
        }
        center<GrainDisplay>()
        right{
            vbox{
                spacing = 10.0
                button("Restart"){
                    action{
                        println("hurdur")
                        controller.model.reset()
                    }
                }
                vbox {
                    sizeMenu()
                }
                vbox{
                    neighbourDropDown()
                }
            }
        }
    }

    fun VBox.sizeMenu(){
        label("Size of simulation")
        textfield("50") {
            textProperty().addListener { _, _, c ->
                GrainGrowth.refreshBoard(c.toInt())
                println(c.toInt())
            }
        }
    }

    fun VBox.neighbourDropDown(){
        label("Neighbour type: ")
        combobox(controller.model.neighbourhoodType,
                FXCollections.observableArrayList("Moore","Neuman"))
                .selectionModel.selectFirst()
    }
}