package me.tom.sparse.bukkit.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

public interface ClickResponse
{
	boolean onClick(Player player, ClickType clickType, InventoryAction action);
}
