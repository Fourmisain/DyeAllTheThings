package io.github.fourmisain.dyeallthethings.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import net.minecraft.client.render.entity.equipment.EquipmentModel;
import net.minecraft.client.render.entity.equipment.EquipmentRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(EquipmentRenderer.class)
public abstract class EquipmentRendererMixin {
	@ModifyExpressionValue(
		method = "render(Lnet/minecraft/client/render/entity/equipment/EquipmentModel$LayerType;Lnet/minecraft/registry/RegistryKey;Lnet/minecraft/client/model/Model;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/util/Identifier;)V",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/equipment/EquipmentModel;getLayers(Lnet/minecraft/client/render/entity/equipment/EquipmentModel$LayerType;)Ljava/util/List;")
	)
	public List<EquipmentModel.Layer> checkForDyeableLayers(List<EquipmentModel.Layer> layers, @Share("hasDyeableLayer") LocalBooleanRef hasDyeableLayer) {
		for (var layer : layers) {
			if (layer.dyeable().isPresent()) {
				hasDyeableLayer.set(true);
				break;
			}
		}

		return layers;
	}

	@ModifyExpressionValue(
		method = "getDyeColor",
		at = @At(value = "CONSTANT", args = "intValue=-1")
	)
	private static int dyeEquipmentWithoutDyeableLayers(int original, @Local(argsOnly = true) int dyeColor, @Share("color") LocalIntRef color, @Share("hasDyeableLayer") LocalBooleanRef hasDyeableLayer) {
		// when the equipment already has a dyeable layer, do not dye the others
		if (hasDyeableLayer.get())
			return original;

		if (dyeColor != 0)
			return dyeColor;

		return original;
	}
}
