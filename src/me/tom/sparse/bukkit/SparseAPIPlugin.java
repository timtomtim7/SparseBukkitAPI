package me.tom.sparse.bukkit;

import me.tom.sparse.bukkit.menu.MenuManager;
import me.tom.sparse.bukkit.version.general.VersionUtils;
import me.tom.sparse.spigot.SpigotResourceVersionChecker;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.logging.Level;

public class SparseAPIPlugin extends JavaPlugin
{
	public static final int SPIGOT_RESOURCE_ID = 40815;
	
	public static final String NMS_VERSION = Bukkit.getServer().getClass().getPackage().getName().replace(".",  ",").split(",")[3];
	private static VersionUtils versionUtils;
	
	private static SpigotResourceVersionChecker resourceVersionChecker;
	
	private static SparseAPIPlugin instance;
	
	public static SparseAPIPlugin getInstance()
	{
		return instance;
	}
	
	public static VersionUtils getVersionUtils()
	{
		return versionUtils;
	}
	
	public static SpigotResourceVersionChecker getResourceVersionChecker()
	{
		return resourceVersionChecker;
	}
	
	public void onEnable()
	{
		instance = this;
		
		resourceVersionChecker = new SpigotResourceVersionChecker("98BE0FE67F88AB82B4C197FAF1DC3B69206EFDCC4D3B80FC83A00037510B99B4");
		try
		{
			String latestVer = resourceVersionChecker.getVersion(SPIGOT_RESOURCE_ID).replaceAll("[^0-9.]+", "");
			getLogger().log(Level.INFO, "The latest version of SparseBukkitAPI is "+latestVer);
			if(!resourceVersionChecker.isUpdated(getDescription().getVersion(), latestVer))
			{
				getLogger().log(Level.WARNING, "+-------------------------------------------+");
				getLogger().log(Level.WARNING, "| SparseBukkitAPI has an update available!  |");
				getLogger().log(Level.WARNING, "| https://www.spigotmc.org/resources/40815/ |");
				getLogger().log(Level.WARNING, "+-------------------------------------------+");
			}else{
				getLogger().log(Level.INFO, "SparseBukkitAPI "+getDescription().getVersion()+" is up to date!");
			}
		}catch(IOException e)
		{
			getLogger().log(Level.WARNING, "Unable to check for new SparseBukkitAPI version.");
		}
		
		try
		{
			Class<?> clazz = Class.forName("me.tom.sparse.bukkit.version.general.VersionUtils_" + NMS_VERSION);
			versionUtils = (VersionUtils) clazz.newInstance();
			getLogger().log(Level.INFO, "Using "+clazz.getSimpleName()+" for VersionUtils");
		}catch(IllegalAccessException | InstantiationException | ClassNotFoundException e)
		{
			getLogger().log(Level.SEVERE, "SparseBukkitAPI does not support Minecraft "+NMS_VERSION);
			e.printStackTrace();
			setEnabled(false);
			return;
		}
		AntiTimeout.init();
		MenuManager.init();
	}
	
	public void onDisable()
	{
		AntiTimeout.stop();
	}
}
