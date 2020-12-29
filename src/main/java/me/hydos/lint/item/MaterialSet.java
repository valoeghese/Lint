/*
 * Lint
 * Copyright (C) 2020 hYdos, Valoeghese, ramidzkh
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package me.hydos.lint.item;

import me.hydos.lint.Lint;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.AxeItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public final class MaterialSet {
	public MaterialSet(String registryName, ArmorMaterial armour, ToolMaterial tool, ItemGroup group) {
		this.pickaxe = new PickaxeItem(tool, 1, -2.8F, new Item.Settings().group(group)) {
		};
		this.axe = new AxeItem(tool, 6.0F, -3.1F, new Item.Settings().group(group)) {
		};
		this.shovel = new ShovelItem(tool, 1.5F, -3.0F, new Item.Settings().group(group)) {
		};
		this.hoe = new HoeItem(tool, 1, 4f, new Item.Settings().group(group).rarity(Rarity.EPIC)) {
		};
		this.sword = new SwordItem(tool, -2, -1.0F, new Item.Settings().group(group)) {
		};

		this.helmet = new ArmorItem(armour, EquipmentSlot.HEAD, new Item.Settings().group(group));
		this.chestplate = new ArmorItem(armour, EquipmentSlot.CHEST, new Item.Settings().group(group));
		this.leggings = new ArmorItem(armour, EquipmentSlot.LEGS, new Item.Settings().group(group));
		this.boots = new ArmorItem(armour, EquipmentSlot.FEET, new Item.Settings().group(group));

		this.registryName = registryName;
	}

	private final String registryName;

	public final Item pickaxe;
	public final Item axe;
	public final Item shovel;
	public final Item hoe;
	public final Item sword;
	
	public final Item helmet;
	public final Item chestplate;
	public final Item leggings;
	public final Item boots;
	
	public void registerItems() {
		Registry.register(Registry.ITEM, Lint.id(this.registryName + "_sword"), this.sword);
		Registry.register(Registry.ITEM, Lint.id(this.registryName + "_axe"), this.axe);
		Registry.register(Registry.ITEM, Lint.id(this.registryName + "_pickaxe"), this.pickaxe);
		Registry.register(Registry.ITEM, Lint.id(this.registryName + "_shovel"), this.shovel);
		Registry.register(Registry.ITEM, Lint.id(this.registryName + "_hoe"), this.hoe);
		
		Registry.register(Registry.ITEM, Lint.id(this.registryName + "_helmet"), this.helmet);
		Registry.register(Registry.ITEM, Lint.id(this.registryName + "_chestplate"), this.chestplate);
		Registry.register(Registry.ITEM, Lint.id(this.registryName + "_leggings"), this.leggings);
		Registry.register(Registry.ITEM, Lint.id(this.registryName + "_boots"), this.boots);
	}
}
