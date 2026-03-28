package io.github.fourmisain.dyeallthethings;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.ShapelessCraftingRecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

import static io.github.fourmisain.dyeallthethings.DyeAllTheThings.isDyeable;

// basically a copy of DyeRecipe that works for *all* items
// registered as dyeallthethings:generic_dyed and swapped to the very end of the recipe list so it won't override existing recipes
public class GenericDyeRecipe extends NormalCraftingRecipe {
	public static final MapCodec<GenericDyeRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance
		.group(
			CommonInfo.MAP_CODEC.forGetter(o -> o.commonInfo),
			CraftingBookInfo.MAP_CODEC.forGetter(o -> o.bookInfo),
			Ingredient.CODEC.fieldOf("dye").forGetter(o -> o.dye)
		).apply(instance, GenericDyeRecipe::new));

	public static final StreamCodec<RegistryFriendlyByteBuf, GenericDyeRecipe> STREAM_CODEC = StreamCodec.composite(
		CommonInfo.STREAM_CODEC, o -> o.commonInfo,
		CraftingBookInfo.STREAM_CODEC, o -> o.bookInfo,
		Ingredient.CONTENTS_STREAM_CODEC, o -> o.dye,
		GenericDyeRecipe::new);

	public static final RecipeSerializer<GenericDyeRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

	private final static Ingredient TARGET = Ingredient.of(Items.IRON_CHESTPLATE); // only for recipe display
	private final Ingredient dye;

	public GenericDyeRecipe(Recipe.CommonInfo commonInfo, CraftingRecipe.CraftingBookInfo bookInfo, Ingredient dye) {
		super(commonInfo, bookInfo);
		this.dye = dye;
	}

	@Override
	public boolean matches(CraftingInput input, Level level) {
		if (input.ingredientCount() < 2)
			return false;

		boolean hasTarget = false;
		boolean hasDyes = false;

		for (int slot = 0; slot < input.size(); slot++) {
			var itemStack = input.getItem(slot);
			if (itemStack.isEmpty())
				continue;

			if (dye.test(itemStack) && itemStack.has(DataComponents.DYE)) {
				hasDyes = true;
			} else if (isDyeable(itemStack.getItem())) {
				if (hasTarget)
					return false;

				hasTarget = true;
			}
		}

		return hasDyes && hasTarget;
	}

	@Override
	public ItemStack assemble(CraftingInput input) {
		var targetStack = ItemStack.EMPTY;
		var dyes = new ArrayList<DyeColor>();

		for (int slot = 0; slot < input.size(); slot++) {
			var itemStack = input.getItem(slot);
			if (itemStack.isEmpty())
				continue;

			if (dye.test(itemStack) && itemStack.has(DataComponents.DYE)) {
				var dye = itemStack.getOrDefault(DataComponents.DYE, DyeColor.WHITE);
				dyes.add(dye);
			} else if (isDyeable(itemStack.getItem())) {
				if (!targetStack.isEmpty())
					return ItemStack.EMPTY;

				targetStack = itemStack;
			}
		}

		if (targetStack.isEmpty() || dyes.isEmpty())
			return ItemStack.EMPTY;

		var oldColor = targetStack.get(DataComponents.DYED_COLOR);
		var newColor = DyedItemColor.applyDyes(oldColor, dyes);

		var resultStack = TransmuteRecipe.createWithOriginalComponents(ItemStackTemplate.fromNonEmptyStack(targetStack), targetStack);
		resultStack.set(DataComponents.DYED_COLOR, newColor);
		return resultStack;
	}

	@Override
	public RecipeSerializer<GenericDyeRecipe> getSerializer() {
		return SERIALIZER;
	}

	@Override
	protected PlacementInfo createPlacementInfo() {
		return PlacementInfo.create(List.of(TARGET, dye));
	}

	@Override
	public List<RecipeDisplay> display() {
		var dyes = new SlotDisplay.OnlyWithComponent(dye.display(), DataComponents.DYE);

		return List.of(new ShapelessCraftingRecipeDisplay(List.of(TARGET.display(), dyes),
			new SlotDisplay.DyedSlotDemo(dyes, TARGET.display()),
			new SlotDisplay.ItemSlotDisplay(Items.CRAFTING_TABLE)
		));
	}
}
