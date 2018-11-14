package views


import controllers.InventFancyNameController
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.input.MouseEvent
import model.GrainGrowth
import model.colorprovider.colors
import model.colorprovider.getRandomColor

import tornadofx.*


class GrainDisplay : View() {

    private val controller: InventFancyNameController by inject()

    private var context: GraphicsContext by singleAssign()



    override val root = vbox {
        val canvas: Canvas = canvas(1000.0, 1000.0)

        context = canvas.graphicsContext2D
        controller.addViewToModel(::refreshBoard)

        canvas.setOnMousePressed(::drawCell)
        canvas.setOnScroll {
            controller.evolve()
        }
    }

    private fun refreshBoard(cell: Pair<Int, Int>) {
        val rectangleSize = getRectangleSize()
        val y = cell.first / controller.getBoardSize()
        val x = cell.first % controller.getBoardSize()
        context.fill = colors[cell.second]
        val bla: Double = 2 * 2.0
        drawSquere(x * rectangleSize, y * rectangleSize, rectangleSize)
        if (cell.second != 0) println(cell.first)
    }

    private fun drawCell(mouseEvent: MouseEvent) {
        val rectangleSize = getRectangleSize()
        val color = getRandomColor()
        println("Board size:"+controller.getBoardSize())
        println("Rectangle size:"+rectangleSize.toInt())
        println("MouseEvent x:"+mouseEvent.x.toInt())
        println("MouseEvent y:"+mouseEvent.y.toInt())



        val postion =   mouseEvent.x.toInt() / rectangleSize.toInt() to mouseEvent.y.toInt() / rectangleSize.toInt()

        println("MPosition:"+postion)

        if(GrainGrowth.inclusion.isInclusionSelect) {
            GrainGrowth.drawInclusion(postion)
        }else{
            GrainGrowth.selectGrain(postion, color)
        }

    }

    private fun drawSquere(x:Double,y:Double, size: Double){
        context.fillRect(x ,y,  size, size)
    }

    private fun getRectangleSize() = Math.floor(1000.0 / controller.getBoardSize())
}
