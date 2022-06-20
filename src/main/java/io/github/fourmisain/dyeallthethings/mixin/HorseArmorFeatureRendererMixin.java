package io.github.fourmisain.dyeallthethings.mixin;

import io.github.fourmisain.dyeallthethings.DyeAllTheThingsClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.HorseArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.HorseEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.item.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Environment(EnvType.CLIENT)
@Mixin(HorseArmorFeatureRenderer.class)
public abstract class HorseArmorFeatureRendererMixin extends FeatureRenderer<HorseEntity, HorseEntityModel<HorseEntity>> {
	public HorseArmorFeatureRendererMixin(FeatureRendererContext<HorseEntity, HorseEntityModel<HorseEntity>> context) {
		super(context);
	}

	@Shadow
	@Final
	private HorseEntityModel<HorseEntity> model;

	@Unique
	private static ItemStack armorStack;

	@Unique
	private static HorseArmorItem armorItem;

	@Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/passive/HorseEntity;FFFFFF)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/render/entity/model/HorseEntityModel;animateModel(Lnet/minecraft/entity/passive/AbstractHorseEntity;FFF)V",
			ordinal = 0
		),
		locals = LocalCapture.CAPTURE_FAILHARD)
	private void captureArmor(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, HorseEntity horseEntity, float f, float g, float h, float j, float k, float l, CallbackInfo ci, ItemStack itemStack, HorseArmorItem horseArmorItem) {
		HorseArmorFeatureRendererMixin.armorStack = itemStack;
		HorseArmorFeatureRendererMixin.armorItem = horseArmorItem;
	}

	// inside the else branch
	@Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/passive/HorseEntity;FFFFFF)V",
		at = @At(value = "CONSTANT", args = "floatValue=1.0F", ordinal = 0), cancellable = true)
	private void renderNonDyeableHorseArmorItem(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, HorseEntity horseEntity, float limbAngle, float limbDistance, float tickDelta, float j, float k, float l, CallbackInfo ci) {
		float r, g, b;

		int color = DyeAllTheThingsClient.getColor(armorStack);
		if (color != -1) {
			r = (color >> 16 & 255) / 255.0F;
			g = (color >> 8  & 255) / 255.0F;
			b = (color       & 255) / 255.0F;
		} else {
			r = g = b = 1.0f;
		}

		VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityCutoutNoCull(armorItem.getEntityTexture()));
		model.render(matrixStack, vertexConsumer, light, OverlayTexture.DEFAULT_UV, r, g, b, 1.0F);
		ci.cancel();
	}
}
