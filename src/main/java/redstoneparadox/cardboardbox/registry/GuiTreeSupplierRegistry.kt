package redstoneparadox.cardboardbox.registry

import net.minecraft.util.Identifier
import redstoneparadox.cardboardbox.gui.GuiTree

/**
 * Created by RedstoneParadox on 1/3/2019.
 */

/**
 * Registry for GuiTree suppliers.
 */
object GuiTreeSupplierRegistry {

    private val guiTreeProviders = mutableMapOf<Identifier, (id : Identifier) -> GuiTree>()

    /**
     * Registers a new GuiTree provider
     *
     * @param id the Identifier of the provider.
     * @param treeSupplier The function that suppliers the GuiTree.
     */
    fun registerSupplier(id : Identifier, treeSupplier : (id : Identifier) -> GuiTree) {

        if (guiTreeProviders.containsKey(id)) {
            println("Error, a supplier with an ID of $id already exists!")
            return
        }

        guiTreeProviders.put(id, treeSupplier)
    }

    /**
     * Invokes a supplier function to return a GuiTree.
     *
     * @param id the Identifier of the supplier.
     */
    fun supplyTree(id: Identifier) : GuiTree? {
        return guiTreeProviders[id]!!.invoke(id)
    }
}