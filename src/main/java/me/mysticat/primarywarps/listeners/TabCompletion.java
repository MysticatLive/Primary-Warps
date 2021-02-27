package me.mysticat.primarywarps.listeners;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import me.mysticat.primarywarps.commands.WarpPermissions;

//Command QOL
public class TabCompletion implements TabCompleter, Listener {

	@Override
	public List<String> onTabComplete (CommandSender sender, Command cmd, String label, String[] args) {
		List<String> tab = new ArrayList<>();
		//Must test specifically this way or all commands will be affected!
		if (cmd.getName().equalsIgnoreCase("warp")) {
			if (args.length == 1) {
				Player p = (Player) sender;
				if (WarpPermissions.permissionToCreate(p)) {
					tab.add("create");
					tab.add("set");
					tab.add("add");
				}
				if (WarpPermissions.permissionToMenu(p))
					tab.add("menu");
				if (WarpPermissions.permissionToList(p))
					tab.add("list");
				if (WarpPermissions.permissionToRemove(p)) {
					tab.add("remove");
					tab.add("delete");
					tab.add("del");
				}
				if (WarpPermissions.permissionToSetItem(p))
					tab.add("setitem");
				if (WarpPermissions.permissionToGetItem(p))
					tab.add("getitem");
				if (WarpPermissions.permissionToSetName(p)) {
					tab.add("setname");
					tab.add("rename");
				}
				if (WarpPermissions.permissionToAddLore(p))
					tab.add("addlore");
				if (WarpPermissions.permissionToRemoveLore(p)) {
					tab.add("removelore");
					tab.add("deletelore");
					tab.add("dellore");
				}
				if (WarpPermissions.permissionToSetLore(p))
					tab.add("setlore");
				if (WarpPermissions.permissionToGetHelp(p))
					tab.add("help");
			}
			else if (args.length == 2 && (
						args[0].equalsIgnoreCase("create") ||
						args[0].equalsIgnoreCase("set") ||
						args[0].equalsIgnoreCase("add") ||
						args[0].equalsIgnoreCase("remove") ||
						args[0].equalsIgnoreCase("delete") ||
						args[0].equalsIgnoreCase("del") ||
						args[0].equalsIgnoreCase("setitem") ||
						args[0].equalsIgnoreCase("getitem") ||
						args[0].equalsIgnoreCase("setname") ||
						args[0].equalsIgnoreCase("rename") ||
						args[0].equalsIgnoreCase("addlore") ||
						args[0].equalsIgnoreCase("setlore") ||
						args[0].equalsIgnoreCase("removelore") ||
						args[0].equalsIgnoreCase("deletelore") ||
						args[0].equalsIgnoreCase("dellore")
				)
			) tab.add("<warp id>");
			else if (args.length == 3 &&
					args[0].equalsIgnoreCase("setname") ||
					args[0].equalsIgnoreCase("rename")
			) tab.add("[display name]");
			else if (args.length == 3 && (
						args[0].equalsIgnoreCase("removelore") ||
						args[0].equalsIgnoreCase("deletelore") ||
						args[0].equalsIgnoreCase("dellore")
					)
			) {
				tab.add("<line number>");
				tab.add("all");
			}
			else if (args.length == 3 && args[0].equalsIgnoreCase("setlore")) tab.add("<line number>");
			else if (args.length > 2 &&
					args[0].equalsIgnoreCase("addlore") ||
					args[0].equalsIgnoreCase("setlore")
			) tab.add("[text]");
			Collections.sort(tab);
		}
		return tab;
	}
}