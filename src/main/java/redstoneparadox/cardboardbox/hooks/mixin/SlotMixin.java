package redstoneparadox.cardboardbox.hooks.mixin;

import net.minecraft.container.Slot;
import org.spongepowered.asm.mixin.Mixin;
import redstoneparadox.cardboardbox.hooks.ExtendedSlot;

/**
 * Created by RedstoneParadox on 1/29/2019.
 */
@Mixin(Slot.class)
public class SlotMixin implements ExtendedSlot {

    public int originalX = ((Slot) (Object) this).xPosition;
    public int originalY = ((Slot) (Object) this).yPosition;

    @Override
    public int getOriginalX() {
        return originalX;
    }

    @Override
    public int getOriginalY() {
        return originalY;
    }

}
