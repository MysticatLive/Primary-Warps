package me.mysticat.primarywarps.commands;

import java.util.Arrays;
import java.util.Objects;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import me.mysticat.primarywarps.Main;
import me.mysticat.primarywarps.ui.GUI;
import me.mysticat.primarywarps.utils.Utils;
import me.mysticat.primarywarps.utils.Warp;

public class WarpCommand implements CommandExecutor {

	//Master command for plugin
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players can use that command!");
			return true;
		}
		Player p = (Player) sender;
		if (args.length == 0) {
			displayHelp(p);
		}
		else if (args.length == 1) {
			if (args[0].equalsIgnoreCase("menu")) warpMenu(p);
			else if (args[0].equalsIgnoreCase("help")) displayHelp(p);
			else if (args[0].equalsIgnoreCase("list")) warpList(p);
			else if (Warp.warpExists(args[0])) warpUse(args[0], p);
			else p.sendMessage(Main.responseWarpDoesNotExist.replace("<warp>", args[0]));
		}
		else if (args.length == 2) {
			if (args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("set") || args[0].equalsIgnoreCase("add"))
				warpCreate(args[1], p);
			else if (args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("delete") || args[0].equalsIgnoreCase("del"))
				warpRemove(args[1], p);
			else if (args[0].equalsIgnoreCase("setitem"))
				warpSetItem(args[1], p);
			else if (args[0].equalsIgnoreCase("getitem"))
				warpGetItem(args[1], p);
			else p.sendMessage(Main.responseInvalidCmd.replace("<warp>", args[1]));
		}
		else {
			if (args[0].equalsIgnoreCase("setname") || args[0].equalsIgnoreCase("rename")) {
				String input = String.join(" ", Arrays.asList(args).subList(2, args.length).toArray(new String[]{}));
				if (!input.equalsIgnoreCase("") && !input.equalsIgnoreCase(" ")) warpSetName(args[1], input, p);
				else p.sendMessage(Main.responseInvalidCmd.replace("<warp>", args[1]));
			}
			else if (args[0].equalsIgnoreCase("addlore")) {
				String input = String.join(" ", Arrays.asList(args).subList(2, args.length).toArray(new String[]{}));
				if (!input.equalsIgnoreCase("") && !input.equalsIgnoreCase(" ")) warpAddLore(args[1], p, input);
				else p.sendMessage(Main.responseInvalidCmd.replace("<warp>", args[1]));
			}
			else if (args[0].equalsIgnoreCase("setlore")) {
				String input = String.join(" ", Arrays.asList(args).subList(3, args.length).toArray(new String[]{}));
				if (!input.equalsIgnoreCase("") && !input.equalsIgnoreCase(" ")) warpSetLore(args[1], p, args[2], input);
				else p.sendMessage(Main.responseInvalidCmd.replace("<warp>", args[1]));
			}
			else if (args[0].equalsIgnoreCase("removelore") || args[0].equalsIgnoreCase("deletelore") || args[0].equalsIgnoreCase("dellore")) {
				warpRemoveLore(args[1], p, args[2]);
			}
			else p.sendMessage(Main.responseInvalidCmd.replace("<warp>", args[1]));
		}
		return true;
	}
	
	private void warpMenu(Player p) {
		if (!WarpPermissions.permissionToMenu(p)) p.sendMessage(Main.responseInvalidPerms);
		else if (Main.warps.getFile().contains("warps")) {
			GUI gui = new GUI();
			p.openInventory(gui.getInventory());
		}
		else p.sendMessage(Main.responseNoWarpsExist);
	}
	
	private void warpList(Player p) {
		if (!WarpPermissions.permissionToList(p)) p.sendMessage(Main.responseInvalidPerms);
		if (Main.warps.getFile().contains("warps")) Warp.listAllWarps(p);
		else p.sendMessage(Main.responseNoWarpsExist);
	}
	
	private void warpUse(String name, Player p) {
		if (!WarpPermissions.permissionToUse(name, p)) p.sendMessage(Main.responseInvalidPerms);
		Warp.useWarp(name, p);
	}
	
	private void warpCreate(String name, Player p) {
		if (!WarpPermissions.permissionToCreate(p)) p.sendMessage(Main.responseInvalidPerms.replace("<warp>", name));
		else if (!validWarpName(name)) p.sendMessage(Main.responseWarpInvalidName.replace("<warp>", name));
		else if (!(Warp.warpExists(name))) {
			Warp.createWarp(name, p);
			p.sendMessage(Main.responseWarpCreated.replace("<warp>", name));
		}
		else p.sendMessage(Main.responseWarpAlreadyExists.replace("<warp>", name));
	}
	
	private void warpRemove(String name, Player p) {
		if (!WarpPermissions.permissionToRemove(p)) p.sendMessage(Main.responseInvalidPerms);
		else if (Warp.warpExists(name)) {
			Warp.removeWarp(name);
			p.sendMessage(Main.responseWarpRemoved.replace("<warp>", name));
		}
		else p.sendMessage(Main.responseWarpDoesNotExist.replace("<warp>", name));
	}
	
	private void warpSetItem(String name, Player p) {
		if (!WarpPermissions.permissionToSetItem(p)) p.sendMessage(Main.responseInvalidPerms);
		else if (Warp.warpExists(name)) {
			Warp.setWarpItem(name, p.getInventory().getItemInMainHand());
			p.sendMessage(Main.responseItemUpdated.replace("<warp>", name));
		}
		else p.sendMessage(Main.responseWarpDoesNotExist.replace("<warp>", name));
	}
	
	private void warpGetItem(String name, Player p) {
		if (!WarpPermissions.permissionToGetItem(p)) p.sendMessage(Main.responseInvalidPerms);
		else if (Warp.warpExists(name)) {
			p.getInventory().addItem(Warp.getWarpItem(name));
			p.updateInventory();
		}
		else p.sendMessage(Main.responseWarpDoesNotExist.replace("<warp>", name));
	}
	
	private void warpSetName(String name, String newName, Player p) {
		if (!WarpPermissions.permissionToSetName(p)) p.sendMessage(Main.responseInvalidPerms);
		else if (Warp.warpExists(name)) {
			if (!validWarpName(name)) p.sendMessage(Main.responseWarpInvalidName.replace("<warp>", name));
			else if (!(Warp.warpExists(newName))) {
				Warp.setWarpName(name, newName);
				p.sendMessage(Main.responseWarpRenamed.replace("<warp>", name).replace("<name>", Utils.color(newName)));
			}
			else p.sendMessage(Main.responseWarpAlreadyExists.replace("<warp>", name));
		}
		else p.sendMessage(Main.responseWarpDoesNotExist.replace("<warp>", name));
	}
	
	private void warpAddLore(String name, Player p, String s) {
		FileConfiguration data = Main.warps.getFile();
		if (!WarpPermissions.permissionToAddLore(p)) p.sendMessage(Main.responseInvalidPerms.replace("<warp>", name));
		else if (Warp.warpExists(name)) {
			int numLore = 0;
			if (data.contains("warps." + name + ".item.lore"))
				numLore = Objects.requireNonNull(data.getConfigurationSection("warps." + name + ".item.lore")).getKeys(false).size();
			data.set("warps." + name + ".item.lore." + numLore, s);
			Main.warps.saveFile();
			p.sendMessage(Utils.color(Main.responseLoreAdded.replace("<warp>", name).replace("<lore>", Utils.color(s))));
		}
		else p.sendMessage(Main.responseWarpDoesNotExist.replace("<warp>", name));
	}
	
	private void warpSetLore(String name, Player p, String line, String s) {
		FileConfiguration data = Main.warps.getFile();
		if (!WarpPermissions.permissionToSetLore(p)) p.sendMessage(Main.responseInvalidPerms.replace("<warp>", name));
		else if (Warp.warpExists(name)) {
			String realLine = String.valueOf(Integer.parseInt(line) - 1);
			if (data.contains("warps." + name + ".item.lore." + realLine)) {
				data.set("warps." + name + ".item.lore." + realLine, s);
				Main.warps.saveFile();
				p.sendMessage(Utils.color(Main.responseLoreSet.replace("<warp>", name).replace("<lore>", Utils.color(s)).replace("<line>", line)));
			}
			else p.sendMessage(Utils.color(Main.responseLoreDoesNotExist.replace("<warp>", name).replace("<line>", line)));
		}
		else p.sendMessage(Main.responseWarpDoesNotExist.replace("<warp>", name));
	}
	
	private void warpRemoveLore(String name, Player p, String line) {
		FileConfiguration data = Main.warps.getFile();
		if (!WarpPermissions.permissionToRemoveLore(p)) p.sendMessage(Main.responseInvalidPerms.replace("<warp>", name));
		else if (Warp.warpExists(name)) {
			if (line.equalsIgnoreCase("all")) {
				data.set("warps." + name + ".item.lore", null);
				Main.warps.saveFile();
				p.sendMessage(Utils.color(Main.responseLoreRemovedAll.replace("<warp>", name).replace("<line>", line)));
			}
			else if (!line.matches("\\d+")) p.sendMessage(Main.responseInvalidCmd.replace("<warp>", name));
			else {
				String realLine = String.valueOf(Integer.parseInt(line) - 1);
				if (data.contains("warps." + name + ".item.lore." + realLine)) {
					data.set("warps." + name + ".item.lore." + realLine, null);
					Main.warps.saveFile();
					p.sendMessage(Utils.color(Main.responseLoreRemoved.replace("<warp>", name).replace("<line>", line)));
				}
				else p.sendMessage(Utils.color(Main.responseLoreDoesNotExist.replace("<warp>", name).replace("<line>", line)));
			}
		}
		else p.sendMessage(Main.responseWarpDoesNotExist.replace("<warp>", name));
	}
	
	private void displayHelp(Player p) {
		if (!WarpPermissions.permissionToGetHelp(p)) p.sendMessage(Main.responseInvalidPerms);
		p.sendMessage(Utils.color("&f-------[&b&lPrimary Warps&f]-------"));
		p.sendMessage(Utils.color("&b/warp [help] &7&oView this page."));
		p.sendMessage(Utils.color("&b/warp menu &7&oView the warp menu."));
		p.sendMessage(Utils.color("&b/warp list &7&oList every warp ID."));
		p.sendMessage(Utils.color("&b/warp <id> &7&oWarp to a saved warp."));
		p.sendMessage(Utils.color("&b/warp create/set/add <id> &7&oCreate a new warp here."));
		p.sendMessage(Utils.color("&b/warp remove/delete/del <id> &7&oRemove a warp."));
		p.sendMessage(Utils.color("&b/warp setitem <id> &7&oChange the item displayed for a warp to the item held."));
		p.sendMessage(Utils.color("&b/warp getitem <id> &7&oGet the item displayed for a warp."));
		p.sendMessage(Utils.color("&b/warp setname/rename <id> <name> &7&oChange the display name of a warp."));
		p.sendMessage(Utils.color("&b/warp addlore <id> <text> &7&oAdd subtext to a warp name."));
		p.sendMessage(Utils.color("&b/warp removelore/deletelore/dellore <id> <line/all> &7&oRemove subtext in a warp's name."));
		p.sendMessage(Utils.color("&b/warp setlore <id> <line> <text> &7&oChange subtext in a warp's name."));
		p.sendMessage(Utils.color("&f-----------------------------"));
	}
	
	private boolean validWarpName(String name) {
		return !(name.equalsIgnoreCase("help") || name.equalsIgnoreCase("menu") || name.equalsIgnoreCase("list")
				|| name.equalsIgnoreCase("create") || name.equalsIgnoreCase("set") || name.equalsIgnoreCase("add")
				|| name.equalsIgnoreCase("remove") || name.equalsIgnoreCase("delete") || name.equalsIgnoreCase("del")
				|| name.equalsIgnoreCase("setitem") || name.equalsIgnoreCase("setname") || name.equalsIgnoreCase("rename")
				|| name.equalsIgnoreCase("addlore") || name.equalsIgnoreCase("removelore") || name.equalsIgnoreCase("deletelore")
				|| name.equalsIgnoreCase("dellore") || name.equalsIgnoreCase("setlore") || name.equalsIgnoreCase("getitem"));
	}
	
}