package redstoneparadox.cardboardbox.reference

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.gui.GuiProviderRegistry
import net.fabricmc.fabric.api.container.ContainerProviderRegistry
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.Material
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.container.Container
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundTag
import net.minecraft.util.DefaultedList
import net.minecraft.util.Hand
import net.minecraft.util.Identifier
import net.minecraft.util.InventoryUtil
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.Registry.register
import net.minecraft.world.BlockView
import net.minecraft.world.World
import redstoneparadox.cardboardbox.CardboardBox
import redstoneparadox.cardboardbox.container.CardboardContainer
import redstoneparadox.cardboardbox.container.InventoryType
import redstoneparadox.cardboardbox.gui.ContainerTreeGUI
import redstoneparadox.cardboardbox.gui.GuiTree
import redstoneparadox.cardboardbox.gui.GuiTreeBuilder
import redstoneparadox.cardboardbox.gui.nodes.LabelNode
import redstoneparadox.cardboardbox.gui.nodes.SlotNode
import redstoneparadox.cardboardbox.gui.nodes.TextureRectNode
import redstoneparadox.cardboardbox.gui.util.GuiTreeManager
import redstoneparadox.cardboardbox.misc.GuiTreeController
import redstoneparadox.cardboardbox.reference.ReferenceOneMain.Companion.EXAMPLE_ONE_ID
import redstoneparadox.cardboardbox.registry.GuiTreeSupplierRegistry

/**
 * Created by RedstoneParadox on 1/6/2019.
 */
/**
 * An example implementation of a GuiTree and controller that displays the player's position every time the Gui is
 * opened.
 */
class ReferenceOneMain : ModInitializer {

    companion object {
        var EXAMPLE_ONE_ID : Identifier = Identifier(CardboardBox.MOD_ID, "example_one")

        lateinit var EXAMPLE_ONE_BLOCK : Block
        lateinit var EXAMPLE_ONE_BE : BlockEntityType<ExampleOneBlockEntity>
    }

    override fun onInitialize() {
        EXAMPLE_ONE_BLOCK = register(Registry.BLOCK, EXAMPLE_ONE_ID, ReferenceOneBlock())
        EXAMPLE_ONE_BE = registerBlockEntity(EXAMPLE_ONE_ID, BlockEntityType.Builder.create { ExampleOneBlockEntity() })

        /**
         * When registering the container, make sure to use the same Identifier you use to register the GuiTree supplier
         * as that is the Identifier that the CardboardContainer uses to get the GuiTree supplier.
         */
        ContainerProviderRegistry.INSTANCE.registerFactory(EXAMPLE_ONE_ID) { syncId, identifier, player, buf ->
            val pos : BlockPos = buf.readBlockPos()
            return@registerFactory CardboardContainer(syncId, pos, player, identifier)
        }

    }

    fun <T : BlockEntity> registerBlockEntity(identifier: Identifier, builder : BlockEntityType.Builder<T>) : BlockEntityType<T> {
        val blockEntityType = builder.build(null)
        register(Registry.BLOCK_ENTITY, identifier, blockEntityType)
        return blockEntityType
    }

}

class ReferenceOneClient : ClientModInitializer {

    override fun onInitializeClient() {

        /**
         * Here we register a lamdba function that uses a GuiTreeBuilder to supply a GuiTree when given an identifier
         * and a player. The GuiTree has a label node with the text `Position: ` and a second one which is blank. The
         * second one will later be set to display the position of the player when they open the gui.
         */
        GuiTreeSupplierRegistry.registerSupplier(EXAMPLE_ONE_ID) { id, player, treeGui ->
            val builder = GuiTreeBuilder(id, player, treeGui, 10, 10)
            val tree : GuiTree = builder.tree

            builder
                    //.addNode(ColoredRectNode("color", 10, 10, tree,100, 60, RGBAColor.Presets.WHITE.pick()))
                    //.addNode(HoverNode("area", 10, 10, tree, 100, 60))
                    //.addNode(LabelNode("header_label", 20, 20, tree, "Position:"))
                    //.addNode(LabelNode("position_label", 20, 40, tree, ""))
                    .addNode(TextureRectNode("backgrounds", 0, 0, tree, 256, 256, Identifier("textures/gui/container/shulker_box.png")))
                    .addPlayerInventory(8,84)
                    .addNodeGrid(SlotNode("container", 8, 18, tree, InventoryType.CONTAINER, 0), 3, 9, 18, 18)
                    .build()
        }

        GuiProviderRegistry.INSTANCE.registerFactory<Container>(ReferenceOneMain.EXAMPLE_ONE_ID) { ContainerTreeGUI(it as CardboardContainer, EXAMPLE_ONE_ID) }
    }

}

/**
 * A reference implementation of GuiTreeController on a BlockEntity.
 */
class ExampleOneBlockEntity : BlockEntity(ReferenceOneMain.EXAMPLE_ONE_BE), GuiTreeController, Inventory {

    val inventory = DefaultedList.create(invSize, ItemStack.EMPTY)

    var labelNode : LabelNode? = null

    init {
        GuiTreeManager.addController(this)
    }

    /**
     * An int to store time remaining for the logic seen in <code>tick()</code>
     */
    var countdown : Int = 20


    override fun receive(guiTree: GuiTree) {

        /**
         * Checks if the the identifier of the received tree is the identifier that the tree was registered under. If it
         * is, it sets the LabelNode's text to display the player's position.
         */
        if (guiTree.identifier == ReferenceOneMain.EXAMPLE_ONE_ID) {
            //labelNode = (guiTree.getChild("position_label") as LabelNode)

            //labelNode!!.text = guiTree.player.pos.toString()
            //guiTree.listeners.add(this)
        }
    }

    /*
    override fun nodeUpdate(tree: GuiTree, guiNode: GuiNode) {
        if (guiNode is HoverNode) {
            if (guiNode.isMouseInside) {
                labelNode!!.text = "Hello, mouse!"
            }
            else {
                labelNode!!.text = "Goodbye, mouse!"
            }

            (tree.gui as ContainerTreeGUI).forceUpdate()
        }
    }
    */

    //Inventory functions
    override fun getInvSize(): Int {
        return 27
    }

    override fun isInvEmpty(): Boolean {
        return inventory.stream().anyMatch(ItemStack::isEmpty)
    }

    override fun getInvStack(slot: Int): ItemStack {
        return inventory[slot]
    }

    override fun takeInvStack(slot: Int, amount: Int): ItemStack {
        return InventoryUtil.splitStack(this.inventory, slot, amount)
    }

    override fun removeInvStack(slot: Int): ItemStack {
        return InventoryUtil.removeStack(this.inventory, slot)
    }

    override fun setInvStack(slot: Int, stack: ItemStack) {
        this.inventory[slot] = stack
        if (stack.amount > this.invMaxStackAmount) {
            stack.amount = this.invMaxStackAmount
        }
    }

    override fun getInvMaxStackAmount(): Int {
        return 64
    }

    override fun canPlayerUseInv(player: PlayerEntity): Boolean {
        return true
    }

    override fun onInvOpen(player : PlayerEntity) {

    }

    override fun onInvClose(player : PlayerEntity) {

    }

    override fun isValidInvStack(slot : Int, stack : ItemStack): Boolean {
        return true
    }

    override fun clearInv() {
        inventory.clear()
    }

    override fun fromTag(compoundTag: CompoundTag) {
        super.fromTag(compoundTag)
        InventoryUtil.deserialize(compoundTag, inventory)
    }

    override fun toTag(compoundTag: CompoundTag): CompoundTag {
        super.toTag(compoundTag)
        InventoryUtil.serialize(compoundTag, inventory)
        return compoundTag
    }

}

/**
 * The block that contains the BlockEntity controller and creates the CardboardContainer instance.
 */
class ReferenceOneBlock : BlockWithEntity(Settings.of(Material.STONE)) {

    override fun createBlockEntity(view: BlockView): BlockEntity {
        return ExampleOneBlockEntity()
    }

    override fun activate(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hitResult: BlockHitResult): Boolean {
        if (!world.isClient() && hasBlockEntity()) {
            ContainerProviderRegistry.INSTANCE.openContainer(ReferenceOneMain.EXAMPLE_ONE_ID, player) { it.writeBlockPos(pos)}
        }

        return super.activate(state, world, pos, player, hand, hitResult)
    }
}