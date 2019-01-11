package redstoneparadox.cardboardbox.gui.util

import net.fabricmc.fabric.events.client.ClientTickEvent
import net.minecraft.client.MinecraftClient
import net.minecraft.client.Mouse
import redstoneparadox.cardboardbox.gui.nodes.interfaces.InteractiveNode
import java.util.function.Consumer


/**
 * Created by RedstoneParadox on 1/7/2019.
 */
object MouseUtil {

    private var mouseX : Float = 0f
    private var mouseY : Float = 0f

    private var listeners : ArrayList<InteractiveNode> = ArrayList()

    var pressed : Boolean = false

    var printSlots : Boolean = true

    fun init() {
        ClientTickEvent.CLIENT.register( Consumer { client ->

            if (client.player != null && MouseUtil.printSlots) {
                println(client.player.containerPlayer.slotList)
                MouseUtil.printSlots = false
            }
            update(client.mouse)
        } )
    }

    private fun update(mouse: Mouse) {
        mouseX = mouse.x.toFloat()
        mouseY = mouse.y.toFloat()



        val size : Int = listeners.size
        if (size > 0) {

            for (i in 0 until size) {
                listeners[i].updateNode(mouseX, mouseY)
            }
        }

        pressed = MinecraftClient.getInstance().mouse.method_1609()
    }

    fun addListener(interactiveNode: InteractiveNode) {

        for (listener in listeners) {
            if (listener == interactiveNode) {
                return
            }
        }

        listeners.add(interactiveNode)
    }

    fun removeListener(interactiveNode: InteractiveNode) {
        listeners.remove(interactiveNode)
    }
}




