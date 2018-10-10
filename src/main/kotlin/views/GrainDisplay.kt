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
        val canvas: Canvas = canvas(300.0, 300.0)

        context = canvas.graphicsContext2D
        controller.addViewToModel(::refreshBoard)

        canvas.setOnMousePressed(::drawRectangle)
        canvas.setOnScroll {
            controller.evolve()
        }
    }

    private fun refreshBoard(cell: Pair<Int, Int>) {
        val y = cell.first / 30
        val x = cell.first % 30
        context.fill = colors[cell.second]
        context.fillRect(x * 10.0, y * 10.0, 10.0, 10.0)
        if (cell.second != 0) println(cell.first)
    }

    private fun drawRectangle(mouseEvent: MouseEvent) {
        val color = getRandomColor()
        context.fill = color
        context.fillRect(mouseEvent.x - mouseEvent.x % 10, mouseEvent.y - mouseEvent.y % 10, 10.0, 10.0)
        controller.model.list[mouseEvent.y.toInt() / 10 * 30 + mouseEvent.x.toInt() / 10] = colors.indexOf(color)
    }
}
