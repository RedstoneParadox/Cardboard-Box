package redstoneparadox.cardboardbox

import net.fabricmc.api.ModInitializer

/**
 * Created by RedstoneParadox on 1/3/2019.
 */
class CardboardBox : ModInitializer {

    override fun onInitialize() {
        println("Hello Fabric world!")
    }

    companion object {
        val MOD_ID = "cardboard_box"
    }

}