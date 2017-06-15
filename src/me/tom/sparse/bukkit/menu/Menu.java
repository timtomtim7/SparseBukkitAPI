package me.tom.sparse.bukkit.menu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class Menu implements InventoryHolder
{
	private static final EmptySlot EMPTY_SLOT = new EmptySlot();
	
	Set<Player> viewers = new HashSet<>();
	
	public final String title;
	
	public final int width = 9;
	public final int height;
	
	private final MenuItem[] slots;
	private final Inventory inventory;
	
	public boolean disablePlayerInventory = true;
	
	public Consumer<InventoryClickEvent> playerInventoryCallback;
	
	public Menu(String title, int height)
	{
		this.title = title;
		this.height = height;
		this.slots = new MenuItem[width*height];
		this.inventory = Bukkit.createInventory(this, width*height, title);
		Arrays.fill(slots, EMPTY_SLOT);
	}
	
	public void onTick()
	{
		for(int i = 0; i < slots.length; i++)
		{
			MenuItem item = slots[i];
			if(item.shouldUpdateItem())
				inventory.setItem(i, item.getItem());
		}
	}
	
	public void onClick(InventoryClickEvent e)
	{
		if(e.getClickedInventory() == e.getView().getTopInventory())
		{
			int slot = e.getSlot();
			if(slot != -1)
			{
				try{
					e.setCancelled(!slots[slot].onClick((Player) e.getWhoClicked(), e.getClick(), e.getAction()));
				}catch(Exception ex)
				{
					ex.printStackTrace();
					e.setCancelled(true);
				}
			}
		}else if(disablePlayerInventory)
		{
			e.setCancelled(true);
		}else{
			if(playerInventoryCallback != null)
			{
				try{
					playerInventoryCallback.accept(e);
				}catch(Exception ex)
				{
					ex.printStackTrace();
					e.setCancelled(true);
				}
			}
		}
	}
	
	public int getSlotIndex(int x, int y)
	{
		return (y * width)+x;
	}
	
	public MenuItem getItem(int index)
	{
		if(index < 0 || index >= slots.length)
			throw new IndexOutOfBoundsException(index+" is outside of the inventory");
		return slots[index];
	}
	
	public MenuItem getItem(int x, int y)
	{
		if(x < 0 || x >= width || y < 0 || y >= height)
			throw new IndexOutOfBoundsException(x+", "+y+" is outside of the inventory");
		return getItem(getSlotIndex(x, y));
	}
	
	public void setItem(int index, MenuItem item)
	{
		if(index < 0 || index >= slots.length)
			throw new IndexOutOfBoundsException(index+" is outside of the inventory");
		slots[index] = item == null ? EMPTY_SLOT : item;
		inventory.setItem(index, slots[index].getItem());
	}
	
	public void setItem(int x, int y, MenuItem item)
	{
		if(x < 0 || x >= width || y < 0 || y >= height)
			throw new IndexOutOfBoundsException(x+", "+y+" is outside of the inventory");
		setItem(getSlotIndex(x, y), item);
	}
	
	public Inventory getInventory()
	{
		return inventory;
	}
	
	public void open(Player player)
	{
		player.openInventory(inventory);
	}
}
