package me.tom.sparse.old_math.vector.vec4.impl;

import me.tom.sparse.old_math.Matrix4f;
import me.tom.sparse.old_math.vector.vec3.Vector3f;
import me.tom.sparse.old_math.vector.vec4.Vector4f;

public class Quaternion implements Vector4f
{
	public static Quaternion euler(float x, float y, float z)
	{
		return (Quaternion)
				new Quaternion(Vector3f.create(0, 0, 1), z)
						.multiply(new Quaternion(Vector3f.create(0, 1, 0), y))
						.multiply(new Quaternion(Vector3f.create(1, 0, 0), x));
	}

	public static Quaternion lookAt(Vector3f from, Vector3f to, Vector3f up)
	{
		return new Quaternion(Matrix4f.rotation(to.clone().subtract(from).normalize(), up));
	}

//	public static Quaternion lookAtStatic(Vector3f from, Vector3f to)
//	{
//		return new Quaternion().lookAt(from, to);
//	}
//
//	public Quaternion lookAt(Vector3f from, Vector3f to)
//	{
//		Vector3f forward = to.clone().subtract(from);
////		Vector3f up = getUp();
//		Vector3f currentForward = Vector3f.create(0, 0, 1);
//		float dot = forward.dot(currentForward);
//
//		if(Math.abs(dot + 1) < 0.000001)
//			return (Quaternion) set(0, 1, 0, (float) Math.PI);
//		if(Math.abs(dot - 1) < 0.000001)
//			return (Quaternion) set(0, 0, 0, 1);
//
//		return set(forward.cross(currentForward), (float) Math.acos(dot));
//	}

	private float x, y, z, w;

	public Quaternion()
	{
		this(0, 0, 0, 1);
	}

	public Quaternion(float x, float y, float z, float w)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	public Quaternion(Vector3f axis, float angle)
	{
		set(axis, angle);
	}

	public Quaternion(Matrix4f rot)
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
	}

	public Matrix4f toRotationMatrix()
	{
		Vector3f forward = Vector3f.create(2.0f * (x * z - w * y), 2.0f * (y * z + w * x), 1.0f - 2.0f * (x * x + y * y));
		Vector3f up = Vector3f.create(2.0f * (x * y + w * z), 1.0f - 2.0f * (x * x + z * z), 2.0f * (y * z - w * x));
		Vector3f right = Vector3f.create(1.0f - 2.0f * (y * y + z * z), 2.0f * (x * y - w * z), 2.0f * (x * z + w * y));
		
		return Matrix4f.rotation(forward, up, right);
	}

	public Quaternion nLerp(Quaternion dest, float lerpFactor, boolean shortest)
	{
		Quaternion correctedDest = dest;

		if(shortest && dot(dest) < 0)
			correctedDest = new Quaternion(-dest.getX(), -dest.getY(), -dest.getZ(), -dest.getW());

		return (Quaternion) set(correctedDest.subtract(this).multiply(lerpFactor).add(this).normalize());
	}

	public Quaternion sLerp(Quaternion dest, float lerpFactor, boolean shortest)
	{
		final float EPSILON = 1e3f;

		float cos = dot(dest);
		Quaternion correctedDest = dest;

		if(shortest && cos < 0)
		{
			cos = -cos;
			correctedDest = new Quaternion(-dest.getX(), -dest.getY(), -dest.getZ(), -dest.getW());
		}

		if(Math.abs(cos) >= 1 - EPSILON)
			return nLerp(correctedDest, lerpFactor, false);

		float sin = (float)Math.sqrt(1.0f - cos * cos);
		float angle = (float)Math.atan2(sin, cos);
		float invSin =  1.0f/sin;

		float srcFactor = (float)Math.sin((1.0f - lerpFactor) * angle) * invSin;
		float destFactor = (float)Math.sin((lerpFactor) * angle) * invSin;

		System.out.println("slerp");
		return (Quaternion) multiply(srcFactor).add(correctedDest.multiply(destFactor));
	}

	public float dot(Vector4f other)
	{
		return Vector4f.create(0, 0, 0, 0).set(this).multiply(other).sum();
//		return clone().multiply(other).sum();
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
		return multiply(other.getX(), other.getY(), other.getZ());
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

	public Quaternion conjugate()
	{
		return new Quaternion(-x, -y, -z, w);
	}

	public Vector3f getForward()
	{
		return Vector3f.create(0, 0, 1).rotate(this);
	}

	public Vector3f getUp()
	{
		return Vector3f.create(0, 1, 0).rotate(this);
	}

	public Vector3f getRight()
	{
		return Vector3f.create(1, 0, 0).rotate(this);
	}

	public Vector3f getBack()
	{
		return Vector3f.create(0, 0, -1).rotate(this);
	}

	public Vector3f getDown()
	{
		return Vector3f.create(0, -1, 0).rotate(this);
	}

	public Vector3f getLeft()
	{
		return Vector3f.create(-1, 0, 0).rotate(this);
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

	public Quaternion set(Vector3f axis, float angle)
	{
		axis.normalize();
		float sinHalf = (float) Math.sin(Math.toRadians(angle / 2));
		float cosHalf = (float) Math.cos(Math.toRadians(angle / 2));

		set(axis.getX() * sinHalf, axis.getY() * sinHalf, axis.getZ() * sinHalf, cosHalf);
		return this;
	}

	@Override
	public Vector4f clone()
	{
		return new Quaternion(x, y, z, w);
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Quaternion that = (Quaternion) o;

		if (Float.compare(that.x, x) != 0) return false;
		if (Float.compare(that.y, y) != 0) return false;
		if (Float.compare(that.z, z) != 0) return false;
		return Float.compare(that.w, w) == 0;

	}

	@Override
	public int hashCode()
	{
		int result = (x != +0.0f ? Float.floatToIntBits(x) : 0);
		result = 31 * result + (y != +0.0f ? Float.floatToIntBits(y) : 0);
		result = 31 * result + (z != +0.0f ? Float.floatToIntBits(z) : 0);
		result = 31 * result + (w != +0.0f ? Float.floatToIntBits(w) : 0);
		return result;
	}
}
