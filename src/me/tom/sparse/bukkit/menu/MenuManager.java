package me.tom.sparse.bukkit.menu;

import me.tom.sparse.bukkit.SparseAPIPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.HashSet;
import java.util.Set;

public final class MenuManager implements Listener, Runnable
{
	private static Set<Menu> openMenus = new HashSet<>();
	
	private static boolean initialized;
	
	public static void init()
	{
		if(initialized)
			return;
		initialized = true;
		MenuManager instance = new MenuManager();
		Bukkit.getScheduler().scheduleSyncRepeatingTask(SparseAPIPlugin.getInstance(), instance, 1, 1);
		Bukkit.getPluginManager().registerEvents(instance, SparseAPIPlugin.getInstance());
	}
	
	private MenuManager() {}
	
	@EventHandler
	public void onInventoryOpen(InventoryOpenEvent e)
	{
		if(!(e.getPlayer() instanceof Player))
			return;
		
		Inventory inv = e.getInventory();
		InventoryHolder holder = inv.getHolder();
		if(holder != null && holder instanceof Menu)
		{
			Menu menu = (Menu) holder;
			menu.viewers.add((Player) e.getPlayer());
			openMenus.add(menu);
		}
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e)
	{
		if(!(e.getPlayer() instanceof Player))
			return;
		
		Inventory inv = e.getInventory();
		InventoryHolder holder = inv.getHolder();
		if(holder != null && holder instanceof Menu)
		{
			Menu menu = (Menu) holder;
			menu.viewers.remove(e.getPlayer());
			if(menu.viewers.size() == 0)
				openMenus.remove(menu);
		}
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e)
	{
		if(!(e.getWhoClicked() instanceof Player))
			return;
		
		Inventory inv = e.getInventory();
		InventoryHolder holder = inv.getHolder();
		if(holder != null && holder instanceof Menu)
		{
			Menu menu = (Menu) holder;
			menu.onClick(e);
		}
	}
	
	public void run()
	{
		Set<Menu> deadMenus = new HashSet<>();
		
		for(Menu openMenu : openMenus)
		{
			openMenu.viewers.removeIf(it -> !it.isOnline());
			if(openMenu.viewers.size() == 0)
			{
				deadMenus.add(openMenu);
			}else{
				openMenu.onTick();
			}
		}
		
		openMenus.removeAll(deadMenus);
	}
}
