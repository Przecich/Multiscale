package views


import controllers.InventFancyNameController
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.input.MouseEvent
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
        drawSquere(x * rectangleSize, y * rectangleSize, rectangleSize)
        if (cell.second != 0) println(cell.first)
    }

    private fun drawCell(mouseEvent: MouseEvent) {
        val rectangleSize = getRectangleSize()
        val color = getRandomColor()
        context.fill = color
        drawSquere(mouseEvent.x - mouseEvent.x % rectangleSize,
                mouseEvent.y - mouseEvent.y % rectangleSize,
                rectangleSize)
        println(rectangleSize)

        controller.model.board.list[mouseEvent.y.toInt() / rectangleSize.toInt() * controller.getBoardSize() + mouseEvent.x.toInt() / rectangleSize.toInt()] = colors.indexOf(color)
    }

    private fun drawSquere(x:Double,y:Double, size: Double){
        context.fillRect(x ,y,  size, size)
    }

    private fun getRectangleSize() = 1000.0 / controller.getBoardSize()

}
