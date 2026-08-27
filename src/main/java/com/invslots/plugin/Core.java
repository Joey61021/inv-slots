package com.invslots.plugin;

import com.invslots.plugin.config.Config;
import com.invslots.plugin.events.ClickListener;
import com.invslots.plugin.events.DeathListener;
import com.invslots.plugin.events.JoinQuitListener;
import com.invslots.plugin.services.SlotService;
import com.invslots.plugin.user.UserManager;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin {
	/* configs */
	@Getter private static Config cfg;

	/* variables */
	@Getter private static int SLOTS = 9;

	/* services */
	@Getter private SlotService slotService;

	void registerEvent(Listener listener) {
		getServer().getPluginManager().registerEvents(listener, this);
	}

	void registerEvents() {
		registerEvent(new UserManager());
		registerEvent(new ClickListener());
		registerEvent(new DeathListener());
		registerEvent(new JoinQuitListener(slotService));
	}

	@Override
	public void onLoad() {
	}

	@Override
	public void onEnable() {
		/* configs */
		cfg = new Config(this, Object.class, "config.yml");

		/* variables */
		SLOTS = cfg.getInt("slots");

		/* services */
		slotService = new SlotService();

		registerEvents();

		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

		Bukkit.getServicesManager().register(Core.class, this, this, ServicePriority.Normal);
	}

	@Override
	public void onDisable() {}

	public static String color(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}
}
