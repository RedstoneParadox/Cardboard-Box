package redstoneparadox.cardboardbox.client.mixin;

import net.minecraft.client.gui.ContainerGui;
import net.minecraft.client.gui.Gui;
import net.minecraft.container.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import redstoneparadox.cardboardbox.client.hooks.IContainerGui;

/**
 * Created by RedstoneParadox on 12/31/2018.
 */
@Mixin(ContainerGui.class)
public abstract class ContainerGuiMixin extends Gui implements IContainerGui {

    @Shadow
    protected int containerWidth;

    @Shadow
    protected int containerHeight;

    @Shadow
    private void drawSlot(Slot slot_1) {

    }

    @Override
    public void drawContainerSlot(Slot slot) {
        drawSlot(slot);
    }

    @Override
    public int getWidth() {
        return containerWidth;
    }

    @Override
    public int getHeight() {
        return containerHeight;
    }

}
