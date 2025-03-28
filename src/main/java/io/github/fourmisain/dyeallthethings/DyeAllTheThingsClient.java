package io.github.fourmisain.dyeallthethings;

import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.item.Item;

public class DyeAllTheThingsClient {
	public static final int WHITE = -1;

	// or rather, uses EquipmentRenderer
	public static boolean isArmor(Item item) {
		ComponentMap components = item.getComponents();
		EquippableComponent equippableComponent = components.get(DataComponentTypes.EQUIPPABLE);

		return equippableComponent != null && equippableComponent.assetId().isPresent();
	}
}
