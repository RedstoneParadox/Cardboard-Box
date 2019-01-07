package redstoneparadox.cardboardbox.networking

import io.netty.buffer.Unpooled
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.networking.CustomPayloadPacketRegistry
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.container.Slot
import net.minecraft.server.network.packet.CustomPayloadServerPacket
import net.minecraft.util.PacketByteBuf
import redstoneparadox.cardboardbox.CardboardBox
import redstoneparadox.cardboardbox.container.CardboardContainer

/**
 * Created by RedstoneParadox on 1/6/2019.
 */
object NetworkUtil {

    private val listeners : ArrayList<CardboardContainer> = ArrayList()

    fun init() {
        CustomPayloadPacketRegistry.SERVER.register(CardboardBox.CHANGE_SLOT) { packetContext, packetByteBuff ->
            val data = packetByteBuff.readIntArray()

            val xPos = data[0]
            val yPos = data[1]
            val index = data[2]
            val syncId = data[3]

            updateSlot(xPos, yPos, index, syncId)
        }
    }

    @Environment(EnvType.CLIENT)
    fun syncSlot(posX : Int, posY : Int, index : Int, syncId : Int, player : ClientPlayerEntity) {
        val buf = PacketByteBuf(Unpooled.buffer())
        buf.writeIntArray(intArrayOf(posX, posY, index, syncId))
        player.networkHandler.sendPacket(CustomPayloadServerPacket(CardboardBox.CHANGE_SLOT, buf))
    }

    fun listenForUpdate(cardboardContainer: CardboardContainer) {
        listeners.add(cardboardContainer)
    }

    private fun updateSlot(posX : Int, posY : Int, index : Int, syncId : Int) {

        for (listener in listeners) {
            if (listener.syncId == syncId) {

                if (!listener.containerSlotList.isEmpty()) {
                    val slot: Slot = listener.containerSlotList[index]

                    slot.xPosition = posX
                    slot.yPosition = posY
                }
            }
        }
    }
}