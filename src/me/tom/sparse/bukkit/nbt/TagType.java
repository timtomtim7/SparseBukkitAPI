package me.tom.sparse.bukkit.nbt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class TagType
{
	private static final Map<Integer, TagType> types = new HashMap<>();
	
	public static TagTypeByte      TYPE_BYTE       = new TagTypeByte();
	public static TagTypeShort     TYPE_SHORT      = new TagTypeShort();
	public static TagTypeInt       TYPE_INT        = new TagTypeInt();
	public static TagTypeLong      TYPE_LONG       = new TagTypeLong();
	public static TagTypeFloat     TYPE_FLOAT      = new TagTypeFloat();
	public static TagTypeDouble    TYPE_DOUBLE     = new TagTypeDouble();
	public static TagTypeByteArray TYPE_BYTE_ARRAY = new TagTypeByteArray();
	public static TagTypeString    TYPE_STRING     = new TagTypeString();
	public static TagTypeList      TYPE_LIST       = new TagTypeList();
	public static TagTypeCompound  TYPE_COMPOUND   = new TagTypeCompound();
	public static TagTypeIntArray  TYPE_INT_ARRAY  = new TagTypeIntArray();
	public final int id;
	
	public TagType(int id)
	{
		this.id = id;
		types.put(id, this);
	}
	
	public static boolean isValid(Object o)
	{
		return types.values().stream().anyMatch(it -> it.isType(o));
	}
	
	public abstract boolean isType(Object o);
	
	public static TagType getType(Object o)
	{
		return types.values().stream().filter(it -> it.isType(o)).findAny().orElse(null);
	}
	
	public abstract void write(DataOutputStream out, Object o) throws IOException;
	
	public abstract Object read(DataInputStream in) throws IOException;
	
	public String toJSON(Object o)
	{
		return o.toString();
	}
	
	public static class TagTypeByte extends TagType
	{
		public TagTypeByte()
		{
			super(1);
		}
		
		public boolean isType(Object o)
		{
			return o instanceof Byte;
		}
		
		public void write(DataOutputStream out, Object o) throws IOException
		{
			out.writeByte((Byte) o);
		}
		
		public Object read(DataInputStream in) throws IOException
		{
			return in.readByte();
		}
		
		public String toJSON(Object o)
		{
			return o + "b";
		}
	}
	
	public static class TagTypeShort extends TagType
	{
		public TagTypeShort()
		{
			super(2);
		}
		
		public boolean isType(Object o)
		{
			return o instanceof Short;
		}
		
		public void write(DataOutputStream out, Object o) throws IOException
		{
			out.writeShort((Short) o);
		}
		
		public Object read(DataInputStream in) throws IOException
		{
			return in.readShort();
		}
		
		public String toJSON(Object o)
		{
			return o + "s";
		}
	}
	
	public static class TagTypeInt extends TagType
	{
		public TagTypeInt()
		{
			super(3);
		}
		
		public boolean isType(Object o)
		{
			return o instanceof Integer;
		}
		
		public void write(DataOutputStream out, Object o) throws IOException
		{
			out.writeInt((Integer) o);
		}
		
		public Object read(DataInputStream in) throws IOException
		{
			return in.readInt();
		}
	}
	
	public static class TagTypeLong extends TagType
	{
		public TagTypeLong()
		{
			super(4);
		}
		
		public boolean isType(Object o)
		{
			return o instanceof Long;
		}
		
		public void write(DataOutputStream out, Object o) throws IOException
		{
			out.writeLong((Long) o);
		}
		
		public Object read(DataInputStream in) throws IOException
		{
			return in.readLong();
		}
		
		public String toJSON(Object o)
		{
			return o + "l";
		}
	}
	
	public static class TagTypeFloat extends TagType
	{
		public TagTypeFloat()
		{
			super(5);
		}
		
		public boolean isType(Object o)
		{
			return o instanceof Float;
		}
		
		public void write(DataOutputStream out, Object o) throws IOException
		{
			out.writeFloat((Float) o);
		}
		
		public Object read(DataInputStream in) throws IOException
		{
			return in.readFloat();
		}
		
		public String toJSON(Object o)
		{
			return o + "f";
		}
	}
	
	public static class TagTypeDouble extends TagType
	{
		public TagTypeDouble()
		{
			super(6);
		}
		
		public boolean isType(Object o)
		{
			return o instanceof Double;
		}
		
		public void write(DataOutputStream out, Object o) throws IOException
		{
			out.writeDouble((Double) o);
		}
		
		public Object read(DataInputStream in) throws IOException
		{
			return in.readDouble();
		}
	}
	
	public static class TagTypeByteArray extends TagType
	{
		public TagTypeByteArray()
		{
			super(7);
		}
		
		public boolean isType(Object o)
		{
			return o instanceof byte[];
		}
		
		public void write(DataOutputStream out, Object o) throws IOException
		{
			byte[] array = (byte[])o;
			out.writeInt(array.length);
			out.write(array);
		}
		
		public Object read(DataInputStream in) throws IOException
		{
			int length = in.readInt();
			byte[] bytes = new byte[length];
			in.read(bytes);
			return bytes;
		}
		
		public String toJSON(Object o)
		{
			StringBuilder sb = new StringBuilder();
			byte[] array = (byte[]) o;
			for(int i = 0; i < array.length; i++)
			{
				if(sb.length() != 0)
					sb.append(", ");
				sb.append(array[i]);
				sb.append('b');
			}
			return '[' + sb.toString() + ']';
		}
	}
	
	public static class TagTypeString extends TagType
	{
		public TagTypeString()
		{
			super(8);
		}
		
		public boolean isType(Object o)
		{
			return o instanceof String;
		}
		
		public void write(DataOutputStream out, Object o) throws IOException
		{
			out.writeUTF((String) o);
		}
		
		public Object read(DataInputStream in) throws IOException
		{
			return in.readUTF();
		}
		
		public String toJSON(Object o)
		{
			return "\"" + o + "\"";
		}
	}
	
	public static class TagTypeList extends TagType
	{
		public TagTypeList()
		{
			super(9);
		}
		
		public boolean isType(Object o)
		{
			if(!(o instanceof List))
				return false;
			
			List list = (List) o;
			if(list.size() == 0)
				return true;
			
			Object first = list.get(0);
			if(!isValid(first))
				return false;
			
			for(Object e : list)
				if(!e.getClass().equals(first.getClass()))
					return false;
			
			return true;
		}
		
		public void write(DataOutputStream out, Object o) throws IOException
		{
			List list = (List) o;
			if(list.size() == 0)
			{
				out.write(0);
				out.writeInt(0);
				return;
			}
			
			TagType type = getType(list.get(0));
			out.write(type.id);
			out.writeInt(list.size());
			for(Object e : list)
				type.write(out, e);
		}
		
		public Object read(DataInputStream in) throws IOException
		{
			List<Object> list = new ArrayList<>();
			int id = in.readByte();
			int length = in.readInt();
			if(length == 0)
				return list;
			TagType type = types.get(id);
			
			for(int i = 0; i < length; i++)
				list.add(type.read(in));
			
			return list;
		}
		
		public String toJSON(Object o)
		{
			List list = (List) o;
			if(list.size() == 0)
				return "[]";
			
			TagType type = getType(list.get(0));
			StringBuilder sb = new StringBuilder();
			for(Object e : list)
			{
				if(sb.length() != 0)
					sb.append(", ");
				sb.append(type.toJSON(e));
			}
			return '[' + sb.toString() + ']';
		}
	}
	
	public static class TagTypeCompound extends TagType
	{
		public TagTypeCompound()
		{
			super(10);
		}
		
		public boolean isType(Object o)
		{
			return o instanceof Compound;
		}
		
		public void write(DataOutputStream out, Object o) throws IOException
		{
			Compound compound = (Compound) o;
			for(Map.Entry<String, Object> entry : compound.entrySet())
			{
				String name = entry.getKey();
				Object e = entry.getValue();
				TagType type = getType(e);
				
				out.write(type.id);
				out.writeUTF(name);
				type.write(out, e);
			}
			out.write(0);
		}
		
		public Object read(DataInputStream in) throws IOException
		{
			Compound compound = new Compound();
			
			while(true)
			{
				int id = in.readByte();
				if(id == 0)
					break;
				
				compound.put(in.readUTF(), types.get(id).read(in));
			}
			
			return compound;
		}
		
		public String toJSON(Object o)
		{
			Compound compound = (Compound) o;
			StringBuilder sb = new StringBuilder();
			for(Map.Entry<String, Object> entry : compound.entrySet())
			{
				if(sb.length() != 0)
					sb.append(", ");
				sb.append('\"').append(entry.getKey()).append('\"').append(": ");
				
				Object e = entry.getValue();
				TagType type = getType(e);
				sb.append(type.toJSON(e));
			}
			return '{' + sb.toString() + '}';
		}
	}
	
	public static class TagTypeIntArray extends TagType
	{
		public TagTypeIntArray()
		{
			super(11);
		}
		
		public boolean isType(Object o)
		{
			return o instanceof int[];
		}
		
		public void write(DataOutputStream out, Object o) throws IOException
		{
			int[] array = (int[]) o;
			out.writeInt(array.length);
			for(int i : array)
				out.writeInt(i);
		}
		
		public Object read(DataInputStream in) throws IOException
		{
			int length = in.readInt();
			int[] array = new int[length];
			for(int i = 0; i < array.length; i++)
				array[i] = in.readInt();
			return array;
		}
		
		public String toJSON(Object o)
		{
			StringBuilder sb = new StringBuilder();
			byte[] array = (byte[]) o;
			for(int i = 0; i < array.length; i++)
			{
				if(sb.length() != 0)
					sb.append(", ");
				sb.append(array[i]);
			}
			return '[' + sb.toString() + ']';
		}
	}
}
