package me.hydos.lint.item;

import me.hydos.lint.Lint;
import me.hydos.lint.item.group.ItemGroups;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class Items {

	/**
	 * Boss Materials
	 */
	public static final Item TATER_ESSENCE = new TaterEssenceItem(new Item.Settings().group(ItemGroups.ITEMS).rarity(Rarity.EPIC).maxCount(1));

	/**
	 * Armor & Tool Sets
	 */
	public static final Item SICIERON_HELMET = new ArmorItem(ArmorMaterials.SICIERON, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroups.TOOLS));
	public static final Item SICIERON_CHESTPLATE = new ArmorItem(ArmorMaterials.SICIERON, EquipmentSlot.CHEST, new Item.Settings().group(ItemGroups.TOOLS));
	public static final Item SICIERON_LEGGINGS = new ArmorItem(ArmorMaterials.SICIERON, EquipmentSlot.LEGS, new Item.Settings().group(ItemGroups.TOOLS));
	public static final Item SICIERON_BOOTS = new ArmorItem(ArmorMaterials.SICIERON, EquipmentSlot.FEET, new Item.Settings().group(ItemGroups.TOOLS));
	public static final Item SICIERON_PICKAXE = new PickaxeItem(ToolMaterials.SICIERON, 6, 1.2f, new Item.Settings().group(ItemGroups.TOOLS)){};
	public static final Item SICIERON_AXE = new AxeItem(ToolMaterials.SICIERON, 9, 1.2f, new Item.Settings().group(ItemGroups.TOOLS)){};
	public static final Item SICIERON_SHOVEL = new ShovelItem(ToolMaterials.SICIERON, 5, 1.6f, new Item.Settings().group(ItemGroups.TOOLS)){};
	public static final Item SICIERON_HOE = new HoeItem(ToolMaterials.SICIERON, 1, 4f, new Item.Settings().group(ItemGroups.TOOLS).rarity(Rarity.EPIC)){};
	public static final Item SICIERON_SWORD = new SwordItem(ToolMaterials.SICIERON, 8, 1.6f, new Item.Settings().group(ItemGroups.TOOLS)){};

	/**
	 * Ore Materials
	 */
	public static final Item SICIERON_INGOT = new Item(new Item.Settings().group(ItemGroups.ITEMS).maxCount(64));
	public static final Item TARSCAN_SHARD = new Item(new Item.Settings().group(ItemGroups.ITEMS).maxCount(64));
	public static final Item JUREL_POWDER = new Item(new Item.Settings().group(ItemGroups.ITEMS).rarity(Rarity.RARE).maxCount(64));

	public static void register() {
		ItemGroups.register();
		registerOreMaterials();
		registerArmorSets();
		registerToolSets();
	}

	private static void registerToolSets() {
		Registry.register(Registry.ITEM, Lint.id("sicieron_sword"), SICIERON_SWORD);
		Registry.register(Registry.ITEM, Lint.id("sicieron_axe"), SICIERON_AXE);
		Registry.register(Registry.ITEM, Lint.id("sicieron_pickaxe"), SICIERON_PICKAXE);
		Registry.register(Registry.ITEM, Lint.id("sicieron_shovel"), SICIERON_SHOVEL);
		Registry.register(Registry.ITEM, Lint.id("sicieron_hoe"), SICIERON_HOE);
	}

	private static void registerArmorSets() {
		Registry.register(Registry.ITEM, Lint.id("sicieron_helmet"), SICIERON_HELMET);
		Registry.register(Registry.ITEM, Lint.id("sicieron_chestplate"), SICIERON_CHESTPLATE);
		Registry.register(Registry.ITEM, Lint.id("sicieron_leggings"), SICIERON_LEGGINGS);
		Registry.register(Registry.ITEM, Lint.id("sicieron_boots"), SICIERON_BOOTS);
	}

	private static void registerOreMaterials() {
		Registry.register(Registry.ITEM, Lint.id("tater_essence"), TATER_ESSENCE);
		Registry.register(Registry.ITEM, Lint.id("sicieron_ingot"), SICIERON_INGOT);
		Registry.register(Registry.ITEM, Lint.id("jurel_powder"), JUREL_POWDER);
		Registry.register(Registry.ITEM, Lint.id("tarscan_shard"), TARSCAN_SHARD);
	}
}