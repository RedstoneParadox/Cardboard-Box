package redstoneparadox.cardboardbox.client.hooks;

import net.minecraft.container.Slot;

/**
 * Created by RedstoneParadox on 12/31/2018.
 */
public interface IContainerGui {

    void drawContainerSlot(Slot slot);

    int getWidth();

    int getHeight();

}
