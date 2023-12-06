package io.github.fourmisain.dyeallthethings;

import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;

import java.util.List;
import java.util.stream.Collectors;

public class DyeAllTheThings {
	public static List<Item> getArmorItems() {
		return Registries.ITEM.stream()
			.filter(item -> (item instanceof ArmorItem || item instanceof HorseArmorItem))
			.collect(Collectors.toList());
	}

	public static void lateInit() {
		for (Item item : getArmorItems()) {
			CauldronBehavior.WATER_CAULDRON_BEHAVIOR.map().putIfAbsent(item, CauldronBehavior.CLEAN_DYEABLE_ITEM);
		}
	}
}
