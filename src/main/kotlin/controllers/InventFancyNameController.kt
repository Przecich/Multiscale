package controllers

import tornadofx.Controller
import model.GrainGrowth

class InventFancyNameController: Controller(){
    var model = GrainGrowth(30,30)

    fun evolve(){
        model.growth()
    }

    fun addViewToModel(action: (Pair<Int,Int>)->Unit){
        model.observers.add(action)
    }
}