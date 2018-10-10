package controllers

import tornadofx.Controller
import model.GrainGrowth
import model.Board

class InventFancyNameController: Controller(){
    var model = GrainGrowth(20)

    fun evolve(){
        model.growth()
    }

    fun addViewToModel(action: (Pair<Int,Int>)->Unit){
        model.onUpdate=action
    }

    fun changeSimulationSize(newSize: Int){
        model.board = Board(newSize)
    }
}