package me.tom.sparse.math.vector.floats.impl;

import me.tom.sparse.math.Matrix4f;
import me.tom.sparse.math.vector.floats.Vector3f;
import me.tom.sparse.math.vector.floats.Vector4f;

public class Quaternion4f extends Vector4f
{
	public Quaternion4f()
	{
		set(0, 0, 0, 1);
	}
	
	public Quaternion4f(Quaternion4f other)
	{
		set(other);
	}
	
	public Quaternion4f(Vector3f axis, float angle)
	{
		set(axis, angle);
	}
	
	public Quaternion4f(float x, float y, float z, float w)
	{
		set(x, y, z, w);
	}
	
	public Quaternion4f(float value)
	{
		set(value);
	}
	
	public Quaternion4f(float x, float y, float z)
	{
		this(new Vector3f(0, 0, 1), z);
		multiply(new Quaternion4f(new Vector3f(0, 1, 0), y));
		multiply(new Quaternion4f(new Vector3f(1, 0, 0), x));
//		Quaternion4f.get(Vector3f.get(0, 0, 1), z)
//				.multiply(Quaternion4f.get(Vector3f.get(0, 1, 0), y))
//				.multiply(Quaternion4f.get(Vector3f.get(1, 0, 0), x));
	}
	
	public Quaternion4f(Matrix4f rotation)
	{
		set(rotation);
	}

//	public Quaternion4f(float x, float y, float z, float w)
//	{
//		this.x = x;
//		this.y = y;
//		this.z = z;
//		this.w = w;
//	}

//	public Quaternion4f(Vector3f axis, float angle)
//	{
//		set(axis, angle);
//	}

	public Quaternion4f set(Matrix4f rot)
	{
		float trace = rot.get(0, 0) + rot.get(1, 1) + rot.get(2, 2);

		if (trace > 0)
		{
			float s = 0.5f / (float) Math.sqrt(trace + 1.0f);
			w = 0.25f / s;
			x = (rot.get(1, 2) - rot.get(2, 1)) * s;
			y = (rot.get(2, 0) - rot.get(0, 2)) * s;
			z = (rot.get(0, 1) - rot.get(1, 0)) * s;
		} else
		{
			if (rot.get(0, 0) > rot.get(1, 1) && rot.get(0, 0) > rot.get(2, 2))
			{
				float s = 2.0f * (float) Math.sqrt(1.0f + rot.get(0, 0) - rot.get(1, 1) - rot.get(2, 2));
				w = (rot.get(1, 2) - rot.get(2, 1)) / s;
				x = 0.25f * s;
				y = (rot.get(1, 0) + rot.get(0, 1)) / s;
				z = (rot.get(2, 0) + rot.get(0, 2)) / s;
			} else if (rot.get(1, 1) > rot.get(2, 2))
			{
				float s = 2.0f * (float) Math.sqrt(1.0f + rot.get(1, 1) - rot.get(0, 0) - rot.get(2, 2));
				w = (rot.get(2, 0) - rot.get(0, 2)) / s;
				x = (rot.get(1, 0) + rot.get(0, 1)) / s;
				y = 0.25f * s;
				z = (rot.get(2, 1) + rot.get(1, 2)) / s;
			} else
			{
				float s = 2.0f * (float) Math.sqrt(1.0f + rot.get(2, 2) - rot.get(0, 0) - rot.get(1, 1));
				w = (rot.get(0, 1) - rot.get(1, 0)) / s;
				x = (rot.get(2, 0) + rot.get(0, 2)) / s;
				y = (rot.get(1, 2) + rot.get(2, 1)) / s;
				z = 0.25f * s;
			}
		}

		normalize();
		return this;
	}

	public Matrix4f toRotationMatrix()
	{
		Vector3f forward = new Vector3f(2.0f * (x * z - w * y), 2.0f * (y * z + w * x), 1.0f - 2.0f * (x * x + y * y));
		Vector3f up = new Vector3f(2.0f * (x * y + w * z), 1.0f - 2.0f * (x * x + z * z), 2.0f * (y * z - w * x));
		Vector3f right = new Vector3f(1.0f - 2.0f * (y * y + z * z), 2.0f * (x * y - w * z), 2.0f * (x * z + w * y));

		return Matrix4f.rotation(forward, up, right);
	}

	public Quaternion4f nLerp(Quaternion4f dest, float lerpFactor, boolean shortest)
	{
		Quaternion4f correctedDest = dest;

		if (shortest && dot(dest) < 0)
			correctedDest = new Quaternion4f(-dest.x(), -dest.y(), -dest.z(), -dest.w());

		return (Quaternion4f) set(correctedDest.subtract(this).multiply(lerpFactor).add(this).normalize());
	}

	public Quaternion4f sLerp(Quaternion4f dest, float lerpFactor, boolean shortest)
	{
		final float EPSILON = 1e3f;

		float cos = dot(dest);
		Quaternion4f correctedDest = dest;

		if (shortest && cos < 0)
		{
			cos = -cos;
			correctedDest = new Quaternion4f(-dest.x(), -dest.y(), -dest.z(), -dest.y());
		}

		if (Math.abs(cos) >= 1 - EPSILON)
			return nLerp(correctedDest, lerpFactor, false);

		float sin = (float) Math.sqrt(1.0f - cos * cos);
		float angle = (float) Math.atan2(sin, cos);
		float invSin = 1.0f / sin;

		float srcFactor = (float) Math.sin((1.0f - lerpFactor) * angle) * invSin;
		float destFactor = (float) Math.sin((lerpFactor) * angle) * invSin;

		System.out.println("slerp");
		return (Quaternion4f) multiply(srcFactor).add(correctedDest.multiply(destFactor));
	}

	public float dot(Vector4f other)
	{
		return new Vector4f(this).multiply(other).sum();
//		return clone().multiply(other).sum();
	}

	@Override
	public Vector4f multiply(Vector4f other)
	{
		return multiply(other.x(), other.y(), other.z(), other.w());
	}

	@Override
	public Vector4f multiply(float rx, float ry, float rz, float rw)
	{
		float nw = w * rw - x * rx - y * ry - z * rz;
		float nx = x * rw + w * rx + y * rz - z * ry;
		float ny = y * rw + w * ry + z * rx - x * rz;
		float nz = z * rw + w * rz + x * ry - y * rx;

		return set(nx, ny, nz, nw);
	}

	public Vector4f multiply(Vector3f other)
	{
		return multiply(other.x(), other.y(), other.z());
	}

	public Vector4f multiply(float rx, float ry, float rz)
	{
		float nw = -x * rx - y * ry - z * rz;
		float nx = w * rx + y * rz - z * ry;
		float ny = w * ry + z * rx - x * rz;
		float nz = w * rz + x * ry - y * rx;

		return set(nx, ny, nz, nw);
	}

	@Override
	public Vector4f multiply(float v)
	{
		return set(x * v, y * v, z * v, w * v);
	}

	public Quaternion4f conjugate()
	{
		return new Quaternion4f(-x, -y, -z, w);
	}

	public Vector3f getForward()
	{
		return new Vector3f(0, 0, 1).rotate(this);
	}

	public Vector3f getUp()
	{
		return new Vector3f(0, 1, 0).rotate(this);
	}

	public Vector3f getRight()
	{
		return new Vector3f(1, 0, 0).rotate(this);
	}

	public Vector3f getBack()
	{
		return new Vector3f(0, 0, -1).rotate(this);
	}

	public Vector3f getDown()
	{
		return new Vector3f(0, -1, 0).rotate(this);
	}

	public Vector3f getLeft()
	{
		return new Vector3f(-1, 0, 0).rotate(this);
	}

	public Quaternion4f set(Vector3f axis, float angle)
	{
		axis.normalize();
		float sinHalf = (float) Math.sin(Math.toRadians(angle / 2));
		float cosHalf = (float) Math.cos(Math.toRadians(angle / 2));

		set(axis.x() * sinHalf, axis.y() * sinHalf, axis.z() * sinHalf, cosHalf);
		return this;
	}

	public Quaternion4f set(Vector3f forward)
	{
		Vector3f globalForward = new Vector3f(0, 0, 1);
		float dot = globalForward.dot(forward);

		if (Math.abs(dot - (-1.0f)) < 0.000001f)
		{
			return (Quaternion4f) set(0, 1, 0, (float)Math.PI);
		}
		if (Math.abs(dot - (1.0f)) < 0.000001f)
		{
			return (Quaternion4f) set(0, 0, 0, 1);
		}
//
		float rotAngle = (float)Math.acos(dot);
		Vector3f rotAxis = globalForward.cross(forward).normalize();
		return set(rotAxis, (float) Math.toDegrees(rotAngle));
	}

	@Override
	public Vector4f clone()
	{
		return new Quaternion4f(this);
	}

	@Override
	public String toString()
	{
		return "Quaternion4f{" +
				"x=" + x +
				", y=" + y +
				", z=" + z +
				", w=" + w +
				'}';
	}
}
