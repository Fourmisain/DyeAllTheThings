package io.github.fourmisain.dyeallthethings.mixin.trident;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import io.github.fourmisain.dyeallthethings.DyeAllTheThings;
import io.github.fourmisain.dyeallthethings.render.Dyeable;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.object.projectile.TridentModel;
import net.minecraft.client.renderer.OrderedSubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownTridentRenderer;
import net.minecraft.client.renderer.entity.state.ThrownTridentRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.projectile.arrow.ThrownTrident;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.client.renderer.entity.ThrownTridentRenderer.TRIDENT_LOCATION;

@Mixin(ThrownTridentRenderer.class)
public abstract class ThrownTridentRendererMixin extends EntityRenderer<ThrownTrident, ThrownTridentRenderState> {
	@Shadow @Final
	private TridentModel model;

	protected ThrownTridentRendererMixin(EntityRendererProvider.Context context) {
		super(context);
	}

	@SuppressWarnings("unchecked")
	@WrapOperation(
		method = "submit(Lnet/minecraft/client/renderer/entity/state/ThrownTridentRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/renderer/OrderedSubmitNodeCollector;submitModel(Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/resources/Identifier;IIILnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;)V",
			ordinal = 0
		)
	)
	private <S> void submitColoredModel(OrderedSubmitNodeCollector instance, Model<? extends S> model, S state, PoseStack poseStack, Identifier texture, int lightCoords, int overlayCoords, int outlineColor, ModelFeatureRenderer.CrumblingOverlay crumblingOverlay, Operation<Void> original,
			@Local(argsOnly = true) ThrownTridentRenderState tridentState) {
		int color = ((Dyeable) tridentState).dyeallthethings$getColor();
		if (color != DyeAllTheThings.WHITE) {
			instance.submitModel((Model<Unit>) model, Unit.INSTANCE, poseStack, model.renderType(TRIDENT_LOCATION), tridentState.lightCoords, OverlayTexture.NO_OVERLAY, color, null, tridentState.outlineColor, null);
		} else {
			original.call(instance, model, state, poseStack, texture, lightCoords, overlayCoords, outlineColor, crumblingOverlay);
		}
	}

	@Inject(
		method = "extractRenderState(Lnet/minecraft/world/entity/projectile/arrow/ThrownTrident;Lnet/minecraft/client/renderer/entity/state/ThrownTridentRenderState;F)V",
		at = @At("HEAD")
	)
	private void transferColor(ThrownTrident trident, ThrownTridentRenderState state, float partialTicks, CallbackInfo ci) {
		((Dyeable) state).dyeallthethings$setColor(((Dyeable) trident).dyeallthethings$getColor());
	}
}
