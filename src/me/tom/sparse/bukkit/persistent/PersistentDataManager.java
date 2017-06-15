package me.tom.sparse.bukkit.persistent;

import me.tom.sparse.bukkit.SparseAPIPlugin;
import me.tom.sparse.bukkit.nbt.Compound;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/*
	Use Compounds to store data.
	Ensure compounds are compressed in the resulting file.
	Have one file for every region
	Unload regions that haven't been accessed in 10 minutes
	Save all loaded region data every 10 minutes
*/
public final class PersistentDataManager
{
	private static final Map<UUID, PersistentDataWorld> WORLDS = new ConcurrentHashMap<>();
	
	static
	{
		Bukkit.getScheduler().scheduleSyncRepeatingTask(SparseAPIPlugin.getInstance(), PersistentDataManager::save, 10*60*20, 10*60*20);
	}
	
	public static void init()
	{
	
	}
	
	public static void save()
	{
		WORLDS.values().forEach(PersistentDataWorld::save);
	}
	
	private static PersistentDataWorld getWorld(World world)
	{
		if(world == null)
			return null;
		return WORLDS.computeIfAbsent(world.getUID(), PersistentDataWorld::new);
	}
	
	public static Optional<Compound> getOptional(Block block)
	{
		return Optional.ofNullable(get(block));
	}
	
	public static Compound get(Block block)
	{
		return getWorld(block.getWorld()).get(block.getX(), block.getY(), block.getZ());
	}
	
	public static Compound getOrCreate(Block block)
	{
		return getWorld(block.getWorld()).getOrCreate(block.getX(), block.getY(), block.getZ());
	}
	
	public static boolean isSet(Block block)
	{
		return getWorld(block.getWorld()).isSet(block.getX(), block.getY(), block.getZ());
	}
	
	private PersistentDataManager() {}
}
