package me.mysticat.primarywarps.commands;

import org.bukkit.entity.Player;

//Permissions handler for warp command
public class WarpPermissions {
	
	public static boolean permissionToMenu(Player p) {
		return (p.hasPermission("PrimaryWarps.menu") || p.hasPermission("PrimaryWarps.*"));
	}
	
	public static boolean permissionToList(Player p) {
		return (p.hasPermission("PrimaryWarps.list") && p.hasPermission("PrimaryWarps.*"));
	}
	
	public static boolean permissionToUse(String name, Player p) {
		return (!p.hasPermission("PrimaryWarps.use." + name) && !p.hasPermission("PrimaryWarps.use.*") && !p.hasPermission("PrimaryWarps.*"));
	}
	
	public static boolean permissionToCreate(Player p) {
		return (p.hasPermission("PrimaryWarps.create") && p.hasPermission("PrimaryWarps.*"));
	}
	
	public static boolean permissionToRemove(Player p) {
		return (p.hasPermission("PrimaryWarps.remove") && p.hasPermission("PrimaryWarps.*"));
	}
	
	public static boolean permissionToSetItem(Player p) {
		return (p.hasPermission("PrimaryWarps.set.item") && p.hasPermission("PrimaryWarps.set.*") && p.hasPermission("PrimaryWarps.*"));
	}
	
	public static boolean permissionToGetItem(Player p) {
		return (p.hasPermission("PrimaryWarps.get.item") && p.hasPermission("PrimaryWarps.get.*") && p.hasPermission("PrimaryWarps.*"));
	}
	
	public static boolean permissionToSetName(Player p) {
		return (p.hasPermission("PrimaryWarps.set.name") && p.hasPermission("PrimaryWarps.set.*") && p.hasPermission("PrimaryWarps.*"));
	}
	
	public static boolean permissionToAddLore(Player p) {
		return (p.hasPermission("PrimaryWarps.lore.add") && p.hasPermission("PrimaryWarps.lore.*") && p.hasPermission("PrimaryWarps.*"));
	}
	
	public static boolean permissionToSetLore(Player p) {
		return (p.hasPermission("PrimaryWarps.lore.set") && p.hasPermission("PrimaryWarps.lore.*") && p.hasPermission("PrimaryWarps.*"));
	}
	
	public static boolean permissionToRemoveLore(Player p) {
		return (p.hasPermission("PrimaryWarps.lore.remove") && p.hasPermission("PrimaryWarps.lore.*") && p.hasPermission("PrimaryWarps.*"));
	}
	
	public static boolean permissionToGetHelp(Player p) {
		return (p.hasPermission("PrimaryWarps.help") && p.hasPermission("PrimaryWarps.*"));
	}
}
