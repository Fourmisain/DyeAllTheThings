package io.github.fourmisain.dyeallthethings;

import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.registry.Registries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DyeAllTheThings {
	public static String MOD_ID = "dyeallthethings";
	public static Logger LOGGER = LogManager.getLogger(MOD_ID);

	public static void lateInit() {
		Registries.ITEM.stream()
			.filter(DyeAllTheThingsClient::isArmor)
			.forEach(item -> CauldronBehavior.WATER_CAULDRON_BEHAVIOR.map().putIfAbsent(item, CauldronBehavior::cleanArmor));
	}
}
