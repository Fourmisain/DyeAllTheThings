package io.github.fourmisain.dyeallthethings.mixin.trident;

import io.github.fourmisain.dyeallthethings.render.Dyeable;
import net.minecraft.client.renderer.entity.state.ThrownTridentRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import static io.github.fourmisain.dyeallthethings.DyeAllTheThings.WHITE;

@Mixin(ThrownTridentRenderState.class)
public abstract class ThrownTridentRenderStateMixin implements Dyeable {
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
}
