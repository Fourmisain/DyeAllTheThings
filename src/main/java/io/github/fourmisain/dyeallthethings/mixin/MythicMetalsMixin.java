package io.github.fourmisain.dyeallthethings.mixin;

import io.github.fourmisain.dyeallthethings.DyeAllTheThingsClient;
import net.minecraft.client.model.Model;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import nourl.mythicmetals.MythicMetalsClient;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MythicMetalsClient.class)
public abstract class MythicMetalsMixin {
	@Dynamic
	@Redirect(
		method = "lambda$registerArmorRenderer$11(Lnet/minecraft/class_4587;Lnet/minecraft/class_4597;Lnet/minecraft/class_1799;Lnet/minecraft/class_1309;Lnet/minecraft/class_1304;ILnet/minecraft/class_572;)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/fabricmc/fabric/api/client/rendering/v1/ArmorRenderer;renderPart(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/item/ItemStack;Lnet/minecraft/client/model/Model;Lnet/minecraft/util/Identifier;)V"
		),
		require = 0
	)
	private static void renderNonDyeableArmorItem(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ItemStack stack, Model model, Identifier texture) {
		int color = DyeAllTheThingsClient.getColor(stack);

		float r = 1, g = 1, b = 1;

		if (color != -1) {
			r = (color >> 16 & 255) / 255.0F;
			g = (color >> 8  & 255) / 255.0F;
			b = (color       & 255) / 255.0F;
		}

		VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getArmorCutoutNoCull(texture), false, stack.hasGlint());
		model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, r, g, b, 1);
	}
}
