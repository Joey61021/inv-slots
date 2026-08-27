package com.invslots.plugin.events;

import com.invslots.plugin.globals.Keys;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.persistence.PersistentDataType;

public class DeathListener implements Listener {

	@EventHandler
	private void onDeath(PlayerDeathEvent event) {
		for (int i = 0; i < event.getDrops().size(); i++) {
			if (event.getDrops().get(i).getItemMeta().getPersistentDataContainer().has(Keys.BLOCKED_SLOT, PersistentDataType.BOOLEAN)) {
				event.getDrops().remove(i);
				i--;
			}
		}
	}
}
