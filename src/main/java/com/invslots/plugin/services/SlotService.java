package com.invslots.plugin.services;

import com.invslots.plugin.Core;
import com.invslots.plugin.builders.ItemBuilder;
import com.invslots.plugin.globals.Keys;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.persistence.PersistentDataType;

public class SlotService implements Listener {

	public void blockSlots(Player player) {
		Inventory inv = player.getInventory();

		ItemBuilder ib = new ItemBuilder(Material.BARRIER);
		ib.setDisplayName("&0#");
		ib.addPersistentContainer(Keys.BLOCKED_SLOT, PersistentDataType.BOOLEAN, true);

		for (int i = Core.getSLOTS(); i < 35; i++) {
			inv.setItem(i, ib.build());
		}
	}
}
