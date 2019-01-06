package redstoneparadox.cardboardbox.container

import net.minecraft.container.Container
import net.minecraft.container.Slot
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventory
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import redstoneparadox.cardboardbox.gui.GuiTree
import redstoneparadox.cardboardbox.registry.GuiTreeSupplierRegistry

/**
 * Created by RedstoneParadox on 12/30/2018.
 */
class CardboardContainer(var pos: BlockPos, val player : PlayerEntity, val id : Identifier) : Container() {

    var playerSlotList : ArrayList<Slot> = ArrayList()
    var hotbarSlotList : ArrayList<Slot> = ArrayList()
    var containerSlotList : ArrayList<Slot> = ArrayList()

    var guiTree : GuiTree

    init {
        println("Opened container, $pos")
        guiTree = GuiTreeSupplierRegistry.supplyTree(id)!!
        inventoryToSlots()
        guiTree.setup(this)
        guiTree.player = this.player

        println(guiTree.player)
    }

    fun inventoryToSlots() {
        var inventory : Inventory = player.world.getBlockEntity(pos)!! as? Inventory ?: return

        for (i in 0..8) {
            addHotbarSlot(Slot(player.inventory, i, (20 * (i + 1)), 120))
        }

        for (i in 9..17) {
            addPlayerSlot(Slot(player.inventory, i, (20 * (i - 8)),60))
        }

        for (i in 18..26) {
            addPlayerSlot(Slot(player.inventory, i, (20 * (i - 17)), 80))
        }

        for (i in 27..35) {
            addPlayerSlot(Slot(player.inventory, i, (20 * (i - 26)), 100))
        }

        for (i in 0..(inventory.invSize - 1)) {
            addContainerSlot(Slot(inventory, i, (20*i), 20))
        }
    }

    fun addPlayerSlot(slot: Slot) {
        playerSlotList.add(slot)
        addSlot(slot)
    }

    fun addHotbarSlot(slot: Slot) {
        hotbarSlotList.add(slot)
        addSlot(slot)
    }

    fun addContainerSlot(slot : Slot) {
        containerSlotList.add(slot)
        addSlot(slot)
    }

    override fun canUse(player: PlayerEntity): Boolean {
        return true
    }
}