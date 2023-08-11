package io.github.fourmisain.dyeallthethings.mixin;

import dev.emi.trinkets.api.SlotReference;
import io.github.fourmisain.dyeallthethings.DyeAllTheThingsClient;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "dev/itsmeow/betteranimalsplus/client/fabric/BetterAnimalsPlusClientFabric$TrinketsSafeClassHack$1")
public abstract class BetterAnimalsPlusTrinketCompatMixin {
	int dyeallthethings$color;

	@Inject(method = "render", at = @At("HEAD"), require = 0)
	private void captureItemStack(ItemStack stack, SlotReference slotReference, EntityModel<? extends LivingEntity> contextModel,
	                              MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, LivingEntity entity,
	                              float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw,
	                              float headPitch, CallbackInfo ci) {
		dyeallthethings$color = DyeAllTheThingsClient.getColor(stack);
	}

	@Redirect(method = "render",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/render/entity/model/BipedEntityModel;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;IIFFFF)V"
		),
		require = 0
	)
	private void renderNonDyeableArmorItem(BipedEntityModel<?> instance, MatrixStack matrices, VertexConsumer vertexConsumers, int light, int overlay, float r, float g, float b, float a) {
		if (dyeallthethings$color != -1) {
			r = (dyeallthethings$color >> 16 & 255) / 255.0F;
			g = (dyeallthethings$color >> 8  & 255) / 255.0F;
			b = (dyeallthethings$color       & 255) / 255.0F;
		} else {
			r = g = b = 1.0f;
		}

		instance.render(matrices, vertexConsumers, light, overlay, r, g, b, a);
	}
}
