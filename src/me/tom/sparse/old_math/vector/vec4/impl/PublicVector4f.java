package me.tom.sparse.old_math.vector.vec4.impl;

import me.tom.sparse.old_math.vector.vec4.Vector4f;

public class PublicVector4f implements Vector4f
{
	public float x, y, z, w;

	public PublicVector4f()
	{
		this(0);
	}

	public PublicVector4f(float v)
	{
		this(v, v, v, v);
	}

	public PublicVector4f(float x, float y, float z, float w)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
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
	public float getW()
	{
		return w;
	}

	@Override
	public Vector4f setX(float x)
	{
		this.x = x;
		return this;
	}

	@Override
	public Vector4f setY(float y)
	{
		this.y = y;
		return this;
	}

	@Override
	public Vector4f setZ(float z)
	{
		this.z = z;
		return this;
	}

	@Override
	public Vector4f setW(float w)
	{
		this.w = w;
		return this;
	}

	@Override
	public Vector4f clone()
	{
		return new PublicVector4f(x, y, z, w);
	}

	@Override
	public String toString()
	{
		return "PublicVector4f{" +
				"x=" + x +
				", y=" + y +
				", z=" + z +
				", w=" + w +
				'}';
	}
}
