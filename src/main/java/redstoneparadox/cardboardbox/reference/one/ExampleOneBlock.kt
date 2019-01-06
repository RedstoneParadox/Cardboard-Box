package redstoneparadox.cardboardbox.reference.one

import net.fabricmc.fabric.api.container.ContainerProviderRegistry
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.Material
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.Hand
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.BlockView
import net.minecraft.world.World

/**
 * The block that contains the BlockEntity controller and creates the CardboardContainer instance.
 */
class ExampleOneBlock : BlockWithEntity(Settings.of(Material.STONE)) {

    override fun createBlockEntity(view: BlockView): BlockEntity {
        return ExampleOneBlockEntity()
    }

    override fun activate(blockState_1: BlockState, world_1: World, blockPos_1: BlockPos, playerEntity_1: PlayerEntity, hand_1: Hand, direction_1: Direction, float_1: Float, float_2: Float, float_3: Float): Boolean {
        if (!world_1.isClient() && hasBlockEntity()) {
            ContainerProviderRegistry.INSTANCE.openContainer(ReferenceOne.EXAMPLE_ONE_ID, playerEntity_1) { it.writeBlockPos(blockPos_1)}
        }

        return super.activate(blockState_1, world_1, blockPos_1, playerEntity_1, hand_1, direction_1, float_1, float_2, float_3)
    }
}