package redstoneparadox.cardboardbox.registry

import net.minecraft.util.Identifier
import redstoneparadox.cardboardbox.gui.GuiTree

/**
 * Created by RedstoneParadox on 12/30/2018.
 */
object CardboardGuiTreeRegistry {

    private var guiTrees : ArrayList<GuiTree> = ArrayList()

    fun initTrees() {

    }

    fun registerTree(guiTree: GuiTree) {

        if (guiTrees.isEmpty()) {
            guiTrees.add(guiTree)
            return
        }

        for (tree in guiTrees) {
            if (tree.identifier == guiTree.identifier) {
                println("Tree ${guiTree.identifier} already exists!")
                return
            }
        }

        guiTrees.add(guiTree)
        return
    }

    fun getComponentTree(identifier: Identifier) : GuiTree? {

        for (tree in guiTrees) {

            if (tree.identifier == identifier) {
                return tree
            }
        }

        return null
    }
}