package io.github.fourmisain.dyeallthethings.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import io.github.fourmisain.dyeallthethings.DyeAllTheThings;
import io.github.fourmisain.dyeallthethings.GenericDyeRecipe;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeSerializers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RecipeSerializers.class)
public abstract class RecipeSerializersMixin {
	@Inject(method = "bootstrap", at = @At("TAIL"))
	private static void registerGenericDyeRecipe(CallbackInfoReturnable<Object> cir, @Local(argsOnly = true) Registry<RecipeSerializer<?>> registry) {
		Registry.register(registry, DyeAllTheThings.id("generic_crafting_dye"), GenericDyeRecipe.SERIALIZER);
	}
}
