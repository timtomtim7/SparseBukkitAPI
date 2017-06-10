package me.tom.sparse.math.vector.doubles;

import me.tom.sparse.math.Matrix4d;
import me.tom.sparse.math.vector.doubles.impl.Quaternion4d;
import me.tom.sparse.math.vector.floats.Vector3f;
import me.tom.sparse.math.vector.integers.Vector3i;

import java.util.function.BiFunction;
import java.util.function.Function;

@SuppressWarnings({"SuspiciousNameCombination", "unused"})
public class Vector3d
{
	protected double x, y, z;
	
	public Vector3d()
	{
	
	}
	
	public Vector3d(double x, double y, double z)
	{
		set(x, y, z);
	}
	
	public Vector3d(Vector3d other)
	{
		set(other);
	}
	
	public Vector3d(double x, Vector2d other)
	{
		set(x, other);
	}
	
	public Vector3d(Vector2d other, double z)
	{
		set(other, z);
	}
	
	public Vector3d(double value)
	{
		set(value);
	}

	public Vector3d set(double x, double y, double z)
	{
		return setX(x).setY(y).setZ(z);
	}

	public Vector3d set(Vector3d other)
	{
		return set(other.x, other.y, other.z);
	}

	public Vector3d set(double x, Vector2d other)
	{
		return set(x, other.x(), other.y());
	}

	public Vector3d set(Vector2d other, double z)
	{
		return set(other.x(), other.y(), z);
	}

	public Vector3d set(double value)
	{
		return set(value, value, value);
	}

	public Vector3d setX(double x)
	{
		this.x = x;
		return this;
	}

	public Vector3d setY(double y)
	{
		this.y = y;
		return this;
	}

	public Vector3d setZ(double z)
	{
		this.z = z;
		return this;
	}

	public double x()
	{
		return x;
	}

	public double y()
	{
		return y;
	}

	public double z()
	{
		return z;
	}

	public Vector3d op(Function<Double, Double> function)
	{
		setX(function.apply(x()));
		setY(function.apply(y()));
		setZ(function.apply(z()));
		return this;
	}

	public Vector3d pairOp(Vector3d other, BiFunction<Double, Double, Double> function)
	{
		setX(function.apply(x(), other.x()));
		setY(function.apply(y(), other.y()));
		setZ(function.apply(z(), other.z()));
		return this;
	}

	public Vector3d abs()
	{
		return set(Math.abs(x()), Math.abs(y()), Math.abs(z()));
	}

	public Vector3d rotate(Vector3d axis, double angle)
	{
		return rotate(new Quaternion4d(axis, angle));
//		double sinHalf = Math.sin(Math.toRadians(angle / 2));
//		double cosHalf = Math.cos(Math.toRadians(angle / 2));
//
//		Quaternion rotation = new Quaternion(axis.x() * sinHalf, axis.y() * sinHalf, axis.z() * sinHalf, cosHalf);
//		Quaternion conjugate = rotation.conjugate();
//
//		rotation.multiply(this).multiply(conjugate);
//
//		return set(rotation.x(), rotation.y(), rotation.z());
	}

	public Vector3d rotate(Quaternion4d rotation)
	{
		Quaternion4d conjugate = rotation.conjugate();
		Quaternion4d w = (Quaternion4d) ((Quaternion4d)rotation.clone()).multiply(this).multiply(conjugate);
//		set((Vector3d) w.swizzle("xyz"));

		return w.xyz();

//		return (Vector3d) w.swizzle("xyz");
//		rotation = (Quaternion) rotation.clone();
//		Quaternion conjugate = rotation.conjugate();
//		rotation = (Quaternion) rotation.multiply(this).multiply(conjugate);
//
//		return set((Vector3d) rotation.swizzle("xyz"));
//		return (Vector3d) rotation.swizzle("xyz");
	}

	public Vector4d toVector4d(double w)
	{
		return new Vector4d(x, y, z, w);
	}

	public Matrix4d toRotationMatrix()
	{
		return Matrix4d.rotation(x(), y(), z());
	}

	public Matrix4d toScaleMatrix()
	{
		return Matrix4d.scale(x(), y(), z());
	}

	public Matrix4d toTranslationMatrix()
	{
		return Matrix4d.translation(x(), y(), z());
	}
	
	public double min()
	{
		return Math.min(x(), Math.min(y(), z()));
	}

	public Vector3d min(Vector3d other)
	{
		return min(other.x(), other.y(), other.z());
	}

	public Vector3d min(double x, double y, double z)
	{
		return set(Math.min(x(), x), Math.min(y(), y), Math.min(z(), z));
	}

	public double max()
	{
		return Math.max(x(), Math.max(y(), z()));
	}

	public Vector3d max(Vector3d other)
	{
		return max(other.x(), other.y(), other.z());
	}

	public Vector3d max(double x, double y, double z)
	{
		return set(Math.max(x(), x), Math.max(y(), y), Math.max(z(), z));
	}
	
	public boolean withinMinMax(double minX, double minY, double minZ, double maxX, double maxY, double maxZ)
	{
		double x = x();
		double y = y();
		double z = z();
		return x >= minX && x <= maxX && y >= minY && y <= maxY && z >= minZ && z <= maxZ;
	}

	public boolean withinMinMax(Vector3d min, Vector3d max)
	{
		return withinMinMax(min.x(), min.y(), min.z(), max.x(), max.y(), max.z());
	}

	public boolean within(Vector3d a, Vector3d b)
	{
		return withinMinMax(a.clone().min(b), a.clone().max(b));
	}
	
	public double sum()
	{
		return x() + y() + z();
	}

	public double dot(Vector3d other)
	{
		return clone().multiply(other).sum();
	}

	public double dot(double x, double y, double z)
	{
		return clone().multiply(x, y, z).sum();
	}
	
	public double lengthSquared()
	{
		return dot(this);
	}

	public double length()
	{
		return Math.sqrt(lengthSquared());
	}
	
	public double distance(Vector3d other)
	{
		return clone().subtract(other).length();
	}
	
	public double distance(double x, double y, double z)
	{
		return clone().subtract(x, y, z).length();
	}
	
	public double distanceSquared(Vector3d other)
	{
		return clone().subtract(other).lengthSquared();
	}
	
	public Vector3d normalize()
	{
		double length = length();
		return length == 0 ? this : divide(length);
	}
	
	public Vector3d lerp(Vector3d target, double lerpFactor)
	{
		return target.clone().subtract(this).multiply(lerpFactor).add(this);
	}

	public Vector3d cross(Vector3d other)
	{
		return cross(other.x(), other.y(), other.z());
	}
	
	public Vector3d cross(double x, double y, double z)
	{
		double ax = x();
		double ay = y();
		double az = z();
		double bx = x;
		double by = y;
		double bz = z;
		
		return new Vector3d(ay * bz - az * by, az * bx - ax * bz, ax * by - ay * bx);
	}
	
	public Vector3d pow(double pow)
	{
		return set(Math.pow(x(), pow), Math.pow(y(), pow), Math.pow(z(), pow));
	}
	
	public Vector3d floor()
	{
		return set(Math.floor(x()), Math.floor(y()), Math.floor(z()));
	}
	
	public Vector3d ceil()
	{
		return set(Math.ceil(x()), Math.ceil(y()), Math.ceil(z()));
	}
	
	public Vector3d round()
	{
		return set(Math.round(x()), Math.round(y()), Math.round(z()));
	}
	
	public Vector3d add(double v)
	{
		return add(v, v, v);
	}

	public Vector3d add(double x, double y, double z)
	{
		return set(x() + x, y() + y, z() + z);
	}

	public Vector3d add(Vector3d other)
	{
		return add(other.x(), other.y(), other.z());
	}
	
	public Vector3d subtract(double v)
	{
		return subtract(v, v, v);
	}

	public Vector3d subtract(double x, double y, double z)
	{
		return add(-x, -y, -z);
	}

	public Vector3d subtract(Vector3d other)
	{
		return subtract(other.x(), other.y(), other.z());
	}
	
	public Vector3d multiply(double v)
	{
		return multiply(v, v, v);
	}

	public Vector3d multiply(double x, double y, double z)
	{
		return set(x() * x, y() * y, z() * z);
	}

	public Vector3d multiply(Vector3d other)
	{
		return multiply(other.x(), other.y(), other.z());
	}

//	public Vector3d multiply(Matrix4f mat)
//	{
//		return null;
//	}
	
	public Vector3d divide(double v)
	{
		return divide(v, v, v);
	}

	public Vector3d divide(double x, double y, double z)
	{
		return set(x() / x, y() / y, z() / z);
	}
	
	public Vector3d divide(Vector3d other)
	{
		return divide(other.x(), other.y(), other.z());
	}

	@Override
	public Vector3d clone()
	{
		return new Vector3d(this);
	}
	
	public boolean equals(Object o)
	{
		if(this == o) return true;
		if(!(o instanceof Vector3d)) return false;
		
		Vector3d vector3d = (Vector3d) o;
		
		if(Double.compare(vector3d.x, x) != 0) return false;
		if(Double.compare(vector3d.y, y) != 0) return false;
		return Double.compare(vector3d.z, z) == 0;
	}
	
	public int hashCode()
	{
		int result;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(z);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	
	@Override
	public String toString()
	{
		return "Vector3d{" +
				"x=" + x +
				", y=" + y +
				", z=" + z +
				'}';
	}
	
	public Vector3f toVector3f()
	{
		return new Vector3f((float)x, (float)y, (float)z);
	}
	
	public Vector3i toVector3i()
	{
		return new Vector3i((int)x, (int)y, (int)z);
	}

	/*
	--------------------------------------------------------------------------------------------
								Generated Vector Swizzling code
	--------------------------------------------------------------------------------------------
	 */

	public Vector2d xx()   { return new Vector2d(x, x); }
	public Vector2d xy()   { return new Vector2d(x, y); }
	public Vector2d xz()   { return new Vector2d(x, z); }
	public Vector2d yx()   { return new Vector2d(y, x); }
	public Vector2d yy()   { return new Vector2d(y, y); }
	public Vector2d yz()   { return new Vector2d(y, z); }
	public Vector2d zx()   { return new Vector2d(z, x); }
	public Vector2d zy()   { return new Vector2d(z, y); }
	public Vector2d zz()   { return new Vector2d(z, z); }
	public Vector3d xxx()  { return new Vector3d(x, x, x); }
	public Vector3d xxy()  { return new Vector3d(x, x, y); }
	public Vector3d xxz()  { return new Vector3d(x, x, z); }
	public Vector3d xyx()  { return new Vector3d(x, y, x); }
	public Vector3d xyy()  { return new Vector3d(x, y, y); }
	public Vector3d xyz()  { return new Vector3d(x, y, z); }
	public Vector3d xzx()  { return new Vector3d(x, z, x); }
	public Vector3d xzy()  { return new Vector3d(x, z, y); }
	public Vector3d xzz()  { return new Vector3d(x, z, z); }
	public Vector3d yxx()  { return new Vector3d(y, x, x); }
	public Vector3d yxy()  { return new Vector3d(y, x, y); }
	public Vector3d yxz()  { return new Vector3d(y, x, z); }
	public Vector3d yyx()  { return new Vector3d(y, y, x); }
	public Vector3d yyy()  { return new Vector3d(y, y, y); }
	public Vector3d yyz()  { return new Vector3d(y, y, z); }
	public Vector3d yzx()  { return new Vector3d(y, z, x); }
	public Vector3d yzy()  { return new Vector3d(y, z, y); }
	public Vector3d yzz()  { return new Vector3d(y, z, z); }
	public Vector3d zxx()  { return new Vector3d(z, x, x); }
	public Vector3d zxy()  { return new Vector3d(z, x, y); }
	public Vector3d zxz()  { return new Vector3d(z, x, z); }
	public Vector3d zyx()  { return new Vector3d(z, y, x); }
	public Vector3d zyy()  { return new Vector3d(z, y, y); }
	public Vector3d zyz()  { return new Vector3d(z, y, z); }
	public Vector3d zzx()  { return new Vector3d(z, z, x); }
	public Vector3d zzy()  { return new Vector3d(z, z, y); }
	public Vector3d zzz()  { return new Vector3d(z, z, z); }
	public Vector4d xxxx() { return new Vector4d(x, x, x, x); }
	public Vector4d xxxy() { return new Vector4d(x, x, x, y); }
	public Vector4d xxxz() { return new Vector4d(x, x, x, z); }
	public Vector4d xxyx() { return new Vector4d(x, x, y, x); }
	public Vector4d xxyy() { return new Vector4d(x, x, y, y); }
	public Vector4d xxyz() { return new Vector4d(x, x, y, z); }
	public Vector4d xxzx() { return new Vector4d(x, x, z, x); }
	public Vector4d xxzy() { return new Vector4d(x, x, z, y); }
	public Vector4d xxzz() { return new Vector4d(x, x, z, z); }
	public Vector4d xyxx() { return new Vector4d(x, y, x, x); }
	public Vector4d xyxy() { return new Vector4d(x, y, x, y); }
	public Vector4d xyxz() { return new Vector4d(x, y, x, z); }
	public Vector4d xyyx() { return new Vector4d(x, y, y, x); }
	public Vector4d xyyy() { return new Vector4d(x, y, y, y); }
	public Vector4d xyyz() { return new Vector4d(x, y, y, z); }
	public Vector4d xyzx() { return new Vector4d(x, y, z, x); }
	public Vector4d xyzy() { return new Vector4d(x, y, z, y); }
	public Vector4d xyzz() { return new Vector4d(x, y, z, z); }
	public Vector4d xzxx() { return new Vector4d(x, z, x, x); }
	public Vector4d xzxy() { return new Vector4d(x, z, x, y); }
	public Vector4d xzxz() { return new Vector4d(x, z, x, z); }
	public Vector4d xzyx() { return new Vector4d(x, z, y, x); }
	public Vector4d xzyy() { return new Vector4d(x, z, y, y); }
	public Vector4d xzyz() { return new Vector4d(x, z, y, z); }
	public Vector4d xzzx() { return new Vector4d(x, z, z, x); }
	public Vector4d xzzy() { return new Vector4d(x, z, z, y); }
	public Vector4d xzzz() { return new Vector4d(x, z, z, z); }
	public Vector4d yxxx() { return new Vector4d(y, x, x, x); }
	public Vector4d yxxy() { return new Vector4d(y, x, x, y); }
	public Vector4d yxxz() { return new Vector4d(y, x, x, z); }
	public Vector4d yxyx() { return new Vector4d(y, x, y, x); }
	public Vector4d yxyy() { return new Vector4d(y, x, y, y); }
	public Vector4d yxyz() { return new Vector4d(y, x, y, z); }
	public Vector4d yxzx() { return new Vector4d(y, x, z, x); }
	public Vector4d yxzy() { return new Vector4d(y, x, z, y); }
	public Vector4d yxzz() { return new Vector4d(y, x, z, z); }
	public Vector4d yyxx() { return new Vector4d(y, y, x, x); }
	public Vector4d yyxy() { return new Vector4d(y, y, x, y); }
	public Vector4d yyxz() { return new Vector4d(y, y, x, z); }
	public Vector4d yyyx() { return new Vector4d(y, y, y, x); }
	public Vector4d yyyy() { return new Vector4d(y, y, y, y); }
	public Vector4d yyyz() { return new Vector4d(y, y, y, z); }
	public Vector4d yyzx() { return new Vector4d(y, y, z, x); }
	public Vector4d yyzy() { return new Vector4d(y, y, z, y); }
	public Vector4d yyzz() { return new Vector4d(y, y, z, z); }
	public Vector4d yzxx() { return new Vector4d(y, z, x, x); }
	public Vector4d yzxy() { return new Vector4d(y, z, x, y); }
	public Vector4d yzxz() { return new Vector4d(y, z, x, z); }
	public Vector4d yzyx() { return new Vector4d(y, z, y, x); }
	public Vector4d yzyy() { return new Vector4d(y, z, y, y); }
	public Vector4d yzyz() { return new Vector4d(y, z, y, z); }
	public Vector4d yzzx() { return new Vector4d(y, z, z, x); }
	public Vector4d yzzy() { return new Vector4d(y, z, z, y); }
	public Vector4d yzzz() { return new Vector4d(y, z, z, z); }
	public Vector4d zxxx() { return new Vector4d(z, x, x, x); }
	public Vector4d zxxy() { return new Vector4d(z, x, x, y); }
	public Vector4d zxxz() { return new Vector4d(z, x, x, z); }
	public Vector4d zxyx() { return new Vector4d(z, x, y, x); }
	public Vector4d zxyy() { return new Vector4d(z, x, y, y); }
	public Vector4d zxyz() { return new Vector4d(z, x, y, z); }
	public Vector4d zxzx() { return new Vector4d(z, x, z, x); }
	public Vector4d zxzy() { return new Vector4d(z, x, z, y); }
	public Vector4d zxzz() { return new Vector4d(z, x, z, z); }
	public Vector4d zyxx() { return new Vector4d(z, y, x, x); }
	public Vector4d zyxy() { return new Vector4d(z, y, x, y); }
	public Vector4d zyxz() { return new Vector4d(z, y, x, z); }
	public Vector4d zyyx() { return new Vector4d(z, y, y, x); }
	public Vector4d zyyy() { return new Vector4d(z, y, y, y); }
	public Vector4d zyyz() { return new Vector4d(z, y, y, z); }
	public Vector4d zyzx() { return new Vector4d(z, y, z, x); }
	public Vector4d zyzy() { return new Vector4d(z, y, z, y); }
	public Vector4d zyzz() { return new Vector4d(z, y, z, z); }
	public Vector4d zzxx() { return new Vector4d(z, z, x, x); }
	public Vector4d zzxy() { return new Vector4d(z, z, x, y); }
	public Vector4d zzxz() { return new Vector4d(z, z, x, z); }
	public Vector4d zzyx() { return new Vector4d(z, z, y, x); }
	public Vector4d zzyy() { return new Vector4d(z, z, y, y); }
	public Vector4d zzyz() { return new Vector4d(z, z, y, z); }
	public Vector4d zzzx() { return new Vector4d(z, z, z, x); }
	public Vector4d zzzy() { return new Vector4d(z, z, z, y); }
	public Vector4d zzzz() { return new Vector4d(z, z, z, z); }
}
