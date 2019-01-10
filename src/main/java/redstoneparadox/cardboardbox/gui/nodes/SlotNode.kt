package redstoneparadox.cardboardbox.gui.nodes

import net.minecraft.client.gui.Gui
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.container.Slot
import redstoneparadox.cardboardbox.container.InventoryType
import redstoneparadox.cardboardbox.gui.ContainerTreeGUI
import redstoneparadox.cardboardbox.gui.GuiTree
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
class SlotNode(name: String, x: Float, y: Float, root : GuiTree, val type: InventoryType, val index: Int) : GuiNode(name, x, y, root) {

    override fun setupSelf(gui : Gui) {
        val slot : Slot = when (type) {
            InventoryType.CONTAINER -> (gui as ContainerTreeGUI).getCardboardContainer().getSlot(index + 36)
            InventoryType.PLAYER -> (gui as ContainerTreeGUI).getCardboardContainer().getSlot(index + 9)
            InventoryType.HOTBAR -> (gui as ContainerTreeGUI).getCardboardContainer().getSlot(index)
        }

        var actualX : Int = (x.roundToInt()) - gui.getLeft()
        var actualY : Int = (y.roundToInt()) - gui.getTop()

        slot.xPosition = actualX
        slot.yPosition = actualY

        if (gui.getCardboardContainer().player is ClientPlayerEntity) {
            NetworkUtil.syncSlot(actualX, actualY, index, gui.getCardboardContainer().syncId, (gui.getCardboardContainer().player) as ClientPlayerEntity)
        }
    }

    override fun createGridCopy(xShift: Float, yShift: Float, iteration: Int) : GuiNode {
        return SlotNode(name +  "_" + iteration.toString(), x + xShift, y + yShift, root, type, index + iteration)
    }

}