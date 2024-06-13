package io.github.fourmisain.dyeallthethings;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.VersionParsingException;
import net.fabricmc.loader.api.metadata.version.VersionPredicate;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.item.ArmorItem;
import net.minecraft.registry.Registries;

public class DyeAllTheThings {
	public static String MOD_ID = "dyeallthethings";

	public static String ITEM_TAGS;
	static {
		try {
			Version minecraftVersion = FabricLoader.getInstance().getModContainer("minecraft").orElseThrow().getMetadata().getVersion();
			if (VersionPredicate.parse(">=1.21").test(minecraftVersion)) {
				ITEM_TAGS = "tags/item";
			} else {
				ITEM_TAGS = "tags/items";
			}
		} catch (VersionParsingException e) {
			throw new AssertionError(e);
		}
	}

	public static void lateInit() {
		Registries.ITEM.stream()
			.filter(item -> item instanceof ArmorItem)
			.forEach(item -> CauldronBehavior.WATER_CAULDRON_BEHAVIOR.map().putIfAbsent(item, CauldronBehavior.CLEAN_DYEABLE_ITEM));
	}
}
