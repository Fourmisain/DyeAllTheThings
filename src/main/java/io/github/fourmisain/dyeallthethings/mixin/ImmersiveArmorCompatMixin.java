package io.github.fourmisain.dyeallthethings.mixin;

import immersive_armors.client.render.entity.piece.LayerPiece;
import immersive_armors.client.render.entity.piece.Piece;
import immersive_armors.item.ExtendedArmorItem;
import io.github.fourmisain.dyeallthethings.DyeAllTheThingsClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LayerPiece.class)
public abstract class ImmersiveArmorCompatMixin extends Piece {
	protected abstract BipedEntityModel<LivingEntity> getModel();

	// in else branch
	@Redirect(method = "render",
		at = @At(
			value = "INVOKE",
			target = "Limmersive_armors/client/render/entity/piece/LayerPiece;renderParts(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/item/ItemStack;Limmersive_armors/item/ExtendedArmorItem;Lnet/minecraft/client/render/entity/model/EntityModel;FFFZ)V"
		),
		require = 0 // better to not render than crash in this case
	)
	private void renderNonDyeableArmorItem(LayerPiece instance, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ItemStack itemStack, ExtendedArmorItem extendedArmorItem, EntityModel entityModel, float r, float g, float b, boolean overlay) {
		int color = DyeAllTheThingsClient.getColor(itemStack);

		if (color != -1) {
			r = (color >> 16 & 255) / 255.0F;
			g = (color >> 8  & 255) / 255.0F;
			b = (color       & 255) / 255.0F;
		} else {
			r = g = b = 1.0f;
		}

		renderParts(matrices, vertexConsumers, light, itemStack, (ExtendedArmorItem) itemStack.getItem(), getModel(), r, g, b, overlay);
	}
}
