package redstoneparadox.cardboardbox.reference.one

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.gui.GuiProviderRegistry
import net.minecraft.container.Container
import redstoneparadox.cardboardbox.container.CardboardContainer
import redstoneparadox.cardboardbox.gui.CardboardContainerGUI

class ReferenceOneClient : ClientModInitializer {

    override fun onInitializeClient() {
        GuiProviderRegistry.INSTANCE.registerFactory<Container>(ReferenceOne.EXAMPLE_ONE_ID) { CardboardContainerGUI(it as CardboardContainer) }
    }

}