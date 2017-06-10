package me.tom.sparse.bukkit.tag;

import me.tom.sparse.bukkit.tag.wrapper.Tag;
import net.minecraft.server.v1_11_R1.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.util.UUID;

@Deprecated
public final class TagUtils
{
	public static Tag toTag(ItemStack item)
	{
		return new Tag(CraftItemStack.asNMSCopy(item).save(new NBTTagCompound()));
	}

	public static Tag toTag(Location location)
	{
		if(location == null)
			throw new IllegalArgumentException("Cannot convert null location to tag");
		
		return new Tag(
				"world", location.getWorld().getName(),
				"x", location.getX(),
				"y", location.getY(),
				"z", location.getZ(),
				"yaw", location.getYaw(),
				"pitch", location.getPitch()
		);
	}
	
	public static Tag toTag(Vector vector)
	{
		return new Tag(
				"x", vector.getX(),
				"y", vector.getY(),
				"z", vector.getZ()
		);
	}
	
	public static Tag toTag(UUID id)
	{
		return new Tag(
				"mostSigBits", id.getMostSignificantBits(),
				"leastSigBits", id.getLeastSignificantBits()
		);
	}
	
	public static Tag toTag(Block block)
	{
		return new Tag(
				"world", block.getWorld().getName(),
				"x", block.getX(),
				"y", block.getY(),
				"z", block.getZ()
		);
	}
	
	public static Location toLocation(Tag tag)
	{
		return new Location(
				Bukkit.getWorld(tag.getString("world")),
				tag.getDouble("x"),
				tag.getDouble("y"),
				tag.getDouble("z"),
				tag.getFloat("yaw"),
				tag.getFloat("pitch")
		);
	}
	
	public static ItemStack toItemStack(Tag tag)
	{
		return CraftItemStack.asBukkitCopy(new net.minecraft.server.v1_11_R1.ItemStack(tag.getHandle()));
	}
	
	public static UUID toUUID(Tag tag)
	{
		return new UUID(tag.getLong("mostSigBits"), tag.getLong("leastSigBits"));
	}
	
	public static Field itemHandleField;
	
	public static CraftItemStack ensureCraftItemStack(ItemStack item)
	{
		return item instanceof CraftItemStack ? (CraftItemStack) item : CraftItemStack.asCraftCopy(item);
	}
	
	public static Tag getTag(ItemStack item)
	{
		return getDirectTag(ensureCraftItemStack(item));
	}
	
	public static Tag getDirectTag(CraftItemStack item)
	{
		if(itemHandleField == null)
		{
			try
			{
				itemHandleField = CraftItemStack.class.getDeclaredField("handle");
				itemHandleField.setAccessible(true);
			}catch(NoSuchFieldException e)
			{
				e.printStackTrace();
			}
		}
		
		try
		{
			net.minecraft.server.v1_11_R1.ItemStack handle = (net.minecraft.server.v1_11_R1.ItemStack) itemHandleField.get(item);
			
			if(handle.hasTag())
				return new Tag(handle.getTag());
			NBTTagCompound tag = new NBTTagCompound();
			handle.setTag(tag);
			return new Tag(tag);
			
		}catch(IllegalAccessException e)
		{
			e.printStackTrace();
		}
		
		throw new RuntimeException("Unable to return direct item tag.");
//		return null;
	}
	
	public static void setDirectTag(ItemStack item, Tag tag)
	{
		if(itemHandleField == null)
		{
			try
			{
				itemHandleField = CraftItemStack.class.getDeclaredField("handle");
				itemHandleField.setAccessible(true);
			}catch(NoSuchFieldException e)
			{
				e.printStackTrace();
			}
		}
		
		try
		{
			net.minecraft.server.v1_11_R1.ItemStack handle = (net.minecraft.server.v1_11_R1.ItemStack) itemHandleField.get(item);
			handle.setTag(tag.getHandle());
			
		}catch(IllegalAccessException e)
		{
			e.printStackTrace();
		}
	}
	
	private TagUtils() {}
}
