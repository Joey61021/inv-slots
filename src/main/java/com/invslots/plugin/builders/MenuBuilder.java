package com.invslots.plugin.builders;

import com.invslots.plugin.Core;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class MenuBuilder implements InventoryHolder {

	protected final Inventory inventory;

	public MenuBuilder(String title, int rows) {
		this.inventory = Bukkit.createInventory(this, rows * 9, Core.color(title));
	}

	@Override
	public Inventory getInventory() {
		return inventory;
	}

	public MenuBuilder fill() {
		ItemStack item = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("&0#").build();

		for (int i = 0; i < inventory.getSize(); i++) {
			inventory.setItem(i, item);
		}

		return this;
	}

	public Inventory build() {
		return inventory;
	}
}
