package me.mysticat.primarywarps;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import me.mysticat.primarywarps.commands.WarpCommand;
import me.mysticat.primarywarps.files.DataManager;
import me.mysticat.primarywarps.listeners.InventoryClickListener;
import me.mysticat.primarywarps.listeners.TabCompletion;
import me.mysticat.primarywarps.utils.Utils;

public class Main extends JavaPlugin {

	public static String internalPluginPrefix = "&f[&b&lPW&f] ", pluginPrefix, responseNoWarpsExist, responseWarpDoesNotExist,
			responseWarpCreated, responseWarpAlreadyExists, responseWarpRemoved, responseWarpRenamed, responseWarpInvalidName,
			responseItemUpdated, responseInvalidPerms, responseInvalidCmd, responseLoreAdded, responseLoreSet,
			responseLoreRemoved, responseLoreRemovedAll, responseLoreDoesNotExist;
	public static DataManager config;
	public static DataManager warps;
	
	@Override
	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage(Utils.color(internalPluginPrefix + "> Plugin ENABLED successfully!"));
		new Utils(this);
		config = new DataManager(this, "config.yml");
		warps = new DataManager(this, "warps.yml");
		setResponses();
		getCommand("warp").setExecutor(new WarpCommand());
		getCommand("warp").setTabCompleter(new TabCompletion());
		getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
	}

	@Override
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage(Utils.color(internalPluginPrefix + "> Plugin DISABLED successfully!"));
	}

	private void setResponses() {
		FileConfiguration file = config.getFile();
		pluginPrefix = Utils.color("" + file.getString("messages.pluginPrefix") + " ");
		responseNoWarpsExist = pluginPrefix + Utils.color(file.getString("messages.responseNoWarpsExist"));
		responseWarpDoesNotExist = pluginPrefix + Utils.color(file.getString("messages.responseWarpDoesNotExist"));
		responseWarpCreated = pluginPrefix + Utils.color(file.getString("messages.responseWarpCreated"));
		responseWarpAlreadyExists = pluginPrefix + Utils.color(file.getString("messages.responseWarpAlreadyExists"));
		responseWarpRemoved = pluginPrefix + Utils.color(file.getString("messages.responseWarpRemoved"));
		responseWarpRenamed = pluginPrefix + Utils.color(file.getString("messages.responseWarpRenamed"));
		responseWarpInvalidName = pluginPrefix + Utils.color(file.getString("messages.responseWarpInvalidName"));
		responseItemUpdated = pluginPrefix + Utils.color(file.getString("messages.responseItemUpdated"));
		responseInvalidPerms = pluginPrefix + Utils.color(file.getString("messages.responseInvalidPerms"));
		responseInvalidCmd = pluginPrefix + Utils.color(file.getString("messages.responseInvalidCmd"));
		responseLoreAdded = pluginPrefix + Utils.color(file.getString("messages.responseLoreAdded"));
		responseLoreSet = pluginPrefix + Utils.color(file.getString("messages.responseLoreSet"));
		responseLoreRemoved = pluginPrefix + Utils.color(file.getString("messages.responseLoreRemoved"));
		responseLoreRemovedAll = pluginPrefix + Utils.color(file.getString("messages.responseLoreRemovedAll"));
		responseLoreDoesNotExist = pluginPrefix + Utils.color(file.getString("messages.responseLoreDoesNotExist"));
	}
	
}
