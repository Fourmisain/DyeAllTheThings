package immersive_armors.item;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;

public class ExtendedArmorItem extends ArmorItem {
	public ExtendedArmorItem(Item.Settings settings, EquipmentSlot slot, ExtendedArmorMaterial material) {
		super(material, slot, settings);
	}
}
