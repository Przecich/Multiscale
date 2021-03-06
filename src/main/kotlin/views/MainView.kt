package views

import controllers.InventFancyNameController
import javafx.collections.FXCollections
import javafx.scene.control.ToggleGroup
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import model.GrainGrowth
import model.inclusions.InclusionType
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
                hbox{
                    spacing = 10.0
                    button("Remove grains"){
                        action{
                            controller.model.reset()
                        }
                    }
                    button("Draw borders"){
                        action{
                            GrainGrowth.drawBorders()
                        }
                    }

                    button("Clear N.Borders"){
                        action{
                            GrainGrowth.resetNonBorders()
                        }
                    }
                    button("Restart"){
                        action{
                            controller.model.fullReset()
                        }
                    }
                }


                sizeMenu()

                hbox{
                    val textfield = textfield {

                    }
                    button("Generate").action {
                        GrainGrowth.selectRandom(textfield.text.toInt())
                    }
                }


                hbox {
                    spacing = 10.0
                    neighbourDropDown()
                }




                hbox {
                    inclusionMenu()
                }
                hbox{
                    spacing = 10.0
                    button("Monte Carlo").action {
                        GrainGrowth.monteCarloGeneration(7)
                    }

                    button("Monte Carlo iteration").action {
                        GrainGrowth.monteCarloIteration(1)
                    }

                    textfield("2") {
                        textProperty().addListener { _, _, c ->
                            GrainGrowth.monteCarloColors =  if(c.isEmpty())  1 else c.toInt()
                        }
                    }
                }

                hbox{
                    spacing = 10.0
                    textfield("0") {
                        textProperty().addListener { _, _, c ->
                            GrainGrowth.energyIn =  if(c.isEmpty())  0 else c.toInt()
                        }
                    }
                    textfield("0") {
                        textProperty().addListener { _, _, c ->
                            GrainGrowth.eneryBorder =  if(c.isEmpty())  0 else c.toInt()
                        }
                    }

                    button("Distribute energy").action {
                        GrainGrowth.distributeEnergy()
                    }

                    button("Show Energy").action {
                        GrainGrowth.displayEnergy()
                    }

                    button("Revert Energy").action {
                        GrainGrowth.loadStructure()
                    }

                }

                hbox{
                    spacing = 10.0
                    textfield("0") {
                        textProperty().addListener { _, _, c ->
                            GrainGrowth.nucleaNumber =  if(c.isEmpty())  0 else c.toInt()
                        }
                    }
                    togglegroup{
                        togglebutton("Border")
                        selectedToggleProperty().addListener { _,_,new ->
                            GrainGrowth.isBorder = new != null
                        }

                    }

                    togglegroup{
                        togglebutton("Constant").userData = "Const"
                        togglebutton("Increasing").userData = "Incr"
                        togglebutton ("Site").userData = "Site"
                        selectedToggleProperty().addListener { _,_,new ->
                            GrainGrowth.increaseType = new.userData.toString()
                        }


                    }


                    button("Generate").action {
                        GrainGrowth.generateNucleons()
                    }


                }
                hbox{
                    spacing = 10.0


                    button("Recrystalise").action {
                        GrainGrowth.recrystalise()
                    }


                }
            }
        }
    }

    private fun HBox.inclusionMenu(){
        spacing = 10.0
        togglebutton("Inclusions select"){
            isSelected = false
            action {
                GrainGrowth.inclusion.isInclusionSelect = isSelected
            }
        }
        vbox{
            val toggleGroup = ToggleGroup()
            toggleGroup.selectedToggleProperty().addListener { _,_,toggle ->
                println(toggle.userData)
                GrainGrowth.inclusion.inlucisonType = (toggle.userData) as InclusionType
            }
            radiobutton("Radius", toggleGroup){
                userData = InclusionType.RADIUS
                isSelected = true
            }
            radiobutton("Size", toggleGroup).userData = InclusionType.SIZE

        }
        textfield("1") {
            textProperty().addListener { _, _, c ->
                GrainGrowth.inclusion.size = c.toInt()
            }
        }
    }

    private fun VBox.sizeMenu(){
        label("Size of simulation")
        textfield("50") {
            textProperty().addListener { _, _, c ->
                GrainGrowth.refreshBoard(if(c.isEmpty()) 100 else  c.toInt())
            }
        }
    }

    private fun HBox.neighbourDropDown(){
        label("Neighbour type: ")
        combobox(controller.model.neighbourhoodType,
                FXCollections.observableArrayList("Neuman","Moore 2.0"))
                .selectionModel.selectFirst()
        textfield { bind(GrainGrowth.mooreValue) }
    }
}