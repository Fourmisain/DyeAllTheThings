package io.github.fourmisain.dyeallthethings.mixin.trident;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import io.github.fourmisain.dyeallthethings.DyeAllTheThings;
import io.github.fourmisain.dyeallthethings.render.Dyeable;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.OrderedSubmitNodeCollector;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.special.TridentSpecialRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Unit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import static io.github.fourmisain.dyeallthethings.DyeAllTheThings.WHITE;
import static net.minecraft.client.renderer.entity.ThrownTridentRenderer.TRIDENT_LOCATION;

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

	@SuppressWarnings("unchecked")
	@WrapOperation(
		method = "submit",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/renderer/OrderedSubmitNodeCollector;submitModel(Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/resources/Identifier;IIILnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;)V",
			ordinal = 0
		)
	)
	private <S> void submitColoredModel(OrderedSubmitNodeCollector instance, Model<? extends S> model, S state, PoseStack poseStack, Identifier texture, int lightCoords, int overlayCoords, int outlineColor, ModelFeatureRenderer.CrumblingOverlay crumblingOverlay, Operation<Void> original) {
		if (dyeallthethings$color != DyeAllTheThings.WHITE) {
			instance.submitModel((Model<Unit>) model, Unit.INSTANCE, poseStack, model.renderType(TRIDENT_LOCATION), lightCoords, OverlayTexture.NO_OVERLAY, dyeallthethings$color, null, outlineColor, crumblingOverlay);
		} else {
			original.call(instance, model, state, poseStack, texture, lightCoords, overlayCoords, outlineColor, crumblingOverlay);
		}
	}
}
