package me.tom.sparse.bukkit.persistent;

import me.tom.sparse.bukkit.nbt.Compound;
import me.tom.sparse.math.vector.integers.Vector3i;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PersistentDataRegion
{
	public static final long MAX_IDLE_TIME = 10*60*1000;
	
	public final PersistentDataWorld world;
	public final int x, z;
	private long lastAccessTime;
	
	private final Vector3i key = new Vector3i();
	
	private final Map<Vector3i, Compound> data = new ConcurrentHashMap<>();
	
	PersistentDataRegion(PersistentDataWorld world, int x, int z)
	{
		this.world = world;
		this.x = x;
		this.z = z;
		load();
	}
	
	public void save() throws IOException
	{
		System.out.println("Saving region "+x+","+z+" to "+getFile().getAbsolutePath());
		List<Compound> compounds = new ArrayList<>();
		for(Map.Entry<Vector3i, Compound> entry : data.entrySet())
		{
			Compound compound = new Compound();
			compound.setCompound("compound", entry.getValue());
			Vector3i key = entry.getKey();
			compound.setInt("x", key.x());
			compound.setInt("y", key.y());
			compound.setInt("z", key.z());
			compounds.add(compound);
		}
		
		new Compound("data", compounds).write(getFile());
	}
	
	public void load()
	{
		File file = getFile();
		if(!file.exists())
			return;
		
		try
		{
			Compound fileData = Compound.read(file);
			List<Compound> list = fileData.getList("data");
			
			for(Compound compound : list)
			{
				Vector3i key = new Vector3i(compound.getInt("x"), compound.getInt("y"), compound.getInt("z"));
				data.put(key, compound.getCompound("compound"));
			}
			
		}catch(IOException e)
		{
			System.err.println("Error loading persistent region data:");
			e.printStackTrace();
		}
	}
	
	File getFile()
	{
		return new File(world.getFolder(), "r."+x+"."+z+".dat");
	}
	
	public Compound getOrCreate(Vector3i vector)
	{
		Compound compound = data.get(vector);
		if(compound == null)
			data.put(vector.clone(), compound = new Compound());
		return compound;
	}
	
	public Compound getOrCreate(int x, int y, int z)
	{
		key.set(x, y, z);
		return getOrCreate(key);
	}
	
	public Compound get(Vector3i vector)
	{
		access();
		if(vector == null)
			return null;
		
		return data.get(vector);
	}
	
	public Compound get(int x, int y, int z)
	{
		key.set(x, y, z);
		return get(key);
	}
	
	public boolean isSet(Vector3i vector)
	{
		access();
		return data.containsKey(vector);
	}
	
	public boolean isSet(int x, int y, int z)
	{
		key.set(x, y, z);
		return data.containsKey(key);
	}
	
	private void access()
	{
		lastAccessTime = System.currentTimeMillis();
	}
	
	public long getLastAccessTime()
	{
		return lastAccessTime;
	}
	
	public boolean isOld()
	{
		return (System.currentTimeMillis()-lastAccessTime) > MAX_IDLE_TIME;
	}
}
