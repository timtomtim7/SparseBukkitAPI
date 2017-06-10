package me.tom.sparse.bukkit;

import me.tom.sparse.bukkit.version.general.VersionUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class AntiTimeout
{
	private static AntiTimeoutThread thread;
	public static final long SECOND = 1000;
	public static final long MAX_LAG = 2 * SECOND;
	public static final long PACKET_DELAY = 2 * SECOND;
	
	public static void init()
	{
		if(thread != null)
			return;
		thread = new AntiTimeoutThread();
		thread.setDaemon(true);
		thread.start();
		Bukkit.getScheduler().scheduleSyncRepeatingTask(SparseAPIPlugin.getInstance(), thread::bukkitRun, 1, 1);
	}
	
	public static void stop()
	{
		if(thread == null || !thread.running)
			return;
		thread.running = false;
	}
	
	private static final class AntiTimeoutThread extends Thread
	{
		private AntiTimeoutThread() {}
		
		private long lastTick = getTime();
		private long lastFalsePacket = getTime();
		
		private boolean running = true;
		private boolean isLagging = false;
		private long lastLagStart;
		
		public void run()
		{
			System.out.println("Starting AntiTimeout thread.");
			while(running)
			{
				long now = getTime();
				long frozenTime = now - lastTick;
				VersionUtils versionUtils = SparseAPIPlugin.getVersionUtils();
				if(frozenTime > MAX_LAG)
				{
					if(!isLagging)
					{
//						System.out.println("Server is frozen.");
						SparseAPIPlugin.getInstance().getLogger().log(Level.WARNING, "Server is frozen.");
						isLagging = true;
						lastLagStart = now;
					}
					if(now - lastFalsePacket > PACKET_DELAY)
					{
						lastFalsePacket = now;
						Bukkit.getOnlinePlayers().forEach(versionUtils::keepAlive);
					}
					Bukkit.getOnlinePlayers().forEach(versionUtils::resetLocation);
					for(Player player : Bukkit.getOnlinePlayers())
						versionUtils.sendTitle(player, "", String.format("\u00a7cThe server has been frozen for \u00a76%.1fs", frozenTime/(double)SECOND), 0, 20, 0);
					try
					{
						Thread.sleep(100);
					}catch(InterruptedException e)
					{
						e.printStackTrace();
					}
				}else{
					if(isLagging)
					{
//						System.out.println(String.format("Lag ended, lasted %.3fs", lastLagStart/(double)SECOND));
						SparseAPIPlugin.getInstance().getLogger().log(Level.WARNING, String.format("Server no longer frozen, lasted %.3fs", (now-lastLagStart) /(double)SECOND));
						isLagging = false;
					}
					if(now - lastFalsePacket < PACKET_DELAY)
					{
						for(Player player : Bukkit.getOnlinePlayers())
							versionUtils.sendTitle(player,"", "\u00a7aThe server is no longer frozen.", 0, 30, 10);
						lastFalsePacket = 0;
					}
					try
					{
						Thread.sleep(500);
					}catch(InterruptedException e)
					{
						e.printStackTrace();
					}
				}
			}
		}
		
		public void bukkitRun()
		{
			lastTick = getTime();
		}
		
		private static long getTime()
		{
			return System.currentTimeMillis();
		}
	}
	
//	public static boolean isServerRunning()
//	{
//		return ((CraftServer)Bukkit.getServer()).getHandle().getServer().isRunning();
//	}
}
