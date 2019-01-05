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
import net.minecraft.util.Hand
import net.minecraft.util.Identifier
import net.minecraft.util.Tickable
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.registry.Registry
import net.minecraft.world.BlockView
import net.minecraft.world.World
import redstoneparadox.cardboardbox.CardboardBox
import redstoneparadox.cardboardbox.container.CardboardContainer
import redstoneparadox.cardboardbox.gui.CardboardContainerGUI
import redstoneparadox.cardboardbox.gui.GuiTree
import redstoneparadox.cardboardbox.gui.GuiTreeBuilder
import redstoneparadox.cardboardbox.gui.nodes.LabelNode
import redstoneparadox.cardboardbox.misc.GuiTreeController
import redstoneparadox.cardboardbox.misc.GuiTreeManager
import redstoneparadox.cardboardbox.reference.ReferenceOneMain.Companion.EXAMPLE_ONE_BE
import redstoneparadox.cardboardbox.reference.ReferenceOneMain.Companion.EXAMPLE_ONE_ID
import redstoneparadox.cardboardbox.registry.GuiTreeSupplierRegistry
import kotlin.random.Random

/**
 * Created by RedstoneParadox on 1/3/2019.
 */

/**
 * An example implementation of a GuiTree and controller that displays a random number every second (20 ticks).
 */
class ReferenceOneMain : ModInitializer {

    companion object {
        var EXAMPLE_ONE_ID : Identifier = Identifier(CardboardBox.MOD_ID, "example_one")

        lateinit var EXAMPLE_ONE_BLOCK : Block
        lateinit var EXAMPLE_ONE_BE : BlockEntityType<ExampleOneBlockEntity>
    }

    override fun onInitialize() {
        EXAMPLE_ONE_BLOCK = Registry.register(Registry.BLOCK, EXAMPLE_ONE_ID, ExampleOneBlock())
        EXAMPLE_ONE_BE = registerBlockEntity(EXAMPLE_ONE_ID, BlockEntityType.Builder.create { ExampleOneBlockEntity() })

        /**
         * When registering the container, make sure to use the same Identifier you use to register the GuiTree supplier
         * as that is the Identifier that the CardboardContainer uses to get the GuiTree supplier.
         */
        ContainerProviderRegistry.INSTANCE.registerFactory(EXAMPLE_ONE_ID) { identifier, player, buf ->
            val pos : BlockPos = buf.readBlockPos()
            return@registerFactory CardboardContainer(pos, player, identifier)
        }

        /**
         * Here, we register a lambda function that takes an Identifier and supplies a GuiTree using a GuiTreeBuilder.
         * The label displays a random number when the tree is build.
         *
         * While it may seem like this is something we could get away with doing this on the client only, things
         * SlotNodes need to set the position of their respective slots on the client and the server to avoid issues,
         * something that is too trivial to use networking for, not to mention other things in your mod may have the
         * same problem, so it's best to just register all your GuiTrees on both sides to avoid confusion.
         */
        GuiTreeSupplierRegistry.registerSupplier(EXAMPLE_ONE_ID) { id ->
            GuiTreeBuilder(id)
                    .addNode(LabelNode("label", 0f, 0f, "Number: " + Random.nextInt(1, 11).toString()))
                    .build()
        }
    }

    fun <T : BlockEntity> registerBlockEntity(identifier: Identifier, builder : BlockEntityType.Builder<T>) : BlockEntityType<T> {
        val blockEntityType = builder.method_11034(null)
        Registry.register(Registry.BLOCK_ENTITY, identifier, blockEntityType)
        return blockEntityType
    }

}

class ReferenceOneClient : ClientModInitializer {

    override fun onInitializeClient() {
        GuiProviderRegistry.INSTANCE.registerFactory<Container>(EXAMPLE_ONE_ID) { CardboardContainerGUI(it as CardboardContainer)}

        println("ReferenceOneClient!")
    }

}

/**
 * A reference implementation of GuiTreeController on a BlockEntity.
 */
class ExampleOneBlockEntity : BlockEntity(EXAMPLE_ONE_BE), GuiTreeController, Tickable {

    /**
     * Stores a reference to the tree that was registered earlier.
     */
    var tree : GuiTree? = null

    init {
        GuiTreeManager.addController(this)
    }

    /**
     * An int to store time remaining for the logic seen in <code>tick()</code>
     */
    var countdown : Int = 20

    override fun receive(guiTree: GuiTree) {

        /**
         * Checks if the the identifier of the received tree is the identifier that the tree was registered under.
         */
        if (tree == null && guiTree.identifier == EXAMPLE_ONE_ID) {
            tree = guiTree
        }
    }

    override fun removeTree(guiTree: GuiTree) {

        /**
         * Checks if the tree that is being removed matches the tree that is stored so cleanup can occur properly.
         */
        if (guiTree == tree) {
            tree = null
        }
    }

    /**
     * Every second (20 ticks), the BlockEntity will change the label node to display a random number between 1 and 10.
     */
    override fun tick() {

        if (countdown > 0) {
            countdown -= 1
        }
        else {
            val randomNumber : Int = Random.nextInt(1, 11)
            (tree!!.getChild("label")!! as LabelNode).text = "Number: " + randomNumber.toString()
            countdown = 20

            if (world.isClient) {
                (tree!!.gui as CardboardContainerGUI).forceUpdate()
            }
        }
    }
}

/**
 * The block that contains the BlockEntity controller and creates the CardboardContainer instance.
 */
class ExampleOneBlock : BlockWithEntity(Block.Settings.of(Material.STONE)) {

    override fun createBlockEntity(view: BlockView): BlockEntity {
        return ExampleOneBlockEntity()
    }

    override fun activate(blockState_1: BlockState, world_1: World, blockPos_1: BlockPos, playerEntity_1: PlayerEntity, hand_1: Hand, direction_1: Direction, float_1: Float, float_2: Float, float_3: Float): Boolean {
        if (!world_1.isClient() && hasBlockEntity()) {
            ContainerProviderRegistry.INSTANCE.openContainer(EXAMPLE_ONE_ID, playerEntity_1) { it.writeBlockPos(blockPos_1)}
        }

        return super.activate(blockState_1, world_1, blockPos_1, playerEntity_1, hand_1, direction_1, float_1, float_2, float_3)
    }
}