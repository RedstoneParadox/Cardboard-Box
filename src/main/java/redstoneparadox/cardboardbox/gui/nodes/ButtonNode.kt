package redstoneparadox.cardboardbox.gui.nodes

import redstoneparadox.cardboardbox.gui.GuiTree
import redstoneparadox.cardboardbox.gui.nodes.interfaces.InteractiveNode

/**
 * Created by RedstoneParadox on 1/8/2019.
 */

/**
 * A class for creating simple buttons.
 */
class ButtonNode(name: String, x: Int, y: Int, root: GuiTree, override var width: Int, override var height: Int, var toggleable : Boolean) : GuiNode(name, x, y, root), InteractiveNode {

    override var isMouseInside: Boolean = false
    override var isMousePressed: Boolean = false

    var isPressed : Boolean = false

    override fun onMousePressed() {
        isPressed = if (toggleable) {
            !isPressed
        }
        else {
            true
        }

        root.updateListeners(this)
    }

    override fun onMouseReleased() {
        if (!toggleable) {
            isPressed = false
            root.updateListeners(this)
        }
    }

    override fun onMouseExit() {
        if (!toggleable) {
            isPressed = false
            root.updateListeners(this)
        }
    }
}