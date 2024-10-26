package io.github.fourmisain.dyeallthethings;

import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.item.AnimalArmorItem;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;

import static net.minecraft.component.type.DyedColorComponent.getColor;

public class DyeAllTheThingsClient {
	public static final int WHITE = -1;

	public static boolean isArmor(Item item) {
		return item instanceof ArmorItem || item instanceof AnimalArmorItem;
	}

	public static void lateInit() {
		Item[] uncoloredArmorItems = Registries.ITEM.stream()
			.filter(item -> isArmor(item) && ColorProviderRegistry.ITEM.get(item) == null)
			.toArray(Item[]::new);

		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex > 0 ? -1 : getColor(stack, WHITE), uncoloredArmorItems);
	}
}
