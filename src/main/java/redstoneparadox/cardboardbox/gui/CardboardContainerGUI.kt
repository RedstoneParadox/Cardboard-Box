package redstoneparadox.cardboardbox.gui

import net.minecraft.client.gui.ContainerGui
import redstoneparadox.cardboardbox.client.hooks.IGui
import redstoneparadox.cardboardbox.container.CardboardContainer
import redstoneparadox.cardboardbox.gui.nodes.GuiNode

/**
 * Created by RedstoneParadox on 12/30/2018.
 */
class CardboardContainerGUI(cardboardContainer: CardboardContainer) : ContainerGui(cardboardContainer), IGui {

    var float: Float = 0f
    var int1: Int = 0
    var int2: Int = 0
    private var guiTree : GuiTree = cardboardContainer.guiTree

    fun forceUpdate() {
        draw(int1, int2, float)
    }

    override fun setup() {
        guiTree.setupClient(this)
    }

    override fun drawBackground(p0: Float, p1: Int, p2: Int) {

        this.float = p0
        this.int1 = p1
        this.int2 = p2

        guiTree.drawChildren(this, p0, p1, p2, fontRenderer)

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

    fun getChild(name : String) : GuiNode? {
        return guiTree.getChild(name)
    }
}