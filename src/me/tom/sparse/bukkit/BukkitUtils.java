package me.tom.sparse.bukkit;

import me.tom.sparse.math.vector.doubles.Vector2d;
import me.tom.sparse.math.vector.doubles.Vector3d;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;

public final class BukkitUtils
{
	private BukkitUtils() {}
	
	public static String color(String string)
	{
		return ChatColor.translateAlternateColorCodes('&', string);
	}
	
	public static Location toLocation(World world, Vector3d vector)
	{
		return new Location(world, vector.x(), vector.y(), vector.z());
	}
	
	public static Vector toVector(Vector3d vector)
	{
		return new Vector(vector.x(), vector.y(), vector.z());
	}
	
	public static Vector3d fromVector(Vector vector)
	{
		return new Vector3d(vector.getX(), vector.getY(), vector.getZ());
	}
	
	public static Vector3d fromLocation(Location location)
	{
		return new Vector3d(location.getX(), location.getY(), location.getZ());
	}
	
	public static BlockFace getBlockFace(Vector vector)
	{
		return getBlockFace(vector.getX(), vector.getY(), vector.getZ());
	}
	
	public static BlockFace getBlockFace(Vector3d vector)
	{
		return getBlockFace(vector.x(), vector.y(), vector.z());
	}
	
	public static Vector3d fromBlockFace(BlockFace face)
	{
		return new Vector3d(face.getModX(), face.getModY(), face.getModZ());
	}
	
	public static BlockFace getBlockFace(double x, double y, double z)
	{
		int ix = (int) Math.round(x);
		int iy = (int) Math.round(y);
		int iz = (int) Math.round(z);
		
		for(BlockFace face : BlockFace.values())
			if(face.getModX() == ix && face.getModY() == iy && face.getModZ() == iz)
				return face;
		return null;
	}
	
	public static boolean isCardinal(BlockFace face)
	{
		return face.ordinal() < 6;
	}
	
	public static int getAxisIndex(BlockFace face)
	{
		if(!isCardinal(face))
			throw new IllegalArgumentException("Non-cardinal BlockFace has more than one axis.");
		
		if(face.getModX() != 0)
			return 0;
		if(face.getModY() != 0)
			return 1;
		if(face.getModZ() != 0)
			return 2;
		throw new IllegalStateException("Unexpected BlockFace coordinates. ("+face+")");
	}
	
	public static double extractFaceCoordinate(Vector3d vector, BlockFace face)
	{
		if(!isCardinal(face))
			throw new IllegalArgumentException("Cannot extract coordinate from non-cardinal BlockFace");
		
		if(face.getModX() != 0)
			return vector.x();
		if(face.getModY() != 0)
			return vector.y();
		if(face.getModZ() != 0)
			return vector.z();
		throw new IllegalStateException("Unexpected BlockFace coordinates. ("+face+")");
//		return vector.getGetters().get(getAxisIndex(face)).get();
	}
	
	public static Vector2d excludeFaceCoordinate(Vector3d vector, BlockFace face)
	{
		if(!isCardinal(face))
			throw new IllegalArgumentException("Cannot exclude coordinate from non-cardinal BlockFace");
		
		if(face.getModX() != 0)
			return new Vector2d(vector.y(), vector.z());
		if(face.getModY() != 0)
			return new Vector2d(vector.x(), vector.z());
		if(face.getModZ() != 0)
			return new Vector2d(vector.x(), vector.y());
		throw new IllegalStateException("Unexpected BlockFace coordinates. ("+face+")");
		
//		List<Float> values = vector.getGetters().stream().map(Supplier::get).collect(Collectors.toList());
//		values.remove(getAxisIndex(face));
//		return Vector2f.create(values.get(0), values.get(1));
	}
	
	public static Vector3d mergeFaceCoordinate(Vector2d excluded, double extracted, BlockFace face)
	{
		if(!isCardinal(face))
			throw new IllegalArgumentException("Cannot merge coordinates from non-cardinal BlockFace");
		if(face.getModX() != 0)
			return new Vector3d(extracted, excluded.x(), excluded.y());
		if(face.getModY() != 0)
			return new Vector3d(excluded.x(), extracted, excluded.y());
		if(face.getModZ() != 0)
			return new Vector3d(excluded.x(), excluded.y(), extracted);
		throw new IllegalStateException("Unexpected BlockFace coordinates. ("+face+")");
//		List<Float> values = new ArrayList<>(Arrays.asList(excluded.getX(), excluded.getY()));
//		int axis = getAxisIndex(face);
//		values.add(axis, extracted);
//		return Vector3f.create(values.get(0), values.get(1), values.get(2));
	}
}
