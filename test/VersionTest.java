import me.tom.sparse.spigot.SpigotResourceVersionChecker;

import java.io.IOException;

public class VersionTest
{
	public static void main(String[] args) throws IOException
	{
		SpigotResourceVersionChecker resourceVersionChecker = new SpigotResourceVersionChecker("98BE0FE67F88AB82B4C197FAF1DC3B69206EFDCC4D3B80FC83A00037510B99B4");
		String version = resourceVersionChecker.getVersion(40815);
		System.out.println(version);
		System.out.println(resourceVersionChecker.isUpdated("0.0.5", version));
		System.out.println(resourceVersionChecker.isUpdated("0.5", version));
		System.out.println(resourceVersionChecker.isUpdated("0.5", "1.0"));
		System.out.println(resourceVersionChecker.isUpdated("0.4", "0.5"));
		System.out.println(resourceVersionChecker.isUpdated("0.5", "0.4"));
		System.out.println();
		System.out.println(resourceVersionChecker.isUpdated("0.1", "1.0"));
		System.out.println(resourceVersionChecker.isUpdated("1.0", "0.1"));
		System.out.println(resourceVersionChecker.isUpdated("1.0", "1.0"));
//		System.out.println(SparseAPIPlugin.getResourceVersionChecker().isUpdated("0.0.5", SparseAPIPlugin.SPIGOT_RESOURCE_ID));
	}
}
