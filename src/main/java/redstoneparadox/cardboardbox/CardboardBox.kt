package redstoneparadox.cardboardbox

import net.fabricmc.api.ModInitializer
import net.minecraft.util.Identifier
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import redstoneparadox.cardboardbox.gui.util.MouseUtil
import redstoneparadox.cardboardbox.networking.NetworkUtil

/**
 * Created by RedstoneParadox on 1/3/2019.
 */
class CardboardBox : ModInitializer {

    override fun onInitialize() {
        NetworkUtil.init()
        MouseUtil.init()
    }
    companion object {
        val MOD_ID = "cardboard_box"
        val SYNC_SLOT = Identifier(MOD_ID, "slot_sync")
        val CHANGE_SLOT = Identifier(MOD_ID, "slot_change")
        val LOGGER : Logger = LogManager.getLogger()
    }


}