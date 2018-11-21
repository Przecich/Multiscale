package controllers

import javafx.scene.paint.Color
import tornadofx.Controller
import model.GrainGrowth
import model.Board

class InventFancyNameController: Controller(){
    var model = GrainGrowth

    fun evolve(){
        model.growth()
    }

    fun addViewToModel(action: (Pair<Int, Color>)->Unit){
        model.onUpdate=action
        model.board.onUpdate =model.onUpdate
    }

    fun getBoardSize() = model.size


    fun changeSimulationSize(newSize: Int){
        //model.board = Board(newSize)
    }
}