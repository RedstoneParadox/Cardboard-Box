package redstoneparadox.cardboardbox.gui.nodes

import com.mojang.blaze3d.platform.GlStateManager
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.Gui
import net.minecraft.util.Identifier
import redstoneparadox.cardboardbox.client.hooks.IContainerGui
import redstoneparadox.cardboardbox.gui.GuiTree
import kotlin.math.roundToInt

/**
 * Created by RedstoneParadox on 1/7/2019.
 */

/**
 * Class for drawing textures on the screen.
 *
 * @param id The identifier of the texture. Should be your mod id and the path to the texture in your resources folder.
 */
class TextureRectNode(name: String, x: Float, y: Float, root: GuiTree, var width : Int, var height : Int , var id : Identifier) : GuiNode(name, x, y, root) {

    override fun drawSelf(gui: Gui, float: Float, int1: Int, int2: Int) {
        GlStateManager.color4f(1.0f,1.0f, 1.0f, 1.0f)
        MinecraftClient.getInstance().textureManager.bindTexture(id)
        val int3 : Int = (gui.width - (gui as IContainerGui).width) / 2
        val int4 : Int = (gui.height - (gui as IContainerGui).height) / 2
        gui.drawTexturedRect(x.roundToInt(), y.roundToInt(), 0, 0, (gui as IContainerGui).width, (gui as IContainerGui).height)
    }
}