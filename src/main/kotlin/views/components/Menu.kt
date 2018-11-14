package views.components

import helpers.FileOperationsService
import javafx.application.Platform
import javafx.scene.layout.BorderPane
import model.GrainGrowth
import tornadofx.action
import tornadofx.item
import tornadofx.menu
import tornadofx.menubar
import javax.swing.JFileChooser
import java.io.File



fun BorderPane.addMenu(){
    menubar {
        menu("File") {
            menu("Save us") {
                item("JSON").action{
                    val fileChooser =  JFileChooser()
                    val returnVal = fileChooser.showSaveDialog(null)

                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        val file = fileChooser.selectedFile
                        FileOperationsService.saveToFile(file.path)
                    }
                }
                item("Bitmap").action{
                    val fileChooser =  JFileChooser()
                    val returnVal = fileChooser.showSaveDialog(null)

                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        val file = fileChooser.selectedFile
                        FileOperationsService.saveToBmp(file.path)
                    }
                }
            }
            menu("Load from") {
                item("JSON").action{
                    val fileChooser =  JFileChooser()
                    val returnVal = fileChooser.showOpenDialog(null)

                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        val file = fileChooser.selectedFile
                        FileOperationsService.loadFromFile(file.path)
                    }


                }
                item("Bitmap").action{
                    val fileChooser =  JFileChooser()
                    val returnVal = fileChooser.showOpenDialog(null)

                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        val file = fileChooser.getSelectedFile()
                        FileOperationsService.loadFromBmp(file.path)
                    }
                }
            }
            item("Quit").action{
                Platform.exit()
            }
        }
    }

}
