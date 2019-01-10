package helpers

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import javafx.scene.paint.Color
import model.Board
import model.Cell
import model.GrainGrowth
import java.io.File
import java.awt.image.BufferedImage
import javax.imageio.ImageIO


object FileOperationsService {
    fun saveToFile(path:String){

        val jsonParser = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()

        File(path).printWriter().use { out ->
            out.println(jsonParser.toJson(GrainGrowth.board))
        }
    }

    fun loadFromFile(path:String){
        val jsonBoard = File(path).inputStream().readBytes().toString(Charsets.UTF_8)
        val board = Gson().fromJson(jsonBoard,Board::class.java)
        GrainGrowth.loadBoard(board, board.size)

    }

    fun saveToBmp(path:String){
        val grainColors = GrainGrowth.board.list.map { it.color }
        val size = GrainGrowth.board.size

        val bufferedImage = BufferedImage(size, size,
                BufferedImage.TYPE_INT_RGB)

        for (x in 0 until size) {
            for (y in 0 until size) {
                var oldColor = grainColors[y*size+x]
                val newColor = java.awt.Color(oldColor.getRed().toFloat(),
                        oldColor.getGreen().toFloat() ,
                        oldColor.getBlue().toFloat() ,
                        oldColor.getOpacity().toFloat() )

                bufferedImage.setRGB(x, y, newColor.rgb)
            }
        }

        val outputfile = File(path)
        ImageIO.write(bufferedImage, "bmp", outputfile)
    }

    fun loadFromBmp(path:String){
        val bi = ImageIO.read(File(path))
        val size = bi.width
        GrainGrowth.board.list =  Array(size*size){ Cell(Color.WHITE,0) }

        for (x in 0 until size) {
            for (y in 0 until size) {
                val pixel = bi.getRGB(x,y)
                val color = java.awt.Color(pixel)
                GrainGrowth.selectGrain(x to y,Color.rgb(color.red,color.green,color.blue))
            }
        }
    }
}