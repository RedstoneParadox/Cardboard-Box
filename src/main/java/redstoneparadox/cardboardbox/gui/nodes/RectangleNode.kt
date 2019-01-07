package redstoneparadox.cardboardbox.gui.nodes

import net.minecraft.client.gui.Gui
import redstoneparadox.cardboardbox.gui.ContainerTreeGUI
import redstoneparadox.cardboardbox.misc.RGBAColor

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
class RectangleNode(name: String, x: Float, y: Float, var width : Float, var height : Float, var primaryColor : RGBAColor, var secondaryColor : RGBAColor) : GuiNode(name, x, y) {

    /**
     * An alternative constructor with only one color so it does not draw a gradient.
     */
    constructor(name: String, x: Float, y: Float, width : Float, height : Float, color : RGBAColor) : this(name, x, y, width, height, color, color)

    override fun drawSelf(gui: Gui, float: Float, int1: Int, int2: Int) {
        (gui as ContainerTreeGUI).drawColoredRectangle(x, y, width, height, primaryColor, secondaryColor)
    }
}