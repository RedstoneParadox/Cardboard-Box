package redstoneparadox.cardboardbox.reference.one

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.container.ContainerProviderRegistry
import net.minecraft.block.Block
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.registry.Registry
import redstoneparadox.cardboardbox.CardboardBox
import redstoneparadox.cardboardbox.container.CardboardContainer
import redstoneparadox.cardboardbox.gui.GuiTreeBuilder
import redstoneparadox.cardboardbox.gui.nodes.LabelNode
import redstoneparadox.cardboardbox.registry.GuiTreeSupplierRegistry

/**
 * Created by RedstoneParadox on 1/3/2019.
 */

/**
 * An example implementation of a GuiTree and controller that displays the player's position every time the Gui is
 * opened.
 */
class ReferenceOne : ModInitializer {

    companion object {
        var EXAMPLE_ONE_ID : Identifier = Identifier(CardboardBox.MOD_ID, "example_one")

        lateinit var EXAMPLE_ONE_BLOCK : Block
        lateinit var EXAMPLE_ONE_BE : BlockEntityType<ExampleOneBlockEntity>
    }

    override fun onInitialize() {
        EXAMPLE_ONE_BLOCK = Registry.register<Block>(Registry.BLOCK, EXAMPLE_ONE_ID, ExampleOneBlock())
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
        GuiTreeSupplierRegistry.registerSupplier(EXAMPLE_ONE_ID) { id : Identifier ->
            GuiTreeBuilder(id)
                    .addNode(LabelNode("position_label", 0f, 0f, "Position: "))
                    .build()
        }
    }

    fun <T : BlockEntity> registerBlockEntity(identifier: Identifier, builder : BlockEntityType.Builder<T>) : BlockEntityType<T> {
        val blockEntityType = builder.method_11034(null)
        Registry.register(Registry.BLOCK_ENTITY, identifier, blockEntityType)
        return blockEntityType
    }

}

