package me.mysticat.primarywarps.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import me.mysticat.primarywarps.Main;

//Useful file to save time
public class Utils {
	
	private static Main plugin;
	
	public Utils(Main plugin) {
		Utils.plugin = plugin;
	}

	public static String color(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}
	
	public static String leftAlign(String s, int limit) {
		StringBuilder text = new StringBuilder(s);
		int fill = limit - s.length();
		for (int i = 0; i < fill; i++) {
			text.append(" ");
		}
		return text.toString();
	}
	
	public static ItemStack createItem(String materialString, int amount, String displayName, String... loreString) {
		ItemStack item = new ItemStack(Objects.requireNonNull(Material.matchMaterial(materialString)), amount);
		List<String> lore = new ArrayList<>();
		ItemMeta meta = item.getItemMeta();
		
		for (String s : loreString) {
			lore.add(Utils.color(s));
		}
		meta.setLore(lore);
		meta.setDisplayName(Utils.color(displayName));
		item.setItemMeta(meta);
		
		return item;
	}
	
	public static String getItemNBTString(ItemStack item, String customKey) {
		NamespacedKey key = new NamespacedKey(plugin, customKey);
		ItemMeta meta = item.getItemMeta();
		PersistentDataContainer container = meta.getPersistentDataContainer();
		
		if (container.has(key,  PersistentDataType.STRING)) {
			return container.get(key, PersistentDataType.STRING);
		}
		return null;
	}
	
	public static Integer getItemNBTInt(ItemStack item, String customKey) {
		NamespacedKey key = new NamespacedKey(plugin, customKey);
		ItemMeta meta = item.getItemMeta();
		PersistentDataContainer container = meta.getPersistentDataContainer();
		
		if (container.has(key,  PersistentDataType.INTEGER)) {
			return container.get(key, PersistentDataType.INTEGER);
		}
		return null;
	}
	
	public static Double getItemNBTDouble(ItemStack item, String customKey) {
		NamespacedKey key = new NamespacedKey(plugin, customKey);
		ItemMeta meta = item.getItemMeta();
		PersistentDataContainer container = meta.getPersistentDataContainer();
		
		if (container.has(key,  PersistentDataType.DOUBLE)) {
			return container.get(key, PersistentDataType.DOUBLE);
		}
		return null;
	}
	
	public static void addItemNBTString(ItemStack item, String customKey, String info) {
		NamespacedKey key = new NamespacedKey(plugin, customKey);
		ItemMeta meta = item.getItemMeta();
		meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, info);
		item.setItemMeta(meta);
	}
	
	public static void addItemNBTInt(ItemStack item, String customKey, int info) {
		NamespacedKey key = new NamespacedKey(plugin, customKey);
		ItemMeta meta = item.getItemMeta();
		meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, info);
		item.setItemMeta(meta);
	}
	
	public static void addItemNBTDouble(ItemStack item, String customKey, Double info) {
		NamespacedKey key = new NamespacedKey(plugin, customKey);
		ItemMeta meta = item.getItemMeta();
		meta.getPersistentDataContainer().set(key, PersistentDataType.DOUBLE, info);
		item.setItemMeta(meta);
	}
	
	public static String getEnchantName(Enchantment e) {
		String full = Objects.requireNonNull(Enchantment.getByKey(e.getKey())).toString();
		return full.substring(full.indexOf(':') + 1, full.indexOf(','));
	}
	
	public static String getEnchantType(Enchantment e) {
		String full = Objects.requireNonNull(Enchantment.getByKey(e.getKey())).toString();
		full = full.substring(full.indexOf(' ') + 1);
		return full.substring(0, full.length() - 1);
	}
	
}
