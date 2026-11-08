package com.fastervillagertrading.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.world.inventory.MerchantMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MerchantMenu.class)
public abstract class MerchantMenuMixin {
    @Inject(method = "tryMoveItems", at = @At("HEAD"), cancellable = true)
    private void fasterVillagerTrading$requireShiftToFill(int offerIndex, CallbackInfo callbackInfo) {
        if (!Minecraft.getInstance().hasShiftDown()) {
            callbackInfo.cancel();
        }
    }
}