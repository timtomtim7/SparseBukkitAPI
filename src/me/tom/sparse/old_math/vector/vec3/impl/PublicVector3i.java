package me.tom.sparse.old_math.vector.vec3.impl;

import me.tom.sparse.old_math.vector.vec3.Vector3i;

public class PublicVector3i implements Vector3i
{
	public int x, y, z;

	public PublicVector3i()
	{
		this(0);
	}

	public PublicVector3i(int v)
	{
		this(v, v, v);
	}

	public PublicVector3i(int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public int getX()
	{
		return x;
	}

	@Override
	public int getY()
	{
		return y;
	}

	@Override
	public int getZ()
	{
		return z;
	}

	@Override
	public Vector3i setX(int x)
	{
		this.x = x;
		return this;
	}

	@Override
	public Vector3i setY(int y)
	{
		this.y = y;
		return this;
	}

	@Override
	public Vector3i setZ(int z)
	{
		this.z = z;
		return this;
	}

	@Override
	public Vector3i clone()
	{
		return new PublicVector3i(x, y, z);
	}

	@Override
	public String toString()
	{
		return "PublicVector3f{" +
				"x=" + x +
				", y=" + y +
				", z=" + z +
				'}';
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		PublicVector3i that = (PublicVector3i) o;

		if (Integer.compare(that.x, x) != 0) return false;
		if (Integer.compare(that.y, y) != 0) return false;
		return Integer.compare(that.z, z) == 0;

	}
	
	public int hashCode()
	{
		int result = x;
		result = 31 * result + y;
		result = 31 * result + z;
		return result;
	}
}
