package redstoneparadox.cardboardbox.gui.nodes

import net.minecraft.client.font.FontRenderer
import redstoneparadox.cardboardbox.gui.GuiTree
import redstoneparadox.cardboardbox.gui.TreeGui
import redstoneparadox.cardboardbox.gui.nodes.interfaces.GuiTreeElement

/**
 * Created by RedstoneParadox on 12/30/2018.
 */

/**
 * The base component for all other GUI tree nodes.
 *
 * @param name the specific name of the component in the tree.
 * @param x The x position on screen.
 * @param y The y position on screen.
 * @param root the GuiTree.
 *
 * Unless otherwise noted in child classes, all constructor parameters here have the same uses.
 */
open class GuiNode(var name: String, override var x: Int, override var y: Int, var root: GuiTree) : GuiTreeElement {

    var children : ArrayList<GuiNode> = ArrayList()

    private val originalX = x
    private val originalY = y

    fun setup(treeGui: TreeGui, tree: GuiTree, treeElement: GuiTreeElement) {
        root = tree

        x += treeElement.x
        y += treeElement.y
        setupSelf(treeGui)
        setupChildren(treeGui, tree, treeElement)
    }

    /**
     * Function for setup of all GuiNodes in a GuiTree,
     *
     * @param gui the Gui this node's tree is attached to.
     */
    open fun setupSelf(treeGui: TreeGui) {

    }

    private fun setupChildren(treeGui: TreeGui, tree: GuiTree, treeElement: GuiTreeElement) {

        if (children.isEmpty()) {
            return
        }

        for (child in children) {
            child.setup(treeGui, tree, treeElement)
        }
    }

    fun cleanup() {
        cleanupSelf()
        cleanupChildren()
    }

    /**
     * Function used for any cleanup operations that may need to occur.
     */
    open fun cleanupSelf() {

    }

    private fun cleanupChildren() {
        for (child in children) {
            child.cleanup()
        }
    }

    fun draw(treeGui: TreeGui, float: Float, int1: Int, int2: Int, fontRenderer: FontRenderer) {
        drawSelf(treeGui, float, int1, int2)
        drawChildren(treeGui, float, int1, int2, fontRenderer)

        x = originalX
        y = originalY
    }

    /**
     * Function for drawing the GuiNodes on screen.
     *
     * @param gui the gui to draw to.
     * @param float Unknown Minecraft parameter.
     * @param int1 Unknown Minecraft parameter.
     * @param int2 Unknown.
     */
    open fun drawSelf(treeGui: TreeGui, float: Float, int1: Int, int2: Int) {

    }

    private fun drawChildren(treeGui: TreeGui, float: Float, int1: Int, int2: Int, fontRenderer: FontRenderer) {

        if (children.isEmpty()) {
            return
        }

        for (child in children) {
            child.draw(treeGui, float, int1, int2, fontRenderer)
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

    /**
     * Function that creates a copy of this node and moves it to fit in a grid.
     *
     * @param xShift the number of pixels to shift this in the X direction.
     * @param yShift the number of pixels to shift this in the Y direction.
     * @param iteration the number of this node. Iteration 0 is the original copy of the node.
     */
    open fun createGridCopy(xShift: Int, yShift: Int, iteration: Int): GuiNode {
        return GuiNode(name + "_" + iteration.toString(), x + xShift, y + yShift, root)
    }
}