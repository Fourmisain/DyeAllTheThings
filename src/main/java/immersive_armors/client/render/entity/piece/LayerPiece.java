package immersive_armors.client.render.entity.piece;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public abstract class LayerPiece extends Piece {
	protected abstract BipedEntityModel<LivingEntity> getModel();

	public <T extends LivingEntity, A extends BipedEntityModel<T>> void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, ItemStack itemStack, float tickDelta, EquipmentSlot armorSlot, A armorModel) {
		renderParts(matrices, vertexConsumers, light, itemStack, null, getModel(), 1.0F, 1.0F, 1.0F, false);
		renderParts(matrices, vertexConsumers, light, itemStack, null, getModel(), 1.0F, 1.0F, 1.0F, true);
		renderParts(matrices, vertexConsumers, light, itemStack, null, getModel(), 1.0F, 1.0F, 1.0F, false);
	}
}