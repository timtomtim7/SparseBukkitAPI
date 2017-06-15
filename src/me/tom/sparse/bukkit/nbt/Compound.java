package me.tom.sparse.bukkit.nbt;

import me.tom.sparse.bukkit.SparseAPIPlugin;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Compound implements Map<String, Object>
{
	/**
	 * Parses one Compound from the given InputStream.
	 * @param in The InputStream to read from.
	 * @return The parsed compound
	 * @throws IOException
	 */
	public static Compound read(DataInputStream in) throws IOException
	{
		if(in.read() != 10 || in.read() != 0 || in.read() != 0)
			throw new IOException("File not in NBT format.");
		return (Compound) TagType.TYPE_COMPOUND.read(in);
	}
	
	/**
	 * Decompresses (GZIP) and parses one Compound from the given InputStream.
	 * @param in The InputStream to read from.
	 * @param close If the InputStream should automatically be closed after reading.
	 * @return The parsed compound
	 * @throws IOException
	 */
	public static Compound read(InputStream in, boolean close) throws IOException
	{
		DataInputStream dataIn = new DataInputStream(new BufferedInputStream(new GZIPInputStream(in)));
		Compound result = read(dataIn);
		if(close)
			dataIn.close();
		return result;
	}
	
	/**
	 * Decompresses (GZIP) and parses one Compound from the given InputStream.
	 * <br>
	 * Automatically closes the InputStream.
	 * @param in The InputStream to read from.
	 * @return The parsed compound
	 * @throws IOException
	 */
	public static Compound read(InputStream in) throws IOException
	{
		return read(in, true);
	}
	
	/**
	 * Decompresses (GZIP) and parses one Compound from the given file.
	 * @return The parsed compound
	 * @throws IOException
	 */
	public static Compound read(File file) throws IOException
	{
		return read(new FileInputStream(file));
	}
	
	private final Map<String, Object> backingMap = new ConcurrentHashMap<>();
	
	/**
	 * Creates an empty Compound
	 */
	public Compound()
	{
	
	}
	
	/**
	 * Creates a compound from the objects.
	 * The object array must be in the format of (name, object, name, object)
	 * <br>
	 * Every name must be a {@link java.lang.String} and every object must be a valid NBT-serializable object.
	 * @param objects
	 */
	public Compound(Object... objects)
	{
		set(objects);
	}
	
	/**
	 * Creates a compound from the map.
	 * @param map The map, every value must be a valid NBT-serializable object.
	 */
	public Compound(Map<? extends String, ?> map)
	{
		putAll(map);
	}
	
	/**
	 * Creates an empty compound and gives it to the Consumer
	 * @param consumer
	 */
	public Compound(Consumer<Compound> consumer)
	{
		consumer.accept(this);
	}
	
//	public Compound(DataInputStream in) throws IOException
//	{
//		this((Compound) TagType.TYPE_COMPOUND.read(in));
//	}
//
//	public Compound(InputStream in) throws IOException
//	{
//		this(in instanceof DataInputStream ? (DataInputStream)in : new DataInputStream(in));
//	}
//
//	public Compound(File file) throws IOException
//	{
//		this(new DataInputStream(new FileInputStream(file)));
//	}
	
	/**
	 * Writes this compound to the given OutputStream
	 * @param out The OutputStream to write to.
	 * @throws IOException
	 */
	public void write(DataOutputStream out) throws IOException
	{
		out.write(new byte[] {10, 0, 0});
		TagType.TYPE_COMPOUND.write(out, this);
	}
	
	/**
	 * Writes this compound to the given OutputStream, GZIP compressed.
	 * <br>
	 * Automatically closes the OutputStream
	 * @param out The OutputStream to write to.
	 * @throws IOException
	 */
	public void write(OutputStream out) throws IOException
	{
		write(out, true);
	}
	
	/**
	 *
	 * @param out
	 * @param close
	 * @throws IOException
	 */
	public void write(OutputStream out, boolean close) throws IOException
	{
		DataOutputStream dataOut = new DataOutputStream(new BufferedOutputStream(new GZIPOutputStream(out)));
		write(dataOut);
		if(close)
		{
			dataOut.flush();
			dataOut.close();
		}
	}
	
	public void write(File file) throws IOException
	{
		write(new FileOutputStream(file));
	}
	
	public Compound set(Object... objects)
	{
		if(objects.length % 2 != 0)
			throw new IllegalArgumentException("Mismatched name or object in array.");
		for(int i = 0; i < objects.length; i += 2)
		{
			if(!(objects[i] instanceof String))
				throw new IllegalArgumentException("Expected tag name, found "+objects[i].getClass());
			
			put((String) objects[i], objects[i+1]);
		}
		return this;
	}
	
	public Compound set(String name, Object value)
	{
		put(name, value);
		return this;
	}
	
	public Object toNMSCompound()
	{
		return SparseAPIPlugin.getVersionUtils().compoundToNMSCompound(this);
	}
	
	public Compound setBoolean(String name, boolean value)
	{
		put(name, value ? (byte)1 : (byte)0);
		return this;
	}

	public Compound setByte(String name, byte value)
	{
		put(name, value);
		return this;
	}

	public Compound setShort(String name, short value)
	{
		put(name, value);
		return this;
	}

	public Compound setInt(String name, int value)
	{
		put(name, value);
		return this;
	}

	public Compound setFloat(String name, float value)
	{
		put(name, value);
		return this;
	}

	public Compound setDouble(String name, double value)
	{
		put(name, value);
		return this;
	}

	public Compound setLong(String name, long value)
	{
		put(name, value);
		return this;
	}

	public Compound setString(String name, String value)
	{
		put(name, value);
		return this;
	}

	public Compound setByteArray(String name, byte[] value)
	{
		put(name, value);
		return this;
	}

	public Compound setIntArray(String name, int[] value)
	{
		put(name, value);
		return this;
	}

	public Compound setList(String name, List value)
	{
		put(name, value);
		return this;
	}

	public Compound setCompound(String name, Compound value)
	{
		put(name, value);
		return this;
	}
	
	public boolean getBoolean(String name)
	{
		return getByte(name) != 0;
	}
	
	public byte getByte(String name)
	{
		return (Byte)get(name);
	}
	
	public short getShort(String name)
	{
		return (Short)get(name);
	}
	
	public int getInt(String name)
	{
		return (Integer)get(name);
	}
	
	public float getFloat(String name)
	{
		return (Float)get(name);
	}
	
	public double getDouble(String name)
	{
		return (Double)get(name);
	}
	
	public long getLong(String name)
	{
		return (Long)get(name);
	}
	
	public String getString(String name)
	{
		return (String)get(name);
	}
	
	public byte[] getByteArray(String name)
	{
		return (byte[])get(name);
	}
	
	public int[] getIntArray(String name)
	{
		return (int[])get(name);
	}
	
	public List getList(String name)
	{
		return (List)get(name);
	}
	
	public Compound getCompound(String name)
	{
		return (Compound)get(name);
	}
	
	public String toString()
	{
		return TagType.TYPE_COMPOUND.toJSON(this);
	}
	
	public Object put(String key, Object value)
	{
		if(!TagType.isValid(value))
			throw new IllegalArgumentException("Unable to find TagType for value.");
		if(key == null)
			throw new IllegalArgumentException("Name cannot be null.");
		return backingMap.put(key, value);
	}
	
	public int size()
	{
		return backingMap.size();
	}
	
	public boolean isEmpty()
	{
		return backingMap.isEmpty();
	}
	
	public boolean containsKey(Object key)
	{
		return backingMap.containsKey(key);
	}
	
	public boolean containsValue(Object value)
	{
		return backingMap.containsValue(value);
	}
	
	public Object get(Object key)
	{
		return backingMap.get(key);
	}
	
	public Object remove(Object key)
	{
		return backingMap.remove(key);
	}
	
	public void putAll(Map<? extends String, ?> m)
	{
		for(Entry<? extends String, ?> entry : m.entrySet())
			put(entry.getKey(), entry.getValue());
	}
	
	public void clear()
	{
		backingMap.clear();
	}
	
	public Set<String> keySet()
	{
		return backingMap.keySet();
	}
	
	public Collection<Object> values()
	{
		return backingMap.values();
	}
	
	public Set<Entry<String, Object>> entrySet()
	{
		return backingMap.entrySet();
	}
	
	public boolean equals(Object o)
	{
		if(this == o) return true;
		if(!(o instanceof Compound)) return false;
		
		Compound compound = (Compound) o;
		
		return backingMap.equals(compound.backingMap);
	}
	
	public int hashCode()
	{
		return backingMap.hashCode();
	}
	
	public boolean isPresent(String name)
	{
		return containsKey(name);
	}
}
