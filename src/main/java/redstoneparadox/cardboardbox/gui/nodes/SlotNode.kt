package redstoneparadox.cardboardbox.gui.nodes

import net.minecraft.client.gui.Gui
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.container.Slot
import redstoneparadox.cardboardbox.container.CardboardContainer
import redstoneparadox.cardboardbox.container.InventoryType
import redstoneparadox.cardboardbox.gui.ContainerTreeGUI
import redstoneparadox.cardboardbox.networking.NetworkUtil
import kotlin.math.roundToInt

/**
 * Created by RedstoneParadox on 12/31/2018.
 */

/**
 * A helper component for drawing inventory slots on the screen.
 *
 * @param index the index of the slot to draw.
 */
class SlotNode(name: String, x: Float, y: Float, val type: InventoryType, val index: Int) : GuiNode(name, x, y) {

    override fun setupSelf(gui : Gui) {
        val slot : Slot = when (type) {
            InventoryType.CONTAINER -> (gui as ContainerTreeGUI).container.getSlot(index + 36)
            InventoryType.PLAYER -> (gui as ContainerTreeGUI).container.getSlot(index + 9)
            InventoryType.HOTBAR -> (gui as ContainerTreeGUI).container.getSlot(index)
        }

        slot.xPosition = x.roundToInt()
        slot.yPosition = y.roundToInt()

        if ((gui.container as CardboardContainer).player is ClientPlayerEntity) {
            NetworkUtil.syncSlot(x.toInt(), y.toInt(), index, gui.container.syncId, ((gui.container as CardboardContainer).player) as ClientPlayerEntity)
        }
    }

    override fun createGridCopy(xShift: Float, yShift: Float, iteration: Int) : GuiNode {
        return SlotNode(name +  "_" + iteration.toString(), x + xShift, y + yShift, type, index + iteration)
    }

}