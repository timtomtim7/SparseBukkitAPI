package me.tom.sparse.old_math.vector.vec2.impl;

import me.tom.sparse.old_math.vector.vec2.Vector2f;

public class PublicVector2f implements Vector2f
{
	public float x, y;

	public PublicVector2f()
	{
		this(0);
	}

	public PublicVector2f(float v)
	{
		this(v, v);
	}

	public PublicVector2f(float x, float y)
	{
		this.x = x;
		this.y = y;
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
	public Vector2f setX(float x)
	{
		this.x = x;
		return this;
	}

	@Override
	public Vector2f setY(float y)
	{
		this.y = y;
		return this;
	}

	@Override
	public Vector2f clone()
	{
		return new PublicVector2f(x, y);
	}

	@Override
	public String toString()
	{
		return "PublicVector2f{" +
				"x=" + x +
				", y=" + y +
				'}';
	}
}
