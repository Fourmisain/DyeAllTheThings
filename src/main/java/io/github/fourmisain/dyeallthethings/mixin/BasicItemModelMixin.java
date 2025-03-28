package io.github.fourmisain.dyeallthethings.mixin;

import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.render.item.model.BasicItemModel;
import net.minecraft.client.render.item.tint.DyeTintSource;
import net.minecraft.client.render.item.tint.TintSource;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

import static io.github.fourmisain.dyeallthethings.DyeAllTheThingsClient.WHITE;
import static io.github.fourmisain.dyeallthethings.DyeAllTheThingsClient.isArmor;

@Mixin(BasicItemModel.class)
public abstract class BasicItemModelMixin {
	@Shadow @Final @Mutable
	private List<TintSource> tints; // should only be used on the render thread, thus fine to modify - hopefully

	@Inject(
		method = "update",
		at = @At("HEAD")
	)
	private void addTintSourceToArmors(ItemRenderState state, ItemStack stack, ItemModelManager resolver, ItemDisplayContext displayContext, ClientWorld world, LivingEntity user, int seed, CallbackInfo ci) {
		if (isArmor(stack.getItem()) && tints.isEmpty()) {
			tints = List.of(new DyeTintSource(WHITE));
		}
	}
}
