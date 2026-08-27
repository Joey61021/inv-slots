package com.invslots.plugin.events;

import com.invslots.plugin.globals.Keys;
import com.invslots.plugin.services.SlotService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.persistence.PersistentDataType;

@RequiredArgsConstructor
public class DeathListener implements Listener {

	@NonNull
	private final SlotService slotService;

	@EventHandler
	private void onDeath(PlayerDeathEvent event) {
		for (int i = 0; i < event.getDrops().size(); i++) {
			if (event.getDrops().get(i).getItemMeta().getPersistentDataContainer().has(Keys.BLOCKED_SLOT, PersistentDataType.BOOLEAN)) {
				event.getDrops().remove(i);
				i--;
			}
		}
	}

	@EventHandler
	private void onRespawn(PlayerRespawnEvent event) {
		slotService.blockSlots(event.getPlayer());
	}
}
