package me.tom.sparse.bukkit.tag.wrapper;

import net.minecraft.server.v1_11_R1.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Deprecated
public enum TagType
{
	BOOLEAN		(Boolean.class, NBTTagByte.class),
	BYTE		(Byte.class, NBTTagByte.class),
	SHORT		(Short.class, NBTTagShort.class),
	INT			(Integer.class, NBTTagInt.class),
	LONG		(Long.class, NBTTagLong.class),
	FLOAT		(Float.class, NBTTagFloat.class),
	DOUBLE		(Double.class, NBTTagDouble.class),
	BYTE_ARRAY	(byte[].class, NBTTagByteArray.class),
	STRING		(String.class, NBTTagString.class),
	LIST		(List.class, NBTTagList.class),
	TAG			(Tag.class, NBTTagCompound.class),
	INT_ARRAY	(int[].class, NBTTagIntArray.class);

	private Class type;
	private Class<? extends NBTBase> handleType;

	TagType(Class type, Class<? extends NBTBase> handleType)
	{
		this.type = type;
		this.handleType = handleType;
	}

	public byte getId()
	{
		byte b = (byte) ordinal();
		return b == 0 ? 1 : b;
	}

	public Class getType()
	{
		return type;
	}

	public Class<? extends NBTBase> getHandleType()
	{
		return handleType;
	}

	public boolean matches(Object obj)
	{
		return type.isInstance(obj) || handleType.isInstance(obj);
	}

	public NBTBase asHandle(Object obj)
	{
		if(obj == null)
			return null;
		if(this == BOOLEAN)
			return new NBTTagByte(((Boolean)obj) ? (byte)1 : (byte)0);
		if(this == LIST)
			return convertToHandleList((List) obj);
		if(this == TAG)
			return ((Tag)obj).getHandle();

		try
		{
			Class c = type;
			try{
				Field type = this.type.getDeclaredField("TYPE");
				c = (Class) type.get(null);
			}catch(Exception e)
			{

			}

			return handleType.getConstructor(c).newInstance(obj);
		}catch(Exception e)
		{
			System.out.println("Error when converting object to NBT handle: "+this);
			e.printStackTrace();
			return null;
		}
	}
	
	private NBTTagList convertToHandleList(List list)
	{
		if(list.contains(null))
			throw new IllegalArgumentException("list cannot contain any null elements.");
		if(list.size() == 0)
			return new NBTTagList();
		if(list.size() > 1)
		{
			Object o = list.get(0);
			if(list.stream().filter(v -> !v.getClass().equals(o.getClass())).findAny().isPresent())
				throw new IllegalArgumentException("All elements in list must be the same type.");

			boolean m = false;
			for(TagType tagType : values())
			{
				if(tagType.matches(o))
				{
					m = true;
					break;
				}
			}

			if(!m)
				throw new IllegalArgumentException("The objects in list do not match any TagType: "+o.getClass().getName());
		}

		Object o = list.get(0);

		TagType type = null;
		for(TagType t : values())
		{
			if(t.matches(o))
			{
				type = t;
				break;
			}
		}
		if(type == null)
			throw new IllegalArgumentException("The objects in list do not match any TagType");

		NBTTagList result = new NBTTagList();
		for(int i = 0; i < list.size(); i++)
			result.add(type.asHandle(list.get(i)));
		return result;
	}

	public Object fromHandle(Object obj)
	{
		if(this == LIST)
			return fromHandleList((NBTTagList)obj);
		if(this == BOOLEAN)
			return ((NBTTagByte)obj).f() == 1;
		if(this == TAG)
			return new Tag((NBTTagCompound)obj);
		
		if(!(obj instanceof NBTBase))
			return null;

		try
		{
			Field field = obj.getClass().getDeclaredField("data");
			field.setAccessible(true);
			Object v =  field.get(obj);
			field.setAccessible(false);
			return v;
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	private List<Object> fromHandleList(NBTTagList list)
	{
		List<Object> result = new ArrayList<>();
		for(int i = 0; i < list.size(); i++)
		{
//			NBTBase o = list.g(i);
			NBTBase o = list.h(i);
			result.add(getType(o).fromHandle(o));
		}

		return result;
	}
	
	public static TagType getType(Object obj)
	{
		if(obj == null) return null;

		for(TagType tagType : values())
			if(tagType.matches(obj))
				return tagType;
		return null;
	}
}
