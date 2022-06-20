package io.github.fourmisain.dyeallthethings;

import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;

public class DyeAllTheThingsClient {
	// DyeableItem.getColor() but with white as default
	public static int getColor(ItemStack itemStack) {
		NbtCompound displayTag = itemStack.getSubNbt("display");
		if (displayTag != null && displayTag.contains("color", NbtElement.NUMBER_TYPE))
			return displayTag.getInt("color");
		return -1;
	}

	public static void lateInit() {
		Item[] uncoloredArmorItems = DyeAllTheThings.getArmorItems().stream()
			.filter(item -> ColorProviderRegistry.ITEM.get(item) == null)
			.toArray(Item[]::new);

		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex > 0 ? -1 : getColor(stack), uncoloredArmorItems);
	}
}
