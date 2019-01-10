package redstoneparadox.cardboardbox.mixin;

import net.minecraft.container.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import redstoneparadox.cardboardbox.ISlot;

/**
 * Created by RedstoneParadox on 1/10/2019.
 */
@Mixin(Slot.class)
public class SlotMixin implements ISlot {

    @Shadow
    private final int invSlot;

    private SlotMixin() {
        invSlot = 0;
    }

    @Override
    public int getInvSlot() {
        return invSlot;
    }
}
