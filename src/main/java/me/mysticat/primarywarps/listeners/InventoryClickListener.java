package me.mysticat.primarywarps.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import me.mysticat.primarywarps.ui.GUI;

public class InventoryClickListener implements Listener {
	
	@EventHandler
	public void onClick (InventoryClickEvent e) {	
		if (e.getClickedInventory() == null) return;
		if (e.getClickedInventory().getHolder() instanceof GUI) {
			e.setCancelled(true);
			if (e.getCurrentItem() == null) return;
			GUI.clicked((Player) e.getWhoClicked(), e.getCurrentItem());
		}
	}

}
