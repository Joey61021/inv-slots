package com.invslots.plugin.events;

import com.invslots.plugin.globals.Keys;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class ClickListener implements Listener {

	@EventHandler
	private void onClick(InventoryClickEvent event) {
		ItemStack item = event.getCurrentItem();

		if (item == null || item.getType().isAir()) {
			return;
		}

		PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
		if (container.has(Keys.BLOCKED_SLOT, PersistentDataType.BOOLEAN)) {
			event.setCancelled(true);
		}
	}
}
