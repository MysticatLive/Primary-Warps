package me.mysticat.primarywarps.ui;


import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import me.mysticat.primarywarps.Main;
import me.mysticat.primarywarps.utils.Utils;
import me.mysticat.primarywarps.utils.Warp;

import java.util.Objects;

//Warp Menu GUI handler
public class GUI implements InventoryHolder {

	@Override
	public Inventory getInventory() {
		int invSpaces = 9;
		FileConfiguration file = Main.warps.getFile();
		
		if (Main.warps.getFile().contains("warps")) {
			invSpaces = Objects.requireNonNull(Main.warps.getFile().getConfigurationSection("warps")).getKeys(false).size() - 1;
			invSpaces += (9 - invSpaces % 9);
			if (invSpaces >= 54) invSpaces = 54;
		}

		Inventory inv = Bukkit.createInventory(this, invSpaces, Utils.color(Main.config.getFile().getString("inv-menu.title")));
		
		Objects.requireNonNull(file.getConfigurationSection("warps")).getKeys(false).forEach(warp -> {
			if (inv.firstEmpty() != -1) {
				inv.setItem(inv.firstEmpty(), Warp.getWarpItem(warp));
			}
		});
		return inv;
	}
	
	public static void clicked(Player p, ItemStack item) {
		Warp.useWarp(Utils.getItemNBTString(item, "warp-id"), p);
	}

}
