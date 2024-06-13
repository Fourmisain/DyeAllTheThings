package io.github.fourmisain.dyeallthethings.mixin;

import io.github.fourmisain.dyeallthethings.DyeAllTheThings;
import net.minecraft.item.ArmorItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagEntry;
import net.minecraft.registry.tag.TagGroupLoader;
import net.minecraft.registry.tag.TagGroupLoader.TrackedEntry;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
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

import static io.github.fourmisain.dyeallthethings.DyeAllTheThings.MOD_ID;

@Mixin(TagGroupLoader.class)
public abstract class TagGroupLoaderMixin {
	@Shadow @Final
	private String dataType;

	// runs on worker thread
	@Inject(method = "loadTags", at = @At("RETURN"))
	public void makeAllArmorDyeable(ResourceManager resourceManager, CallbackInfoReturnable<Map<Identifier, List<TrackedEntry>>> cir) {
		var map = cir.getReturnValue();

		if (!dataType.equals(DyeAllTheThings.ITEM_TAGS))
			return;

		map.compute(ItemTags.DYEABLE.id(), (k, entries) -> {
			var newEntries = (entries == null ? new ArrayList<TrackedEntry>() : entries);

			Registries.ITEM.stream()
				.filter(item -> item instanceof ArmorItem)
				.map(Registries.ITEM::getId)
				.forEach(itemId -> dyeallthethings$addEntry(newEntries, itemId));

			return newEntries;
		});
	}

	@Unique
	private static void dyeallthethings$addEntry(List<TrackedEntry> entries, Identifier itemId) {
		entries.add(new TrackedEntry(TagEntry.create(itemId), MOD_ID));
	}
}
