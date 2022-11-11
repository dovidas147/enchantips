package com.fedpol1.enchantips.mixin;

import com.fedpol1.enchantips.config.ModConfig;
import com.fedpol1.enchantips.util.TooltipBuilder;
import com.terraformersmc.modmenu.util.mod.Mod;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtList;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(EnchantedBookItem.class)
public class EnchantedBookItemMixin {

    @Inject(method = "appendTooltip(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/item/TooltipContext;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;appendEnchantments(Ljava/util/List;Lnet/minecraft/nbt/NbtList;)V", ordinal = 0))
    private void enchantipsInjectGetTooltipRepairCost(ItemStack stack, World world, List<Text> tooltip, TooltipContext context, CallbackInfo ci) {
        if(ModConfig.SHOW_REPAIRCOST.getValue()) {
            int cost = stack.getRepairCost();
            if(!(cost == 0 && !ModConfig.SHOW_REPAIRCOST_WHEN_0.getValue())) {
                tooltip.add(TooltipBuilder.buildRepairCost(cost));
            }
        }
    }

    @Redirect(method = "appendTooltip(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/item/TooltipContext;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;appendEnchantments(Ljava/util/List;Lnet/minecraft/nbt/NbtList;)V"))
    private void enchantipsRedirectAppendEnchantments(List<Text> tooltip, NbtList enchantments) {
        TooltipBuilder.appendEnchantmentsEbook(tooltip, enchantments, true);
    }
}
