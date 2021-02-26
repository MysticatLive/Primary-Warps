package me.mysticat.primarywarps.utils;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import me.mysticat.primarywarps.Main;

public class Warp {
	
	public static void createWarp(String name, Player p) {
		
		FileConfiguration data = Main.warps.getFile();
		data.set("warps." + name + ".x", p.getLocation().getX());
		data.set("warps." + name + ".y", p.getLocation().getY());
		data.set("warps." + name + ".z", p.getLocation().getZ());
		data.set("warps." + name + ".pitch", p.getLocation().getPitch());
		data.set("warps." + name + ".yaw", p.getLocation().getYaw());
		data.set("warps." + name + ".world", p.getLocation().getWorld().getName());
		setWarpItem(name, p.getInventory().getItemInMainHand());
	}
	
	public static void setWarpItem(String name, ItemStack item) {
		FileConfiguration data = Main.warps.getFile();
		data.set("warps." + name + ".item", null);
		String displayName = name, mat = item.getType().name();
		ItemMeta meta = item.getItemMeta();
		int amount = item.getAmount();
		
		if (mat.equalsIgnoreCase("AIR")) {
			mat = Utils.color(Main.config.getFile().getString("inv-menu.default-item"));
			amount = 1;
		}
		else {
			if (meta.hasDisplayName())
				displayName = meta.getDisplayName();
			if (meta.hasEnchants()) {
				Map<Enchantment, Integer> enchantments = item.getEnchantments();
				for(Enchantment e : enchantments.keySet()) {
					data.set("warps." + name + ".item.enchantments." + Utils.getEnchantName(e), enchantments.get(e));
				}
			}
			if (meta.hasLore()) {
				int i = 0;
				for (String s : Objects.requireNonNull(meta.getLore())) {
					data.set("warps." + name + ".item.lore." + i, s);
					i++;
				}
			}
		}
		data.set("warps." + name + ".item.material.type", mat);
		data.set("warps." + name + ".item.amount", amount);
		data.set("warps." + name + ".item.meta", meta);
		data.set("warps." + name + ".item.meta.name", displayName);
		Main.warps.saveFile();		
	}
	
	public static ItemStack getWarpItem(String name) {
		FileConfiguration file = Main.warps.getFile();
		ItemStack item = Utils.createItem(file.getString("warps." + name + ".item.material.type"),
				file.getInt("warps." + name + ".item.amount"),
				file.getString("warps." + name + ".item.meta.name")); //add lore
		Utils.addItemNBTString(item, "warp-id", name);
		
		if (Main.warps.getFile().contains("warps." + name + ".item.enchantments")) {
			Objects.requireNonNull(file.getConfigurationSection("warps." + name + ".item.enchantments")).getKeys(false).forEach(e -> {
				item.addUnsafeEnchantment(Objects.requireNonNull(Enchantment.getByKey(NamespacedKey.minecraft(e.toLowerCase()))), file.getInt("warps." + name + ".item.enchantments." + e));
			});
		}
		
		if (Main.warps.getFile().contains("warps." + name + ".item.lore")) {
			ArrayList<String> lore = new ArrayList<>();
			ItemMeta meta = item.getItemMeta();
			Objects.requireNonNull(file.getConfigurationSection("warps." + name + ".item.lore")).getKeys(false).forEach(s -> {
				lore.add(Utils.color(file.getString("warps." + name + ".item.lore." + s)));
			});
			meta.setLore(lore);
			item.setItemMeta(meta);
		}
		return item;
	}
	
	public static void setWarpName(String name, String newName) {
		Main.warps.getFile().set("warps." + name + ".item.meta.name", newName);
		Main.warps.saveFile();
	}
	
	public static void removeWarp(String name) {
		Main.warps.getFile().set("warps." + name, null);
		Main.warps.saveFile();
	}
	
	public static boolean warpExists(String name) {
		return Main.warps.getFile().contains("warps." + name);
	}
	
	public static void useWarp(String name, Player p) {
		FileConfiguration data = Main.warps.getFile();
		Location dest = new Location(Bukkit.getWorld(Objects.requireNonNull(data.getString("warps." + name + ".world"))),
				data.getDouble("warps." + name + ".x"),data.getDouble("warps." + name + ".y"), data.getDouble("warps." + name + ".z"), 
				(float)data.getDouble("warps." + name + ".yaw"), (float)data.getDouble("warps." + name + ".pitch"));
		p.teleport(dest);
		p.sendMessage(Utils.color("&f[&b&lPW&f] You have been warped to &b&o" + name + "&f."));
	}
	
	public static void listAllWarps(Player p) {
		FileConfiguration data = Main.warps.getFile();
		p.sendMessage(Utils.color("&f-------[&b&lPrimary Warps&f]-------"));
		Objects.requireNonNull(data.getConfigurationSection("warps")).getKeys(false).forEach(w -> {
				p.sendMessage(Utils.color(Utils.leftAlign(" &fid: &b&o" + w, 26) + Utils.leftAlign(" &fname: &b&o" + data.getString("warps." + w + ".item.meta.name"), 28)));
		});
		p.sendMessage(Utils.color("&f-----------------------------"));
	}
	
}
