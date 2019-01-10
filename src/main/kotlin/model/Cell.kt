package model

import javafx.scene.paint.Color

data class Cell(var color:Color = Color.WHITE, var energy: Int = 0, var isRecrysatlised:Boolean = false)