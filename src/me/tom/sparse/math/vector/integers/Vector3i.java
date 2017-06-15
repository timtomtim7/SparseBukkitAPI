package me.tom.sparse.math.vector.integers;

import me.tom.sparse.math.Matrix4f;
import me.tom.sparse.math.vector.doubles.Vector3d;
import me.tom.sparse.math.vector.floats.Vector3f;

import java.util.function.BiFunction;
import java.util.function.Function;

@SuppressWarnings({"SuspiciousNameCombination", "unused"})
public class Vector3i
{
	protected int x, y, z;
	
	public Vector3i(int x, int y, int z)
	{
		set(x, y, z);
	}
	
	public Vector3i(Vector3i other)
	{
		set(other);
	}
	
	public Vector3i(int x, Vector2i other)
	{
		set(x, other);
	}
	
	public Vector3i(Vector2i other, int z)
	{
		set(other, z);
	}
	
	public Vector3i(int value)
	{
		set(value);
	}
	
	public Vector3i()
	{
	
	}

	public Vector3i set(int x, int y, int z)
	{
		return setX(x).setY(y).setZ(z);
	}

	public Vector3i set(Vector3i other)
	{
		return set(other.x, other.y, other.z);
	}

	public Vector3i set(int x, Vector2i other)
	{
		return set(x, other.x(), other.y());
	}

	public Vector3i set(Vector2i other, int z)
	{
		return set(other.x(), other.y(), z);
	}

	public Vector3i set(int value)
	{
		return set(value, value, value);
	}

	public Vector3i setX(int x)
	{
		this.x = x;
		return this;
	}

	public Vector3i setY(int y)
	{
		this.y = y;
		return this;
	}

	public Vector3i setZ(int z)
	{
		this.z = z;
		return this;
	}

	public int x()
	{
		return x;
	}

	public int y()
	{
		return y;
	}

	public int z()
	{
		return z;
	}

	public Vector3i op(Function<Integer, Integer> function)
	{
		setX(function.apply(x()));
		setY(function.apply(y()));
		setZ(function.apply(z()));
		return this;
	}

	public Vector3i pairOp(Vector3i other, BiFunction<Integer, Integer, Integer> function)
	{
		setX(function.apply(x(), other.x()));
		setY(function.apply(y(), other.y()));
		setZ(function.apply(z(), other.z()));
		return this;
	}

	public Vector3i abs()
	{
		return set(Math.abs(x()), Math.abs(y()), Math.abs(z()));
	}

//	public Vector3i rotate(Vector3i axis, double angle)
//	{
//		return rotate(Quaternion.get(axis, angle));
////		float sinHalf = (float) Math.sin(Math.toRadians(angle / 2));
////		float cosHalf = (float) Math.cos(Math.toRadians(angle / 2));
////
////		Quaternion rotation = new Quaternion(axis.x() * sinHalf, axis.y() * sinHalf, axis.z() * sinHalf, cosHalf);
////		Quaternion conjugate = rotation.conjugate();
////
////		rotation.multiply(this).multiply(conjugate);
////
////		return set(rotation.x(), rotation.y(), rotation.z());
//	}
//
//	public Vector3i rotate(Quaternion rotation)
//	{
//		Quaternion conjugate = rotation.conjugate();
//		Quaternion w = (Quaternion) ((Quaternion)rotation.clone()).multiply(this).multiply(conjugate);
////		set((Vector3f) w.swizzle("xyz"));
//
//		return w.xyz();
//
////		return (Vector3f) w.swizzle("xyz");
////		rotation = (Quaternion) rotation.clone();
////		Quaternion conjugate = rotation.conjugate();
////		rotation = (Quaternion) rotation.multiply(this).multiply(conjugate);
////
////		return set((Vector3f) rotation.swizzle("xyz"));
////		return (Vector3f) rotation.swizzle("xyz");
//	}

	public Matrix4f toRotationMatrix()
	{
		return Matrix4f.rotation(x(), y(), z());
	}

	public Matrix4f toScaleMatrix()
	{
		return Matrix4f.scale(x(), y(), z());
	}

	public Matrix4f toTranslationMatrix()
	{
		return Matrix4f.translation(x(), y(), z());
	}
	
	public float min()
	{
		return Math.min(x(), Math.min(y(), z()));
	}

	public Vector3i min(Vector3i other)
	{
		return min(other.x(), other.y(), other.z());
	}

	public Vector3i min(int x, int y, int z)
	{
		return set(Math.min(x(), x), Math.min(y(), y), Math.min(z(), z));
	}

	public float max()
	{
		return Math.max(x(), Math.max(y(), z()));
	}

	public Vector3i max(Vector3i other)
	{
		return max(other.x(), other.y(), other.z());
	}

	public Vector3i max(int x, int y, int z)
	{
		return set(Math.max(x(), x), Math.max(y(), y), Math.max(z(), z));
	}
	
	public boolean withinMinMax(int minX, int minY, int minZ, int maxX, int maxY, int maxZ)
	{
		int x = x();
		int y = y();
		int z = z();
		return x >= minX && x <= maxX && y >= minY && y <= maxY && z >= minZ && z <= maxZ;
	}

	public boolean withinMinMax(Vector3i min, Vector3i max)
	{
		return withinMinMax(min.x(), min.y(), min.z(), max.x(), max.y(), max.z());
	}

	public boolean within(Vector3i a, Vector3i b)
	{
		return withinMinMax(a.clone().min(b), a.clone().max(b));
	}
	
	public int sum()
	{
		return x() + y() + z();
	}

	public double dot(Vector3i other)
	{
		return clone().multiply(other).sum();
	}

	public double dot(int x, int y, int z)
	{
		return clone().multiply(x, y, z).sum();
	}
	
	public double lengthSquared()
	{
		return dot(this);
	}

	public double length()
	{
		return (float) Math.sqrt(lengthSquared());
	}
	
	public double distance(Vector3i other)
	{
		return clone().subtract(other).length();
	}
	
	public double distance(int x, int y, int z)
	{
		return clone().subtract(x, y, z).length();
	}
	
	public double distanceSquared(Vector3i other)
	{
		return clone().subtract(other).lengthSquared();
	}
	
	public Vector3i cross(Vector3i other)
	{
		return cross(other.x(), other.y(), other.z());
	}
	
	public Vector3i cross(int x, int y, int z)
	{
		int ax = x();
		int ay = y();
		int az = z();
		int bx = x;
		int by = y;
		int bz = z;
		
		return new Vector3i(ay * bz - az * by, az * bx - ax * bz, ax * by - ay * bx);
	}
	
	public Vector3i pow(double pow)
	{
		return set((int) Math.pow(x(), pow), (int) Math.pow(y(), pow), (int) Math.pow(z(), pow));
	}
	
	public Vector3i add(int v)
	{
		return add(v, v, v);
	}

	public Vector3i add(int x, int y, int z)
	{
		return set(x() + x, y() + y, z() + z);
	}

	public Vector3i add(Vector3i other)
	{
		return add(other.x(), other.y(), other.z());
	}
	
	public Vector3i subtract(int v)
	{
		return subtract(v, v, v);
	}

	public Vector3i subtract(int x, int y, int z)
	{
		return add(-x, -y, -z);
	}

	public Vector3i subtract(Vector3i other)
	{
		return subtract(other.x(), other.y(), other.z());
	}
	
	public Vector3i multiply(int v)
	{
		return multiply(v, v, v);
	}

	public Vector3i multiply(int x, int y, int z)
	{
		return set(x() * x, y() * y, z() * z);
	}

	public Vector3i multiply(Vector3i other)
	{
		return multiply(other.x(), other.y(), other.z());
	}

//	public Vector3f multiply(Matrix4f mat)
//	{
//		return null;
//	}
	
	public Vector3i divide(int v)
	{
		return divide(v, v, v);
	}

	public Vector3i divide(int x, int y, int z)
	{
		return set(x() / x, y() / y, z() / z);
	}
	
	public Vector3i divide(Vector3i other)
	{
		return divide(other.x(), other.y(), other.z());
	}

	@Override
	public Vector3i clone()
	{
		return new Vector3i(this);
	}
	
	public boolean equals(Object o)
	{
		if(this == o) return true;
		if(!(o instanceof Vector3i)) return false;
		
		Vector3i vector3i = (Vector3i) o;
		
		if(x != vector3i.x) return false;
		if(y != vector3i.y) return false;
		return z == vector3i.z;
	}
	
	public int hashCode()
	{
		int result = x;
		result = 31 * result + y;
		result = 31 * result + z;
		return result;
	}
	
	public String toString()
	{
		return "Vector3i{" +
				"x=" + x +
				", y=" + y +
				", z=" + z +
				'}';
	}
	
	public Vector3f toVector3f()
	{
		return new Vector3f(x, y, z);
	}
	
	public Vector3d toVector3d()
	{
		return new Vector3d(x, y, z);
	}

	/*
	--------------------------------------------------------------------------------------------
								Generated Vector Swizzling code
	--------------------------------------------------------------------------------------------
	 */

	public Vector2i xx()   { return new Vector2i(x, x); }
	public Vector2i xy()   { return new Vector2i(x, y); }
	public Vector2i xz()   { return new Vector2i(x, z); }
	public Vector2i yx()   { return new Vector2i(y, x); }
	public Vector2i yy()   { return new Vector2i(y, y); }
	public Vector2i yz()   { return new Vector2i(y, z); }
	public Vector2i zx()   { return new Vector2i(z, x); }
	public Vector2i zy()   { return new Vector2i(z, y); }
	public Vector2i zz()   { return new Vector2i(z, z); }
	public Vector3i xxx()  { return new Vector3i(x, x, x); }
	public Vector3i xxy()  { return new Vector3i(x, x, y); }
	public Vector3i xxz()  { return new Vector3i(x, x, z); }
	public Vector3i xyx()  { return new Vector3i(x, y, x); }
	public Vector3i xyy()  { return new Vector3i(x, y, y); }
	public Vector3i xyz()  { return new Vector3i(x, y, z); }
	public Vector3i xzx()  { return new Vector3i(x, z, x); }
	public Vector3i xzy()  { return new Vector3i(x, z, y); }
	public Vector3i xzz()  { return new Vector3i(x, z, z); }
	public Vector3i yxx()  { return new Vector3i(y, x, x); }
	public Vector3i yxy()  { return new Vector3i(y, x, y); }
	public Vector3i yxz()  { return new Vector3i(y, x, z); }
	public Vector3i yyx()  { return new Vector3i(y, y, x); }
	public Vector3i yyy()  { return new Vector3i(y, y, y); }
	public Vector3i yyz()  { return new Vector3i(y, y, z); }
	public Vector3i yzx()  { return new Vector3i(y, z, x); }
	public Vector3i yzy()  { return new Vector3i(y, z, y); }
	public Vector3i yzz()  { return new Vector3i(y, z, z); }
	public Vector3i zxx()  { return new Vector3i(z, x, x); }
	public Vector3i zxy()  { return new Vector3i(z, x, y); }
	public Vector3i zxz()  { return new Vector3i(z, x, z); }
	public Vector3i zyx()  { return new Vector3i(z, y, x); }
	public Vector3i zyy()  { return new Vector3i(z, y, y); }
	public Vector3i zyz()  { return new Vector3i(z, y, z); }
	public Vector3i zzx()  { return new Vector3i(z, z, x); }
	public Vector3i zzy()  { return new Vector3i(z, z, y); }
	public Vector3i zzz()  { return new Vector3i(z, z, z); }
	public Vector4i xxxx() { return new Vector4i(x, x, x, x); }
	public Vector4i xxxy() { return new Vector4i(x, x, x, y); }
	public Vector4i xxxz() { return new Vector4i(x, x, x, z); }
	public Vector4i xxyx() { return new Vector4i(x, x, y, x); }
	public Vector4i xxyy() { return new Vector4i(x, x, y, y); }
	public Vector4i xxyz() { return new Vector4i(x, x, y, z); }
	public Vector4i xxzx() { return new Vector4i(x, x, z, x); }
	public Vector4i xxzy() { return new Vector4i(x, x, z, y); }
	public Vector4i xxzz() { return new Vector4i(x, x, z, z); }
	public Vector4i xyxx() { return new Vector4i(x, y, x, x); }
	public Vector4i xyxy() { return new Vector4i(x, y, x, y); }
	public Vector4i xyxz() { return new Vector4i(x, y, x, z); }
	public Vector4i xyyx() { return new Vector4i(x, y, y, x); }
	public Vector4i xyyy() { return new Vector4i(x, y, y, y); }
	public Vector4i xyyz() { return new Vector4i(x, y, y, z); }
	public Vector4i xyzx() { return new Vector4i(x, y, z, x); }
	public Vector4i xyzy() { return new Vector4i(x, y, z, y); }
	public Vector4i xyzz() { return new Vector4i(x, y, z, z); }
	public Vector4i xzxx() { return new Vector4i(x, z, x, x); }
	public Vector4i xzxy() { return new Vector4i(x, z, x, y); }
	public Vector4i xzxz() { return new Vector4i(x, z, x, z); }
	public Vector4i xzyx() { return new Vector4i(x, z, y, x); }
	public Vector4i xzyy() { return new Vector4i(x, z, y, y); }
	public Vector4i xzyz() { return new Vector4i(x, z, y, z); }
	public Vector4i xzzx() { return new Vector4i(x, z, z, x); }
	public Vector4i xzzy() { return new Vector4i(x, z, z, y); }
	public Vector4i xzzz() { return new Vector4i(x, z, z, z); }
	public Vector4i yxxx() { return new Vector4i(y, x, x, x); }
	public Vector4i yxxy() { return new Vector4i(y, x, x, y); }
	public Vector4i yxxz() { return new Vector4i(y, x, x, z); }
	public Vector4i yxyx() { return new Vector4i(y, x, y, x); }
	public Vector4i yxyy() { return new Vector4i(y, x, y, y); }
	public Vector4i yxyz() { return new Vector4i(y, x, y, z); }
	public Vector4i yxzx() { return new Vector4i(y, x, z, x); }
	public Vector4i yxzy() { return new Vector4i(y, x, z, y); }
	public Vector4i yxzz() { return new Vector4i(y, x, z, z); }
	public Vector4i yyxx() { return new Vector4i(y, y, x, x); }
	public Vector4i yyxy() { return new Vector4i(y, y, x, y); }
	public Vector4i yyxz() { return new Vector4i(y, y, x, z); }
	public Vector4i yyyx() { return new Vector4i(y, y, y, x); }
	public Vector4i yyyy() { return new Vector4i(y, y, y, y); }
	public Vector4i yyyz() { return new Vector4i(y, y, y, z); }
	public Vector4i yyzx() { return new Vector4i(y, y, z, x); }
	public Vector4i yyzy() { return new Vector4i(y, y, z, y); }
	public Vector4i yyzz() { return new Vector4i(y, y, z, z); }
	public Vector4i yzxx() { return new Vector4i(y, z, x, x); }
	public Vector4i yzxy() { return new Vector4i(y, z, x, y); }
	public Vector4i yzxz() { return new Vector4i(y, z, x, z); }
	public Vector4i yzyx() { return new Vector4i(y, z, y, x); }
	public Vector4i yzyy() { return new Vector4i(y, z, y, y); }
	public Vector4i yzyz() { return new Vector4i(y, z, y, z); }
	public Vector4i yzzx() { return new Vector4i(y, z, z, x); }
	public Vector4i yzzy() { return new Vector4i(y, z, z, y); }
	public Vector4i yzzz() { return new Vector4i(y, z, z, z); }
	public Vector4i zxxx() { return new Vector4i(z, x, x, x); }
	public Vector4i zxxy() { return new Vector4i(z, x, x, y); }
	public Vector4i zxxz() { return new Vector4i(z, x, x, z); }
	public Vector4i zxyx() { return new Vector4i(z, x, y, x); }
	public Vector4i zxyy() { return new Vector4i(z, x, y, y); }
	public Vector4i zxyz() { return new Vector4i(z, x, y, z); }
	public Vector4i zxzx() { return new Vector4i(z, x, z, x); }
	public Vector4i zxzy() { return new Vector4i(z, x, z, y); }
	public Vector4i zxzz() { return new Vector4i(z, x, z, z); }
	public Vector4i zyxx() { return new Vector4i(z, y, x, x); }
	public Vector4i zyxy() { return new Vector4i(z, y, x, y); }
	public Vector4i zyxz() { return new Vector4i(z, y, x, z); }
	public Vector4i zyyx() { return new Vector4i(z, y, y, x); }
	public Vector4i zyyy() { return new Vector4i(z, y, y, y); }
	public Vector4i zyyz() { return new Vector4i(z, y, y, z); }
	public Vector4i zyzx() { return new Vector4i(z, y, z, x); }
	public Vector4i zyzy() { return new Vector4i(z, y, z, y); }
	public Vector4i zyzz() { return new Vector4i(z, y, z, z); }
	public Vector4i zzxx() { return new Vector4i(z, z, x, x); }
	public Vector4i zzxy() { return new Vector4i(z, z, x, y); }
	public Vector4i zzxz() { return new Vector4i(z, z, x, z); }
	public Vector4i zzyx() { return new Vector4i(z, z, y, x); }
	public Vector4i zzyy() { return new Vector4i(z, z, y, y); }
	public Vector4i zzyz() { return new Vector4i(z, z, y, z); }
	public Vector4i zzzx() { return new Vector4i(z, z, z, x); }
	public Vector4i zzzy() { return new Vector4i(z, z, z, y); }
	public Vector4i zzzz() { return new Vector4i(z, z, z, z); }
}
