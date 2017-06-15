package me.tom.sparse.spigot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

public final class SpigotResourceVersionChecker
{
	private final String API_KEY;
	
	public SpigotResourceVersionChecker(String apiKey)
	{
		//TODO: Escape this?
		this.API_KEY = apiKey;//rUlEscapers.urlFragmentEscaper().escape(apiKey);
	}
	
	public String getVersion(int resource) throws IOException
	{
		HttpURLConnection con = (HttpURLConnection) new URL("http://www.spigotmc.org/api/general.php").openConnection();
		con.setDoOutput(true);
		con.setRequestMethod("POST");
		con.getOutputStream().write(("key=" + API_KEY + "&resource=" + resource).getBytes("UTF-8"));
		return new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
	}
	
	public boolean isUpdated(String ourVersion, int resource) throws IOException
	{
		return isUpdated(ourVersion, getVersion(resource));
	}
	
	public boolean isUpdated(String ourVersion, String latestVersion)
	{
		int[] a = tokenize(ourVersion);
		int[] b = tokenize(latestVersion);

//		System.out.println();
//		System.out.println();
//		System.out.println();
//		System.out.println("ourVersion = "+Arrays.toString(a));
//		System.out.println("latestVersion = "+Arrays.toString(b));
		
		for(int i = 0; i < Math.min(a.length, b.length); i++)
		{
			if(a[i] < b[i])
			{
//				System.out.println("FALSE on index "+i);
				return false;
			}else if(a[i] > b[i])
			{
//				System.out.println("TRUE on index "+i);
				return true;
			}
		}
		
		if(b.length > a.length)
			for(int i = a.length; i < b.length; i++)
				if(b[i] > 0)
					return false;
		
		return true;
	}
	
	private int[] tokenize(String version)
	{
		version = version.replaceAll("[^0-9.]+", "");
		String[] split = version.split("\\.");
		int[] result = new int[split.length];
		for(int i = 0; i < split.length; i++)
			result[i] = Integer.parseInt(split[i]);
		return result;
	}
}
