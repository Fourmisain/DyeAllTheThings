package io.github.fourmisain.dyeallthethings;

import net.minecraft.item.AnimalArmorItem;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;

public class DyeAllTheThingsClient {
	public static final int WHITE = -1;

	public static boolean isArmor(Item item) {
		return item instanceof ArmorItem || item instanceof AnimalArmorItem;
	}
}
