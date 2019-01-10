package redstoneparadox.cardboardbox.gui.nodes

import redstoneparadox.cardboardbox.gui.GuiTree
import redstoneparadox.cardboardbox.gui.nodes.interfaces.InteractiveNode
import redstoneparadox.cardboardbox.gui.util.MouseUtil

/**
 * Created by RedstoneParadox on 1/7/2019.
 */

/**
 * Basic node that detects when it is being hovered over with the cursor.
 */
class HoverNode(name: String, x: Float, y: Float, root : GuiTree, override var width : Float, override var height : Float) : GuiNode(name, x, y, root), InteractiveNode {

    override var isMouseInside: Boolean = false
    override var isMousePressed: Boolean = false

    init {
        MouseUtil.addListener(this)
    }

    override fun cleanupSelf() {
        MouseUtil.removeListener(this)
    }

    override fun onMouseEnter() {
        root.updateListeners(this)
    }

    override fun onMouseExit() {
        root.updateListeners(this)
    }
}