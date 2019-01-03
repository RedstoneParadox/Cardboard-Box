package redstoneparadox.cardboardbox.misc

import redstoneparadox.cardboardbox.gui.GuiTree

/**
 * Created by RedstoneParadox on 12/30/2018.
 */

/**
 * Interface to allow another class to control the GUI. Use if you need to control the GUI from a specific instance of
 * another class, otherwise extend the GUI itself and write your control code there.
 */
interface GuiController {

    /**
     * The tree
     */
    var guiTree : GuiTree

}

