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

    private val size = 20
    private val rectangleSize:Double = 300.0/size

    override val root = vbox {
        val canvas: Canvas = canvas(300.0, 300.0)

        context = canvas.graphicsContext2D
        controller.addViewToModel(::refreshBoard)

        canvas.setOnMousePressed(::drawRectangle)
        canvas.setOnScroll {
            controller.evolve()
        }
    }

    private fun refreshBoard(cell: Pair<Int, Int>) {
        val y = cell.first / size
        val x = cell.first % size
        context.fill = colors[cell.second]
        context.fillRect(x * rectangleSize, y * rectangleSize, rectangleSize, rectangleSize)
        if (cell.second != 0) println(cell.first)
    }

    private fun drawRectangle(mouseEvent: MouseEvent) {
        val color = getRandomColor()
        context.fill = color
        context.fillRect(mouseEvent.x - mouseEvent.x % rectangleSize, mouseEvent.y - mouseEvent.y % rectangleSize, rectangleSize, rectangleSize)
        controller.model.board.list[mouseEvent.y.toInt() / rectangleSize.toInt() * size + mouseEvent.x.toInt() / rectangleSize.toInt()] = colors.indexOf(color)
    }
}
