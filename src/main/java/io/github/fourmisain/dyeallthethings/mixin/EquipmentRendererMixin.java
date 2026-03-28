package io.github.fourmisain.dyeallthethings.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(EquipmentLayerRenderer.class)
public abstract class EquipmentRendererMixin {
	@ModifyExpressionValue(
		method = "renderLayers(Lnet/minecraft/client/resources/model/EquipmentClientInfo$LayerType;Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lnet/minecraft/world/item/ItemStack;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/resources/Identifier;II)V",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/model/EquipmentClientInfo;getLayers(Lnet/minecraft/client/resources/model/EquipmentClientInfo$LayerType;)Ljava/util/List;")
	)
	public List<EquipmentClientInfo.Layer> checkForDyeableLayers(List<EquipmentClientInfo.Layer> layers, @Share("hasDyeableLayer") LocalBooleanRef hasDyeableLayer) {
		for (var layer : layers) {
			if (layer.dyeable().isPresent()) {
				hasDyeableLayer.set(true);
				break;
			}
		}

		return layers;
	}

	@ModifyExpressionValue(
		method = "getColorForLayer",
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
