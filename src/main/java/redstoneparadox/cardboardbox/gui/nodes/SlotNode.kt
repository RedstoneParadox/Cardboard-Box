package redstoneparadox.cardboardbox.gui.nodes

import net.minecraft.container.Slot
import redstoneparadox.cardboardbox.container.CardboardContainer
import redstoneparadox.cardboardbox.container.InventoryType
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

    override fun setupSelf(cardboardContainer: CardboardContainer) {
        val slot : Slot = when (type) {
            InventoryType.CONTAINER -> cardboardContainer.getSlot(index + 36)
            InventoryType.PLAYER -> cardboardContainer.getSlot(index + 9)
            InventoryType.HOTBAR -> cardboardContainer.getSlot(index)
        }

        slot.xPosition = x.roundToInt()
        slot.yPosition = y.roundToInt()
    }

    override fun createGridCopy(xShift: Float, yShift: Float, iteration: Int) : GuiNode {
        return SlotNode(name +  "_" + iteration.toString(), x + xShift, y + yShift, type, index + iteration)
    }

}