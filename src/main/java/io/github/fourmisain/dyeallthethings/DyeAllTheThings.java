package io.github.fourmisain.dyeallthethings;

import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.registries.BuiltInRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DyeAllTheThings {
	public static String MOD_ID = "dyeallthethings";
	public static Logger LOGGER = LogManager.getLogger(MOD_ID);

	public static void lateInit() {
		BuiltInRegistries.ITEM.stream()
			.filter(DyeAllTheThingsClient::isArmor)
			.forEach(item -> CauldronInteraction.WATER.map().putIfAbsent(item, CauldronInteraction::dyedItemIteration));
	}
}
