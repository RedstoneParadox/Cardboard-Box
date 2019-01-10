package redstoneparadox.cardboardbox.gui.nodes.interfaces

import net.minecraft.client.MinecraftClient
import redstoneparadox.cardboardbox.gui.util.MouseUtil

/**
 * Created by RedstoneParadox on 1/7/2019.
 */

/**
 * Used for GuiNodes that can be interacted with in some manner using the mouse.
 */
interface InteractiveNode {

    var x : Float
    var y : Float
    var width : Float
    var height : Float

    /**
     * Override these and set them to false.
     */
    var isMouseInside : Boolean
    var isMousePressed : Boolean

    /**
     * Called when the mouse enters an InteractiveNode's area.
     */
    fun onMouseEnter() {

    }

    /**
     * Called while the mouse is still inside the node.
     */
    fun whileMouseInside() {

    }

    /**
     * Called when the mouse exits an InteractiveNode's area.
     */
    fun onMouseExit() {

    }

    /**
     * Called when the mouse is pressed.
     */
    fun onMousePressed() {

    }

    /**
     * Called when the mouse is released.
     */
    fun onMouseReleased() {

    }

    /**
     * Function used to update the node.
     */
    fun updateNode(xPos : Float, yPos : Float) {
        if (scaledCheck(x, width, xPos) && scaledCheck(y, height, yPos)) {
            if (isMouseInside) {
                whileMouseInside()
            }
            else {
                isMouseInside = true
                onMouseEnter()
                whileMouseInside()
            }

            if (!MouseUtil.pressed && !isMousePressed) {
                if (MinecraftClient.getInstance().mouse.method_1609()) {
                    isMousePressed = true
                    onMousePressed()
                }
                else {
                    isMousePressed = false
                    onMouseReleased()
                }
            }
        }
        else {
            isMouseInside = false
            isMousePressed = false
            onMouseExit()
        }
    }

    private fun scaledCheck(start : Float, length : Float, pos : Float) : Boolean {
        val scale: Double = MinecraftClient.getInstance().window.method_4495()

        return (pos >= (start * scale) && pos <= ((start + length) * scale))
    }
}