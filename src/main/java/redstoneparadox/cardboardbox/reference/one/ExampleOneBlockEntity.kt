package redstoneparadox.cardboardbox.reference.one

import net.minecraft.block.entity.BlockEntity
import redstoneparadox.cardboardbox.gui.GuiTree
import redstoneparadox.cardboardbox.gui.nodes.LabelNode
import redstoneparadox.cardboardbox.misc.GuiTreeController
import redstoneparadox.cardboardbox.misc.GuiTreeManager

/**
 * A reference implementation of GuiTreeController on a BlockEntity.
 */
class ExampleOneBlockEntity : BlockEntity(ReferenceOne.EXAMPLE_ONE_BE), GuiTreeController {

    init {
        GuiTreeManager.addController(this)
    }

    /**
     * An int to store time remaining for the logic seen in <code>tick()</code>
     */
    var countdown : Int = 20

    override fun receive(guiTree: GuiTree) {

        /**
         * Checks if the the identifier of the received tree is the identifier that the tree was registered under. If it
         * is, it sets the LabelNode's text to display the player's position.
         */
        if (guiTree.identifier == ReferenceOne.EXAMPLE_ONE_ID) {
            (guiTree.getChild("position_label") as LabelNode).text = "Position: " + guiTree.player.pos.toString()
        }
    }

    /**
     * Since there is no reference to the tree being stored, nothing needs to be done here.
     */
    override fun removeTree(guiTree: GuiTree) {

    }
}