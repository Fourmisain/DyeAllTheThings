package io.github.fourmisain.dyeallthethings.mixin;

import io.github.fourmisain.dyeallthethings.DyeAllTheThings;
import io.github.fourmisain.dyeallthethings.render.Dyeable;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//YesData
@Mixin(NoDataSpecialModelRenderer.class)
public interface NoDataSpecialModelRendererMixin {
	@Inject(method = "extractArgument(Lnet/minecraft/world/item/ItemStack;)Ljava/lang/Void;", at = @At("HEAD"))
	default void extractColor(ItemStack stack, CallbackInfoReturnable<Void> cir) {
		if (this instanceof Dyeable dyeable) {
			dyeable.dyeallthethings$setColor(DyeAllTheThings.getColor(stack));
		}
	}
}
