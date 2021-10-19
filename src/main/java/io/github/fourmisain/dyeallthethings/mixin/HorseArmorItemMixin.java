package io.github.fourmisain.dyeallthethings.mixin;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.HorseArmorItem;
import org.spongepowered.asm.mixin.Mixin;

/** Makes all horse armor dyable */
@Mixin(HorseArmorItem.class)
public class HorseArmorItemMixin implements DyeableItem {

}
