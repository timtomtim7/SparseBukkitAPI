package me.tom.sparse.old_math.vector.vec3.impl;

import me.tom.sparse.old_math.vector.vec3.Vector3f;

public class PublicVector3f implements Vector3f
{
	public float x, y, z;

	public PublicVector3f()
	{
		this(0);
	}

	public PublicVector3f(float v)
	{
		this(v, v, v);
	}

	public PublicVector3f(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public float getX()
	{
		return x;
	}

	@Override
	public float getY()
	{
		return y;
	}

	@Override
	public float getZ()
	{
		return z;
	}

	@Override
	public Vector3f setX(float x)
	{
		this.x = x;
		return this;
	}

	@Override
	public Vector3f setY(float y)
	{
		this.y = y;
		return this;
	}

	@Override
	public Vector3f setZ(float z)
	{
		this.z = z;
		return this;
	}

	@Override
	public Vector3f clone()
	{
		return new PublicVector3f(x, y, z);
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

		PublicVector3f that = (PublicVector3f) o;

		if (Float.compare(that.x, x) != 0) return false;
		if (Float.compare(that.y, y) != 0) return false;
		return Float.compare(that.z, z) == 0;

	}

	@Override
	public int hashCode()
	{
		int result = (x != +0.0f ? Float.floatToIntBits(x) : 0);
		result = 31 * result + (y != +0.0f ? Float.floatToIntBits(y) : 0);
		result = 31 * result + (z != +0.0f ? Float.floatToIntBits(z) : 0);
		return result;
	}
}
