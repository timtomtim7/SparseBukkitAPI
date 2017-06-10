package me.tom.sparse.bukkit.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

public class BasicItem extends MenuItem
{
	private ItemStack item;
	private boolean update = false;
	
	private ClickResponse onClick;
	
	public BasicItem(ItemStack item)
	{
		this.item = item;
	}
	
	public BasicItem(ItemStack item, ClickResponse onClick)
	{
		this.item = item;
		this.onClick = onClick;
	}
	
	public void setItem(ItemStack item)
	{
		this.item = item;
		update = true;
	}
	
	public ItemStack getItem()
	{
		return item;
	}
	
	public boolean shouldUpdateItem()
	{
		if(update)
		{
			update = false;
			return true;
		}
		return false;
	}
	
	public boolean onClick(Player player, ClickType clickType, InventoryAction action)
	{
		if(onClick != null)
			return onClick.onClick(player, clickType, action);
		return false;
	}
}
