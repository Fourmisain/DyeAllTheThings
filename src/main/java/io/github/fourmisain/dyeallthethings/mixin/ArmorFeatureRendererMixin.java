package io.github.fourmisain.dyeallthethings.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ArmorFeatureRenderer.class)
public abstract class ArmorFeatureRendererMixin {
	@ModifyExpressionValue(
		method = "renderArmor(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/EquipmentSlot;ILnet/minecraft/client/render/entity/model/BipedEntityModel;)V",
		at = @At(
			value = "CONSTANT",
			args = "intValue=-6265536"
		)
	)
	private int debrownArmorWithoutDyaebleLayers(int original, @Local(argsOnly = true) LivingEntity entity, @Local(argsOnly = true) EquipmentSlot armorSlot, @Share("hasDyeableLayer") LocalBooleanRef hasDyeableLayer) {
		if (entity.getEquippedStack(armorSlot).getItem() instanceof ArmorItem armorItem
				&& armorItem.getMaterial().value().layers().stream().anyMatch(ArmorMaterial.Layer::isDyeable)) {
			hasDyeableLayer.set(true);
			return original;
		}

		return -1;
	}

	@ModifyExpressionValue(
		method = "renderArmor(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/EquipmentSlot;ILnet/minecraft/client/render/entity/model/BipedEntityModel;)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/item/ArmorMaterial$Layer;isDyeable()Z"
		)
	)
	private boolean dyeArmorWithoutDyeableLayers(boolean original, @Share("hasDyeableLayer") LocalBooleanRef hasDyeableLayer) {
		return hasDyeableLayer.get() ? original : true;
	}
}
