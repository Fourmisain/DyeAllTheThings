package io.github.fourmisain.dyeallthethings.mixin;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.DyeableItem;
import org.spongepowered.asm.mixin.Mixin;

/** Makes all armor dyable (works for ArmorDyeRecipe, CauldronBehavior, TradeOffers, ... basically everything except rendering) */
@Mixin(ArmorItem.class)
public abstract class ArmorItemMixin implements DyeableItem {

}
