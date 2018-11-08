package views.components

import helpers.FileOperationsService
import javafx.scene.layout.BorderPane
import model.GrainGrowth
import tornadofx.action
import tornadofx.item
import tornadofx.menu
import tornadofx.menubar

fun BorderPane.addMenu(){
    menubar {
        menu("File") {
            menu("Connect") {
                item("Facebook")
                item("Twitter")
            }
            item("Load").action{
                FileOperationsService.saveToFile(GrainGrowth.board,GrainGrowth.size);
            }
            item("Save").action{
                FileOperationsService.saveToFile(GrainGrowth.board,GrainGrowth.size);
            }
            item("Quit")
        }
        menu("Edit") {
            item("Copy")
            item("Paste")
        }
    }
}
