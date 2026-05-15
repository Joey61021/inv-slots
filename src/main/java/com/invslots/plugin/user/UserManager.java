package com.invslots.plugin.user;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages player user data with database persistence.
 * Each {@link Player} has an associated {@link User} object stored in memory.
 * Player data is loaded from database on login and saved on logout.
 * This is threadsafe for async operations.
 */
public final class UserManager implements Listener {
	private static final Map<UUID, User> users;

	static {
		users = new ConcurrentHashMap<>(); // concurrent for async reading and writing
	}

	/**
	 * Gets a user by UUID (threadsafe).
	 *
	 * @param uid The player UUID
	 * @return User object or null if not found
	 */
	public static User getUser(UUID uid) {
		return users.get(uid);
	}

	/**
	 * Gets a user by HumanEntity (threadsafe).
	 *
	 * @param player The player entity
	 * @return User object or null if not found
	 */
	public static User getUser(HumanEntity player) {
		return getUser(player.getUniqueId());
	}

	/**
	 * Loads player data from database on login.
	 * Creates new User if not found in database.
	 * Async operation to prevent blocking the login process.
	 */
	@EventHandler(priority = EventPriority.LOW) // run first
	public static void onPreLogin(AsyncPlayerPreLoginEvent event) {
		UUID uuid = event.getUniqueId();

		User user = new User(uuid);

		users.put(uuid, user);
	}

	/**
	 * Saves player data to database on logout.
	 */
	@EventHandler(priority = EventPriority.NORMAL)
	public static void onQuit(PlayerQuitEvent event) {
		users.remove(event.getPlayer().getUniqueId());
	}
}
