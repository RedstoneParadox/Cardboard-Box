package redstoneparadox.cardboardbox.container

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
class CardboardContainer(syncID : Int, val pos: BlockPos, val player : PlayerEntity, val id : Identifier) : Container(syncID) {

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
        var inventory : Inventory? = null
        if (player.world.getBlockEntity(pos) is Inventory) {
            inventory = player.world.getBlockEntity(pos) as Inventory
        }

        addPlayerSlots()


        if (inventory != null) {
            addInventorySlots(inventory)
        }
    }

    private fun addPlayerSlots() {
        var iteration : Int = 9

        for (i in 0 until 9) {
            addSlot(Slot(playerInventory, i, (18 * i) + 2, 120))
        }

        for (j in 0 until 3) {

            for (i in 0 until 9) {
                addSlot(Slot(playerInventory, iteration, (18 * i) + 2, (18 * j) + 60))
                iteration += 1
            }
        }
    }

    private fun addInventorySlots(inventory : Inventory) {
        checkContainerSize(inventory, inventory.invSize)
        inventory.onInvOpen(player)


        val rows = ((inventory.invSize)/9)
        val columns = ((inventory.invSize)/3)

        var iteration : Int = 0

        for (j in 0 until rows) {

            for (i in 0 until columns) {
                addSlot(Slot(inventory, iteration, (18 * i) + 2, 140 + (18 * j)))
                iteration += 1
            }
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