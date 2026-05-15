package com.invslots.plugin.globals;

import com.invslots.plugin.Core;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public class Keys {
	public static NamespacedKey BLOCKED_SLOT = createKey("blocked_slot");

	private static NamespacedKey createKey(String value) {
		return new NamespacedKey(JavaPlugin.getPlugin(Core.class), value);
	}
}
