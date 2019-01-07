package redstoneparadox.cardboardbox.gui.nodes

import net.minecraft.client.font.FontRenderer
import net.minecraft.client.gui.Gui

/**
 * Created by RedstoneParadox on 12/30/2018.
 */

/**
 * The base component for all other GUI tree nodes.
 *
 * @param name the specific name of the component in the tree.
 * @param x The x position on screen.
 * @param y The y position on screen.
 *
 * Unless otherwise noted in child classes, all constructor parameters here have the same uses.
 */
open class GuiNode(var name : String, var x : Float, var y : Float) {

    var children : ArrayList<GuiNode> = ArrayList()

    fun setup(gui : Gui) {
        setupSelf(gui)
        setupChildren(gui)
    }

    /**
     * Function for client-sided setup of all GuiNodes in a GuiTree,
     *
     * @param gui the Gui this node's tree is attached to.
     */
    open fun setupSelf(gui: Gui) {

    }

    private fun setupChildren(gui : Gui) {

        if (children.isEmpty()) {
            return
        }

        for (child in children) {
            child.setup(gui)
        }
    }

    fun draw(gui: Gui, float: Float, int1: Int, int2: Int, fontRenderer: FontRenderer) {
        drawSelf(gui, float, int1, int2)
        drawChildren(gui, float, int1, int2, fontRenderer)
    }

    /**
     * Function for drawing the GuiNodes on screen.
     *
     * @param gui the gui to draw to.
     * @param float Unknown Minecraft parameter.
     * @param int1 Unknown Minecraft parameter.
     * @param int2 Unknown.
     * @param fontRenderer The font renderer.
     */
    open fun drawSelf(gui: Gui, float: Float, int1: Int, int2: Int) {

    }

    /**
     * Function that creates a copy of this node and moves it to fit in a grid.
     *
     * @param xShift the number of pixels to shift this in the X direction.
     * @param yShift the number of pixels to shift this in the Y direction.
     * @param iteration the number of this node. Iteration 0 is the original copy of the node.
     */
    open fun createGridCopy(xShift: Float, yShift: Float, iteration: Int): GuiNode {
        return GuiNode(name + "_" + iteration.toString(), x + xShift, y + yShift)
    }

    private fun drawChildren(gui: Gui, float: Float, int1: Int, int2: Int, fontRenderer: FontRenderer) {

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

    /**
     * Adds a child to this node, provided it doesn't already have a child of the same name.
     *
     * @param guiNode the node to add as a child.
     */
    fun addChild(guiNode: GuiNode) {

        for (child in children) {

            if (child.name == guiNode.name) {
                println("A node called ${guiNode.name} already exists!")
                return
            }
        }

        children.add(guiNode)
    }
}