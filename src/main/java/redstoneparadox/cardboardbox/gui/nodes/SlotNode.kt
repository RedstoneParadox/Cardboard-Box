package redstoneparadox.cardboardbox.gui.nodes

import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.container.Container
import net.minecraft.container.Slot
import redstoneparadox.cardboardbox.container.InventoryType
import redstoneparadox.cardboardbox.gui.GuiTree
import redstoneparadox.cardboardbox.gui.TreeGui
import redstoneparadox.cardboardbox.networking.NetworkUtil

/**
 * Created by RedstoneParadox on 12/31/2018.
 */

/**
 * A helper component for drawing inventory slots on the screen.
 *
 * @param index the index of the slot to draw.
 */
class SlotNode(name: String, x: Int, y: Int, root : GuiTree, val type: InventoryType, val index: Int) : GuiNode(name, x, y, root) {

    override fun setupSelf(treeGui: TreeGui) {

        val container : Container? = treeGui.getContainer()

        if (container == null) {
            println("Error: The Gui associated with this GuiTree does not have a container!")
            return
        }

        val slot: Slot
        val trueIndex : Int = when(type) {
            InventoryType.CONTAINER -> index + 36
            InventoryType.PLAYER -> index + 9
            InventoryType.HOTBAR -> index
        }

        if (container.slotList.size <= trueIndex) {
            println("Error: The Slot at index [$index] does not exist!")
            return
        }

        slot = container.getSlot(trueIndex)

        val actualX : Int = x - treeGui.getLeft()
        val actualY : Int = y - treeGui.getTop()

        slot.xPosition = actualX
        slot.yPosition = actualY

        if (treeGui.player is ClientPlayerEntity) {
            NetworkUtil.syncSlot(actualX, actualY, index, container.syncId, (treeGui.player) as ClientPlayerEntity)
        }
    }

    override fun createGridCopy(xShift: Int, yShift: Int, iteration: Int) : GuiNode {
        return SlotNode(name +  "_" + iteration.toString(), x + xShift, y + yShift, root, type, index + iteration)
    }

}