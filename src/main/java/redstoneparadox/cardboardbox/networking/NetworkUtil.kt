package redstoneparadox.cardboardbox.networking

import io.netty.buffer.Unpooled
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.networking.CustomPayloadPacketRegistry
import net.minecraft.client.network.ClientPlayerEntity
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
        CustomPayloadPacketRegistry.SERVER.register(CardboardBox.SYNC_SLOT) { packetContext, packetByteBuff ->
            val data = packetByteBuff.readIntArray()

            val xPos = data[0]
            val yPos = data[1]
            val index = data[2]
            val syncId = data[3]

            updateSlot(xPos, yPos, index, syncId)
        }

        /*
        CustomPayloadPacketRegistry.SERVER.register(CardboardBox.SYNC_SLOT) {context: PacketContext, buf: PacketByteBuf ->
            val data = buf.readIntArray()

            val offsetX = data[0]
            val offsetY = data[1]
            val syncId = data[2]


        }
        */
    }

    @Environment(EnvType.CLIENT)
    @Deprecated("Figure out a way to send all the data in a single packet.")
    fun syncSlot(posX : Int, posY : Int, index : Int, syncId : Int, player : ClientPlayerEntity) {
        val buf = PacketByteBuf(Unpooled.buffer())
        buf.writeIntArray(intArrayOf(posX, posY, index, syncId))
        player.networkHandler.sendPacket(CustomPayloadServerPacket(CardboardBox.SYNC_SLOT, buf))
    }

    @Environment(EnvType.CLIENT)
    fun syncSlots(offsetX : Int, offsetY : Int, syncId: Int, player : ClientPlayerEntity) {
        val buf = PacketByteBuf(Unpooled.buffer())
        buf.writeIntArray(intArrayOf(offsetX, offsetY, syncId))
        player.networkHandler.sendPacket(CustomPayloadServerPacket(CardboardBox.SYNC_SLOT, buf))
    }

    fun listenForUpdate(cardboardContainer: CardboardContainer) {
        listeners.add(cardboardContainer)
    }

    private fun updateSlots(offsetX : Int, offsetY : Int, syncId: Int) {

        for (listener in listeners) {

            if (listener.syncId == syncId) {

                if (listener.hasSlots()) {

                    break
                }
            }
        }
    }

    @Deprecated("Figure out a way to send all the data in a single packet.")
    private fun updateSlot(posX : Int, posY : Int, index : Int, syncId : Int) {

        for (listener in listeners) {
            if (listener.syncId == syncId) {

                if (listener.hasSlots()) {
                    listener.syncSlotPosition(posX, posY, index)
                }
                break
            }
        }
    }

}