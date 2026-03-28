package io.github.fourmisain.dyeallthethings.mixin.trident;

import io.github.fourmisain.dyeallthethings.render.Dyeable;
import net.minecraft.client.renderer.special.TridentSpecialRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import static io.github.fourmisain.dyeallthethings.DyeAllTheThings.WHITE;

@Mixin(TridentSpecialRenderer.class)
public abstract class TridentSpecialRendererMixin implements Dyeable {
	@Unique
	int dyeallthethings$color = WHITE;

	@Override
	public void dyeallthethings$setColor(int color) {
		dyeallthethings$color = color;
	}

	@Override
	public int dyeallthethings$getColor() {
		return dyeallthethings$color;
	}

	@ModifyArg(
		method = "submit",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/SubmitNodeCollector;submitModelPart(Lnet/minecraft/client/model/geom/ModelPart;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/rendertype/RenderType;IILnet/minecraft/client/renderer/texture/TextureAtlasSprite;ZZILnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;I)V"),
		index = 8
	)
	public int dye(int tintedColor) {
		return dyeallthethings$color;
	}
}
