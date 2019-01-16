package redstoneparadox.cardboardbox.gui.nodes

import redstoneparadox.cardboardbox.gui.GuiTree
import redstoneparadox.cardboardbox.gui.TreeGui
import redstoneparadox.cardboardbox.gui.util.RGBAColor

/**
 * Created by RedstoneParadox on 1/6/2019.
 */

/**
 * A class for drawing colored.
 *
 * @param width the width of the rectangle.
 * @param height the height of the rectangle.
 * @param primaryColor the primary color of the node.
 * @param secondaryColor the secondary color of the node.
 */
class ColoredRectNode(name: String, x: Int, y: Int, root: GuiTree, var width : Int, var height : Int, var primaryColor : RGBAColor, var secondaryColor : RGBAColor) : GuiNode(name, x, y, root) {

    /**
     * An alternative constructor with only one color so it does not draw a gradient.
     */
    constructor(name: String, x: Int, y: Int, root: GuiTree, width : Int, height : Int, color : RGBAColor) : this(name, x, y, root, width, height, color, color)

    override fun drawSelf(treeGui: TreeGui, float: Float, int1: Int, int2: Int) {
        treeGui.drawColoredRectangle(x, y, width, height, primaryColor, secondaryColor)
    }
}