package redstoneparadox.cardboardbox.gui

import net.minecraft.client.font.FontRenderer
import net.minecraft.client.gui.Gui
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.Identifier
import redstoneparadox.cardboardbox.gui.nodes.GuiNode
import redstoneparadox.cardboardbox.gui.nodes.interfaces.GuiTreeElement
import redstoneparadox.cardboardbox.misc.GuiTreeController

/**
 * Created by RedstoneParadox on 12/30/2018.
 */
class GuiTree(val identifier: Identifier, val player : PlayerEntity, val gui: Gui, override var x: Float, override var y : Float) : GuiTreeElement {

    var children : ArrayList<GuiNode> = ArrayList()
    var listeners : ArrayList<GuiTreeController> = ArrayList()

    fun setup(gui: Gui) {

        if (children.isEmpty()) {
            return
        }

        for (child in children) {
            child.setup(gui, this, this)
        }
    }

    fun drawChildren(gui: Gui, float: Float, int1: Int, int2: Int, fontRenderer: FontRenderer) {

        if (children.isEmpty()) {
            return
        }

        for (child in children) {
            child.draw(gui, float, int1, int2, fontRenderer)
        }
    }

    fun getChild(name : String) : GuiNode? {

        for (child in children) {
            if (child.name == name) {
                return child
            }
        }

        return null
    }

    fun updateListeners(guiNode: GuiNode) {
        for (listener in listeners) {
            listener.nodeUpdate(this, guiNode)
        }
    }

    fun cleanup() {
        for (child in children) {
            child.cleanup()
        }
    }
}