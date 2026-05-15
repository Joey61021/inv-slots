package com.invslots.plugin;

import com.invslots.plugin.config.Config;
import com.invslots.plugin.globals.Messages;
import com.invslots.plugin.user.UserManager;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class Core extends JavaPlugin {
	/* configs */
	@Getter private static Config messages;
	@Getter private static Config cfg;

	void registerEvent(Listener listener) {
		getServer().getPluginManager().registerEvents(listener, this);
	}

	void registerEvents() {
		registerEvent(new UserManager());
	}

	void registerCommands() {
		getServer().getCommandMap().registerAll("invslots", List.of(

		));
	}

	@Override
	public void onLoad() {
	}

	@Override
	public void onEnable() {
		messages = new Config(this, Messages.class, "messages.yml");
		cfg = new Config(this, Object.class, "config.yml");

		registerEvents();
		registerCommands();

		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

		Bukkit.getServicesManager().register(Core.class, this, this, ServicePriority.Normal);
	}

	@Override
	public void onDisable() {}

	public static String color(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}
}
