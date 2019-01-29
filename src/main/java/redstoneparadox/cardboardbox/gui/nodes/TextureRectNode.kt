package redstoneparadox.cardboardbox.gui.nodes

import com.mojang.blaze3d.platform.GlStateManager
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.Gui
import net.minecraft.util.Identifier
import redstoneparadox.cardboardbox.gui.GuiTree
import redstoneparadox.cardboardbox.gui.TreeGui

/**
 * Created by RedstoneParadox on 1/7/2019.
 */

/**
 * Class for drawing textures on the screen.
 *
 * @param id The identifier of the texture. Should be your mod id and the path to the texture in your resources folder.
 */
class TextureRectNode(name: String, x: Int, y: Int, root: GuiTree, var width : Int, var height : Int , var id : Identifier) : GuiNode(name, x, y, root) {

    override fun drawSelf(treeGui: TreeGui, float: Float, int1: Int, int2: Int) {
        GlStateManager.color4f(1.0f,1.0f, 1.0f, 1.0f)
        MinecraftClient.getInstance().textureManager.bindTexture(id)
        val int3 : Int = ((treeGui as Gui).width - 176) / 2
        val int4 : Int = ((treeGui as Gui).height - 166) / 2
        treeGui.drawTexture(x, y, (treeGui as Gui).width, (treeGui as Gui).height)
    }
}