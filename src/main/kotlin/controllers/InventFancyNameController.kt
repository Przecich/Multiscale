package controllers

import tornadofx.Controller
import model.GrainGrowth
import model.Board

class InventFancyNameController: Controller(){
    var model = GrainGrowth

    fun evolve(){
        model.growth()
    }

    fun addViewToModel(action: (Pair<Int,Int>)->Unit){
        model.onUpdate=action
    }

    fun getBoardSize() = model.size


    fun changeSimulationSize(newSize: Int){
        model.board = Board(newSize)
    }
}