package io.github.fourmisain.dyeallthethings.mixin;

import io.github.fourmisain.dyeallthethings.DyeAllTheThingsClient;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagEntry;
import net.minecraft.tags.TagLoader;
import net.minecraft.tags.TagLoader.EntryWithSource;

import static io.github.fourmisain.dyeallthethings.DyeAllTheThings.MOD_ID;

@Mixin(TagLoader.class)
public abstract class TagGroupLoaderMixin {
	@Shadow @Final
	private String directory;

	// runs on worker thread
	@Inject(method = "load", at = @At("RETURN"))
	public void makeAllArmorDyeable(ResourceManager resourceManager, CallbackInfoReturnable<Map<Identifier, List<EntryWithSource>>> cir) {
		var map = cir.getReturnValue();

		if (!directory.equals("tags/item"))
			return;

		map.compute(ItemTags.DYEABLE.location(), (k, entries) -> {
			var newEntries = (entries == null ? new ArrayList<EntryWithSource>() : entries);

			BuiltInRegistries.ITEM.stream()
				.filter(DyeAllTheThingsClient::isArmor)
				.map(BuiltInRegistries.ITEM::getKey)
				.forEach(itemId -> dyeallthethings$addEntry(newEntries, itemId));

			return newEntries;
		});
	}

	@Unique
	private static void dyeallthethings$addEntry(List<EntryWithSource> entries, Identifier itemId) {
		entries.add(new EntryWithSource(TagEntry.element(itemId), MOD_ID));
	}
}
