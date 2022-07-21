package io.github.fourmisain.dyeallthethings.mixin;

import dev.itsmeow.betteranimalsplus.client.fabric.BetterAnimalsPlusClientFabric;
import dev.itsmeow.betteranimalsplus.common.item.ItemModeledArmor;
import io.github.fourmisain.dyeallthethings.DyeAllTheThingsClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BetterAnimalsPlusClientFabric.class)
public abstract class BetterAnimalsPlusCompatMixin {
	private static int dyeallthethings$color;

	@Dynamic("ArmorRenderer lambda")
	@Inject(
		method = "lambda$onInitializeClient$0(Ldev/itsmeow/betteranimalsplus/common/item/ItemModeledArmor;Lnet/minecraft/util/Identifier;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/EquipmentSlot;ILnet/minecraft/client/render/entity/model/BipedEntityModel;)V",
		at = @At("HEAD"),
		require = 0
	)
	private static void captureItemStack(ItemModeledArmor armor, Identifier tex, MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, BipedEntityModel defaultModel, CallbackInfo ci) {
		dyeallthethings$color = DyeAllTheThingsClient.getColor(stack);
	}

	@Dynamic("ArmorRenderer lambda")
	@Redirect(method = "lambda$onInitializeClient$0(Ldev/itsmeow/betteranimalsplus/common/item/ItemModeledArmor;Lnet/minecraft/util/Identifier;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/EquipmentSlot;ILnet/minecraft/client/render/entity/model/BipedEntityModel;)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/render/entity/model/BipedEntityModel;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;IIFFFF)V"
		),
		require = 0
	)
	private static void renderNonDyeableArmorItem(BipedEntityModel<?> instance, MatrixStack matrices, VertexConsumer vertexConsumers, int light, int overlay, float r, float g, float b, float a) {
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
