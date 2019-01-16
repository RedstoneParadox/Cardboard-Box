package redstoneparadox.cardboardbox.gui

import net.minecraft.client.MinecraftClient
import net.minecraft.container.Container
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.Identifier
import redstoneparadox.cardboardbox.client.hooks.GuiSetup
import redstoneparadox.cardboardbox.gui.util.RGBAColor

/**
 * Created by RedstoneParadox on 1/9/2019.
 */

/**
 * An interface used for working with Guis that use GuiTrees.
 */
interface TreeGui: GuiSetup {

    val player : PlayerEntity
    val id : Identifier
    val guiTree : GuiTree

    /**
     * Returns the width of the current GUI. Override if using a custom width.
     */
    fun getWidth(): Int {
        return MinecraftClient.getInstance().window.scaledWidth
    }

    /**
     * Returns the height of the current GUI. Override if using a custom height.
     */
    fun getHeight(): Int {
        return MinecraftClient.getInstance().window.scaledHeight
    }

    /**
     * Returns the container associated with your Gui if it exists.
     */
    fun getContainer(): Container? {
        return null
    }

    fun getTop() : Int

    fun getLeft() : Int

    override fun setup() {
        guiTree.setup()
    }

    fun drawString(text : String, xPos : Int, yPos : Int, withShadow: Boolean)

    fun drawColoredRectangle(xPos: Int, yPos: Int, width: Int, height: Int, primaryColor: RGBAColor, secondaryColor: RGBAColor)

    fun drawTexture(xPos: Int, yPos: Int, width: Int, height: Int)
}