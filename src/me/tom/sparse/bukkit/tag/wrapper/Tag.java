package me.tom.sparse.bukkit.tag.wrapper;

import net.minecraft.server.v1_11_R1.MojangsonParseException;
import net.minecraft.server.v1_11_R1.MojangsonParser;
import net.minecraft.server.v1_11_R1.NBTCompressedStreamTools;
import net.minecraft.server.v1_11_R1.NBTTagCompound;
import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Deprecated
public class Tag implements Map<String, Object>
{
	public static Tag getItemTag(ItemStack item)
	{
		net.minecraft.server.v1_11_R1.ItemStack nms = CraftItemStack.asNMSCopy(item);
		
		if(nms.hasTag())
		{
			return new Tag(nms.getTag());
		}else
		{
			NBTTagCompound tag = new NBTTagCompound();
			nms.setTag(tag);
			return new Tag(tag);
		}
	}
	
	private NBTTagCompound handle;
	
	public Tag()
	{
		this((NBTTagCompound) null);
	}
	
	public Tag(File file) throws IOException
	{
		this(NBTCompressedStreamTools.a(new FileInputStream(file)));
	}
	
	public Tag(NBTTagCompound handle)
	{
		this.handle = handle == null ? new NBTTagCompound() : handle;
	}
	
	public Tag(String json) throws MojangsonParseException
	{
		this(MojangsonParser.parse(json));
	}
	
	public Tag(Object... objects)
	{
		this();
		set(objects);
	}
	
	public Tag(Consumer<Tag> consumer)
	{
		this();
		consumer.accept(this);
	}
	
	public void save(File file) throws IOException
	{
		NBTCompressedStreamTools.a(handle, new FileOutputStream(file));
	}
	
	public Tag set(Object... objects)
	{
		String name = null;
		for(int i = 0; i < objects.length; i++)
		{
			Object o = objects[i];
			if(i % 2 == 0)
			{
				if(o instanceof String)
				{
					name = (String) o;
				}else
				{
					throw new IllegalArgumentException("Element " + i + " of objects is not a string.");
				}
			}else
			{
				TagType type = TagType.getType(o);
				if(type != null)
				{
					handle.set(name, type.asHandle(o));
				}else
				{
					throw new IllegalArgumentException("Element " + i + " of objects does not match any type.");
				}
			}
		}
		return this;
	}
	
	public Tag set(String name, Object value)
	{
		TagType type = TagType.getType(value);
		if(type != null)
		{
			handle.set(name, type.asHandle(value));
		}else
		{
			throw new IllegalArgumentException("Unable to find TagType for value.");
		}
		return this;
	}
	
	public Tag setBoolean(String name, boolean value)
	{
		if(name == null) throw new NullPointerException("name cannot be null.");
		handle.setBoolean(name, value);
		return this;
	}
	
	public Tag setByte(String name, byte value)
	{
		if(name == null) throw new NullPointerException("name cannot be null.");
		handle.setByte(name, value);
		return this;
	}
	
	public Tag setShort(String name, short value)
	{
		if(name == null) throw new NullPointerException("name cannot be null.");
		handle.setShort(name, value);
		return this;
	}
	
	public Tag setInt(String name, int value)
	{
		if(name == null) throw new NullPointerException("name cannot be null.");
		handle.setInt(name, value);
		return this;
	}
	
	public Tag setFloat(String name, float value)
	{
		if(name == null) throw new NullPointerException("name cannot be null.");
		handle.setFloat(name, value);
		return this;
	}
	
	public Tag setDouble(String name, double value)
	{
		if(name == null) throw new NullPointerException("name cannot be null.");
		handle.setDouble(name, value);
		return this;
	}
	
	public Tag setLong(String name, long value)
	{
		if(name == null) throw new NullPointerException("name cannot be null.");
		handle.setLong(name, value);
		return this;
	}
	
	public Tag setString(String name, String value)
	{
		if(name == null) throw new NullPointerException("name cannot be null.");
		if(value == null) throw new NullPointerException("value cannot be null.");
		handle.setString(name, value);
		return this;
	}
	
	public Tag setByteArray(String name, byte[] value)
	{
		if(name == null) throw new NullPointerException("name cannot be null.");
		if(value == null) throw new NullPointerException("value cannot be null.");
		handle.setByteArray(name, value);
		return this;
	}
	
	public Tag setIntArray(String name, int[] value)
	{
		if(name == null) throw new NullPointerException("name cannot be null.");
		if(value == null) throw new NullPointerException("value cannot be null.");
		handle.setIntArray(name, value);
		return this;
	}
	
	public Tag setList(String name, List value)
	{
		if(name == null) throw new NullPointerException("name cannot be null.");
		if(value == null) throw new NullPointerException("value cannot be null.");
		handle.set(name, TagType.LIST.asHandle(value));
		return this;
	}
	
	public Tag setList(String name, Object... value)
	{
		if(value == null) throw new NullPointerException("value cannot be null.");
		return setList(name, Arrays.asList(value));
	}
	
	public Tag setTag(String name, Tag value)
	{
		if(name == null) throw new NullPointerException("name cannot be null.");
		if(value == null) throw new NullPointerException("tag cannot be null.");
		handle.set(name, value.getHandle());
		return this;
	}
	
	public boolean getBoolean(String name)
	{
		if(name == null) throw new NullPointerException("name cannot be null.");
		if(!handle.hasKeyOfType(name, TagType.BOOLEAN.getId()))
			throw new IllegalArgumentException("\"" + name + "\" either doesn't exist, or isn't a boolean.");
		return handle.getBoolean(name);
	}
	
	public byte getByte(String name)
	{
		if(name == null) throw new NullPointerException("name cannot be null.");
		if(!handle.hasKeyOfType(name, TagType.BYTE.getId()))
			throw new IllegalArgumentException("\"" + name + "\" either doesn't exist, or isn't a byte.");
		return handle.getByte(name);
	}
	
	public short getShort(String name)
	{
		if(name == null) throw new NullPointerException("name cannot be null.");
		if(!handle.hasKeyOfType(name, TagType.SHORT.getId()))
			throw new IllegalArgumentException("\"" + name + "\" either doesn't exist, or isn't a short.");
		return handle.getShort(name);
	}
	
	public int getInt(String name)
	{
		if(name == null) throw new NullPointerException("name cannot be null.");
		if(!handle.hasKeyOfType(name, TagType.INT.getId()))
			throw new IllegalArgumentException("\"" + name + "\" either doesn't exist, or isn't an int.");
		return handle.getInt(name);
	}
	
	public float getFloat(String name)
	{
		if(name == null) throw new NullPointerException("name cannot be null.");
		if(!handle.hasKeyOfType(name, TagType.FLOAT.getId()))
			throw new IllegalArgumentException("\"" + name + "\" either doesn't exist, or isn't a float.");
		return handle.getFloat(name);
	}
	
	public double getDouble(String name)
	{
		if(name == null) throw new NullPointerException("name cannot be null.");
		if(!handle.hasKeyOfType(name, TagType.DOUBLE.getId()))
			throw new IllegalArgumentException("\"" + name + "\" either doesn't exist, or isn't a double.");
		return handle.getDouble(name);
	}
	
	public long getLong(String name)
	{
		if(name == null) throw new NullPointerException("name cannot be null.");
		if(!handle.hasKeyOfType(name, TagType.LONG.getId()))
			throw new IllegalArgumentException("\"" + name + "\" either doesn't exist, or isn't a long.");
		return handle.getLong(name);
	}
	
	public String getString(String name)
	{
		if(name == null) throw new NullPointerException("name cannot be null.");
		if(!handle.hasKeyOfType(name, TagType.STRING.getId()))
			throw new IllegalArgumentException("\"" + name + "\" either doesn't exist, or isn't a string.");
		return handle.getString(name);
	}
	
	public byte[] getByteArray(String name)
	{
		if(name == null) throw new NullPointerException("name cannot be null.");
		if(!handle.hasKeyOfType(name, TagType.BYTE_ARRAY.getId()))
			throw new IllegalArgumentException("\"" + name + "\" either doesn't exist, or isn't a byte array.");
		return handle.getByteArray(name);
	}
	
	public int[] getIntArray(String name)
	{
		if(name == null) throw new NullPointerException("name cannot be null.");
		if(!handle.hasKeyOfType(name, TagType.INT_ARRAY.getId()))
			throw new IllegalArgumentException("\"" + name + "\" either doesn't exist, or isn't an int array.");
		return handle.getIntArray(name);
	}
	
	public List getList(String name)
	{
		if(name == null) throw new NullPointerException("name cannot be null.");
		if(!handle.hasKeyOfType(name, TagType.LIST.getId()))
			throw new IllegalArgumentException("\"" + name + "\" either doesn't exist, or isn't a list.");
		return (List) TagType.LIST.fromHandle(handle.get(name));
	}
	
	public Tag getTag(String name)
	{
		if(name == null) throw new NullPointerException("name cannot be null.");
		if(!handle.hasKeyOfType(name, TagType.TAG.getId()))
			throw new IllegalArgumentException("\"" + name + "\" either doesn't exist, or isn't a tag.");
		return new Tag(handle.getCompound(name));
	}
	
	public TagType getType(String name)
	{
		if(name == null) throw new NullPointerException("name cannot be null.");
		byte b = handle.d(name);
		for(TagType type : TagType.values())
			if(type != TagType.BOOLEAN && type.getId() == b)
				return type;
		return null;
	}
	
	public boolean isPresent(String name)
	{
		if(name == null) throw new NullPointerException("name cannot be null.");
		return handle.hasKey(name);
	}
	
	public Tag remove(String name)
	{
		if(name == null) throw new NullPointerException("name cannot be null.");
		if(!handle.hasKey(name)) throw new IllegalArgumentException("tag does not contain \"" + name + "\".");
		handle.remove(name);
		return this;
	}
	
	public Tag merge(Tag other)
	{
		return merge(other, true);
	}
	
	public Tag merge(Tag other, boolean overwrite)
	{
		Set<String> list = other.list();
		for(String s : list)
		{
			if(!overwrite && isPresent(s))
				continue;
			
			handle.set(s, other.handle.get(s));
		}
		return this;
	}
	
	public Set<String> list()
	{
		return handle.c();
	}
	
	public NBTTagCompound getHandle()
	{
		return handle;
	}
	
	public String toString()
	{
		return handle.toString();
	}
	
	public static void main(String[] args)
	{
		Tag tag;
		
		tag = new Tag(
				"anInt", 5,
				"maybe", true,
				"now", System.currentTimeMillis(),
				"name", "TomHazRedstone",
				"tree", new Tag(
				"array", new int[]{24, 62, 91, 11, 5},
				"list", Arrays.asList("hello", "world", "this", "is", "a", "string", "list"),
				"branch", new Tag(
				"vec", Arrays.asList(0f, 2f, 4f),
				"money", 5.23,
				"currency", "USD"
		)
		)
		);
		System.out.println(tag);

//		tag = new Tag()
//			.setInt("anInt", 5)
//			.setBoolean("maybe", true)
//			.setLong("now", System.currentTimeMillis())
//			.setString("name", "TomHazRedstone")
//			.setTag("tree", new Tag()
//				.setIntArray("array", new int[]{24, 62, 91, 11, 5})
//				.setList("list", "hello", "world", "this", "is", "a", "string", "list")
//			);
//		System.out.println(tag);
//
//		try
//		{
//			tag = new Tag("{maybe:1b,now:1454537514077L,anInt:5,name:\"TomHazRedstone\",tree:{array:[24,62,91,11,5,],list:[0:\"hello\",1:\"world\",2:\"this\",3:\"is\",4:\"a\",5:\"string\",6:\"list\"]}}\n");
//			System.out.println(tag);
//		}catch(MojangsonParseException e)
//		{
//			e.printStackTrace();
//		}
//
//		System.out.println(tag.getInt("anInt"));
//		System.out.println(tag.getBoolean("maybe"));
//		System.out.println(tag.getLong("now"));
//		System.out.println(tag.getString("name"));
//		System.out.println(tag.getTag("tree"));
//		System.out.println(tag.getTag("tree").getList("list"));
	}
	
	@Override
	public int size()
	{
		return handle.d();
	}
	
	@Override
	public boolean isEmpty()
	{
		return handle.d() <= 0;
	}
	
	@Override
	public boolean containsKey(Object key)
	{
		if(!(key instanceof String))
			throw new IllegalArgumentException("Keys must be a String");
		
		return isPresent((String) key);
	}
	
	@Override
	public boolean containsValue(Object value)
	{
		return values().contains(value);
	}
	
	@Override
	public Object get(Object key)
	{
		if(!(key instanceof String))
			throw new IllegalArgumentException("Keys must be a String");
		
		String strKey = (String) key;
		return getType(strKey).fromHandle(handle.get(strKey));
	}
	
	@Override
	public Object put(String key, Object value)
	{
		Object before = null;
		if(isPresent(key))
			before = get(key);
		set(key, value);
		return before;
	}
	
	@Override
	public Object remove(Object key)
	{
		if(!(key instanceof String))
			throw new IllegalArgumentException("Keys must be a String");
		
		Object before = null;
		String strKey = (String) key;
		if(isPresent(strKey))
			before = get(key);
		remove(strKey);
		
		return before;
	}
	
	@Override
	public void putAll(Map<? extends String, ?> m)
	{
		for(Entry<? extends String, ?> entry : m.entrySet())
			put(entry.getKey(), entry.getValue());
	}
	
	@Override
	public void clear()
	{
		list().forEach(this::remove);
	}
	
	@Override
	public Set<String> keySet()
	{
		return list();
	}
	
	@Override
	public Collection<Object> values()
	{
		return list().stream().map(this::get).collect(Collectors.toList());
	}
	
	@Override
	public Set<Entry<String, Object>> entrySet()
	{
		return list().stream().map(TagEntry::new).collect(Collectors.toSet());
	}
	
	@Override
	public Tag clone()
	{
		return new Tag(handle.g());
	}
	
	public class TagEntry implements Entry<String, Object>
	{
		private String key;
		
		private TagEntry(String key)
		{
			this.key = key;
		}
		
		@Override
		public String getKey()
		{
			return key;
		}
		
		@Override
		public Object getValue()
		{
			return get(key);
		}
		
		@Override
		public Object setValue(Object value)
		{
			return put(key, value);
		}
	}
}
