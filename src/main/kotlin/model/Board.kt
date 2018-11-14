package model

import com.google.gson.annotations.Expose

class Board(@Expose val size: Int,var onUpdate: (Pair<Int,Int>)->Unit) {
    @Expose var list = IntArray(size * size).toMutableList()

    fun updateGrain(element: Int, color: Int) {
        list[element] = color
        onUpdate(element to color)
    }

    fun updateGrain(element: Pair<Int,Int>,color:Int){
        updateGrain(toLineCoord(element.first,element.second),color)
    }

    private fun toLineCoord(x: Int, y: Int) = size * y + x
}