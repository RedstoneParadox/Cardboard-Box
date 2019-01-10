package redstoneparadox.cardboardbox.misc

import redstoneparadox.cardboardbox.gui.GuiTree
import redstoneparadox.cardboardbox.gui.nodes.GuiNode

/**
 * Created by RedstoneParadox on 12/30/2018.
 */

/**
 * Interface to allow a class to control a GuiTree. Used when the class to instantiate the GuiTree is not the class that
 * controls it, such as with containers or when another mod instantiates the GuiTree.
 */
interface GuiTreeController {

    /**
     * Used to notify the controller when a tree is built so that it can perform checks and such.
     *
     * @param guiTree the tree that was instantiated.
     */
    fun receive(guiTree: GuiTree)

    /**
     * Notifies the controller if a GuiNode has been updated.
     *
     * @param guiNode the GuiNode that has been updated.
     */
    fun nodeUpdate(tree: GuiTree, guiNode: GuiNode) {

    }

    /**
     * Used to notify the controller when a tree's Container or Gui is closed so that the controller can remove any
     * references to the tree; you only need to override this if you store a reference to the tree.
     *
     * @param guiTree the tree that is being removed.
     */
    fun cleanup(guiTree: GuiTree) {

    }

}

