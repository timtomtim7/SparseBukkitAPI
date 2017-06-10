package me.tom.sparse.bukkit.version.general;

import me.tom.sparse.bukkit.nbt.Compound;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface VersionUtils
{
	/**
	 * Creates a CraftItemStack and returns it as a normal Bukkit ItemStack to avoid version imports.
	 * @param material The material of the item.
	 * @param amount The number of items in the stack.
	 * @param metadata The metadata or durability of the item.
	 * @return The created CraftItemStack.
	 */
	ItemStack createCraftItemStack(Material material, int amount, short metadata);
	
	/**
	 * Sends a teleport packet to reset a player's location to the one known by the server.
	 * @param player The player to send the packet to.
	 */
	void resetLocation(Player player);
	
	/**
	 * Sends a keep-alive packet to the player.
	 * @param player The player to send the packet to.
	 */
	void keepAlive(Player player);
	
	/**
	 * <b>Copied from Spigot docs {@link org.bukkit.entity.Player#sendTitle}.</b>
	 * <br></br>
	 *
	 * Sends a title and a subtitle message to the player. If either of these values are null, they will not be sent and the display will remain unchanged. If they are empty strings, the display will be updated as such. If the strings contain a new line, only the first line will be sent. All timings values may take a value of -1 to indicate that they will use the last value sent (or the defaults if no title has been displayed).
	 * @param player The player to send the title to.
	 * @param title Title text
	 * @param subtitle Subtitle text
	 * @param fadeIn time in ticks for titles to fade in.
	 * @param stay time in ticks for titles to say.
	 * @param fadeOut time in ticks for the titles to fade out.
	 */
	void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut);
	
	/**
	 *
	 * @param entity
	 * @return The width of the entity.
	 */
	double getEntityWidth(Entity entity);
	
	/**
	 *
	 * @param entity
	 * @return The height of the entity.
	 */
	double getEntityHeight(Entity entity);
	
	/**
	 * Converts a {@link me.tom.sparse.bukkit.nbt.Compound} to the NMS equivalent.
	 * @param compound The compound to convert.
	 * @return The NMS compound, as an Object to avoid version imports.
	 */
	Object compoundToNMSCompound(Compound compound);
	
	/**
	 * Converts an NMS compound to the {@link me.tom.sparse.bukkit.nbt.Compound} equivalent.
	 * @param nmsCompound The NMS compound.
	 * @return The resulting {@link me.tom.sparse.bukkit.nbt.Compound}.
	 */
	Compound nmsCompoundToCompound(Object nmsCompound);
	
	/**
	 * Parses Mojangson into a {@link me.tom.sparse.bukkit.nbt.Compound}
	 * @param json The JSON to parse.
	 * @return The {@link me.tom.sparse.bukkit.nbt.Compound} equivalent of the JSON.
	 */
	Compound parseJSON(String json);
	
	/**
	 * Replaces the item's NBT with the provided compound.
	 * @param item The item. <b>Must be an instance of CraftItemStack</b>
	 * @param compound The NBT.
	 */
	void setItemNBT(ItemStack item, Compound compound);
	
	/**
	 * Gets the item's NBT.
	 * @param item The item. <b>Must be an instance of CraftItemStack</b>
	 * @return The item's NBT.
	 */
	Compound getItemNBT(ItemStack item);
	
	/**
	 * Creates an {@link org.bukkit.inventory.ItemStack} from a {@link me.tom.sparse.bukkit.nbt.Compound}
	 * @param compound The compound to convert.
	 * @return The resulting {@link org.bukkit.inventory.ItemStack}.
	 */
	ItemStack compoundToItem(Compound compound);
	
	/**
	 * Converts an {@link org.bukkit.inventory.ItemStack} to a {@link me.tom.sparse.bukkit.nbt.Compound}
	 * @param item The item to convert.
	 * @return The resulting {@link me.tom.sparse.bukkit.nbt.Compound}.
	 */
	Compound itemToCompound(ItemStack item);
	
	/**
	 * @param item The item.
	 * @return The item's in-game name. (Usually en_US)
	 */
	String getItemName(ItemStack item);
}
