package model

import com.google.gson.annotations.Expose
import javafx.scene.paint.Color

class Board(@Expose val size: Int,var onUpdate: (Pair<Int,Color>)->Unit) {
    @Expose var list = Array<Color>(size*size){Color.WHITE}

    fun updateGrain(element: Int, color: Color) {
        list[element] = color
        onUpdate(element to color)
    }

    fun updateGrain(element: Pair<Int,Int>,color:Color){
        updateGrain(toLineCoord(element.first,element.second),color)
    }

    private fun toLineCoord(x: Int, y: Int) = size * y + x
}