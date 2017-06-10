package me.tom.sparse.math.vector;

import me.tom.sparse.math.vector.integers.Vector2i;
import me.tom.sparse.math.vector.integers.Vector3i;
import me.tom.sparse.math.vector.integers.Vector4i;

public class IntVectors
{
	public static Vector4i vec4(int x, int y, int z, int w)
	{
		return new Vector4i(x, y, z, w);
	}

	public static Vector4i vec4(Vector4i other)
	{
		return new Vector4i(other);
	}

	public static Vector4i vec4(int x, Vector3i other)
	{
		return new Vector4i(x, other);
	}

	public static Vector4i vec4(Vector3i other, int w)
	{
		return new Vector4i(other, w);
	}

	public static Vector4i vec4(int x, int y, Vector2i other)
	{
		return new Vector4i(x, y, other);
	}

	public static Vector4i vec4(Vector2i other, int z, int w)
	{
		return new Vector4i(other, z, w);
	}

	public static Vector4i vec4(int x, Vector2i other, int w)
	{
		return new Vector4i(x, other, w);
	}

	public static Vector4i vec4(int value)
	{
		return new Vector4i(value);
	}

	public static Vector4i vec4()
	{
		return new Vector4i();
	}

	public static Vector3i vec3(int x, int y, int z)
	{
		return new Vector3i(x, y, z);
	}

	public static Vector3i vec3(Vector3i other)
	{
		return new Vector3i(other);
	}

	public static Vector3i vec3(int x, Vector2i other)
	{
		return new Vector3i(x, other);
	}

	public static Vector3i vec3(Vector2i other, int z)
	{
		return new Vector3i(other, z);
	}

	public static Vector3i vec3(int value)
	{
		return new Vector3i(value);
	}

	public static Vector3i vec3()
	{
		return new Vector3i();
	}

	public static Vector2i vec2(Vector2i other)
	{
		return new Vector2i(other);
	}

	public static Vector2i vec2(int x, int y)
	{
		return new Vector2i(x, y);
	}

	public static Vector2i vec2(int value)
	{
		return new Vector2i(value);
	}

	public static Vector2i vec2()
	{
		return new Vector2i();
	}



	public static Vector4i abs(Vector4i vec)
	{
		return vec.clone().abs();
	}

	public static Vector3i abs(Vector3i vec)
	{
		return vec.clone().abs();
	}

	public static Vector2i abs(Vector2i vec)
	{
		return vec.clone().abs();
	}



	public static int sum(Vector4i vec)
	{
		return vec.sum();
	}

	public static int sum(Vector3i vec)
	{
		return vec.sum();
	}

	public static int sum(Vector2i vec)
	{
		return vec.sum();
	}



	public static double lengthSquared(Vector4i vec)
	{
		return vec.lengthSquared();
	}

	public static double lengthSquared(Vector3i vec)
	{
		return vec.lengthSquared();
	}

	public static double lengthSquared(Vector2i vec)
	{
		return vec.lengthSquared();
	}



	public static double length(Vector4i vec)
	{
		return vec.length();
	}

	public static double length(Vector3i vec)
	{
		return vec.length();
	}

	public static double length(Vector2i vec)
	{
		return vec.length();
	}



	public static double dot(Vector4i a, Vector4i b)
	{
		return a.dot(b);
	}

	public static double dot(Vector3i a, Vector3i b)
	{
		return a.dot(b);
	}

	public static double dot(Vector2i a, Vector2i b)
	{
		return a.dot(b);
	}



	public static double distanceSquared(Vector4i a, Vector4i b)
	{
		return a.distanceSquared(b);
	}

	public static double distanceSquared(Vector3i a, Vector3i b)
	{
		return a.distanceSquared(b);
	}

	public static double distanceSquared(Vector2i a, Vector2i b)
	{
		return a.distanceSquared(b);
	}



	public static double distance(Vector4i a, Vector4i b)
	{
		return a.distance(b);
	}

	public static double distance(Vector3i a, Vector3i b)
	{
		return a.distance(b);
	}

	public static double distance(Vector2i a, Vector2i b)
	{
		return a.distance(b);
	}



	public static Vector3i cross(Vector3i a, Vector3i b)
	{
		return a.cross(b);
	}

	public static Vector2i cross(Vector2i vec)
	{
		return vec.cross();
	}

	public static int cross(Vector2i a, Vector2i b)
	{
		return a.cross(b);
	}
}
