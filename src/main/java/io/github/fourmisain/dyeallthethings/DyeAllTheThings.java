package io.github.fourmisain.dyeallthethings;

import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.item.ArmorItem;
import net.minecraft.registry.Registries;

public class DyeAllTheThings {
	public static String MOD_ID = "dyeallthethings";

	public static void lateInit() {
		Registries.ITEM.stream()
			.filter(item -> item instanceof ArmorItem)
			.forEach(item -> CauldronBehavior.WATER_CAULDRON_BEHAVIOR.map().putIfAbsent(item, CauldronBehavior.CLEAN_DYEABLE_ITEM));
	}
}
