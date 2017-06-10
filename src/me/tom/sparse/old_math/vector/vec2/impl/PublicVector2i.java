
package me.tom.sparse.old_math.vector.vec2.impl;

import me.tom.sparse.old_math.vector.vec2.Vector2i;

public class PublicVector2i implements Vector2i
{
	public int x, y;

	public PublicVector2i()
	{
		this(0);
	}

	public PublicVector2i(int v)
	{
		this(v, v);
	}

	public PublicVector2i(int x, int y)
	{
		this.x = x;
		this.y = y;
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
	public Vector2i setX(int x)
	{
		this.x = x;
		return this;
	}

	@Override
	public Vector2i setY(int y)
	{
		this.y = y;
		return this;
	}

	@Override
	public Vector2i clone()
	{
		return new PublicVector2i(x, y);
	}
}
