package me.tom.sparse.math.vector;

import me.tom.sparse.math.vector.doubles.Vector2d;
import me.tom.sparse.math.vector.doubles.Vector3d;
import me.tom.sparse.math.vector.doubles.Vector4d;

public class DoubleVectors
{
	public static Vector4d vec4(double x, double y, double z, double w)
	{
		return new Vector4d(x, y, z, w);
	}

	public static Vector4d vec4(Vector4d other)
	{
		return new Vector4d(other);
	}

	public static Vector4d vec4(double x, Vector3d other)
	{
		return new Vector4d(x, other);
	}

	public static Vector4d vec4(Vector3d other, double w)
	{
		return new Vector4d(other, w);
	}

	public static Vector4d vec4(double x, double y, Vector2d other)
	{
		return new Vector4d(x, y, other);
	}

	public static Vector4d vec4(Vector2d other, double z, double w)
	{
		return new Vector4d(other, z, w);
	}

	public static Vector4d vec4(double x, Vector2d other, double w)
	{
		return new Vector4d(x, other, w);
	}

	public static Vector4d vec4(double value)
	{
		return new Vector4d(value);
	}

	public static Vector4d vec4()
	{
		return new Vector4d();
	}

	public static Vector3d vec3(double x, double y, double z)
	{
		return new Vector3d(x, y, z);
	}

	public static Vector3d vec3(Vector3d other)
	{
		return new Vector3d(other);
	}

	public static Vector3d vec3(double x, Vector2d other)
	{
		return new Vector3d(x, other);
	}

	public static Vector3d vec3(Vector2d other, double z)
	{
		return new Vector3d(other, z);
	}

	public static Vector3d vec3(double value)
	{
		return new Vector3d(value);
	}

	public static Vector3d vec3()
	{
		return new Vector3d();
	}

	public static Vector2d vec2(Vector2d other)
	{
		return new Vector2d(other);
	}

	public static Vector2d vec2(double x, double y)
	{
		return new Vector2d(x, y);
	}

	public static Vector2d vec2(double value)
	{
		return new Vector2d(value);
	}

	public static Vector2d vec2()
	{
		return new Vector2d();
	}



	public static Vector4d abs(Vector4d vec)
	{
		return vec.clone().abs();
	}

	public static Vector3d abs(Vector3d vec)
	{
		return vec.clone().abs();
	}

	public static Vector2d abs(Vector2d vec)
	{
		return vec.clone().abs();
	}



	public static Vector4d round(Vector4d vec)
	{
		return vec.clone().round();
	}

	public static Vector3d round(Vector3d vec)
	{
		return vec.clone().round();
	}

	public static Vector2d round(Vector2d vec)
	{
		return vec.clone().round();
	}



	public static Vector4d ceil(Vector4d vec)
	{
		return vec.clone().ceil();
	}

	public static Vector3d ceil(Vector3d vec)
	{
		return vec.clone().ceil();
	}

	public static Vector2d ceil(Vector2d vec)
	{
		return vec.clone().ceil();
	}



	public static Vector4d floor(Vector4d vec)
	{
		return vec.clone().ceil();
	}

	public static Vector3d floor(Vector3d vec)
	{
		return vec.clone().ceil();
	}

	public static Vector2d floor(Vector2d vec)
	{
		return vec.clone().ceil();
	}



	public static Vector4d normalize(Vector4d vec)
	{
		return vec.clone().normalize();
	}

	public static Vector3d normalize(Vector3d vec)
	{
		return vec.clone().normalize();
	}

	public static Vector2d normalize(Vector2d vec)
	{
		return vec.clone().normalize();
	}



	public static double sum(Vector4d vec)
	{
		return vec.sum();
	}

	public static double sum(Vector3d vec)
	{
		return vec.sum();
	}

	public static double sum(Vector2d vec)
	{
		return vec.sum();
	}



	public static double lengthSquared(Vector4d vec)
	{
		return vec.lengthSquared();
	}

	public static double lengthSquared(Vector3d vec)
	{
		return vec.lengthSquared();
	}

	public static double lengthSquared(Vector2d vec)
	{
		return vec.lengthSquared();
	}



	public static double length(Vector4d vec)
	{
		return vec.length();
	}

	public static double length(Vector3d vec)
	{
		return vec.length();
	}

	public static double length(Vector2d vec)
	{
		return vec.length();
	}



	public static double dot(Vector4d a, Vector4d b)
	{
		return a.dot(b);
	}

	public static double dot(Vector3d a, Vector3d b)
	{
		return a.dot(b);
	}

	public static double dot(Vector2d a, Vector2d b)
	{
		return a.dot(b);
	}



	public static double distanceSquared(Vector4d a, Vector4d b)
	{
		return a.distanceSquared(b);
	}

	public static double distanceSquared(Vector3d a, Vector3d b)
	{
		return a.distanceSquared(b);
	}

	public static double distanceSquared(Vector2d a, Vector2d b)
	{
		return a.distanceSquared(b);
	}



	public static double distance(Vector4d a, Vector4d b)
	{
		return a.distance(b);
	}

	public static double distance(Vector3d a, Vector3d b)
	{
		return a.distance(b);
	}

	public static double distance(Vector2d a, Vector2d b)
	{
		return a.distance(b);
	}



	public static Vector3d cross(Vector3d a, Vector3d b)
	{
		return a.cross(b);
	}

	public static Vector2d cross(Vector2d vec)
	{
		return vec.cross();
	}

	public static double cross(Vector2d a, Vector2d b)
	{
		return a.cross(b);
	}
}
