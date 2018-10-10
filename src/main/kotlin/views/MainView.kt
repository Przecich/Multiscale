package views

import controllers.InventFancyNameController
import tornadofx.*

class MainView: View() {
    val controller: InventFancyNameController by inject()
    override val root = borderpane{
        center<GrainDisplay>()
        right{
            vbox{
                button("Interesting"){
                    action{
                        println("hurdur")
                        controller.evolve()
                    }
                }
            }
        }
    }
}