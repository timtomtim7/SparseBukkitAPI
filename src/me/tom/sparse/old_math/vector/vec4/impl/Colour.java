package me.tom.sparse.old_math.vector.vec4.impl;

import me.tom.sparse.old_math.vector.vec3.Vector3f;
import me.tom.sparse.old_math.vector.vec4.Vector4f;

public class Colour extends Color
{
	public Colour(Vector4f other)
	{
		super(other);
	}

	public Colour(Vector3f other)
	{
		super(other);
	}

	public Colour(Vector3f other, float a)
	{
		super(other, a);
	}

	public Colour(float r, float g, float b, float a)
	{
		super(r, g, b, a);
	}

	public Colour(float r, float g, float b)
	{
		super(r, g, b);
	}
}
