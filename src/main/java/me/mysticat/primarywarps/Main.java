package me.mysticat.primarywarps;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.mysticat.primarywarps.commands.WarpCommand;
import me.mysticat.primarywarps.files.DataManager;
import me.mysticat.primarywarps.listeners.InventoryClickListener;
import me.mysticat.primarywarps.listeners.TabCompletion;
import me.mysticat.primarywarps.utils.Utils;

public class Main extends JavaPlugin {

	public static String pluginPrefix = "&f[&b&lPW&f] ";
	public static DataManager config;
	public static DataManager warps;
	
	@Override
	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage(Utils.color(pluginPrefix + "> Plugin ENABLED successfully!"));
		new Utils(this);
		Main.config = new DataManager(this, "config.yml");
		Main.warps = new DataManager(this, "warps.yml");
		getCommand("warp").setExecutor(new WarpCommand());
		getCommand("warp").setTabCompleter(new TabCompletion());
		getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
	}

	@Override
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage(Utils.color(pluginPrefix + "> Plugin DISABLED successfully!"));
	}
	
}
