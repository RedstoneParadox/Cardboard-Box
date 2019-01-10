package redstoneparadox.cardboardbox.gui.util

import redstoneparadox.cardboardbox.gui.GuiTree
import redstoneparadox.cardboardbox.misc.GuiTreeController

/**
 * Created by RedstoneParadox on 1/3/2019.
 */
object GuiTreeManager {

    private val trees : ArrayList<GuiTree> = ArrayList()

    private val controllers : ArrayList<GuiTreeController> = ArrayList()

    fun addTree(guiTree: GuiTree) {
        trees.add(guiTree)

        for (controller in controllers) {
            controller.receive(guiTree)
        }
    }

    fun removeTree(guiTree: GuiTree) {
        trees.remove(guiTree)

        for (controller in controllers) {
            controller.cleanup(guiTree)
        }
    }

    fun addController(controller: GuiTreeController) {
        controllers.add(controller)
    }

    fun removeController(controller: GuiTreeController) {
        controllers.remove(controller)
    }
}