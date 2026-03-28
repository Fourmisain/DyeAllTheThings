package io.github.fourmisain.dyeallthethings.mixin.trident;

import com.llamalad7.mixinextras.sugar.Local;
import io.github.fourmisain.dyeallthethings.DyeAllTheThings;
import io.github.fourmisain.dyeallthethings.render.Dyeable;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.entity.projectile.arrow.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("WrongEntityDataParameterClass")
@Mixin(ThrownTrident.class)
public abstract class ThrownTridentMixin extends AbstractArrow implements Dyeable {
	@Unique
	private static final EntityDataAccessor<Integer> dyeallthethings$ID_COLOR = SynchedEntityData.defineId(ThrownTrident.class, EntityDataSerializers.INT);

	protected ThrownTridentMixin(EntityType<? extends AbstractArrow> type, Level level) {
		super(type, level);
	}

	@Inject(method = {
		"<init>(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;)V",
		"<init>(Lnet/minecraft/world/level/Level;DDDLnet/minecraft/world/item/ItemStack;)V"
	}, at = @At("TAIL"))
	public void initSyncedColor(CallbackInfo ci, @Local(argsOnly = true) ItemStack trident) {
		this.entityData.set(dyeallthethings$ID_COLOR, DyeAllTheThings.getColor(trident));
	}

	@Inject(method = "defineSynchedData", at = @At("TAIL"))
	public void defineSyncedColor(SynchedEntityData.Builder entityData, CallbackInfo ci) {
		entityData.define(dyeallthethings$ID_COLOR, DyeAllTheThings.WHITE);
	}

	@Override
	public void dyeallthethings$setColor(int color) {
		this.entityData.set(dyeallthethings$ID_COLOR, color);
	}

	@Override
	public int dyeallthethings$getColor() {
		return this.entityData.get(dyeallthethings$ID_COLOR);
	}
}
