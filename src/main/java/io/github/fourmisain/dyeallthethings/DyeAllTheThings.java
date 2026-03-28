package io.github.fourmisain.dyeallthethings;

import io.github.fourmisain.dyeallthethings.mixin.access.CauldronInteractionDispatcherAccessor;
import net.minecraft.core.cauldron.CauldronInteractions;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jspecify.annotations.Nullable;

public class DyeAllTheThings {
	public static String MOD_ID = "dyeallthethings";
	public static Logger LOGGER = LogManager.getLogger(MOD_ID);

	public static final int WHITE = -1;
	public static final Identifier GENERIC_DYE_RECIPE = DyeAllTheThings.id("generic_dyed");

	public static boolean isDyeable(Item item) {
		return true; // yes
	}

	public static int getColor(ItemStack itemStack) {
		return getColor(itemStack.get(DataComponents.DYED_COLOR));
	}

	public static int getColor(@Nullable DyedItemColor dyedColor) {
		// in some cases like shields in the inventory, alpha is needed
		return dyedColor != null ? ARGB.color(255, dyedColor.rgb()) : WHITE;
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}

	public static void lateInit() {
		BuiltInRegistries.ITEM.stream()
			.filter(DyeAllTheThings::isDyeable)
			.forEach(item -> ((CauldronInteractionDispatcherAccessor) CauldronInteractions.WATER).getItems().putIfAbsent(item, CauldronInteractions::dyedItemIteration));
	}
}
