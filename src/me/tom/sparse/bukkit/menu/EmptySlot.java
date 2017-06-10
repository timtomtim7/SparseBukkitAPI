package me.tom.sparse.bukkit.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

public final class EmptySlot extends MenuItem
{
	public EmptySlot() {}
	
	public ItemStack getItem()
	{
		return null;
	}
	
	public boolean shouldUpdateItem()
	{
		return false;
	}
	
	public boolean onClick(Player player, ClickType clickType, InventoryAction action)
	{
		return false;
	}
}
