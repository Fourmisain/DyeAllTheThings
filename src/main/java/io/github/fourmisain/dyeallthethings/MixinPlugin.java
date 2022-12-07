package io.github.fourmisain.dyeallthethings;

import net.fabricmc.loader.api.FabricLoader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class MixinPlugin implements IMixinConfigPlugin {
	@Override
	public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
		if (mixinClassName.endsWith("BetterAnimalsPlusCompatMixin")) {
			return FabricLoader.getInstance().isModLoaded("betteranimalsplus");
		} else if (mixinClassName.endsWith("BetterAnimalsPlusTrinketCompatMixin")) {
			return FabricLoader.getInstance().isModLoaded("betteranimalsplus") && FabricLoader.getInstance().isModLoaded("trinkets");
		} else if (mixinClassName.endsWith("ImmersiveArmorCompatMixin")) {
			return FabricLoader.getInstance().isModLoaded("immersive_armors");
		}

		return true;
	}

	@Override
	public void onLoad(String mixinPackage) {

	}

	@Override
	public String getRefMapperConfig() {
		return null;
	}

	@Override
	public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

	}

	@Override
	public List<String> getMixins() {
		return null;
	}

	@Override
	public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

	}

	@Override
	public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

	}
}
