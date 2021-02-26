package me.mysticat.primarywarps.files;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import me.mysticat.primarywarps.Main;

//File manager for config files
public class DataManager {

	private final Main plugin;
	private FileConfiguration config = null;
	private File configFile = null;
	private final String fileName;
	
	public DataManager(Main plugin, String fileName) {
		this.plugin = plugin;
		this.fileName = fileName;
		saveDefaultFile();
	}
	
	public void reloadFile() {
		if (this.configFile == null) this.configFile = new File(this.plugin.getDataFolder(), fileName);
		this.config = YamlConfiguration.loadConfiguration(this.configFile);
		InputStream defaultStream = this.plugin.getResource(fileName);
		if (defaultStream != null) {
			YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
			this.config.setDefaults(defaultConfig);
		}
	}
	
	public FileConfiguration getFile() {
		if (this.config == null) reloadFile();
		return this.config;
	}
	
	public void saveFile() {
		if (this.config == null || this.configFile == null) return;
		try {
			this.getFile().save(this.configFile);
		}
		catch (IOException e) {
			plugin.getLogger().log(Level.SEVERE, "Could not save config file to " + this.configFile, e);
		}
	}
	
	public void saveDefaultFile() {
		if (this.configFile == null) this.configFile = new File(this.plugin.getDataFolder(), fileName);
		if (!this.configFile.exists()) this.plugin.saveResource(fileName, false);
	}
	
}