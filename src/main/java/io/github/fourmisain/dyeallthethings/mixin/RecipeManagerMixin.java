package io.github.fourmisain.dyeallthethings.mixin;

import io.github.fourmisain.dyeallthethings.DyeAllTheThings;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.ArrayList;
import java.util.OptionalInt;
import java.util.stream.IntStream;

@Mixin(RecipeManager.class)
public abstract class RecipeManagerMixin {
	@ModifyArg(
		method = "prepare(Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)Lnet/minecraft/world/item/crafting/RecipeMap;",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/crafting/RecipeMap;create(Ljava/lang/Iterable;)Lnet/minecraft/world/item/crafting/RecipeMap;"),
		index = 0
	)
	public Iterable<RecipeHolder<?>> giveGenericDyeRecipeLowestPriority(Iterable<RecipeHolder<?>> iterable) {
		if (iterable instanceof ArrayList<RecipeHolder<?>> recipes) {
			OptionalInt recipeIndex = IntStream.range(0, recipes.size())
				.filter(i -> recipes.get(i).id().identifier().equals(DyeAllTheThings.GENERIC_DYE_RECIPE))
				.findFirst();

			if (recipeIndex.isPresent()) {
				// swap recipe to the end
				var genericDyeRecipe = recipes.get(recipeIndex.getAsInt());
				recipes.set(recipeIndex.getAsInt(), recipes.getLast());
				recipes.set(recipes.size() - 1, genericDyeRecipe);
			} else {
				DyeAllTheThings.LOGGER.error("Dye All The Things couldn't find it's own dye recipe?!");
			}
		} else {
			DyeAllTheThings.LOGGER.error("Dye All The Things' RecipeManagerMixin broke!");
		}

		return iterable;
	}
}
