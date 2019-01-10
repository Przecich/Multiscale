package model

import com.google.gson.annotations.Expose
import javafx.scene.paint.Color

class Board(@Expose val size: Int,var onUpdate: (Pair<Int,Color>)->Unit) {
    @Expose var list = Array(size*size){Cell(Color.WHITE,0)}

    fun updateGrain(element: Int, color: Color, isRecrystalised: Boolean = false) {
        list[element].color = color
        list[element].isRecrysatlised = isRecrystalised
        list[element].energy = if(isRecrystalised) 0 else list[element].energy
        onUpdate(element to color)
    }

    fun updateGrain(element: Pair<Int,Int>,color:Color){
        updateGrain(toLineCoord(element.first,element.second),color)
    }

    fun updateColor(element: Int, color: Color) {
        onUpdate(element to color)
    }

    private fun toLineCoord(x: Int, y: Int) = size * y + x
}