package io.github.fourmisain.dyeallthethings;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.color.item.ItemColorProvider;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;

@Environment(EnvType.CLIENT)
public class DyeAllTheThingsClient implements ClientModInitializer {
	private static boolean initialized = false;

	// DyeableItem.getColor() but with white as default
	public static int getColor(ItemStack itemStack) {
		NbtCompound displayTag = itemStack.getSubNbt("display");
		if (displayTag != null && displayTag.contains("color", NbtElement.NUMBER_TYPE))
			return displayTag.getInt("color");
		return -1;
	}

	@Override
	public void onInitializeClient() {

	}

	public static void initFromTitleScreen() {
		if (initialized) return;
		initialized = true;

		Item[] uncoloredArmorItems = Registry.ITEM.stream()
			.filter(item -> (item instanceof ArmorItem || item instanceof HorseArmorItem) && ColorProviderRegistry.ITEM.get(item) == null)
			.toArray(Item[]::new);

		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex > 0 ? -1 : getColor(stack), uncoloredArmorItems);
	}
}
