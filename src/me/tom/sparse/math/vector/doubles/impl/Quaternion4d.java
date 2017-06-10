package me.tom.sparse.math.vector.doubles.impl;

import me.tom.sparse.math.Matrix4d;
import me.tom.sparse.math.Matrix4f;
import me.tom.sparse.math.vector.doubles.Vector3d;
import me.tom.sparse.math.vector.doubles.Vector4d;

public class Quaternion4d extends Vector4d
{
	public Quaternion4d()
	{
		set(0, 0, 0, 1);
	}
	
	public Quaternion4d(Vector4d other)
	{
		set(other);
	}
	
	public Quaternion4d(Vector3d axis, double angle)
	{
		set(axis, angle);
	}
	
	public Quaternion4d(double x, double y, double z, double w)
	{
		set(x, y, z, w);
	}
	
	public Quaternion4d(double value)
	{
		set(value);
	}
	
	public Quaternion4d(double x, double y, double z)
	{
		this(new Vector3d(0, 0, 1), z);
		multiply(new Quaternion4d(new Vector3d(0, 1, 0), y));
		multiply(new Quaternion4d(new Vector3d(1, 0, 0), x));
	}
	
	public Quaternion4d(Matrix4f rotation)
	{
		set(rotation);
	}

//	public Quaternion(double x, double y, double z, double w)
//	{
//		this.x = x;
//		this.y = y;
//		this.z = z;
//		this.w = w;
//	}

//	public Quaternion(Vector3d axis, double angle)
//	{
//		set(axis, angle);
//	}

	public Quaternion4d set(Matrix4f rot)
	{
		double trace = rot.get(0, 0) + rot.get(1, 1) + rot.get(2, 2);

		if (trace > 0)
		{
			double s = 0.5 / Math.sqrt(trace + 1.0);
			w = 0.25 / s;
			x = (rot.get(1, 2) - rot.get(2, 1)) * s;
			y = (rot.get(2, 0) - rot.get(0, 2)) * s;
			z = (rot.get(0, 1) - rot.get(1, 0)) * s;
		} else
		{
			if (rot.get(0, 0) > rot.get(1, 1) && rot.get(0, 0) > rot.get(2, 2))
			{
				double s = 2.0 * Math.sqrt(1.0 + rot.get(0, 0) - rot.get(1, 1) - rot.get(2, 2));
				w = (rot.get(1, 2) - rot.get(2, 1)) / s;
				x = 0.25 * s;
				y = (rot.get(1, 0) + rot.get(0, 1)) / s;
				z = (rot.get(2, 0) + rot.get(0, 2)) / s;
			} else if (rot.get(1, 1) > rot.get(2, 2))
			{
				double s = 2.0 * Math.sqrt(1.0 + rot.get(1, 1) - rot.get(0, 0) - rot.get(2, 2));
				w = (rot.get(2, 0) - rot.get(0, 2)) / s;
				x = (rot.get(1, 0) + rot.get(0, 1)) / s;
				y = 0.25 * s;
				z = (rot.get(2, 1) + rot.get(1, 2)) / s;
			} else
			{
				double s = 2.0 * Math.sqrt(1.0 + rot.get(2, 2) - rot.get(0, 0) - rot.get(1, 1));
				w = (rot.get(0, 1) - rot.get(1, 0)) / s;
				x = (rot.get(2, 0) + rot.get(0, 2)) / s;
				y = (rot.get(1, 2) + rot.get(2, 1)) / s;
				z = 0.25 * s;
			}
		}

		normalize();
		return this;
	}

	public Matrix4d toRotationMatrix()
	{
		Vector3d forward = new Vector3d(2.0 * (x * z - w * y), 2.0 * (y * z + w * x), 1.0 - 2.0 * (x * x + y * y));
		Vector3d up = new Vector3d(2.0 * (x * y + w * z), 1.0 - 2.0 * (x * x + z * z), 2.0 * (y * z - w * x));
		Vector3d right = new Vector3d(1.0 - 2.0 * (y * y + z * z), 2.0 * (x * y - w * z), 2.0 * (x * z + w * y));

		return Matrix4d.rotation(forward, up, right);
	}

	public Quaternion4d nLerp(Quaternion4d dest, double lerpFactor, boolean shortest)
	{
		Quaternion4d correctedDest = dest;

		if (shortest && dot(dest) < 0)
			correctedDest = new Quaternion4d(-dest.x(), -dest.y(), -dest.z(), -dest.w());

		return (Quaternion4d) set(correctedDest.subtract(this).multiply(lerpFactor).add(this).normalize());
	}

	public Quaternion4d sLerp(Quaternion4d dest, double lerpFactor, boolean shortest)
	{
		final double EPSILON = 1e3f;

		double cos = dot(dest);
		Quaternion4d correctedDest = dest;

		if (shortest && cos < 0)
		{
			cos = -cos;
			correctedDest = new Quaternion4d(-dest.x(), -dest.y(), -dest.z(), -dest.y());
		}

		if (Math.abs(cos) >= 1 - EPSILON)
			return nLerp(correctedDest, lerpFactor, false);

		double sin = Math.sqrt(1.0 - cos * cos);
		double angle = Math.atan2(sin, cos);
		double invSin = 1.0 / sin;

		double srcFactor = Math.sin((1.0 - lerpFactor) * angle) * invSin;
		double destFactor = Math.sin((lerpFactor) * angle) * invSin;

		System.out.println("slerp");
		return (Quaternion4d) multiply(srcFactor).add(correctedDest.multiply(destFactor));
	}

	public double dot(Vector4d other)
	{
		return new Vector4d(this).multiply(other).sum();
//		return clone().multiply(other).sum();
	}

	@Override
	public Vector4d multiply(Vector4d other)
	{
		return multiply(other.x(), other.y(), other.z(), other.w());
	}

	@Override
	public Vector4d multiply(double rx, double ry, double rz, double rw)
	{
		double nw = w * rw - x * rx - y * ry - z * rz;
		double nx = x * rw + w * rx + y * rz - z * ry;
		double ny = y * rw + w * ry + z * rx - x * rz;
		double nz = z * rw + w * rz + x * ry - y * rx;

		return set(nx, ny, nz, nw);
	}

	public Vector4d multiply(Vector3d other)
	{
		return multiply(other.x(), other.y(), other.z());
	}

	public Vector4d multiply(double rx, double ry, double rz)
	{
		double nw = -x * rx - y * ry - z * rz;
		double nx = w * rx + y * rz - z * ry;
		double ny = w * ry + z * rx - x * rz;
		double nz = w * rz + x * ry - y * rx;

		return set(nx, ny, nz, nw);
	}

	@Override
	public Vector4d multiply(double v)
	{
		return set(x * v, y * v, z * v, w * v);
	}

	public Quaternion4d conjugate()
	{
		return new Quaternion4d(-x, -y, -z, w);
	}

	public Vector3d getForward()
	{
		return new Vector3d(0, 0, 1).rotate(this);
	}

	public Vector3d getUp()
	{
		return new Vector3d(0, 1, 0).rotate(this);
	}

	public Vector3d getRight()
	{
		return new Vector3d(1, 0, 0).rotate(this);
	}

	public Vector3d getBack()
	{
		return new Vector3d(0, 0, -1).rotate(this);
	}

	public Vector3d getDown()
	{
		return new Vector3d(0, -1, 0).rotate(this);
	}

	public Vector3d getLeft()
	{
		return new Vector3d(-1, 0, 0).rotate(this);
	}

	public Quaternion4d set(Vector3d axis, double angle)
	{
		axis.normalize();
		double sinHalf = Math.sin(Math.toRadians(angle / 2));
		double cosHalf = Math.cos(Math.toRadians(angle / 2));

		set(axis.x() * sinHalf, axis.y() * sinHalf, axis.z() * sinHalf, cosHalf);
		return this;
	}

	public Quaternion4d set(Vector3d forward)
	{
		Vector3d globalForward = new Vector3d(0, 0, 1);
		double dot = globalForward.dot(forward);

		if (Math.abs(dot - (-1.0)) < 0.000001)
		{
			return (Quaternion4d) set(0, 1, 0, Math.PI);
		}
		if (Math.abs(dot - (1.0)) < 0.000001)
		{
			return (Quaternion4d) set(0, 0, 0, 1);
		}
//
		double rotAngle = Math.acos(dot);
		Vector3d rotAxis = globalForward.cross(forward).normalize();
		return set(rotAxis, Math.toDegrees(rotAngle));
	}

	@Override
	public Vector4d clone()
	{
		return new Quaternion4d(this);
	}

	@Override
	public String toString()
	{
		return "Quaternion4d{" +
				"x=" + x +
				", y=" + y +
				", z=" + z +
				", w=" + w +
				'}';
	}
}
