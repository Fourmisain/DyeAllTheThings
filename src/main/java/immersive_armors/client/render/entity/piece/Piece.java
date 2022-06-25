package immersive_armors.client.render.entity.piece;

import immersive_armors.item.ExtendedArmorItem;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

public abstract class Piece {
	protected void renderParts(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ItemStack itemStack, ExtendedArmorItem item, EntityModel model, float red, float green, float blue, boolean overlay) {

	}
}
