package redstoneparadox.cardboardbox.gui

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.Identifier
import redstoneparadox.cardboardbox.container.InventoryType
import redstoneparadox.cardboardbox.gui.nodes.GuiNode
import redstoneparadox.cardboardbox.gui.nodes.SlotNode
import redstoneparadox.cardboardbox.gui.util.GuiTreeManager

/**
 * Created by RedstoneParadox on 1/2/2019.
 */

/**
 * Helper class for building Gui Trees. Do note that the GUI tree needs to be built on the client and the server as
 * SlotNodes need to be able to update the position of their respective slots on both sides
 *
 * @param identifier The identifier for the built tree.
 * @param playerEntity the player that opens the GUI.
 */
class GuiTreeBuilder(val identifier: Identifier, val playerEntity: PlayerEntity, val treeGui: TreeGui, val x: Int, val y: Int) {

    val tree : GuiTree = GuiTree(identifier, playerEntity, treeGui, x, y)

    /**
     * Adds a node to the root of the tree.
     *
     * @param node the node to add.
     */
    fun addNode(node : GuiNode) : GuiTreeBuilder {
        tree.children.add(node)
        return this
    }

    /**
     * Adds a node as a child of the node specified in the path.
     *
     * @param node the node to add.
     * @param path the path of the parent node in the tree.
     */
    fun addNode(node : GuiNode, path : String) {
        TODO("Figure out how paths will work.")
    }

    /**
     * Creates a grid of the same type of node.
     *
     * @param node the node to add to the the tree and be copied into a grid.
     * @param rows the number of rows in the grid.
     * @param columns the number of columns in the grid.
     * @param rowSize the height of each row.
     * @param columnSize the width of each column.
     */
    fun addNodeGrid(node: GuiNode, rows : Int, columns : Int, rowSize : Int, columnSize : Int) : GuiTreeBuilder {
        var iteration : Int = 0

        for (j in 0 until rows) {

            for (i in 0 until columns) {

                if (iteration == 0) {
                    node.name = node.name + "_0"
                    tree.children.add(node)
                    iteration = 1
                }
                else {
                    tree.children.add(node.createGridCopy((columnSize * i), (rowSize * j), iteration))
                    iteration += 1
                }
            }
        }

        return this
    }

    /**
     * Creates a grid of the same type of node as children of the node specified in the path.
     *
     * @param node the node to add to the the tree and be copied into a grid.
     * @param rows the number of rows in the grid.
     * @param columns the number of columns in the grid.
     * @param rowSize the height of each row.
     * @param columnSize the width of each column.
     * @param path the path of the parent node in the tree.
     */
    fun addNodeGrid(node: GuiNode, rows : Int, columns : Int, rowSize : Float, columnSize : Float, path: String) {
        TODO("Figure out how paths will work.")
    }

    /**
     * Creates a grid of the passed nodes.
     */
    fun addNodeGridOf(vararg nodes: GuiNode, rows: Int, columns: Int, rowSize: Float, columnSize : Float, path: String) {
        TODO("Stop working on this and start working on Tech Suite por favor!")
    }

    /**
     * Sets SlotNodes to draw the player inventory (3 rows + hotbar) in the GuiTree the same way it appears in vanilla
     * chests, furnaces, etc.
     *
     * @param xPos the x position of the player inventory.
     * @param yPos the y position of the player inventory.
     *
     * Setting the coordinates to (0, 83) should put the player inventory in the same position that it appears in for
     * vanilla containers but the exact value may depend on the size of the container.
     */
    fun addPlayerInventory(xPos : Int, yPos : Int): GuiTreeBuilder {
        addNodeGrid(SlotNode("player_slot", xPos, yPos, tree, InventoryType.PLAYER, 0), 3, 9, 18, 18)
        addNodeGrid(SlotNode("hotbar_slot", xPos, yPos + 58, tree, InventoryType.HOTBAR, 0), 1, 9, 18, 18)

        return this
    }

    /**
     *
     */

    /**
     * Builds the tree.
     */
    fun build() : GuiTree {
        GuiTreeManager.addTree(tree)
        return tree
    }
}