package io.github.fourmisain.dyeallthethings.mixin;

import io.github.fourmisain.dyeallthethings.DyeAllTheThingsClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin {
	private static boolean dyeallthethings$initialized = false;

	@Inject(at = @At("HEAD"), method = "init()V")
	private void lateInit(CallbackInfo info) {
		if (dyeallthethings$initialized) return;
		dyeallthethings$initialized = true;

		DyeAllTheThingsClient.lateInit();
	}
}
