package io.github.fourmisain.dyeallthethings;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.VersionParsingException;
import net.fabricmc.loader.api.metadata.version.VersionPredicate;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DyeAllTheThings {
	public static List<Item> getArmorItems() {
		return Registry.ITEM.stream()
			.filter(item -> (item instanceof ArmorItem || item instanceof HorseArmorItem))
			.collect(Collectors.toList());
	}

	public static boolean testVersion(String modId, String versionRange) {
		try {
			Optional<ModContainer> container = FabricLoader.getInstance().getModContainer(modId);
			if (!container.isPresent())
				return false;

			VersionPredicate pred = VersionPredicate.parse(versionRange);
			Version version = container.get().getMetadata().getVersion();

			return pred.test(version);
		} catch (VersionParsingException e) {
			throw new AssertionError(e);
		}
	}

	public static void lateInit() {
		// CauldronBehavior didn't exist in 1.16 (and the behavior worked for all DyeableItem by default)
		if (!testVersion("minecraft", "~1.16.0")) {
			for (Item item : getArmorItems()) {
				CauldronBehavior.WATER_CAULDRON_BEHAVIOR.putIfAbsent(item, CauldronBehavior.CLEAN_DYEABLE_ITEM);
			}
		}
	}
}
