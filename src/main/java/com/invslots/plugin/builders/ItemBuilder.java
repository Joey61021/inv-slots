package com.invslots.plugin.builders;

import com.invslots.plugin.Core;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemBuilder {
	private final Material material;
	private final List<String> lore = new ArrayList<>();
	private final List<PersistentEntry> persistentEntries = new ArrayList<>();
	private final Map<Enchantment, Integer> enchantments = new HashMap<>();
	private int amount = 1;
	private String displayName;
	private boolean glow = false;
	private OfflinePlayer skullOwner;
	private boolean unbreakable = false;
	private Color color;

	public ItemBuilder(Material material) {
		this.material = material;
	}

	public ItemBuilder(Material material, int amount) {
		this.material = material;
		this.amount = amount;
	}

	public ItemBuilder addEnchant(Enchantment enchantment, int level) {
		this.enchantments.put(enchantment, level);
		return this;
	}

	public ItemBuilder setUnbreakable() {
		this.unbreakable = true;
		return this;
	}

	public ItemBuilder setDisplayName(String displayName) {
		this.displayName = displayName;
		return this;
	}

	public ItemBuilder setLore(List<String> lore) {
		for (String value : lore) {
			this.lore.add(Core.color("&7" + value));
		}
		return this;
	}

	public ItemBuilder setGlow() {
		this.glow = true;
		return this;
	}

	public ItemBuilder setSkull(String skullOwner) {
		this.skullOwner = Bukkit.getOfflinePlayer(skullOwner);
		return this;
	}

	public ItemBuilder setSkull(Player player) {
		this.skullOwner = player;
		return this;
	}

	public ItemBuilder setColor(Color color) {
		this.color = color;
		return this;
	}

	public ItemBuilder addPersistentContainer(NamespacedKey key, String value) {
		persistentEntries.add(new PersistentEntry(key, PersistentDataType.STRING, value));
		return this;
	}

	public <T, Z> ItemBuilder addPersistentContainer(NamespacedKey key, PersistentDataType<T, Z> type, Z value) {
		persistentEntries.add(new PersistentEntry(key, type, value));
		return this;
	}

	public ItemBuilder addLore(String value) {
		this.lore.add(Core.color("&7" + value));
		return this;
	}

	public ItemStack build() {
		ItemStack item = new ItemStack(material, amount);
		ItemMeta meta = item.getItemMeta();

		if (meta == null) {
			return item;
		}

		if (skullOwner != null && meta instanceof SkullMeta skullMeta) {
			skullMeta.setOwningPlayer(skullOwner);
		}

		for (PersistentEntry entry : persistentEntries) {
			entry.apply(meta);
		}

		meta.setUnbreakable(unbreakable);

		if (displayName != null) {
			meta.setDisplayName(Core.color(displayName));
		}

		if (!lore.isEmpty()) {
			meta.setLore(lore);
		}

		if (color != null && meta instanceof LeatherArmorMeta leatherArmorMeta) {
			leatherArmorMeta.setColor(color);
		}

		if (glow) {
			meta.addEnchant(Enchantment.UNBREAKING, 1, true);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}

		enchantments.forEach((enchant, level) -> meta.addEnchant(enchant, level, true));

		item.setItemMeta(meta);
		return item;
	}

	private record PersistentEntry(NamespacedKey key, PersistentDataType<?, ?> type, Object value) {

		@SuppressWarnings("unchecked")
		private void apply(ItemMeta meta) {
			PersistentDataType<Object, Object> castType = (PersistentDataType<Object, Object>) type;
			meta.getPersistentDataContainer().set(key, castType, value);
		}
	}
}
