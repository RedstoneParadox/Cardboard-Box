package redstoneparadox.cardboardbox.gui.nodes

import redstoneparadox.cardboardbox.gui.GuiTree
import redstoneparadox.cardboardbox.gui.TreeGui

/**
 * Created by RedstoneParadox on 12/30/2018.
 */

/**
 * A Gui Tree Component that draws a string to the GUI.
 *
 * @param text The text to draw to the GUI.
 */
class LabelNode(name: String, x: Int, y: Int, root : GuiTree, var text: String) : GuiNode(name, x, y, root) {

    override fun drawSelf(treeGui: TreeGui, float: Float, int1: Int, int2: Int) {
        treeGui.drawString(text, x, y, false)
    }

    override fun createGridCopy(xShift: Int, yShift: Int, iteration: Int) : GuiNode {
        return LabelNode(name + "_" + iteration.toString(), x + xShift, y + yShift, root, text)
    }
}