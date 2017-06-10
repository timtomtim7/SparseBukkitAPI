package me.tom.sparse.math.vector.integers;

import me.tom.sparse.math.vector.doubles.Vector4d;
import me.tom.sparse.math.vector.floats.Vector4f;

import java.util.function.BiFunction;
import java.util.function.Function;

@SuppressWarnings({"SuspiciousNameCombination", "unused"})
public class Vector4i
{
	protected int x, y, z, w;
	
	public Vector4i(int x, int y, int z, int w)
	{
		set(x, y, z, w);
	}
	
	public Vector4i(Vector4i other)
	{
		set(other);
	}
	
	public Vector4i(int x, Vector3i other)
	{
		set(x, other);
	}
	
	public Vector4i(Vector3i other, int w)
	{
		set(other, w);
	}
	
	public Vector4i(int x, int y, Vector2i other)
	{
		set(x, y, other);
	}
	
	public Vector4i(Vector2i other, int z, int w)
	{
		set(other, z, w);
	}
	
	public Vector4i(int x, Vector2i other, int w)
	{
		set(x, other, w);
	}
	
	public Vector4i(int value)
	{
		set(value);
	}
	
	public Vector4i()
	{
	
	}

	public Vector4i set(int x, int y, int z, int w)
	{
		return setX(x).setY(y).setZ(z).setW(w);
	}

	public Vector4i set(Vector4i other)
	{
		return set(other.x(), other.y(), other.z(), other.w());
	}

	public Vector4i set(int x, Vector3i other)
	{
		return set(x, other.x(), other.y(), other.z());
	}

	public Vector4i set(Vector3i other, int w)
	{
		return set(other.x(), other.y(), other.z(), w);
	}

	public Vector4i set(int x, int y, Vector2i other)
	{
		return set(x, y, other.x(), other.y());
	}

	public Vector4i set(Vector2i other, int z, int w)
	{
		return set(other.x(), other.y(), z, w);
	}

	public Vector4i set(int x, Vector2i other, int w)
	{
		return set(x, other.x(), other.y(), w);
	}

	public Vector4i set(Vector2i other1, Vector2i other2)
	{
		return set(other1.x(), other1.y(), other2.x(), other2.y());
	}

	public Vector4i set(int value)
	{
		return set(value, value, value, value);
	}



	public Vector4i setX(int x)
	{
		this.x = x;
		return this;
	}

	public Vector4i setY(int y)
	{
		this.y = y;
		return this;
	}

	public Vector4i setZ(int z)
	{
		this.z = z;
		return this;
	}

	public Vector4i setW(int w)
	{
		this.w = w;
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

	public int w()
	{
		return w;
	}

	public Vector4i op(Function<Integer, Integer> function)
	{
		setX(function.apply(x()));
		setY(function.apply(y()));
		setZ(function.apply(z()));
		setW(function.apply(w()));
		return this;
	}

	public Vector4i pairOp(Vector4i other, BiFunction<Integer, Integer, Integer> function)
	{
		setX(function.apply(x(), other.x()));
		setY(function.apply(y(), other.y()));
		setZ(function.apply(z(), other.z()));
		setW(function.apply(w(), other.w()));
		return this;
	}
	
	public Vector4i abs()
	{
		return set(Math.abs(x()), Math.abs(y()), Math.abs(z()), Math.abs(w()));
	}

	public int min()
	{
		return Math.min(x(), Math.min(y(), Math.min(z(), w())));
	}

	public Vector4i min(Vector4i other)
	{
		return min(other.x(), other.y(), other.z(), other.w());
	}

	public Vector4i min(int x, int y, int z, int w)
	{
		return set(Math.min(x(), x), Math.min(y(), y), Math.min(z(), z), Math.min(w(), w));
	}

	public int max()
	{
		return Math.max(x(), Math.max(y(), Math.max(z(), w())));
	}

	public Vector4i max(Vector4i other)
	{
		return max(other.x(), other.y(), other.z(), other.w());
	}

	public Vector4i max(int x, int y, int z, int w)
	{
		return set(Math.max(x(), x), Math.max(y(), y), Math.max(z(), z), Math.max(w(), w));
	}

	public boolean withinMinMax(int minX, int minY, int minZ, int minW, int maxX, int maxY, int maxZ, int maxW)
	{
		int x = x();
		int y = y();
		int z = z();
		int w = w();
		return x >= minX && x <= maxX && y >= minY && y <= maxY && z >= minZ && z <= maxZ && w >= minW && w <= maxW;
	}

	public boolean withinMinMax(Vector4i min, Vector4i max)
	{
		return withinMinMax(min.x(), min.y(), min.z(), min.w(), max.x(), max.y(), max.z(), max.w());
	}

	public boolean within(Vector4i a, Vector4i b)
	{
		return withinMinMax(a.clone().min(b), a.clone().max(b));
	}

	public int sum()
	{
		return x() + y() + z() + w();
	}

	public double dot(Vector4i other)
	{
		return clone().multiply(other).sum();
	}

	public double dot(int x, int y, int z, int w)
	{
		return clone().multiply(x, y, z, w).sum();
	}

	public double lengthSquared()
	{
		return dot(this);
	}

	public double length()
	{
		return Math.sqrt(lengthSquared());
	}

	public double distance(Vector4i other)
	{
		return clone().subtract(other).length();
	}

	public double distance(int x, int y, int z, int w)
	{
		return clone().subtract(x, y, z, w).length();
	}

	public double distanceSquared(Vector4i other)
	{
		return clone().subtract(other).lengthSquared();
	}

	public Vector4i lerp(Vector4i target, int lerpFactor)
	{
		return target.clone().subtract(this).multiply(lerpFactor).add(this);
	}

	public Vector4i pow(double pow)
	{
		return set((int)Math.pow(x(), pow), (int)Math.pow(y(), pow), (int)Math.pow(z(), pow), (int)Math.pow(w(), pow));
	}

	public Vector4i add(int v)
	{
		return add(v, v, v, v);
	}

	public Vector4i add(int x, int y, int z, int w)
	{
		return set(x() + x, y() + y, z() + z, w() + w);
	}

	public Vector4i add(Vector4i other)
	{
		return add(other.x(), other.y(), other.z(), other.w());
	}

	public Vector4i subtract(int v)
	{
		return subtract(v, v, v, v);
	}

	public Vector4i subtract(int x, int y, int z, int w)
	{
		return add(-x, -y, -z, -w);
	}

	public Vector4i subtract(Vector4i other)
	{
		return subtract(other.x(), other.y(), other.z(), other.w());
	}

	public Vector4i multiply(int v)
	{
		return multiply(v, v, v, v);
	}

	public Vector4i multiply(int x, int y, int z, int w)
	{
		return set(x() * x, y() * y, z() * z, w() * w);
	}

	public Vector4i multiply(Vector4i other)
	{
//		new Exception().printStackTrace();
		return multiply(other.x(), other.y(), other.z(), other.w());
	}

	public Vector4i divide(int v)
	{
		return divide(v, v, v, v);
	}

	public Vector4i divide(int x, int y, int z, int w)
	{
		return set(x() / x, y() / y, z() / z, w() / w);
	}

	public Vector4i divide(Vector4i other)
	{
		return divide(other.x(), other.y(), other.z(), other.w());
	}

	@Override
	public Vector4i clone()
	{
		return new Vector4i(this);
	}
	
	public boolean equals(Object o)
	{
		if(this == o) return true;
		if(!(o instanceof Vector4i)) return false;
		
		Vector4i vector4i = (Vector4i) o;
		
		if(x != vector4i.x) return false;
		if(y != vector4i.y) return false;
		if(z != vector4i.z) return false;
		return w == vector4i.w;
	}
	
	public int hashCode()
	{
		int result = x;
		result = 31 * result + y;
		result = 31 * result + z;
		result = 31 * result + w;
		return result;
	}
	
	@Override
	public String toString()
	{
		return "Vector4i{" +
				"x=" + x +
				", y=" + y +
				", z=" + z +
				", w=" + w +
				'}';
	}
	
	public Vector4f toVector4f()
	{
		return new Vector4f(x, y, z, w);
	}
	
	public Vector4d toVector4d()
	{
		return new Vector4d(x, y, z, w);
	}
	
	/*
	--------------------------------------------------------------------------------------------
								Generated Vector Swizzling code
	--------------------------------------------------------------------------------------------
	 */

	public Vector2i ww()   { return new Vector2i(w, w); }
	public Vector2i wx()   { return new Vector2i(w, x); }
	public Vector2i wy()   { return new Vector2i(w, y); }
	public Vector2i wz()   { return new Vector2i(w, z); }
	public Vector2i xw()   { return new Vector2i(x, w); }
	public Vector2i xx()   { return new Vector2i(x, x); }
	public Vector2i xy()   { return new Vector2i(x, y); }
	public Vector2i xz()   { return new Vector2i(x, z); }
	public Vector2i yw()   { return new Vector2i(y, w); }
	public Vector2i yx()   { return new Vector2i(y, x); }
	public Vector2i yy()   { return new Vector2i(y, y); }
	public Vector2i yz()   { return new Vector2i(y, z); }
	public Vector2i zw()   { return new Vector2i(z, w); }
	public Vector2i zx()   { return new Vector2i(z, x); }
	public Vector2i zy()   { return new Vector2i(z, y); }
	public Vector2i zz()   { return new Vector2i(z, z); }
	public Vector3i www()  { return new Vector3i(w, w, w); }
	public Vector3i wwx()  { return new Vector3i(w, w, x); }
	public Vector3i wwy()  { return new Vector3i(w, w, y); }
	public Vector3i wwz()  { return new Vector3i(w, w, z); }
	public Vector3i wxw()  { return new Vector3i(w, x, w); }
	public Vector3i wxx()  { return new Vector3i(w, x, x); }
	public Vector3i wxy()  { return new Vector3i(w, x, y); }
	public Vector3i wxz()  { return new Vector3i(w, x, z); }
	public Vector3i wyw()  { return new Vector3i(w, y, w); }
	public Vector3i wyx()  { return new Vector3i(w, y, x); }
	public Vector3i wyy()  { return new Vector3i(w, y, y); }
	public Vector3i wyz()  { return new Vector3i(w, y, z); }
	public Vector3i wzw()  { return new Vector3i(w, z, w); }
	public Vector3i wzx()  { return new Vector3i(w, z, x); }
	public Vector3i wzy()  { return new Vector3i(w, z, y); }
	public Vector3i wzz()  { return new Vector3i(w, z, z); }
	public Vector3i xww()  { return new Vector3i(x, w, w); }
	public Vector3i xwx()  { return new Vector3i(x, w, x); }
	public Vector3i xwy()  { return new Vector3i(x, w, y); }
	public Vector3i xwz()  { return new Vector3i(x, w, z); }
	public Vector3i xxw()  { return new Vector3i(x, x, w); }
	public Vector3i xxx()  { return new Vector3i(x, x, x); }
	public Vector3i xxy()  { return new Vector3i(x, x, y); }
	public Vector3i xxz()  { return new Vector3i(x, x, z); }
	public Vector3i xyw()  { return new Vector3i(x, y, w); }
	public Vector3i xyx()  { return new Vector3i(x, y, x); }
	public Vector3i xyy()  { return new Vector3i(x, y, y); }
	public Vector3i xyz()  { return new Vector3i(x, y, z); }
	public Vector3i xzw()  { return new Vector3i(x, z, w); }
	public Vector3i xzx()  { return new Vector3i(x, z, x); }
	public Vector3i xzy()  { return new Vector3i(x, z, y); }
	public Vector3i xzz()  { return new Vector3i(x, z, z); }
	public Vector3i yww()  { return new Vector3i(y, w, w); }
	public Vector3i ywx()  { return new Vector3i(y, w, x); }
	public Vector3i ywy()  { return new Vector3i(y, w, y); }
	public Vector3i ywz()  { return new Vector3i(y, w, z); }
	public Vector3i yxw()  { return new Vector3i(y, x, w); }
	public Vector3i yxx()  { return new Vector3i(y, x, x); }
	public Vector3i yxy()  { return new Vector3i(y, x, y); }
	public Vector3i yxz()  { return new Vector3i(y, x, z); }
	public Vector3i yyw()  { return new Vector3i(y, y, w); }
	public Vector3i yyx()  { return new Vector3i(y, y, x); }
	public Vector3i yyy()  { return new Vector3i(y, y, y); }
	public Vector3i yyz()  { return new Vector3i(y, y, z); }
	public Vector3i yzw()  { return new Vector3i(y, z, w); }
	public Vector3i yzx()  { return new Vector3i(y, z, x); }
	public Vector3i yzy()  { return new Vector3i(y, z, y); }
	public Vector3i yzz()  { return new Vector3i(y, z, z); }
	public Vector3i zww()  { return new Vector3i(z, w, w); }
	public Vector3i zwx()  { return new Vector3i(z, w, x); }
	public Vector3i zwy()  { return new Vector3i(z, w, y); }
	public Vector3i zwz()  { return new Vector3i(z, w, z); }
	public Vector3i zxw()  { return new Vector3i(z, x, w); }
	public Vector3i zxx()  { return new Vector3i(z, x, x); }
	public Vector3i zxy()  { return new Vector3i(z, x, y); }
	public Vector3i zxz()  { return new Vector3i(z, x, z); }
	public Vector3i zyw()  { return new Vector3i(z, y, w); }
	public Vector3i zyx()  { return new Vector3i(z, y, x); }
	public Vector3i zyy()  { return new Vector3i(z, y, y); }
	public Vector3i zyz()  { return new Vector3i(z, y, z); }
	public Vector3i zzw()  { return new Vector3i(z, z, w); }
	public Vector3i zzx()  { return new Vector3i(z, z, x); }
	public Vector3i zzy()  { return new Vector3i(z, z, y); }
	public Vector3i zzz()  { return new Vector3i(z, z, z); }
	public Vector4i wwww() { return new Vector4i(w, w, w, w); }
	public Vector4i wwwx() { return new Vector4i(w, w, w, x); }
	public Vector4i wwwy() { return new Vector4i(w, w, w, y); }
	public Vector4i wwwz() { return new Vector4i(w, w, w, z); }
	public Vector4i wwxw() { return new Vector4i(w, w, x, w); }
	public Vector4i wwxx() { return new Vector4i(w, w, x, x); }
	public Vector4i wwxy() { return new Vector4i(w, w, x, y); }
	public Vector4i wwxz() { return new Vector4i(w, w, x, z); }
	public Vector4i wwyw() { return new Vector4i(w, w, y, w); }
	public Vector4i wwyx() { return new Vector4i(w, w, y, x); }
	public Vector4i wwyy() { return new Vector4i(w, w, y, y); }
	public Vector4i wwyz() { return new Vector4i(w, w, y, z); }
	public Vector4i wwzw() { return new Vector4i(w, w, z, w); }
	public Vector4i wwzx() { return new Vector4i(w, w, z, x); }
	public Vector4i wwzy() { return new Vector4i(w, w, z, y); }
	public Vector4i wwzz() { return new Vector4i(w, w, z, z); }
	public Vector4i wxww() { return new Vector4i(w, x, w, w); }
	public Vector4i wxwx() { return new Vector4i(w, x, w, x); }
	public Vector4i wxwy() { return new Vector4i(w, x, w, y); }
	public Vector4i wxwz() { return new Vector4i(w, x, w, z); }
	public Vector4i wxxw() { return new Vector4i(w, x, x, w); }
	public Vector4i wxxx() { return new Vector4i(w, x, x, x); }
	public Vector4i wxxy() { return new Vector4i(w, x, x, y); }
	public Vector4i wxxz() { return new Vector4i(w, x, x, z); }
	public Vector4i wxyw() { return new Vector4i(w, x, y, w); }
	public Vector4i wxyx() { return new Vector4i(w, x, y, x); }
	public Vector4i wxyy() { return new Vector4i(w, x, y, y); }
	public Vector4i wxyz() { return new Vector4i(w, x, y, z); }
	public Vector4i wxzw() { return new Vector4i(w, x, z, w); }
	public Vector4i wxzx() { return new Vector4i(w, x, z, x); }
	public Vector4i wxzy() { return new Vector4i(w, x, z, y); }
	public Vector4i wxzz() { return new Vector4i(w, x, z, z); }
	public Vector4i wyww() { return new Vector4i(w, y, w, w); }
	public Vector4i wywx() { return new Vector4i(w, y, w, x); }
	public Vector4i wywy() { return new Vector4i(w, y, w, y); }
	public Vector4i wywz() { return new Vector4i(w, y, w, z); }
	public Vector4i wyxw() { return new Vector4i(w, y, x, w); }
	public Vector4i wyxx() { return new Vector4i(w, y, x, x); }
	public Vector4i wyxy() { return new Vector4i(w, y, x, y); }
	public Vector4i wyxz() { return new Vector4i(w, y, x, z); }
	public Vector4i wyyw() { return new Vector4i(w, y, y, w); }
	public Vector4i wyyx() { return new Vector4i(w, y, y, x); }
	public Vector4i wyyy() { return new Vector4i(w, y, y, y); }
	public Vector4i wyyz() { return new Vector4i(w, y, y, z); }
	public Vector4i wyzw() { return new Vector4i(w, y, z, w); }
	public Vector4i wyzx() { return new Vector4i(w, y, z, x); }
	public Vector4i wyzy() { return new Vector4i(w, y, z, y); }
	public Vector4i wyzz() { return new Vector4i(w, y, z, z); }
	public Vector4i wzww() { return new Vector4i(w, z, w, w); }
	public Vector4i wzwx() { return new Vector4i(w, z, w, x); }
	public Vector4i wzwy() { return new Vector4i(w, z, w, y); }
	public Vector4i wzwz() { return new Vector4i(w, z, w, z); }
	public Vector4i wzxw() { return new Vector4i(w, z, x, w); }
	public Vector4i wzxx() { return new Vector4i(w, z, x, x); }
	public Vector4i wzxy() { return new Vector4i(w, z, x, y); }
	public Vector4i wzxz() { return new Vector4i(w, z, x, z); }
	public Vector4i wzyw() { return new Vector4i(w, z, y, w); }
	public Vector4i wzyx() { return new Vector4i(w, z, y, x); }
	public Vector4i wzyy() { return new Vector4i(w, z, y, y); }
	public Vector4i wzyz() { return new Vector4i(w, z, y, z); }
	public Vector4i wzzw() { return new Vector4i(w, z, z, w); }
	public Vector4i wzzx() { return new Vector4i(w, z, z, x); }
	public Vector4i wzzy() { return new Vector4i(w, z, z, y); }
	public Vector4i wzzz() { return new Vector4i(w, z, z, z); }
	public Vector4i xwww() { return new Vector4i(x, w, w, w); }
	public Vector4i xwwx() { return new Vector4i(x, w, w, x); }
	public Vector4i xwwy() { return new Vector4i(x, w, w, y); }
	public Vector4i xwwz() { return new Vector4i(x, w, w, z); }
	public Vector4i xwxw() { return new Vector4i(x, w, x, w); }
	public Vector4i xwxx() { return new Vector4i(x, w, x, x); }
	public Vector4i xwxy() { return new Vector4i(x, w, x, y); }
	public Vector4i xwxz() { return new Vector4i(x, w, x, z); }
	public Vector4i xwyw() { return new Vector4i(x, w, y, w); }
	public Vector4i xwyx() { return new Vector4i(x, w, y, x); }
	public Vector4i xwyy() { return new Vector4i(x, w, y, y); }
	public Vector4i xwyz() { return new Vector4i(x, w, y, z); }
	public Vector4i xwzw() { return new Vector4i(x, w, z, w); }
	public Vector4i xwzx() { return new Vector4i(x, w, z, x); }
	public Vector4i xwzy() { return new Vector4i(x, w, z, y); }
	public Vector4i xwzz() { return new Vector4i(x, w, z, z); }
	public Vector4i xxww() { return new Vector4i(x, x, w, w); }
	public Vector4i xxwx() { return new Vector4i(x, x, w, x); }
	public Vector4i xxwy() { return new Vector4i(x, x, w, y); }
	public Vector4i xxwz() { return new Vector4i(x, x, w, z); }
	public Vector4i xxxw() { return new Vector4i(x, x, x, w); }
	public Vector4i xxxx() { return new Vector4i(x, x, x, x); }
	public Vector4i xxxy() { return new Vector4i(x, x, x, y); }
	public Vector4i xxxz() { return new Vector4i(x, x, x, z); }
	public Vector4i xxyw() { return new Vector4i(x, x, y, w); }
	public Vector4i xxyx() { return new Vector4i(x, x, y, x); }
	public Vector4i xxyy() { return new Vector4i(x, x, y, y); }
	public Vector4i xxyz() { return new Vector4i(x, x, y, z); }
	public Vector4i xxzw() { return new Vector4i(x, x, z, w); }
	public Vector4i xxzx() { return new Vector4i(x, x, z, x); }
	public Vector4i xxzy() { return new Vector4i(x, x, z, y); }
	public Vector4i xxzz() { return new Vector4i(x, x, z, z); }
	public Vector4i xyww() { return new Vector4i(x, y, w, w); }
	public Vector4i xywx() { return new Vector4i(x, y, w, x); }
	public Vector4i xywy() { return new Vector4i(x, y, w, y); }
	public Vector4i xywz() { return new Vector4i(x, y, w, z); }
	public Vector4i xyxw() { return new Vector4i(x, y, x, w); }
	public Vector4i xyxx() { return new Vector4i(x, y, x, x); }
	public Vector4i xyxy() { return new Vector4i(x, y, x, y); }
	public Vector4i xyxz() { return new Vector4i(x, y, x, z); }
	public Vector4i xyyw() { return new Vector4i(x, y, y, w); }
	public Vector4i xyyx() { return new Vector4i(x, y, y, x); }
	public Vector4i xyyy() { return new Vector4i(x, y, y, y); }
	public Vector4i xyyz() { return new Vector4i(x, y, y, z); }
	public Vector4i xyzw() { return new Vector4i(x, y, z, w); }
	public Vector4i xyzx() { return new Vector4i(x, y, z, x); }
	public Vector4i xyzy() { return new Vector4i(x, y, z, y); }
	public Vector4i xyzz() { return new Vector4i(x, y, z, z); }
	public Vector4i xzww() { return new Vector4i(x, z, w, w); }
	public Vector4i xzwx() { return new Vector4i(x, z, w, x); }
	public Vector4i xzwy() { return new Vector4i(x, z, w, y); }
	public Vector4i xzwz() { return new Vector4i(x, z, w, z); }
	public Vector4i xzxw() { return new Vector4i(x, z, x, w); }
	public Vector4i xzxx() { return new Vector4i(x, z, x, x); }
	public Vector4i xzxy() { return new Vector4i(x, z, x, y); }
	public Vector4i xzxz() { return new Vector4i(x, z, x, z); }
	public Vector4i xzyw() { return new Vector4i(x, z, y, w); }
	public Vector4i xzyx() { return new Vector4i(x, z, y, x); }
	public Vector4i xzyy() { return new Vector4i(x, z, y, y); }
	public Vector4i xzyz() { return new Vector4i(x, z, y, z); }
	public Vector4i xzzw() { return new Vector4i(x, z, z, w); }
	public Vector4i xzzx() { return new Vector4i(x, z, z, x); }
	public Vector4i xzzy() { return new Vector4i(x, z, z, y); }
	public Vector4i xzzz() { return new Vector4i(x, z, z, z); }
	public Vector4i ywww() { return new Vector4i(y, w, w, w); }
	public Vector4i ywwx() { return new Vector4i(y, w, w, x); }
	public Vector4i ywwy() { return new Vector4i(y, w, w, y); }
	public Vector4i ywwz() { return new Vector4i(y, w, w, z); }
	public Vector4i ywxw() { return new Vector4i(y, w, x, w); }
	public Vector4i ywxx() { return new Vector4i(y, w, x, x); }
	public Vector4i ywxy() { return new Vector4i(y, w, x, y); }
	public Vector4i ywxz() { return new Vector4i(y, w, x, z); }
	public Vector4i ywyw() { return new Vector4i(y, w, y, w); }
	public Vector4i ywyx() { return new Vector4i(y, w, y, x); }
	public Vector4i ywyy() { return new Vector4i(y, w, y, y); }
	public Vector4i ywyz() { return new Vector4i(y, w, y, z); }
	public Vector4i ywzw() { return new Vector4i(y, w, z, w); }
	public Vector4i ywzx() { return new Vector4i(y, w, z, x); }
	public Vector4i ywzy() { return new Vector4i(y, w, z, y); }
	public Vector4i ywzz() { return new Vector4i(y, w, z, z); }
	public Vector4i yxww() { return new Vector4i(y, x, w, w); }
	public Vector4i yxwx() { return new Vector4i(y, x, w, x); }
	public Vector4i yxwy() { return new Vector4i(y, x, w, y); }
	public Vector4i yxwz() { return new Vector4i(y, x, w, z); }
	public Vector4i yxxw() { return new Vector4i(y, x, x, w); }
	public Vector4i yxxx() { return new Vector4i(y, x, x, x); }
	public Vector4i yxxy() { return new Vector4i(y, x, x, y); }
	public Vector4i yxxz() { return new Vector4i(y, x, x, z); }
	public Vector4i yxyw() { return new Vector4i(y, x, y, w); }
	public Vector4i yxyx() { return new Vector4i(y, x, y, x); }
	public Vector4i yxyy() { return new Vector4i(y, x, y, y); }
	public Vector4i yxyz() { return new Vector4i(y, x, y, z); }
	public Vector4i yxzw() { return new Vector4i(y, x, z, w); }
	public Vector4i yxzx() { return new Vector4i(y, x, z, x); }
	public Vector4i yxzy() { return new Vector4i(y, x, z, y); }
	public Vector4i yxzz() { return new Vector4i(y, x, z, z); }
	public Vector4i yyww() { return new Vector4i(y, y, w, w); }
	public Vector4i yywx() { return new Vector4i(y, y, w, x); }
	public Vector4i yywy() { return new Vector4i(y, y, w, y); }
	public Vector4i yywz() { return new Vector4i(y, y, w, z); }
	public Vector4i yyxw() { return new Vector4i(y, y, x, w); }
	public Vector4i yyxx() { return new Vector4i(y, y, x, x); }
	public Vector4i yyxy() { return new Vector4i(y, y, x, y); }
	public Vector4i yyxz() { return new Vector4i(y, y, x, z); }
	public Vector4i yyyw() { return new Vector4i(y, y, y, w); }
	public Vector4i yyyx() { return new Vector4i(y, y, y, x); }
	public Vector4i yyyy() { return new Vector4i(y, y, y, y); }
	public Vector4i yyyz() { return new Vector4i(y, y, y, z); }
	public Vector4i yyzw() { return new Vector4i(y, y, z, w); }
	public Vector4i yyzx() { return new Vector4i(y, y, z, x); }
	public Vector4i yyzy() { return new Vector4i(y, y, z, y); }
	public Vector4i yyzz() { return new Vector4i(y, y, z, z); }
	public Vector4i yzww() { return new Vector4i(y, z, w, w); }
	public Vector4i yzwx() { return new Vector4i(y, z, w, x); }
	public Vector4i yzwy() { return new Vector4i(y, z, w, y); }
	public Vector4i yzwz() { return new Vector4i(y, z, w, z); }
	public Vector4i yzxw() { return new Vector4i(y, z, x, w); }
	public Vector4i yzxx() { return new Vector4i(y, z, x, x); }
	public Vector4i yzxy() { return new Vector4i(y, z, x, y); }
	public Vector4i yzxz() { return new Vector4i(y, z, x, z); }
	public Vector4i yzyw() { return new Vector4i(y, z, y, w); }
	public Vector4i yzyx() { return new Vector4i(y, z, y, x); }
	public Vector4i yzyy() { return new Vector4i(y, z, y, y); }
	public Vector4i yzyz() { return new Vector4i(y, z, y, z); }
	public Vector4i yzzw() { return new Vector4i(y, z, z, w); }
	public Vector4i yzzx() { return new Vector4i(y, z, z, x); }
	public Vector4i yzzy() { return new Vector4i(y, z, z, y); }
	public Vector4i yzzz() { return new Vector4i(y, z, z, z); }
	public Vector4i zwww() { return new Vector4i(z, w, w, w); }
	public Vector4i zwwx() { return new Vector4i(z, w, w, x); }
	public Vector4i zwwy() { return new Vector4i(z, w, w, y); }
	public Vector4i zwwz() { return new Vector4i(z, w, w, z); }
	public Vector4i zwxw() { return new Vector4i(z, w, x, w); }
	public Vector4i zwxx() { return new Vector4i(z, w, x, x); }
	public Vector4i zwxy() { return new Vector4i(z, w, x, y); }
	public Vector4i zwxz() { return new Vector4i(z, w, x, z); }
	public Vector4i zwyw() { return new Vector4i(z, w, y, w); }
	public Vector4i zwyx() { return new Vector4i(z, w, y, x); }
	public Vector4i zwyy() { return new Vector4i(z, w, y, y); }
	public Vector4i zwyz() { return new Vector4i(z, w, y, z); }
	public Vector4i zwzw() { return new Vector4i(z, w, z, w); }
	public Vector4i zwzx() { return new Vector4i(z, w, z, x); }
	public Vector4i zwzy() { return new Vector4i(z, w, z, y); }
	public Vector4i zwzz() { return new Vector4i(z, w, z, z); }
	public Vector4i zxww() { return new Vector4i(z, x, w, w); }
	public Vector4i zxwx() { return new Vector4i(z, x, w, x); }
	public Vector4i zxwy() { return new Vector4i(z, x, w, y); }
	public Vector4i zxwz() { return new Vector4i(z, x, w, z); }
	public Vector4i zxxw() { return new Vector4i(z, x, x, w); }
	public Vector4i zxxx() { return new Vector4i(z, x, x, x); }
	public Vector4i zxxy() { return new Vector4i(z, x, x, y); }
	public Vector4i zxxz() { return new Vector4i(z, x, x, z); }
	public Vector4i zxyw() { return new Vector4i(z, x, y, w); }
	public Vector4i zxyx() { return new Vector4i(z, x, y, x); }
	public Vector4i zxyy() { return new Vector4i(z, x, y, y); }
	public Vector4i zxyz() { return new Vector4i(z, x, y, z); }
	public Vector4i zxzw() { return new Vector4i(z, x, z, w); }
	public Vector4i zxzx() { return new Vector4i(z, x, z, x); }
	public Vector4i zxzy() { return new Vector4i(z, x, z, y); }
	public Vector4i zxzz() { return new Vector4i(z, x, z, z); }
	public Vector4i zyww() { return new Vector4i(z, y, w, w); }
	public Vector4i zywx() { return new Vector4i(z, y, w, x); }
	public Vector4i zywy() { return new Vector4i(z, y, w, y); }
	public Vector4i zywz() { return new Vector4i(z, y, w, z); }
	public Vector4i zyxw() { return new Vector4i(z, y, x, w); }
	public Vector4i zyxx() { return new Vector4i(z, y, x, x); }
	public Vector4i zyxy() { return new Vector4i(z, y, x, y); }
	public Vector4i zyxz() { return new Vector4i(z, y, x, z); }
	public Vector4i zyyw() { return new Vector4i(z, y, y, w); }
	public Vector4i zyyx() { return new Vector4i(z, y, y, x); }
	public Vector4i zyyy() { return new Vector4i(z, y, y, y); }
	public Vector4i zyyz() { return new Vector4i(z, y, y, z); }
	public Vector4i zyzw() { return new Vector4i(z, y, z, w); }
	public Vector4i zyzx() { return new Vector4i(z, y, z, x); }
	public Vector4i zyzy() { return new Vector4i(z, y, z, y); }
	public Vector4i zyzz() { return new Vector4i(z, y, z, z); }
	public Vector4i zzww() { return new Vector4i(z, z, w, w); }
	public Vector4i zzwx() { return new Vector4i(z, z, w, x); }
	public Vector4i zzwy() { return new Vector4i(z, z, w, y); }
	public Vector4i zzwz() { return new Vector4i(z, z, w, z); }
	public Vector4i zzxw() { return new Vector4i(z, z, x, w); }
	public Vector4i zzxx() { return new Vector4i(z, z, x, x); }
	public Vector4i zzxy() { return new Vector4i(z, z, x, y); }
	public Vector4i zzxz() { return new Vector4i(z, z, x, z); }
	public Vector4i zzyw() { return new Vector4i(z, z, y, w); }
	public Vector4i zzyx() { return new Vector4i(z, z, y, x); }
	public Vector4i zzyy() { return new Vector4i(z, z, y, y); }
	public Vector4i zzyz() { return new Vector4i(z, z, y, z); }
	public Vector4i zzzw() { return new Vector4i(z, z, z, w); }
	public Vector4i zzzx() { return new Vector4i(z, z, z, x); }
	public Vector4i zzzy() { return new Vector4i(z, z, z, y); }
	public Vector4i zzzz() { return new Vector4i(z, z, z, z); }
}
