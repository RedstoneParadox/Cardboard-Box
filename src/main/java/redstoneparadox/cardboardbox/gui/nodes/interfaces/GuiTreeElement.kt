package redstoneparadox.cardboardbox.gui.nodes.interfaces

/**
 * Created by RedstoneParadox on 1/9/2019.
 */

/**
 * Interface to allow children to get the position of the parent without having to distinguish between a GuiTree and a
 * GuiNode.
 */
interface GuiTreeElement {

    var x : Float
    var y : Float

}