package me.tom.sparse.bukkit.version.general;

import me.tom.sparse.bukkit.nbt.Compound;
import me.tom.sparse.bukkit.nbt.TagType;
import net.minecraft.server.v1_10_R1.*;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_10_R1.util.CraftChatMessage;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class VersionUtils_v1_10_R1 implements VersionUtils
{
	public ItemStack createCraftItemStack(Material material, int amount, short metadata)
	{
		return CraftItemStack.asCraftCopy(new ItemStack(material, amount, metadata));
	}
	
	public void keepAlive(Player player)
	{
		((CraftPlayer)player).getHandle().playerConnection.sendPacket(new PacketPlayOutKeepAlive());
	}
	
	public void resetLocation(Player player)
	{
		((CraftPlayer)player).getHandle().playerConnection.sendPacket(new PacketPlayOutPosition(((CraftPlayer)player).getHandle().locX, ((CraftPlayer)player).getHandle().locY, ((CraftPlayer)player).getHandle().locZ, ((CraftPlayer)player).getHandle().yaw, ((CraftPlayer)player).getHandle().pitch, Collections.emptySet(), 0));
	}
	
	public void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut)
	{
		EntityPlayer handle = ((CraftPlayer)player).getHandle();
		if(title != null)
			handle.playerConnection.sendPacket(new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, CraftChatMessage.fromString(title)[0]));
		if(subtitle != null)
			handle.playerConnection.sendPacket(new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, CraftChatMessage.fromString(subtitle)[0]));
		handle.playerConnection.sendPacket(new PacketPlayOutTitle(fadeIn, stay, fadeOut));
	}
	
	public double getEntityHeight(Entity entity)
	{
		return ((CraftEntity)entity).getHandle().length;
	}
	
	public double getEntityWidth(Entity entity)
	{
		return ((CraftEntity)entity).getHandle().width;
	}
	
	private NBTBase objectToNBTBase(Object o)
	{
		TagType type = TagType.getType(o);
		switch(type.id)
		{
			case 1: return new NBTTagByte((Byte)o);
			case 2: return new NBTTagShort((Short) o);
			case 3: return new NBTTagInt((Integer)o);
			case 4: return new NBTTagLong((Long)o);
			case 5: return new NBTTagFloat((Float)o);
			case 6: return new NBTTagDouble((Double)o);
			case 7: return new NBTTagByteArray((byte[])o);
			case 8: return new NBTTagString((String)o);
			case 9:
				NBTTagList base = new NBTTagList();
				List list = (List)o;
				for(Object e : list)
				{
					NBTBase eBase = objectToNBTBase(e);
					if(eBase != null)
						base.add(eBase);
				}
				return base;
			case 10: return (NBTTagCompound) compoundToNMSCompound((Compound)o);
			case 11: return new NBTTagIntArray((int[])o);
		}
		return null;
	}
	
	public Object compoundToNMSCompound(Compound compound)
	{
		NBTTagCompound result = new NBTTagCompound();
		for(Map.Entry<String, Object> entry : compound.entrySet())
		{
			String n = entry.getKey();
			Object o = entry.getValue();
			result.set(n, objectToNBTBase(o));
		}
		return result;
	}
	
	private Object nbtBaseToObject(NBTBase o)
	{
		switch(o.getTypeId())
		{
			case 1: return ((NBTTagByte)o).g();
			case 2: return ((NBTTagShort)o).f();
			case 3: return ((NBTTagInt)o).e();
			case 4: return ((NBTTagLong)o).d();
			case 5: return ((NBTTagFloat)o).i();
			case 6: return ((NBTTagDouble)o).h();
			case 7: return ((NBTTagByteArray)o).c();
			case 8: return ((NBTTagString)o).c_();
			case 9:
				List listResult = new ArrayList();
				NBTTagList list = (NBTTagList)o;
				for(int i = 0; i < list.size(); i++)
					listResult.add(nbtBaseToObject(list.h(i)));
				return listResult;
			case 10:
				Compound compoundResult = new Compound();
				NBTTagCompound compound = (NBTTagCompound)o;
				for(String name : compound.c())
					compoundResult.set(name, nbtBaseToObject(compound.get(name)));
				return compoundResult;
			case 11: return ((NBTTagIntArray)o).d();
		}
		return null;
	}
	
	public Compound nmsCompoundToCompound(Object nmsCompound)
	{
		return (Compound)nbtBaseToObject((NBTTagCompound) nmsCompound);
	}
	
	public Compound parseJSON(String json)
	{
		return null;
	}
	
	private Field itemHandleField;
	
	private net.minecraft.server.v1_10_R1.ItemStack getItemHandle(CraftItemStack item)
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
			return (net.minecraft.server.v1_10_R1.ItemStack) itemHandleField.get(item);
		}catch(IllegalAccessException e)
		{
			e.printStackTrace();
		}
	
		throw new RuntimeException("Unable to get item handle");
	}
	
	public void setItemNBT(ItemStack item, Compound compound)
	{
		if(!(item instanceof CraftItemStack))
			throw new IllegalArgumentException("item is not CraftItemStack");
		
		net.minecraft.server.v1_10_R1.ItemStack handle = getItemHandle(((CraftItemStack)item));
		handle.setTag((NBTTagCompound) compoundToNMSCompound(compound));
	}
	
	public Compound getItemNBT(ItemStack item)
	{
		if(!(item instanceof CraftItemStack))
			throw new IllegalArgumentException("item is not CraftItemStack");
		
		net.minecraft.server.v1_10_R1.ItemStack handle = getItemHandle(((CraftItemStack)item));
		if(!handle.hasTag())
			return new Compound();
		return nmsCompoundToCompound(handle.getTag());
	}
	
	public ItemStack compoundToItem(Compound compound)
	{
		return CraftItemStack.asBukkitCopy(net.minecraft.server.v1_10_R1.ItemStack.createStack((NBTTagCompound) compoundToNMSCompound(compound)));
	}
	
	public Compound itemToCompound(ItemStack item)
	{
		return nmsCompoundToCompound(CraftItemStack.asNMSCopy(item).save(new NBTTagCompound()));
	}
	
	public String getItemName(ItemStack item)
	{
		Item nmsItem = Item.getById(item.getTypeId());
		if(nmsItem == null)
			return item.getType().name();
		String localeString = nmsItem.a(CraftItemStack.asNMSCopy(item));
		if(localeString == null)
			return item.getType().name();
		return LocaleI18n.get(localeString + ".name");
	}
}
