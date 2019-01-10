package redstoneparadox.cardboardbox.gui

import com.mojang.blaze3d.platform.GlStateManager
import net.minecraft.client.gui.ContainerGui
import net.minecraft.client.render.BufferBuilder
import net.minecraft.client.render.Tessellator
import net.minecraft.client.render.VertexFormats
import net.minecraft.text.StringTextComponent
import net.minecraft.util.Identifier
import redstoneparadox.cardboardbox.ISlot
import redstoneparadox.cardboardbox.client.hooks.IGui
import redstoneparadox.cardboardbox.container.CardboardContainer
import redstoneparadox.cardboardbox.gui.nodes.GuiNode
import redstoneparadox.cardboardbox.gui.util.RGBAColor
import redstoneparadox.cardboardbox.registry.GuiTreeSupplierRegistry
import kotlin.math.roundToInt

/**
 * Created by RedstoneParadox on 12/30/2018.
 */
class ContainerTreeGUI(cardboardContainer: CardboardContainer, id : Identifier) : ContainerGui<CardboardContainer>(cardboardContainer, cardboardContainer.player.inventory, StringTextComponent("")), IGui {

    var float: Float = 0f
    var int1: Int = 0
    var int2: Int = 0
    private var guiTree : GuiTree = GuiTreeSupplierRegistry.supplyTree(id, cardboardContainer.player, this)!!

    init {
        left = guiTree.x.roundToInt()
        top = guiTree.y.roundToInt()
    }

    /**
     * Forces the Gui to redraw.
     */
    fun forceUpdate() {
        draw(int1, int2, float)
    }

    override fun setup() {
        guiTree.setup(this)
    }

    override fun drawBackground(p0: Float, p1: Int, p2: Int) {

        this.float = p0
        this.int1 = p1
        this.int2 = p2

        guiTree.drawChildren(this, p0, p1, p2, fontRenderer)

        for (slot in container.slotList) {
            println("[${(slot as ISlot).invSlot}, ${slot.inventory}, ${slot.id}]")
        }
    }

    /**
     * Used for drawing strings. What int_1 is used for is currently unknown.
     *
     * @param text the string to draw.
     * @param xPos the x position of the string.
     * @param yPos the y position of the string.
     * @param int_1 Unknown.
     */
    fun drawString(text : String, xPos : Float, yPos : Float, int_1 : Int, withShadow: Boolean) {

        if (withShadow) {
            fontRenderer.drawWithShadow(text, xPos, yPos, int_1)

        }
        else {
            fontRenderer.draw(text, xPos, yPos, int_1)
        }

    }

    /**
     * Used for drawing rectangles with solid color.
     */
    fun drawColoredRectangle(xPos: Float, yPos: Float, width : Float, height : Float, primaryColor : RGBAColor, secondaryColor : RGBAColor) {

        val redPercent = primaryColor.percentRed()
        val greenPercent = primaryColor.percentGreen()
        val bluePercent = primaryColor.percentBlue()
        val alphaPercent = primaryColor.percentAlpha()

        var redPercent2 : Float
        var bluePercent2 : Float
        var greenPercent2 : Float
        var alphaPercent2 : Float


        val tessellator : Tessellator = Tessellator.getInstance()
        val bufferBuilder : BufferBuilder = tessellator.bufferBuilder

        GlStateManager.enableBlend()
        GlStateManager.disableTexture()
        GlStateManager.blendFuncSeparate(GlStateManager.SrcBlendFactor.SRC_ALPHA, GlStateManager.DstBlendFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SrcBlendFactor.ONE, GlStateManager.DstBlendFactor.ZERO)
        GlStateManager.color4f(redPercent, greenPercent, bluePercent, alphaPercent)

        bufferBuilder.begin(7, VertexFormats.POSITION)
        bufferBuilder.vertex(xPos.toDouble(), yPos.toDouble(), 0.0).next()
        bufferBuilder.vertex(xPos.toDouble(), (yPos + height).toDouble(), 0.0).next()
        bufferBuilder.vertex((xPos + width).toDouble(), (yPos + height).toDouble(), 0.0).next()
        bufferBuilder.vertex((xPos + width).toDouble(), yPos.toDouble(), 0.0).next()
        tessellator.draw()

        GlStateManager.enableTexture()
        GlStateManager.disableBlend()
    }

    //2nd: bufferBuilder.vertex((xPos + width).toDouble(), yPos.toDouble(), 0.0).next()
    //4th: bufferBuilder.vertex(xPos.toDouble(), (yPos + height).toDouble(), 0.0).next()

    fun getChild(name : String) : GuiNode? {
        return guiTree.getChild(name)
    }

    override fun onClosed() {
        guiTree.cleanup()
        super.onClosed()
    }

    fun getCardboardContainer() : CardboardContainer {
        return container as CardboardContainer
    }

    fun getLeft() : Int {
        return left
    }

    fun getTop() : Int {
        return top
    }


}