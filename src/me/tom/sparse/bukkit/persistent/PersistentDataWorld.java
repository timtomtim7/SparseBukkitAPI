package me.tom.sparse.bukkit.persistent;

import me.tom.sparse.bukkit.nbt.Compound;
import me.tom.sparse.math.vector.integers.Vector2i;
import me.tom.sparse.math.vector.integers.Vector3i;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PersistentDataWorld
{
	public final UUID world;
	
	private final Map<Vector2i, PersistentDataRegion> regions = new ConcurrentHashMap<>();
	
	public PersistentDataWorld(UUID world)
	{
		this.world = world;
	}
	
	public void save()
	{
		for(PersistentDataRegion region : regions.values())
		{
			try
			{
				region.save();
			}catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		
		regions.values().removeIf(PersistentDataRegion::isOld);
	}
	
	public World getBukkitWorld()
	{
		return Bukkit.getWorld(world);
	}
	
	public Compound getOrCreate(Vector3i vector)
	{
		return getOrCreate(vector.x(), vector.y(), vector.z());
	}
	
	public Compound getOrCreate(int x, int y, int z)
	{
		return getOrLoadRegion(x >> 9, z >> 9).getOrCreate(x & 511, y, z & 511);
	}
	
	public Compound get(Vector3i vector)
	{
		return get(vector.x(), vector.y(), vector.z());
	}
	
	public Compound get(int x, int y, int z)
	{
		return getOrLoadRegion(x >> 9, z >> 9).get(x & 511, y, z & 511);
	}
	
	public boolean isSet(Vector3i vector)
	{
		return isSet(vector.x(), vector.y(), vector.z());
	}
	
	public boolean isSet(int x, int y, int z)
	{
		return getOrLoadRegion(x >> 9, z >> 9).isSet(x & 511, y, z & 511);
	}
	
	File getFolder()
	{
		File file = new File(getBukkitWorld().getWorldFolder(), "sparse/persistent/");
		if(!file.exists())
			file.mkdirs();
		return file;
	}
	
	private final Vector2i key = new Vector2i();
	
//	private PersistentDataRegion getRegionFromBlock(int x, int z)
//	{
//		key.set(x >> 9, z >> 9);
//		return regions.get(key);
//	}
	
	private PersistentDataRegion getOrLoadRegion(Vector2i vector)
	{
//		CacheBuilder.<Vector2i, PersistentDataRegion>newBuilder().expireAfterAccess(10, TimeUnit.MINUTES).removalListener(removalNotification -> {
//
//		}).build();
		PersistentDataRegion region = regions.get(vector);
		if(region == null)
		{
			region = new PersistentDataRegion(this, vector.x(), vector.y());
			regions.put(vector.clone(), region);
		}
		
		return region;
	}
	
	private PersistentDataRegion getOrLoadRegion(int x, int z)
	{
		key.set(x, z);
		return getOrLoadRegion(key);
	}
	
}