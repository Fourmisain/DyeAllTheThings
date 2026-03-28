package io.github.fourmisain.dyeallthethings.mixin;

import net.minecraft.client.color.item.Dye;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.CuboidItemModelWrapper;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

import static io.github.fourmisain.dyeallthethings.DyeAllTheThings.WHITE;
import static io.github.fourmisain.dyeallthethings.DyeAllTheThings.isDyeable;

@Mixin(CuboidItemModelWrapper.class)
public abstract class BasicItemModelMixin {
	@Shadow @Final @Mutable
	private List<ItemTintSource> tints; // should only be used on the render thread, thus fine to modify - hopefully

	@Inject(
		method = "update",
		at = @At("HEAD")
	)
	private void addTintSourceToItems(ItemStackRenderState output, ItemStack stack, ItemModelResolver resolver, ItemDisplayContext displayContext, ClientLevel level, ItemOwner owner, int seed, CallbackInfo ci) {
		if (isDyeable(stack.getItem()) && tints.isEmpty()) {
			tints = List.of(new Dye(WHITE));
		}
	}
}
