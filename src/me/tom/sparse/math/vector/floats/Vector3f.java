package me.tom.sparse.math.vector.floats;

import me.tom.sparse.math.Matrix4f;
import me.tom.sparse.math.vector.doubles.Vector3d;
import me.tom.sparse.math.vector.floats.impl.Quaternion4f;
import me.tom.sparse.math.vector.integers.Vector3i;

import java.util.function.BiFunction;
import java.util.function.Function;

@SuppressWarnings({"SuspiciousNameCombination", "unused"})
public class Vector3f
{
	protected float x, y, z;
	
	public Vector3f(float x, float y, float z)
	{
		set(x, y, z);
	}
	
	public Vector3f(Vector3f other)
	{
		set(other);
	}
	
	public Vector3f(float x, Vector2f other)
	{
		set(x, other);
	}
	
	public Vector3f(Vector2f other, float z)
	{
		set(other, z);
	}
	
	public Vector3f(float value)
	{
		set(value);
	}
	
	public Vector3f()
	{
	
	}

	public Vector3f set(float x, float y, float z)
	{
		return setX(x).setY(y).setZ(z);
	}

	public Vector3f set(Vector3f other)
	{
		return set(other.x, other.y, other.z);
	}

	public Vector3f set(float x, Vector2f other)
	{
		return set(x, other.x(), other.y());
	}

	public Vector3f set(Vector2f other, float z)
	{
		return set(other.x(), other.y(), z);
	}

	public Vector3f set(float value)
	{
		return set(value, value, value);
	}

	public Vector3f setX(float x)
	{
		this.x = x;
		return this;
	}

	public Vector3f setY(float y)
	{
		this.y = y;
		return this;
	}

	public Vector3f setZ(float z)
	{
		this.z = z;
		return this;
	}

	public float x()
	{
		return x;
	}

	public float y()
	{
		return y;
	}

	public float z()
	{
		return z;
	}

	public Vector3f op(Function<Float, Float> function)
	{
		setX(function.apply(x()));
		setY(function.apply(y()));
		setZ(function.apply(z()));
		return this;
	}

	public Vector3f pairOp(Vector3f other, BiFunction<Float, Float, Float> function)
	{
		setX(function.apply(x(), other.x()));
		setY(function.apply(y(), other.y()));
		setZ(function.apply(z(), other.z()));
		return this;
	}

	public Vector3f abs()
	{
		return set(Math.abs(x()), Math.abs(y()), Math.abs(z()));
	}

	public Vector3f rotate(Vector3f axis, float angle)
	{
		return rotate(new Quaternion4f(axis, angle));
//		float sinHalf = (float) Math.sin(Math.toRadians(angle / 2));
//		float cosHalf = (float) Math.cos(Math.toRadians(angle / 2));
//
//		Quaternion rotation = new Quaternion(axis.x() * sinHalf, axis.y() * sinHalf, axis.z() * sinHalf, cosHalf);
//		Quaternion conjugate = rotation.conjugate();
//
//		rotation.multiply(this).multiply(conjugate);
//
//		return set(rotation.x(), rotation.y(), rotation.z());
	}

	public Vector3f rotate(Quaternion4f rotation)
	{
		Quaternion4f conjugate = rotation.conjugate();
		Quaternion4f w = (Quaternion4f) ((Quaternion4f)rotation.clone()).multiply(this).multiply(conjugate);
//		set((Vector3f) w.swizzle("xyz"));

		return w.xyz();

//		return (Vector3f) w.swizzle("xyz");
//		rotation = (Quaternion) rotation.clone();
//		Quaternion conjugate = rotation.conjugate();
//		rotation = (Quaternion) rotation.multiply(this).multiply(conjugate);
//
//		return set((Vector3f) rotation.swizzle("xyz"));
//		return (Vector3f) rotation.swizzle("xyz");
	}

	public Vector4f toVector4f(float w)
	{
		return new Vector4f(x, y, z, w);
	}

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

	public Vector3f min(Vector3f other)
	{
		return min(other.x(), other.y(), other.z());
	}

	public Vector3f min(float x, float y, float z)
	{
		return set(Math.min(x(), x), Math.min(y(), y), Math.min(z(), z));
	}

	public float max()
	{
		return Math.max(x(), Math.max(y(), z()));
	}

	public Vector3f max(Vector3f other)
	{
		return max(other.x(), other.y(), other.z());
	}

	public Vector3f max(float x, float y, float z)
	{
		return set(Math.max(x(), x), Math.max(y(), y), Math.max(z(), z));
	}
	
	public boolean withinMinMax(float minX, float minY, float minZ, float maxX, float maxY, float maxZ)
	{
		float x = x();
		float y = y();
		float z = z();
		return x >= minX && x <= maxX && y >= minY && y <= maxY && z >= minZ && z <= maxZ;
	}

	public boolean withinMinMax(Vector3f min, Vector3f max)
	{
		return withinMinMax(min.x(), min.y(), min.z(), max.x(), max.y(), max.z());
	}

	public boolean within(Vector3f a, Vector3f b)
	{
		return withinMinMax(a.clone().min(b), a.clone().max(b));
	}
	
	public float sum()
	{
		return x() + y() + z();
	}

	public float dot(Vector3f other)
	{
		return clone().multiply(other).sum();
	}

	public float dot(float x, float y, float z)
	{
		return clone().multiply(x, y, z).sum();
	}
	
	public float lengthSquared()
	{
		return dot(this);
	}

	public float length()
	{
		return (float) Math.sqrt(lengthSquared());
	}
	
	public float distance(Vector3f other)
	{
		return clone().subtract(other).length();
	}
	
	public float distance(float x, float y, float z)
	{
		return clone().subtract(x, y, z).length();
	}
	
	public float distanceSquared(Vector3f other)
	{
		return clone().subtract(other).lengthSquared();
	}
	
	public Vector3f normalize()
	{
		float length = length();
		return length == 0 ? this : divide(length);
	}
	
	public Vector3f lerp(Vector3f target, float lerpFactor)
	{
		return target.clone().subtract(this).multiply(lerpFactor).add(this);
	}

	public Vector3f cross(Vector3f other)
	{
		return cross(other.x(), other.y(), other.z());
	}
	
	public Vector3f cross(float x, float y, float z)
	{
		float ax = x();
		float ay = y();
		float az = z();
		float bx = x;
		float by = y;
		float bz = z;
		
		return new Vector3f(ay * bz - az * by, az * bx - ax * bz, ax * by - ay * bx);
	}
	
	public Vector3f pow(float pow)
	{
		return set((float) Math.pow(x(), pow), (float) Math.pow(y(), pow), (float) Math.pow(z(), pow));
	}
	
	public Vector3f floor()
	{
		return set((float) Math.floor(x()), (float) Math.floor(y()), (float) Math.floor(z()));
	}
	
	public Vector3f ceil()
	{
		return set((float) Math.ceil(x()), (float) Math.ceil(y()), (float) Math.ceil(z()));
	}
	
	public Vector3f round()
	{
		return set((float) Math.round(x()), (float) Math.round(y()), (float) Math.round(z()));
	}
	
	public Vector3f add(float v)
	{
		return add(v, v, v);
	}

	public Vector3f add(float x, float y, float z)
	{
		return set(x() + x, y() + y, z() + z);
	}

	public Vector3f add(Vector3f other)
	{
		return add(other.x(), other.y(), other.z());
	}
	
	public Vector3f subtract(float v)
	{
		return subtract(v, v, v);
	}

	public Vector3f subtract(float x, float y, float z)
	{
		return add(-x, -y, -z);
	}

	public Vector3f subtract(Vector3f other)
	{
		return subtract(other.x(), other.y(), other.z());
	}
	
	public Vector3f multiply(float v)
	{
		return multiply(v, v, v);
	}

	public Vector3f multiply(float x, float y, float z)
	{
		return set(x() * x, y() * y, z() * z);
	}

	public Vector3f multiply(Vector3f other)
	{
		return multiply(other.x(), other.y(), other.z());
	}

//	public Vector3f multiply(Matrix4f mat)
//	{
//		return null;
//	}
	
	public Vector3f divide(float v)
	{
		return divide(v, v, v);
	}

	public Vector3f divide(float x, float y, float z)
	{
		return set(x() / x, y() / y, z() / z);
	}
	
	public Vector3f divide(Vector3f other)
	{
		return divide(other.x(), other.y(), other.z());
	}

	@Override
	public Vector3f clone()
	{
		return new Vector3f(this);
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Vector3f vector3f = (Vector3f) o;

		if (Float.compare(vector3f.x, x) != 0) return false;
		if (Float.compare(vector3f.y, y) != 0) return false;
		return Float.compare(vector3f.z, z) == 0;

	}

	@Override
	public int hashCode()
	{
		int result = (x != +0.0f ? Float.floatToIntBits(x) : 0);
		result = 31 * result + (y != +0.0f ? Float.floatToIntBits(y) : 0);
		result = 31 * result + (z != +0.0f ? Float.floatToIntBits(z) : 0);
		return result;
	}

	@Override
	public String toString()
	{
		return "Vector3f{" +
				"x=" + x +
				", y=" + y +
				", z=" + z +
				'}';
	}
	
	public Vector3d toVector3d()
	{
		return new Vector3d(x, y, z);
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

	public Vector2f xx()   { return new Vector2f(x, x); }
	public Vector2f xy()   { return new Vector2f(x, y); }
	public Vector2f xz()   { return new Vector2f(x, z); }
	public Vector2f yx()   { return new Vector2f(y, x); }
	public Vector2f yy()   { return new Vector2f(y, y); }
	public Vector2f yz()   { return new Vector2f(y, z); }
	public Vector2f zx()   { return new Vector2f(z, x); }
	public Vector2f zy()   { return new Vector2f(z, y); }
	public Vector2f zz()   { return new Vector2f(z, z); }
	public Vector3f xxx()  { return new Vector3f(x, x, x); }
	public Vector3f xxy()  { return new Vector3f(x, x, y); }
	public Vector3f xxz()  { return new Vector3f(x, x, z); }
	public Vector3f xyx()  { return new Vector3f(x, y, x); }
	public Vector3f xyy()  { return new Vector3f(x, y, y); }
	public Vector3f xyz()  { return new Vector3f(x, y, z); }
	public Vector3f xzx()  { return new Vector3f(x, z, x); }
	public Vector3f xzy()  { return new Vector3f(x, z, y); }
	public Vector3f xzz()  { return new Vector3f(x, z, z); }
	public Vector3f yxx()  { return new Vector3f(y, x, x); }
	public Vector3f yxy()  { return new Vector3f(y, x, y); }
	public Vector3f yxz()  { return new Vector3f(y, x, z); }
	public Vector3f yyx()  { return new Vector3f(y, y, x); }
	public Vector3f yyy()  { return new Vector3f(y, y, y); }
	public Vector3f yyz()  { return new Vector3f(y, y, z); }
	public Vector3f yzx()  { return new Vector3f(y, z, x); }
	public Vector3f yzy()  { return new Vector3f(y, z, y); }
	public Vector3f yzz()  { return new Vector3f(y, z, z); }
	public Vector3f zxx()  { return new Vector3f(z, x, x); }
	public Vector3f zxy()  { return new Vector3f(z, x, y); }
	public Vector3f zxz()  { return new Vector3f(z, x, z); }
	public Vector3f zyx()  { return new Vector3f(z, y, x); }
	public Vector3f zyy()  { return new Vector3f(z, y, y); }
	public Vector3f zyz()  { return new Vector3f(z, y, z); }
	public Vector3f zzx()  { return new Vector3f(z, z, x); }
	public Vector3f zzy()  { return new Vector3f(z, z, y); }
	public Vector3f zzz()  { return new Vector3f(z, z, z); }
	public Vector4f xxxx() { return new Vector4f(x, x, x, x); }
	public Vector4f xxxy() { return new Vector4f(x, x, x, y); }
	public Vector4f xxxz() { return new Vector4f(x, x, x, z); }
	public Vector4f xxyx() { return new Vector4f(x, x, y, x); }
	public Vector4f xxyy() { return new Vector4f(x, x, y, y); }
	public Vector4f xxyz() { return new Vector4f(x, x, y, z); }
	public Vector4f xxzx() { return new Vector4f(x, x, z, x); }
	public Vector4f xxzy() { return new Vector4f(x, x, z, y); }
	public Vector4f xxzz() { return new Vector4f(x, x, z, z); }
	public Vector4f xyxx() { return new Vector4f(x, y, x, x); }
	public Vector4f xyxy() { return new Vector4f(x, y, x, y); }
	public Vector4f xyxz() { return new Vector4f(x, y, x, z); }
	public Vector4f xyyx() { return new Vector4f(x, y, y, x); }
	public Vector4f xyyy() { return new Vector4f(x, y, y, y); }
	public Vector4f xyyz() { return new Vector4f(x, y, y, z); }
	public Vector4f xyzx() { return new Vector4f(x, y, z, x); }
	public Vector4f xyzy() { return new Vector4f(x, y, z, y); }
	public Vector4f xyzz() { return new Vector4f(x, y, z, z); }
	public Vector4f xzxx() { return new Vector4f(x, z, x, x); }
	public Vector4f xzxy() { return new Vector4f(x, z, x, y); }
	public Vector4f xzxz() { return new Vector4f(x, z, x, z); }
	public Vector4f xzyx() { return new Vector4f(x, z, y, x); }
	public Vector4f xzyy() { return new Vector4f(x, z, y, y); }
	public Vector4f xzyz() { return new Vector4f(x, z, y, z); }
	public Vector4f xzzx() { return new Vector4f(x, z, z, x); }
	public Vector4f xzzy() { return new Vector4f(x, z, z, y); }
	public Vector4f xzzz() { return new Vector4f(x, z, z, z); }
	public Vector4f yxxx() { return new Vector4f(y, x, x, x); }
	public Vector4f yxxy() { return new Vector4f(y, x, x, y); }
	public Vector4f yxxz() { return new Vector4f(y, x, x, z); }
	public Vector4f yxyx() { return new Vector4f(y, x, y, x); }
	public Vector4f yxyy() { return new Vector4f(y, x, y, y); }
	public Vector4f yxyz() { return new Vector4f(y, x, y, z); }
	public Vector4f yxzx() { return new Vector4f(y, x, z, x); }
	public Vector4f yxzy() { return new Vector4f(y, x, z, y); }
	public Vector4f yxzz() { return new Vector4f(y, x, z, z); }
	public Vector4f yyxx() { return new Vector4f(y, y, x, x); }
	public Vector4f yyxy() { return new Vector4f(y, y, x, y); }
	public Vector4f yyxz() { return new Vector4f(y, y, x, z); }
	public Vector4f yyyx() { return new Vector4f(y, y, y, x); }
	public Vector4f yyyy() { return new Vector4f(y, y, y, y); }
	public Vector4f yyyz() { return new Vector4f(y, y, y, z); }
	public Vector4f yyzx() { return new Vector4f(y, y, z, x); }
	public Vector4f yyzy() { return new Vector4f(y, y, z, y); }
	public Vector4f yyzz() { return new Vector4f(y, y, z, z); }
	public Vector4f yzxx() { return new Vector4f(y, z, x, x); }
	public Vector4f yzxy() { return new Vector4f(y, z, x, y); }
	public Vector4f yzxz() { return new Vector4f(y, z, x, z); }
	public Vector4f yzyx() { return new Vector4f(y, z, y, x); }
	public Vector4f yzyy() { return new Vector4f(y, z, y, y); }
	public Vector4f yzyz() { return new Vector4f(y, z, y, z); }
	public Vector4f yzzx() { return new Vector4f(y, z, z, x); }
	public Vector4f yzzy() { return new Vector4f(y, z, z, y); }
	public Vector4f yzzz() { return new Vector4f(y, z, z, z); }
	public Vector4f zxxx() { return new Vector4f(z, x, x, x); }
	public Vector4f zxxy() { return new Vector4f(z, x, x, y); }
	public Vector4f zxxz() { return new Vector4f(z, x, x, z); }
	public Vector4f zxyx() { return new Vector4f(z, x, y, x); }
	public Vector4f zxyy() { return new Vector4f(z, x, y, y); }
	public Vector4f zxyz() { return new Vector4f(z, x, y, z); }
	public Vector4f zxzx() { return new Vector4f(z, x, z, x); }
	public Vector4f zxzy() { return new Vector4f(z, x, z, y); }
	public Vector4f zxzz() { return new Vector4f(z, x, z, z); }
	public Vector4f zyxx() { return new Vector4f(z, y, x, x); }
	public Vector4f zyxy() { return new Vector4f(z, y, x, y); }
	public Vector4f zyxz() { return new Vector4f(z, y, x, z); }
	public Vector4f zyyx() { return new Vector4f(z, y, y, x); }
	public Vector4f zyyy() { return new Vector4f(z, y, y, y); }
	public Vector4f zyyz() { return new Vector4f(z, y, y, z); }
	public Vector4f zyzx() { return new Vector4f(z, y, z, x); }
	public Vector4f zyzy() { return new Vector4f(z, y, z, y); }
	public Vector4f zyzz() { return new Vector4f(z, y, z, z); }
	public Vector4f zzxx() { return new Vector4f(z, z, x, x); }
	public Vector4f zzxy() { return new Vector4f(z, z, x, y); }
	public Vector4f zzxz() { return new Vector4f(z, z, x, z); }
	public Vector4f zzyx() { return new Vector4f(z, z, y, x); }
	public Vector4f zzyy() { return new Vector4f(z, z, y, y); }
	public Vector4f zzyz() { return new Vector4f(z, z, y, z); }
	public Vector4f zzzx() { return new Vector4f(z, z, z, x); }
	public Vector4f zzzy() { return new Vector4f(z, z, z, y); }
	public Vector4f zzzz() { return new Vector4f(z, z, z, z); }
}
