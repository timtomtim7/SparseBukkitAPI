package me.tom.sparse.math.vector.floats;

import me.tom.sparse.math.vector.doubles.Vector4d;
import me.tom.sparse.math.vector.integers.Vector4i;

import java.util.function.BiFunction;
import java.util.function.Function;

@SuppressWarnings({"SuspiciousNameCombination", "unused"})
public class Vector4f
{
	protected float x, y, z, w;
	
	public Vector4f(float x, float y, float z, float w)
	{
		set(x, y, z, w);
	}
	
	public Vector4f(Vector4f other)
	{
		set(other);
	}
	
	public Vector4f(float x, Vector3f other)
	{
		set(x, other);
	}
	
	public Vector4f(Vector3f other, float w)
	{
		set(other, w);
	}
	
	public Vector4f(float x, float y, Vector2f other)
	{
		set(x, y, other);
	}
	
	public Vector4f(Vector2f other, float z, float w)
	{
		set(other, z, w);
	}
	
	public Vector4f(float x, Vector2f other, float w)
	{
		set(x, other, w);
	}
	
	public Vector4f(float value)
	{
		set(value);
	}
	
	public Vector4f()
	{
	
	}
	
	public Vector4f set(float x, float y, float z, float w)
	{
		return setX(x).setY(y).setZ(z).setW(w);
	}

	public Vector4f set(Vector4f other)
	{
		return set(other.x(), other.y(), other.z(), other.w());
	}

	public Vector4f set(float x, Vector3f other)
	{
		return set(x, other.x(), other.y(), other.z());
	}

	public Vector4f set(Vector3f other, float w)
	{
		return set(other.x(), other.y(), other.z(), w);
	}

	public Vector4f set(float x, float y, Vector2f other)
	{
		return set(x, y, other.x(), other.y());
	}

	public Vector4f set(Vector2f other, float z, float w)
	{
		return set(other.x(), other.y(), z, w);
	}

	public Vector4f set(float x, Vector2f other, float w)
	{
		return set(x, other.x(), other.y(), w);
	}

	public Vector4f set(Vector2f other1, Vector2f other2)
	{
		return set(other1.x(), other1.y(), other2.x(), other2.y());
	}

	public Vector4f set(float value)
	{
		return set(value, value, value, value);
	}



	public Vector4f setX(float x)
	{
		this.x = x;
		return this;
	}

	public Vector4f setY(float y)
	{
		this.y = y;
		return this;
	}

	public Vector4f setZ(float z)
	{
		this.z = z;
		return this;
	}

	public Vector4f setW(float w)
	{
		this.w = w;
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

	public float w()
	{
		return w;
	}

	public Vector4f op(Function<Float, Float> function)
	{
		setX(function.apply(x()));
		setY(function.apply(y()));
		setZ(function.apply(z()));
		setW(function.apply(w()));
		return this;
	}

	public Vector4f pairOp(Vector4f other, BiFunction<Float, Float, Float> function)
	{
		setX(function.apply(x(), other.x()));
		setY(function.apply(y(), other.y()));
		setZ(function.apply(z(), other.z()));
		setW(function.apply(w(), other.w()));
		return this;
	}
	
	public Vector4f abs()
	{
		return set(Math.abs(x()), Math.abs(y()), Math.abs(z()), Math.abs(w()));
	}

	public float min()
	{
		return Math.min(x(), Math.min(y(), Math.min(z(), w())));
	}

	public Vector4f min(Vector4f other)
	{
		return min(other.x(), other.y(), other.z(), other.w());
	}

	public Vector4f min(float x, float y, float z, float w)
	{
		return set(Math.min(x(), x), Math.min(y(), y), Math.min(z(), z), Math.min(w(), w));
	}

	public float max()
	{
		return Math.max(x(), Math.max(y(), Math.max(z(), w())));
	}

	public Vector4f max(Vector4f other)
	{
		return max(other.x(), other.y(), other.z(), other.w());
	}

	public Vector4f max(float x, float y, float z, float w)
	{
		return set(Math.max(x(), x), Math.max(y(), y), Math.max(z(), z), Math.max(w(), w));
	}

	public boolean withinMinMax(float minX, float minY, float minZ, float minW, float maxX, float maxY, float maxZ, float maxW)
	{
		float x = x();
		float y = y();
		float z = z();
		float w = w();
		return x >= minX && x <= maxX && y >= minY && y <= maxY && z >= minZ && z <= maxZ && w >= minW && w <= maxW;
	}

	public boolean withinMinMax(Vector4f min, Vector4f max)
	{
		return withinMinMax(min.x(), min.y(), min.z(), min.w(), max.x(), max.y(), max.z(), max.w());
	}

	public boolean within(Vector4f a, Vector4f b)
	{
		return withinMinMax(a.clone().min(b), a.clone().max(b));
	}

	public float sum()
	{
		return x() + y() + z() + w();
	}

	public float dot(Vector4f other)
	{
		return clone().multiply(other).sum();
	}

	public float dot(float x, float y, float z, float w)
	{
		return clone().multiply(x, y, z, w).sum();
	}

	public float lengthSquared()
	{
		return dot(this);
	}

	public float length()
	{
		return (float) Math.sqrt(lengthSquared());
	}

	public float distance(Vector4f other)
	{
		return clone().subtract(other).length();
	}

	public float distance(float x, float y, float z, float w)
	{
		return clone().subtract(x, y, z, w).length();
	}

	public float distanceSquared(Vector4f other)
	{
		return clone().subtract(other).lengthSquared();
	}

	public Vector4f normalize()
	{
		float length = length();
		return length == 0 ? this : divide(length);
	}

	public Vector4f lerp(Vector4f target, float lerpFactor)
	{
		return target.clone().subtract(this).multiply(lerpFactor).add(this);
	}

	public Vector4f pow(float pow)
	{
		return set((float) Math.pow(x(), pow), (float) Math.pow(y(), pow), (float) Math.pow(z(), pow), (float) Math.pow(w(), pow));
	}

	public Vector4f floor()
	{
		return set((float) Math.floor(x()), (float) Math.floor(y()), (float) Math.floor(z()), (float) Math.floor(w()));
	}

	public Vector4f ceil()
	{
		return set((float) Math.ceil(x()), (float) Math.ceil(y()), (float) Math.ceil(z()), (float) Math.ceil(w()));
	}

	public Vector4f round()
	{
		return set((float) Math.round(x()), (float) Math.round(y()), (float) Math.round(z()), (float) Math.round(w()));
	}

	public Vector4f add(float v)
	{
		return add(v, v, v, v);
	}

	public Vector4f add(float x, float y, float z, float w)
	{
		return set(x() + x, y() + y, z() + z, w() + w);
	}

	public Vector4f add(Vector4f other)
	{
		return add(other.x(), other.y(), other.z(), other.w());
	}

	public Vector4f subtract(float v)
	{
		return subtract(v, v, v, v);
	}

	public Vector4f subtract(float x, float y, float z, float w)
	{
		return add(-x, -y, -z, -w);
	}

	public Vector4f subtract(Vector4f other)
	{
		return subtract(other.x(), other.y(), other.z(), other.w());
	}

	public Vector4f multiply(float v)
	{
		return multiply(v, v, v, v);
	}

	public Vector4f multiply(float x, float y, float z, float w)
	{
		return set(x() * x, y() * y, z() * z, w() * w);
	}

	public Vector4f multiply(Vector4f other)
	{
//		new Exception().printStackTrace();
		return multiply(other.x(), other.y(), other.z(), other.w());
	}

	public Vector4f divide(float v)
	{
		return divide(v, v, v, v);
	}

	public Vector4f divide(float x, float y, float z, float w)
	{
		return set(x() / x, y() / y, z() / z, w() / w);
	}

	public Vector4f divide(Vector4f other)
	{
		return divide(other.x(), other.y(), other.z(), other.w());
	}

	@Override
	public Vector4f clone()
	{
		return new Vector4f(this);
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Vector4f vector4f = (Vector4f) o;

		if (Float.compare(vector4f.x, x) != 0) return false;
		if (Float.compare(vector4f.y, y) != 0) return false;
		if (Float.compare(vector4f.z, z) != 0) return false;
		return Float.compare(vector4f.w, w) == 0;

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

	@Override
	public String toString()
	{
		return "Vector4f{" +
				"x=" + x +
				", y=" + y +
				", z=" + z +
				", w=" + w +
				'}';
	}
	
	public Vector4d toVector4d()
	{
		return new Vector4d(x, y, z, w);
	}
	
	public Vector4i toVector4i()
	{
		return new Vector4i((int)x, (int)y, (int)z, (int)w);
	}

	/*
	--------------------------------------------------------------------------------------------
								Generated Vector Swizzling code
	--------------------------------------------------------------------------------------------
	 */

	public Vector2f ww()   { return new Vector2f(w, w); }
	public Vector2f wx()   { return new Vector2f(w, x); }
	public Vector2f wy()   { return new Vector2f(w, y); }
	public Vector2f wz()   { return new Vector2f(w, z); }
	public Vector2f xw()   { return new Vector2f(x, w); }
	public Vector2f xx()   { return new Vector2f(x, x); }
	public Vector2f xy()   { return new Vector2f(x, y); }
	public Vector2f xz()   { return new Vector2f(x, z); }
	public Vector2f yw()   { return new Vector2f(y, w); }
	public Vector2f yx()   { return new Vector2f(y, x); }
	public Vector2f yy()   { return new Vector2f(y, y); }
	public Vector2f yz()   { return new Vector2f(y, z); }
	public Vector2f zw()   { return new Vector2f(z, w); }
	public Vector2f zx()   { return new Vector2f(z, x); }
	public Vector2f zy()   { return new Vector2f(z, y); }
	public Vector2f zz()   { return new Vector2f(z, z); }
	public Vector3f www()  { return new Vector3f(w, w, w); }
	public Vector3f wwx()  { return new Vector3f(w, w, x); }
	public Vector3f wwy()  { return new Vector3f(w, w, y); }
	public Vector3f wwz()  { return new Vector3f(w, w, z); }
	public Vector3f wxw()  { return new Vector3f(w, x, w); }
	public Vector3f wxx()  { return new Vector3f(w, x, x); }
	public Vector3f wxy()  { return new Vector3f(w, x, y); }
	public Vector3f wxz()  { return new Vector3f(w, x, z); }
	public Vector3f wyw()  { return new Vector3f(w, y, w); }
	public Vector3f wyx()  { return new Vector3f(w, y, x); }
	public Vector3f wyy()  { return new Vector3f(w, y, y); }
	public Vector3f wyz()  { return new Vector3f(w, y, z); }
	public Vector3f wzw()  { return new Vector3f(w, z, w); }
	public Vector3f wzx()  { return new Vector3f(w, z, x); }
	public Vector3f wzy()  { return new Vector3f(w, z, y); }
	public Vector3f wzz()  { return new Vector3f(w, z, z); }
	public Vector3f xww()  { return new Vector3f(x, w, w); }
	public Vector3f xwx()  { return new Vector3f(x, w, x); }
	public Vector3f xwy()  { return new Vector3f(x, w, y); }
	public Vector3f xwz()  { return new Vector3f(x, w, z); }
	public Vector3f xxw()  { return new Vector3f(x, x, w); }
	public Vector3f xxx()  { return new Vector3f(x, x, x); }
	public Vector3f xxy()  { return new Vector3f(x, x, y); }
	public Vector3f xxz()  { return new Vector3f(x, x, z); }
	public Vector3f xyw()  { return new Vector3f(x, y, w); }
	public Vector3f xyx()  { return new Vector3f(x, y, x); }
	public Vector3f xyy()  { return new Vector3f(x, y, y); }
	public Vector3f xyz()  { return new Vector3f(x, y, z); }
	public Vector3f xzw()  { return new Vector3f(x, z, w); }
	public Vector3f xzx()  { return new Vector3f(x, z, x); }
	public Vector3f xzy()  { return new Vector3f(x, z, y); }
	public Vector3f xzz()  { return new Vector3f(x, z, z); }
	public Vector3f yww()  { return new Vector3f(y, w, w); }
	public Vector3f ywx()  { return new Vector3f(y, w, x); }
	public Vector3f ywy()  { return new Vector3f(y, w, y); }
	public Vector3f ywz()  { return new Vector3f(y, w, z); }
	public Vector3f yxw()  { return new Vector3f(y, x, w); }
	public Vector3f yxx()  { return new Vector3f(y, x, x); }
	public Vector3f yxy()  { return new Vector3f(y, x, y); }
	public Vector3f yxz()  { return new Vector3f(y, x, z); }
	public Vector3f yyw()  { return new Vector3f(y, y, w); }
	public Vector3f yyx()  { return new Vector3f(y, y, x); }
	public Vector3f yyy()  { return new Vector3f(y, y, y); }
	public Vector3f yyz()  { return new Vector3f(y, y, z); }
	public Vector3f yzw()  { return new Vector3f(y, z, w); }
	public Vector3f yzx()  { return new Vector3f(y, z, x); }
	public Vector3f yzy()  { return new Vector3f(y, z, y); }
	public Vector3f yzz()  { return new Vector3f(y, z, z); }
	public Vector3f zww()  { return new Vector3f(z, w, w); }
	public Vector3f zwx()  { return new Vector3f(z, w, x); }
	public Vector3f zwy()  { return new Vector3f(z, w, y); }
	public Vector3f zwz()  { return new Vector3f(z, w, z); }
	public Vector3f zxw()  { return new Vector3f(z, x, w); }
	public Vector3f zxx()  { return new Vector3f(z, x, x); }
	public Vector3f zxy()  { return new Vector3f(z, x, y); }
	public Vector3f zxz()  { return new Vector3f(z, x, z); }
	public Vector3f zyw()  { return new Vector3f(z, y, w); }
	public Vector3f zyx()  { return new Vector3f(z, y, x); }
	public Vector3f zyy()  { return new Vector3f(z, y, y); }
	public Vector3f zyz()  { return new Vector3f(z, y, z); }
	public Vector3f zzw()  { return new Vector3f(z, z, w); }
	public Vector3f zzx()  { return new Vector3f(z, z, x); }
	public Vector3f zzy()  { return new Vector3f(z, z, y); }
	public Vector3f zzz()  { return new Vector3f(z, z, z); }
	public Vector4f wwww() { return new Vector4f(w, w, w, w); }
	public Vector4f wwwx() { return new Vector4f(w, w, w, x); }
	public Vector4f wwwy() { return new Vector4f(w, w, w, y); }
	public Vector4f wwwz() { return new Vector4f(w, w, w, z); }
	public Vector4f wwxw() { return new Vector4f(w, w, x, w); }
	public Vector4f wwxx() { return new Vector4f(w, w, x, x); }
	public Vector4f wwxy() { return new Vector4f(w, w, x, y); }
	public Vector4f wwxz() { return new Vector4f(w, w, x, z); }
	public Vector4f wwyw() { return new Vector4f(w, w, y, w); }
	public Vector4f wwyx() { return new Vector4f(w, w, y, x); }
	public Vector4f wwyy() { return new Vector4f(w, w, y, y); }
	public Vector4f wwyz() { return new Vector4f(w, w, y, z); }
	public Vector4f wwzw() { return new Vector4f(w, w, z, w); }
	public Vector4f wwzx() { return new Vector4f(w, w, z, x); }
	public Vector4f wwzy() { return new Vector4f(w, w, z, y); }
	public Vector4f wwzz() { return new Vector4f(w, w, z, z); }
	public Vector4f wxww() { return new Vector4f(w, x, w, w); }
	public Vector4f wxwx() { return new Vector4f(w, x, w, x); }
	public Vector4f wxwy() { return new Vector4f(w, x, w, y); }
	public Vector4f wxwz() { return new Vector4f(w, x, w, z); }
	public Vector4f wxxw() { return new Vector4f(w, x, x, w); }
	public Vector4f wxxx() { return new Vector4f(w, x, x, x); }
	public Vector4f wxxy() { return new Vector4f(w, x, x, y); }
	public Vector4f wxxz() { return new Vector4f(w, x, x, z); }
	public Vector4f wxyw() { return new Vector4f(w, x, y, w); }
	public Vector4f wxyx() { return new Vector4f(w, x, y, x); }
	public Vector4f wxyy() { return new Vector4f(w, x, y, y); }
	public Vector4f wxyz() { return new Vector4f(w, x, y, z); }
	public Vector4f wxzw() { return new Vector4f(w, x, z, w); }
	public Vector4f wxzx() { return new Vector4f(w, x, z, x); }
	public Vector4f wxzy() { return new Vector4f(w, x, z, y); }
	public Vector4f wxzz() { return new Vector4f(w, x, z, z); }
	public Vector4f wyww() { return new Vector4f(w, y, w, w); }
	public Vector4f wywx() { return new Vector4f(w, y, w, x); }
	public Vector4f wywy() { return new Vector4f(w, y, w, y); }
	public Vector4f wywz() { return new Vector4f(w, y, w, z); }
	public Vector4f wyxw() { return new Vector4f(w, y, x, w); }
	public Vector4f wyxx() { return new Vector4f(w, y, x, x); }
	public Vector4f wyxy() { return new Vector4f(w, y, x, y); }
	public Vector4f wyxz() { return new Vector4f(w, y, x, z); }
	public Vector4f wyyw() { return new Vector4f(w, y, y, w); }
	public Vector4f wyyx() { return new Vector4f(w, y, y, x); }
	public Vector4f wyyy() { return new Vector4f(w, y, y, y); }
	public Vector4f wyyz() { return new Vector4f(w, y, y, z); }
	public Vector4f wyzw() { return new Vector4f(w, y, z, w); }
	public Vector4f wyzx() { return new Vector4f(w, y, z, x); }
	public Vector4f wyzy() { return new Vector4f(w, y, z, y); }
	public Vector4f wyzz() { return new Vector4f(w, y, z, z); }
	public Vector4f wzww() { return new Vector4f(w, z, w, w); }
	public Vector4f wzwx() { return new Vector4f(w, z, w, x); }
	public Vector4f wzwy() { return new Vector4f(w, z, w, y); }
	public Vector4f wzwz() { return new Vector4f(w, z, w, z); }
	public Vector4f wzxw() { return new Vector4f(w, z, x, w); }
	public Vector4f wzxx() { return new Vector4f(w, z, x, x); }
	public Vector4f wzxy() { return new Vector4f(w, z, x, y); }
	public Vector4f wzxz() { return new Vector4f(w, z, x, z); }
	public Vector4f wzyw() { return new Vector4f(w, z, y, w); }
	public Vector4f wzyx() { return new Vector4f(w, z, y, x); }
	public Vector4f wzyy() { return new Vector4f(w, z, y, y); }
	public Vector4f wzyz() { return new Vector4f(w, z, y, z); }
	public Vector4f wzzw() { return new Vector4f(w, z, z, w); }
	public Vector4f wzzx() { return new Vector4f(w, z, z, x); }
	public Vector4f wzzy() { return new Vector4f(w, z, z, y); }
	public Vector4f wzzz() { return new Vector4f(w, z, z, z); }
	public Vector4f xwww() { return new Vector4f(x, w, w, w); }
	public Vector4f xwwx() { return new Vector4f(x, w, w, x); }
	public Vector4f xwwy() { return new Vector4f(x, w, w, y); }
	public Vector4f xwwz() { return new Vector4f(x, w, w, z); }
	public Vector4f xwxw() { return new Vector4f(x, w, x, w); }
	public Vector4f xwxx() { return new Vector4f(x, w, x, x); }
	public Vector4f xwxy() { return new Vector4f(x, w, x, y); }
	public Vector4f xwxz() { return new Vector4f(x, w, x, z); }
	public Vector4f xwyw() { return new Vector4f(x, w, y, w); }
	public Vector4f xwyx() { return new Vector4f(x, w, y, x); }
	public Vector4f xwyy() { return new Vector4f(x, w, y, y); }
	public Vector4f xwyz() { return new Vector4f(x, w, y, z); }
	public Vector4f xwzw() { return new Vector4f(x, w, z, w); }
	public Vector4f xwzx() { return new Vector4f(x, w, z, x); }
	public Vector4f xwzy() { return new Vector4f(x, w, z, y); }
	public Vector4f xwzz() { return new Vector4f(x, w, z, z); }
	public Vector4f xxww() { return new Vector4f(x, x, w, w); }
	public Vector4f xxwx() { return new Vector4f(x, x, w, x); }
	public Vector4f xxwy() { return new Vector4f(x, x, w, y); }
	public Vector4f xxwz() { return new Vector4f(x, x, w, z); }
	public Vector4f xxxw() { return new Vector4f(x, x, x, w); }
	public Vector4f xxxx() { return new Vector4f(x, x, x, x); }
	public Vector4f xxxy() { return new Vector4f(x, x, x, y); }
	public Vector4f xxxz() { return new Vector4f(x, x, x, z); }
	public Vector4f xxyw() { return new Vector4f(x, x, y, w); }
	public Vector4f xxyx() { return new Vector4f(x, x, y, x); }
	public Vector4f xxyy() { return new Vector4f(x, x, y, y); }
	public Vector4f xxyz() { return new Vector4f(x, x, y, z); }
	public Vector4f xxzw() { return new Vector4f(x, x, z, w); }
	public Vector4f xxzx() { return new Vector4f(x, x, z, x); }
	public Vector4f xxzy() { return new Vector4f(x, x, z, y); }
	public Vector4f xxzz() { return new Vector4f(x, x, z, z); }
	public Vector4f xyww() { return new Vector4f(x, y, w, w); }
	public Vector4f xywx() { return new Vector4f(x, y, w, x); }
	public Vector4f xywy() { return new Vector4f(x, y, w, y); }
	public Vector4f xywz() { return new Vector4f(x, y, w, z); }
	public Vector4f xyxw() { return new Vector4f(x, y, x, w); }
	public Vector4f xyxx() { return new Vector4f(x, y, x, x); }
	public Vector4f xyxy() { return new Vector4f(x, y, x, y); }
	public Vector4f xyxz() { return new Vector4f(x, y, x, z); }
	public Vector4f xyyw() { return new Vector4f(x, y, y, w); }
	public Vector4f xyyx() { return new Vector4f(x, y, y, x); }
	public Vector4f xyyy() { return new Vector4f(x, y, y, y); }
	public Vector4f xyyz() { return new Vector4f(x, y, y, z); }
	public Vector4f xyzw() { return new Vector4f(x, y, z, w); }
	public Vector4f xyzx() { return new Vector4f(x, y, z, x); }
	public Vector4f xyzy() { return new Vector4f(x, y, z, y); }
	public Vector4f xyzz() { return new Vector4f(x, y, z, z); }
	public Vector4f xzww() { return new Vector4f(x, z, w, w); }
	public Vector4f xzwx() { return new Vector4f(x, z, w, x); }
	public Vector4f xzwy() { return new Vector4f(x, z, w, y); }
	public Vector4f xzwz() { return new Vector4f(x, z, w, z); }
	public Vector4f xzxw() { return new Vector4f(x, z, x, w); }
	public Vector4f xzxx() { return new Vector4f(x, z, x, x); }
	public Vector4f xzxy() { return new Vector4f(x, z, x, y); }
	public Vector4f xzxz() { return new Vector4f(x, z, x, z); }
	public Vector4f xzyw() { return new Vector4f(x, z, y, w); }
	public Vector4f xzyx() { return new Vector4f(x, z, y, x); }
	public Vector4f xzyy() { return new Vector4f(x, z, y, y); }
	public Vector4f xzyz() { return new Vector4f(x, z, y, z); }
	public Vector4f xzzw() { return new Vector4f(x, z, z, w); }
	public Vector4f xzzx() { return new Vector4f(x, z, z, x); }
	public Vector4f xzzy() { return new Vector4f(x, z, z, y); }
	public Vector4f xzzz() { return new Vector4f(x, z, z, z); }
	public Vector4f ywww() { return new Vector4f(y, w, w, w); }
	public Vector4f ywwx() { return new Vector4f(y, w, w, x); }
	public Vector4f ywwy() { return new Vector4f(y, w, w, y); }
	public Vector4f ywwz() { return new Vector4f(y, w, w, z); }
	public Vector4f ywxw() { return new Vector4f(y, w, x, w); }
	public Vector4f ywxx() { return new Vector4f(y, w, x, x); }
	public Vector4f ywxy() { return new Vector4f(y, w, x, y); }
	public Vector4f ywxz() { return new Vector4f(y, w, x, z); }
	public Vector4f ywyw() { return new Vector4f(y, w, y, w); }
	public Vector4f ywyx() { return new Vector4f(y, w, y, x); }
	public Vector4f ywyy() { return new Vector4f(y, w, y, y); }
	public Vector4f ywyz() { return new Vector4f(y, w, y, z); }
	public Vector4f ywzw() { return new Vector4f(y, w, z, w); }
	public Vector4f ywzx() { return new Vector4f(y, w, z, x); }
	public Vector4f ywzy() { return new Vector4f(y, w, z, y); }
	public Vector4f ywzz() { return new Vector4f(y, w, z, z); }
	public Vector4f yxww() { return new Vector4f(y, x, w, w); }
	public Vector4f yxwx() { return new Vector4f(y, x, w, x); }
	public Vector4f yxwy() { return new Vector4f(y, x, w, y); }
	public Vector4f yxwz() { return new Vector4f(y, x, w, z); }
	public Vector4f yxxw() { return new Vector4f(y, x, x, w); }
	public Vector4f yxxx() { return new Vector4f(y, x, x, x); }
	public Vector4f yxxy() { return new Vector4f(y, x, x, y); }
	public Vector4f yxxz() { return new Vector4f(y, x, x, z); }
	public Vector4f yxyw() { return new Vector4f(y, x, y, w); }
	public Vector4f yxyx() { return new Vector4f(y, x, y, x); }
	public Vector4f yxyy() { return new Vector4f(y, x, y, y); }
	public Vector4f yxyz() { return new Vector4f(y, x, y, z); }
	public Vector4f yxzw() { return new Vector4f(y, x, z, w); }
	public Vector4f yxzx() { return new Vector4f(y, x, z, x); }
	public Vector4f yxzy() { return new Vector4f(y, x, z, y); }
	public Vector4f yxzz() { return new Vector4f(y, x, z, z); }
	public Vector4f yyww() { return new Vector4f(y, y, w, w); }
	public Vector4f yywx() { return new Vector4f(y, y, w, x); }
	public Vector4f yywy() { return new Vector4f(y, y, w, y); }
	public Vector4f yywz() { return new Vector4f(y, y, w, z); }
	public Vector4f yyxw() { return new Vector4f(y, y, x, w); }
	public Vector4f yyxx() { return new Vector4f(y, y, x, x); }
	public Vector4f yyxy() { return new Vector4f(y, y, x, y); }
	public Vector4f yyxz() { return new Vector4f(y, y, x, z); }
	public Vector4f yyyw() { return new Vector4f(y, y, y, w); }
	public Vector4f yyyx() { return new Vector4f(y, y, y, x); }
	public Vector4f yyyy() { return new Vector4f(y, y, y, y); }
	public Vector4f yyyz() { return new Vector4f(y, y, y, z); }
	public Vector4f yyzw() { return new Vector4f(y, y, z, w); }
	public Vector4f yyzx() { return new Vector4f(y, y, z, x); }
	public Vector4f yyzy() { return new Vector4f(y, y, z, y); }
	public Vector4f yyzz() { return new Vector4f(y, y, z, z); }
	public Vector4f yzww() { return new Vector4f(y, z, w, w); }
	public Vector4f yzwx() { return new Vector4f(y, z, w, x); }
	public Vector4f yzwy() { return new Vector4f(y, z, w, y); }
	public Vector4f yzwz() { return new Vector4f(y, z, w, z); }
	public Vector4f yzxw() { return new Vector4f(y, z, x, w); }
	public Vector4f yzxx() { return new Vector4f(y, z, x, x); }
	public Vector4f yzxy() { return new Vector4f(y, z, x, y); }
	public Vector4f yzxz() { return new Vector4f(y, z, x, z); }
	public Vector4f yzyw() { return new Vector4f(y, z, y, w); }
	public Vector4f yzyx() { return new Vector4f(y, z, y, x); }
	public Vector4f yzyy() { return new Vector4f(y, z, y, y); }
	public Vector4f yzyz() { return new Vector4f(y, z, y, z); }
	public Vector4f yzzw() { return new Vector4f(y, z, z, w); }
	public Vector4f yzzx() { return new Vector4f(y, z, z, x); }
	public Vector4f yzzy() { return new Vector4f(y, z, z, y); }
	public Vector4f yzzz() { return new Vector4f(y, z, z, z); }
	public Vector4f zwww() { return new Vector4f(z, w, w, w); }
	public Vector4f zwwx() { return new Vector4f(z, w, w, x); }
	public Vector4f zwwy() { return new Vector4f(z, w, w, y); }
	public Vector4f zwwz() { return new Vector4f(z, w, w, z); }
	public Vector4f zwxw() { return new Vector4f(z, w, x, w); }
	public Vector4f zwxx() { return new Vector4f(z, w, x, x); }
	public Vector4f zwxy() { return new Vector4f(z, w, x, y); }
	public Vector4f zwxz() { return new Vector4f(z, w, x, z); }
	public Vector4f zwyw() { return new Vector4f(z, w, y, w); }
	public Vector4f zwyx() { return new Vector4f(z, w, y, x); }
	public Vector4f zwyy() { return new Vector4f(z, w, y, y); }
	public Vector4f zwyz() { return new Vector4f(z, w, y, z); }
	public Vector4f zwzw() { return new Vector4f(z, w, z, w); }
	public Vector4f zwzx() { return new Vector4f(z, w, z, x); }
	public Vector4f zwzy() { return new Vector4f(z, w, z, y); }
	public Vector4f zwzz() { return new Vector4f(z, w, z, z); }
	public Vector4f zxww() { return new Vector4f(z, x, w, w); }
	public Vector4f zxwx() { return new Vector4f(z, x, w, x); }
	public Vector4f zxwy() { return new Vector4f(z, x, w, y); }
	public Vector4f zxwz() { return new Vector4f(z, x, w, z); }
	public Vector4f zxxw() { return new Vector4f(z, x, x, w); }
	public Vector4f zxxx() { return new Vector4f(z, x, x, x); }
	public Vector4f zxxy() { return new Vector4f(z, x, x, y); }
	public Vector4f zxxz() { return new Vector4f(z, x, x, z); }
	public Vector4f zxyw() { return new Vector4f(z, x, y, w); }
	public Vector4f zxyx() { return new Vector4f(z, x, y, x); }
	public Vector4f zxyy() { return new Vector4f(z, x, y, y); }
	public Vector4f zxyz() { return new Vector4f(z, x, y, z); }
	public Vector4f zxzw() { return new Vector4f(z, x, z, w); }
	public Vector4f zxzx() { return new Vector4f(z, x, z, x); }
	public Vector4f zxzy() { return new Vector4f(z, x, z, y); }
	public Vector4f zxzz() { return new Vector4f(z, x, z, z); }
	public Vector4f zyww() { return new Vector4f(z, y, w, w); }
	public Vector4f zywx() { return new Vector4f(z, y, w, x); }
	public Vector4f zywy() { return new Vector4f(z, y, w, y); }
	public Vector4f zywz() { return new Vector4f(z, y, w, z); }
	public Vector4f zyxw() { return new Vector4f(z, y, x, w); }
	public Vector4f zyxx() { return new Vector4f(z, y, x, x); }
	public Vector4f zyxy() { return new Vector4f(z, y, x, y); }
	public Vector4f zyxz() { return new Vector4f(z, y, x, z); }
	public Vector4f zyyw() { return new Vector4f(z, y, y, w); }
	public Vector4f zyyx() { return new Vector4f(z, y, y, x); }
	public Vector4f zyyy() { return new Vector4f(z, y, y, y); }
	public Vector4f zyyz() { return new Vector4f(z, y, y, z); }
	public Vector4f zyzw() { return new Vector4f(z, y, z, w); }
	public Vector4f zyzx() { return new Vector4f(z, y, z, x); }
	public Vector4f zyzy() { return new Vector4f(z, y, z, y); }
	public Vector4f zyzz() { return new Vector4f(z, y, z, z); }
	public Vector4f zzww() { return new Vector4f(z, z, w, w); }
	public Vector4f zzwx() { return new Vector4f(z, z, w, x); }
	public Vector4f zzwy() { return new Vector4f(z, z, w, y); }
	public Vector4f zzwz() { return new Vector4f(z, z, w, z); }
	public Vector4f zzxw() { return new Vector4f(z, z, x, w); }
	public Vector4f zzxx() { return new Vector4f(z, z, x, x); }
	public Vector4f zzxy() { return new Vector4f(z, z, x, y); }
	public Vector4f zzxz() { return new Vector4f(z, z, x, z); }
	public Vector4f zzyw() { return new Vector4f(z, z, y, w); }
	public Vector4f zzyx() { return new Vector4f(z, z, y, x); }
	public Vector4f zzyy() { return new Vector4f(z, z, y, y); }
	public Vector4f zzyz() { return new Vector4f(z, z, y, z); }
	public Vector4f zzzw() { return new Vector4f(z, z, z, w); }
	public Vector4f zzzx() { return new Vector4f(z, z, z, x); }
	public Vector4f zzzy() { return new Vector4f(z, z, z, y); }
	public Vector4f zzzz() { return new Vector4f(z, z, z, z); }
}
