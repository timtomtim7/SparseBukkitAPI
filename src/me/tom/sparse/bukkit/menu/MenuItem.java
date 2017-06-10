package me.tom.sparse.bukkit.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

public abstract class MenuItem
{
	public abstract ItemStack getItem();
	public abstract boolean shouldUpdateItem();
	
	public abstract boolean onClick(Player player, ClickType clickType, InventoryAction action);
}
