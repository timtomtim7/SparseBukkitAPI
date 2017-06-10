package me.tom.sparse.bukkit.menu;

import me.tom.sparse.bukkit.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

public class ExampleMenu extends Menu
{
	public ExampleMenu()
	{
		super("Test Menu", 4);
		
		setItem(0, 0, new MenuItem()
		{
			public ItemStack getItem()
			{
				return new ItemBuilder(Material.PAPER).colorName("&a0,0").enchantEffect().build();
			}
			
			public boolean shouldUpdateItem()
			{
				return false;
			}
			
			public boolean onClick(Player player, ClickType clickType, InventoryAction action)
			{
				Bukkit.broadcastMessage(player.getName()+" clicked");
				return false;
			}
		});
		
		setItem(1,1, new BasicItem(
				new ItemBuilder(Material.PAPER).colorName("&a1,1").enchantEffect().build(),
				(player, clickType, action) ->
				{
					Bukkit.broadcastMessage(player.getName() + " clicked BasicItem");
					return false;
				})
		);
	}
}
