package com.fastervillagertrading.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.MerchantScreen;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MerchantScreen.class)
public abstract class MerchantScreenMixin {
    @Shadow private int shopItem;

    @Inject(method = "postButtonClick", at = @At("HEAD"), cancellable = true)
    private void fasterVillagerTrading$requireFullCost(CallbackInfo callbackInfo) {
        Minecraft minecraft = Minecraft.getInstance();
        if (!minecraft.hasShiftDown() || minecraft.player == null) {
            return;
        }

        MerchantMenu merchantMenu = ((MerchantScreen) (Object) this).getMenu();
        if (shopItem < 0 || shopItem >= merchantMenu.getOffers().size()
            || merchantMenu.getOffers().get(shopItem).isOutOfStock()
            || !hasRequiredItems(merchantMenu, merchantMenu.getOffers().get(shopItem))) {
            callbackInfo.cancel();
        }
    }

    @Inject(method = "postButtonClick", at = @At("TAIL"))
    private void fasterVillagerTrading$executeTrade(CallbackInfo callbackInfo) {
        Minecraft minecraft = Minecraft.getInstance();
        if (!minecraft.hasShiftDown() || minecraft.player == null || minecraft.gameMode == null
                || !(((MerchantScreen) (Object) this).getMenu() instanceof MerchantMenu)) {
            return;
        }

        MerchantMenu merchantMenu = ((MerchantScreen) (Object) this).getMenu();
        minecraft.gameMode.handleContainerInput(
                merchantMenu.containerId,
                2,
                0,
                ContainerInput.QUICK_MOVE,
                minecraft.player
        );
        minecraft.gameMode.handleContainerInput(
                merchantMenu.containerId,
                0,
                0,
                ContainerInput.QUICK_MOVE,
                minecraft.player
        );
        minecraft.gameMode.handleContainerInput(
                merchantMenu.containerId,
                1,
                0,
                ContainerInput.QUICK_MOVE,
                minecraft.player
        );
    }

    private static boolean hasRequiredItems(MerchantMenu menu, MerchantOffer offer) {
        int[] availableCounts = new int[menu.slots.size()];
        for (int slotIndex = 0; slotIndex < menu.slots.size(); slotIndex++) {
            if (slotIndex != 2) {
                availableCounts[slotIndex] = menu.getSlot(slotIndex).getItem().getCount();
            }
        }

        if (!hasRequiredCost(menu, availableCounts, offer.getItemCostA(), offer.getCostA().getCount())) {
            return false;
        }

        return offer.getItemCostB()
                .map(cost -> hasRequiredCost(menu, availableCounts, cost, offer.getCostB().getCount()))
                .orElse(true);
    }

    private static boolean hasRequiredCost(MerchantMenu menu, int[] availableCounts, ItemCost cost, int count) {
        int remaining = count;
        for (int slotIndex = 0; slotIndex < availableCounts.length && remaining > 0; slotIndex++) {
            ItemStack available = menu.getSlot(slotIndex).getItem();
            if (slotIndex == 2 || availableCounts[slotIndex] == 0 || !cost.test(available)) {
                continue;
            }

            int used = Math.min(remaining, availableCounts[slotIndex]);
            remaining -= used;
            availableCounts[slotIndex] -= used;
        }

        return remaining == 0;
    }
}