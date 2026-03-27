package io.github.fourmisain.dyeallthethings;

import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.Equippable;

public class DyeAllTheThingsClient {
	public static final int WHITE = -1;

	// or rather, uses EquipmentRenderer
	public static boolean isArmor(Item item) {
		DataComponentMap components = item.components();
		Equippable equippableComponent = components.get(DataComponents.EQUIPPABLE);

		return equippableComponent != null && equippableComponent.assetId().isPresent();
	}
}
