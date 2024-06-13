package io.github.fourmisain.dyeallthethings.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.HorseArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.HorseEntityModel;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.item.AnimalArmorItem;
import net.minecraft.item.ArmorMaterials;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HorseArmorFeatureRenderer.class)
public abstract class HorseArmorFeatureRendererMixin extends FeatureRenderer<HorseEntity, HorseEntityModel<HorseEntity>> {
	public HorseArmorFeatureRendererMixin(FeatureRendererContext<HorseEntity, HorseEntityModel<HorseEntity>> context) {
		super(context);
	}

	@ModifyExpressionValue(
		method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/passive/HorseEntity;FFFFFF)V",
		at = @At(
			value = "CONSTANT",
			args = "intValue=-6265536"
		)
	)
	private int debrownNonLeatherArmor(int original, @Local(argsOnly = true) HorseEntity horseEntity) {
		if (horseEntity.getBodyArmor().getItem() instanceof AnimalArmorItem armorItem && armorItem.getMaterial() == ArmorMaterials.LEATHER)
			return original;

		return -1;
	}
}
