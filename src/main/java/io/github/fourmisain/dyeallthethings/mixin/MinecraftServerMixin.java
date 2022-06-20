package io.github.fourmisain.dyeallthethings.mixin;

import io.github.fourmisain.dyeallthethings.DyeAllTheThings;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {
	private static boolean dyeallthethings$initialized = false;

	@Inject(method = "loadWorld", at = @At("TAIL"))
	protected void loadWorld(CallbackInfo ci) {
		if (dyeallthethings$initialized) return;
		dyeallthethings$initialized = true;

		DyeAllTheThings.lateInit();
	}
}
