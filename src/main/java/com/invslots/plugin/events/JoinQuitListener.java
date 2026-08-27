package com.invslots.plugin.events;

import com.invslots.plugin.services.SlotService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@RequiredArgsConstructor
public class JoinQuitListener implements Listener {

	@NonNull
	private final SlotService slotService;

	@EventHandler
	private void onJoin(PlayerJoinEvent event) {
		slotService.blockSlots(event.getPlayer());
	}
}
