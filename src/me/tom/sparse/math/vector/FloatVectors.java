package me.tom.sparse.math.vector;

import me.tom.sparse.math.vector.floats.Vector2f;
import me.tom.sparse.math.vector.floats.Vector3f;
import me.tom.sparse.math.vector.floats.Vector4f;

public class FloatVectors
{
	public static Vector4f vec4(float x, float y, float z, float w)
	{
		return new Vector4f(x, y, z, w);
	}

	public static Vector4f vec4(Vector4f other)
	{
		return new Vector4f(other);
	}

	public static Vector4f vec4(float x, Vector3f other)
	{
		return new Vector4f(x, other);
	}

	public static Vector4f vec4(Vector3f other, float w)
	{
		return new Vector4f(other, w);
	}

	public static Vector4f vec4(float x, float y, Vector2f other)
	{
		return new Vector4f(x, y, other);
	}

	public static Vector4f vec4(Vector2f other, float z, float w)
	{
		return new Vector4f(other, z, w);
	}

	public static Vector4f vec4(float x, Vector2f other, float w)
	{
		return new Vector4f(x, other, w);
	}

	public static Vector4f vec4(float value)
	{
		return new Vector4f(value);
	}

	public static Vector4f vec4()
	{
		return new Vector4f();
	}

	public static Vector3f vec3(float x, float y, float z)
	{
		return new Vector3f(x, y, z);
	}

	public static Vector3f vec3(Vector3f other)
	{
		return new Vector3f(other);
	}

	public static Vector3f vec3(float x, Vector2f other)
	{
		return new Vector3f(x, other);
	}

	public static Vector3f vec3(Vector2f other, float z)
	{
		return new Vector3f(other, z);
	}

	public static Vector3f vec3(float value)
	{
		return new Vector3f(value);
	}

	public static Vector3f vec3()
	{
		return new Vector3f();
	}

	public static Vector2f vec2(Vector2f other)
	{
		return new Vector2f(other);
	}

	public static Vector2f vec2(float x, float y)
	{
		return new Vector2f(x, y);
	}

	public static Vector2f vec2(float value)
	{
		return new Vector2f(value);
	}

	public static Vector2f vec2()
	{
		return new Vector2f();
	}



	public static Vector4f abs(Vector4f vec)
	{
		return vec.clone().abs();
	}

	public static Vector3f abs(Vector3f vec)
	{
		return vec.clone().abs();
	}

	public static Vector2f abs(Vector2f vec)
	{
		return vec.clone().abs();
	}



	public static Vector4f round(Vector4f vec)
	{
		return vec.clone().round();
	}

	public static Vector3f round(Vector3f vec)
	{
		return vec.clone().round();
	}

	public static Vector2f round(Vector2f vec)
	{
		return vec.clone().round();
	}



	public static Vector4f ceil(Vector4f vec)
	{
		return vec.clone().ceil();
	}

	public static Vector3f ceil(Vector3f vec)
	{
		return vec.clone().ceil();
	}

	public static Vector2f ceil(Vector2f vec)
	{
		return vec.clone().ceil();
	}



	public static Vector4f floor(Vector4f vec)
	{
		return vec.clone().ceil();
	}

	public static Vector3f floor(Vector3f vec)
	{
		return vec.clone().ceil();
	}

	public static Vector2f floor(Vector2f vec)
	{
		return vec.clone().ceil();
	}



	public static Vector4f normalize(Vector4f vec)
	{
		return vec.clone().normalize();
	}

	public static Vector3f normalize(Vector3f vec)
	{
		return vec.clone().normalize();
	}

	public static Vector2f normalize(Vector2f vec)
	{
		return vec.clone().normalize();
	}



	public static float sum(Vector4f vec)
	{
		return vec.sum();
	}

	public static float sum(Vector3f vec)
	{
		return vec.sum();
	}

	public static float sum(Vector2f vec)
	{
		return vec.sum();
	}



	public static float lengthSquared(Vector4f vec)
	{
		return vec.lengthSquared();
	}

	public static float lengthSquared(Vector3f vec)
	{
		return vec.lengthSquared();
	}

	public static float lengthSquared(Vector2f vec)
	{
		return vec.lengthSquared();
	}



	public static float length(Vector4f vec)
	{
		return vec.length();
	}

	public static float length(Vector3f vec)
	{
		return vec.length();
	}

	public static float length(Vector2f vec)
	{
		return vec.length();
	}



	public static float dot(Vector4f a, Vector4f b)
	{
		return a.dot(b);
	}

	public static float dot(Vector3f a, Vector3f b)
	{
		return a.dot(b);
	}

	public static float dot(Vector2f a, Vector2f b)
	{
		return a.dot(b);
	}



	public static float distanceSquared(Vector4f a, Vector4f b)
	{
		return a.distanceSquared(b);
	}

	public static float distanceSquared(Vector3f a, Vector3f b)
	{
		return a.distanceSquared(b);
	}

	public static float distanceSquared(Vector2f a, Vector2f b)
	{
		return a.distanceSquared(b);
	}



	public static float distance(Vector4f a, Vector4f b)
	{
		return a.distance(b);
	}

	public static float distance(Vector3f a, Vector3f b)
	{
		return a.distance(b);
	}

	public static float distance(Vector2f a, Vector2f b)
	{
		return a.distance(b);
	}



	public static Vector3f cross(Vector3f a, Vector3f b)
	{
		return a.cross(b);
	}

	public static Vector2f cross(Vector2f vec)
	{
		return vec.cross();
	}

	public static float cross(Vector2f a, Vector2f b)
	{
		return a.cross(b);
	}
}
