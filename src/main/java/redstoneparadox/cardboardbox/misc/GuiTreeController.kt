package redstoneparadox.cardboardbox.misc

import redstoneparadox.cardboardbox.gui.GuiTree

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
     * Used to notify the controller when a tree's Container or Gui is closed.
     *
     * @param guiTree the tree that is being removed.
     */
    fun removeTree(guiTree: GuiTree)

}

