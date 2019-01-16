package redstoneparadox.cardboardbox.client.mixin;

import net.minecraft.client.gui.DrawableContainer;
import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import redstoneparadox.cardboardbox.client.hooks.GuiSetup;

/**
 * Created by RedstoneParadox on 1/1/2019.
 */
@Mixin(Gui.class)
public abstract class GuiMixin extends DrawableContainer implements GuiSetup {

    @Inject(method = "draw", at = @At("HEAD"))
    public void draw(int int_1, int int_2, float float_1, CallbackInfo ci) {
        setup();
    }

    @Override
    public void setup() {

    }

}
