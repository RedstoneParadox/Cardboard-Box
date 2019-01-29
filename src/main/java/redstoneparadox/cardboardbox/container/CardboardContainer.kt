package redstoneparadox.cardboardbox.container

import net.minecraft.block.entity.BlockEntity
import net.minecraft.container.Container
import net.minecraft.container.ContainerType
import net.minecraft.container.Slot
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventory
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import redstoneparadox.cardboardbox.networking.NetworkUtil

/**
 * Created by RedstoneParadox on 12/30/2018.
 */
class CardboardContainer(syncID : Int, val pos: BlockPos, val player : PlayerEntity, val id : Identifier) : Container(null, syncID) {

    private val playerInventory : PlayerInventory = player.inventory

    override fun getType(): ContainerType<CardboardContainer>? {
        return null
    }

    init {

        inventoryToSlots()
        if (!player.world.isClient()) {
            NetworkUtil.listenForUpdate(this)
        }
    }

    private fun inventoryToSlots() {
        val inventory: BlockEntity? = player.world.getBlockEntity(pos)

        addPlayerSlots()

        if (inventory != null && inventory is Inventory) {
            addInventorySlots(inventory)
        }

    }

    private fun addPlayerSlots() {

        for (i in 0 until 9) {
            addSlot(Slot(playerInventory, i, (18 * i) + 8, 142))
        }

        for (row in 0 until 3) {

            for (column in 0 until 9) {
                addSlot(Slot(playerInventory, column + row * 9 + 9, (18 * column) + 8, (18 * row) + 84))
            }
        }
    }

    private fun addInventorySlots(inventory : Inventory) {
        checkContainerSize(inventory, inventory.invSize)
        inventory.onInvOpen(player)


        val rows = 3
        val columns = 9

        var shouldBreak : Boolean = false

        for (row in 0 until rows) {

            for (column in 0 until columns) {
                val slotIndex : Int = column + row * 9

                if (inventory.invSize > slotIndex) {
                    addSlot(Slot(inventory, slotIndex, (18 * column) + 8, 18 + (18 * row)))
                }
                else {
                    shouldBreak = true
                    break
                }
            }

            if (shouldBreak) break
        }
    }

    override fun canUse(player: PlayerEntity): Boolean {
        return true
    }

    fun hasSlots(): Boolean {
        return slotList.isNotEmpty()
    }

    fun syncSlotPosition(x : Int, y : Int, index : Int) {
        val slot = slotList[index]

        slot.xPosition = x
        slot.yPosition = y
    }

}