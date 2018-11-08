package helpers

import com.google.gson.Gson
import model.Board
import java.io.File

object FileOperationsService {
    fun saveToFile(board: Board, size:Int){
        val saveObject = BoardSaveFormat(board,size)
        val jsonBoard = Gson().toJson(saveObject);
        println(jsonBoard)
        File("somefile.txt").printWriter().use { out ->
                out.println(jsonBoard)
        }
    }

    fun loadFromFile(){
        File("dupa.txt").inputStream().readBytes().toString(Charsets.UTF_8)
    }
    data class BoardSaveFormat(val board: Board,val size:Int)
}