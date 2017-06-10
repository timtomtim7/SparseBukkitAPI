package me.tom.sparse.math.vector.doubles;

import me.tom.sparse.math.vector.floats.Vector4f;
import me.tom.sparse.math.vector.integers.Vector4i;

import java.util.function.BiFunction;
import java.util.function.Function;

@SuppressWarnings({"SuspiciousNameCombination", "unused"})
public class Vector4d
{
	protected double x, y, z, w;
	
	public Vector4d()
	{
	
	}
	
	public Vector4d(double x, double y, double z, double w)
	{
		set(x, y, z, w);
	}
	
	public Vector4d(Vector4d other)
	{
		set(other);
	}
	
	public Vector4d(double x, Vector3d other)
	{
		set(x, other);
	}
	
	public Vector4d(Vector3d other, double w)
	{
		set(other, w);
	}
	
	public Vector4d(double x, double y, Vector2d other)
	{
		set(x, y, other);
	}
	
	public Vector4d(Vector2d other, double z, double w)
	{
		set(other, z, w);
	}
	
	public Vector4d(double x, Vector2d other, double w)
	{
		set(x, other, w);
	}
	
	public Vector4d(double value)
	{
		set(value);
	}

	public Vector4d set(double x, double y, double z, double w)
	{
		return setX(x).setY(y).setZ(z).setW(w);
	}

	public Vector4d set(Vector4d other)
	{
		return set(other.x(), other.y(), other.z(), other.w());
	}

	public Vector4d set(double x, Vector3d other)
	{
		return set(x, other.x(), other.y(), other.z());
	}

	public Vector4d set(Vector3d other, double w)
	{
		return set(other.x(), other.y(), other.z(), w);
	}

	public Vector4d set(double x, double y, Vector2d other)
	{
		return set(x, y, other.x(), other.y());
	}

	public Vector4d set(Vector2d other, double z, double w)
	{
		return set(other.x(), other.y(), z, w);
	}

	public Vector4d set(double x, Vector2d other, double w)
	{
		return set(x, other.x(), other.y(), w);
	}

	public Vector4d set(Vector2d other1, Vector2d other2)
	{
		return set(other1.x(), other1.y(), other2.x(), other2.y());
	}

	public Vector4d set(double value)
	{
		return set(value, value, value, value);
	}



	public Vector4d setX(double x)
	{
		this.x = x;
		return this;
	}

	public Vector4d setY(double y)
	{
		this.y = y;
		return this;
	}

	public Vector4d setZ(double z)
	{
		this.z = z;
		return this;
	}

	public Vector4d setW(double w)
	{
		this.w = w;
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

	public double w()
	{
		return w;
	}

	public Vector4d op(Function<Double, Double> function)
	{
		setX(function.apply(x()));
		setY(function.apply(y()));
		setZ(function.apply(z()));
		setW(function.apply(w()));
		return this;
	}

	public Vector4d pairOp(Vector4d other, BiFunction<Double, Double, Double> function)
	{
		setX(function.apply(x(), other.x()));
		setY(function.apply(y(), other.y()));
		setZ(function.apply(z(), other.z()));
		setW(function.apply(w(), other.w()));
		return this;
	}
	
	public Vector4d abs()
	{
		return set(Math.abs(x()), Math.abs(y()), Math.abs(z()), Math.abs(w()));
	}

	public double min()
	{
		return Math.min(x(), Math.min(y(), Math.min(z(), w())));
	}

	public Vector4d min(Vector4d other)
	{
		return min(other.x(), other.y(), other.z(), other.w());
	}

	public Vector4d min(double x, double y, double z, double w)
	{
		return set(Math.min(x(), x), Math.min(y(), y), Math.min(z(), z), Math.min(w(), w));
	}

	public double max()
	{
		return Math.max(x(), Math.max(y(), Math.max(z(), w())));
	}

	public Vector4d max(Vector4d other)
	{
		return max(other.x(), other.y(), other.z(), other.w());
	}

	public Vector4d max(double x, double y, double z, double w)
	{
		return set(Math.max(x(), x), Math.max(y(), y), Math.max(z(), z), Math.max(w(), w));
	}

	public boolean withinMinMax(double minX, double minY, double minZ, double minW, double maxX, double maxY, double maxZ, double maxW)
	{
		double x = x();
		double y = y();
		double z = z();
		double w = w();
		return x >= minX && x <= maxX && y >= minY && y <= maxY && z >= minZ && z <= maxZ && w >= minW && w <= maxW;
	}

	public boolean withinMinMax(Vector4d min, Vector4d max)
	{
		return withinMinMax(min.x(), min.y(), min.z(), min.w(), max.x(), max.y(), max.z(), max.w());
	}

	public boolean within(Vector4d a, Vector4d b)
	{
		return withinMinMax(a.clone().min(b), a.clone().max(b));
	}

	public double sum()
	{
		return x() + y() + z() + w();
	}

	public double dot(Vector4d other)
	{
		return clone().multiply(other).sum();
	}

	public double dot(double x, double y, double z, double w)
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

	public double distance(Vector4d other)
	{
		return clone().subtract(other).length();
	}

	public double distance(double x, double y, double z, double w)
	{
		return clone().subtract(x, y, z, w).length();
	}

	public double distanceSquared(Vector4d other)
	{
		return clone().subtract(other).lengthSquared();
	}

	public Vector4d normalize()
	{
		double length = length();
		return length == 0 ? this : divide(length);
	}

	public Vector4d lerp(Vector4d target, double lerpFactor)
	{
		return target.clone().subtract(this).multiply(lerpFactor).add(this);
	}

	public Vector4d pow(double pow)
	{
		return set(Math.pow(x(), pow), Math.pow(y(), pow), Math.pow(z(), pow), Math.pow(w(), pow));
	}

	public Vector4d floor()
	{
		return set(Math.floor(x()), Math.floor(y()), Math.floor(z()), Math.floor(w()));
	}

	public Vector4d ceil()
	{
		return set(Math.ceil(x()), Math.ceil(y()), Math.ceil(z()), Math.ceil(w()));
	}

	public Vector4d round()
	{
		return set(Math.round(x()), Math.round(y()), Math.round(z()), Math.round(w()));
	}

	public Vector4d add(double v)
	{
		return add(v, v, v, v);
	}

	public Vector4d add(double x, double y, double z, double w)
	{
		return set(x() + x, y() + y, z() + z, w() + w);
	}

	public Vector4d add(Vector4d other)
	{
		return add(other.x(), other.y(), other.z(), other.w());
	}

	public Vector4d subtract(double v)
	{
		return subtract(v, v, v, v);
	}

	public Vector4d subtract(double x, double y, double z, double w)
	{
		return add(-x, -y, -z, -w);
	}

	public Vector4d subtract(Vector4d other)
	{
		return subtract(other.x(), other.y(), other.z(), other.w());
	}

	public Vector4d multiply(double v)
	{
		return multiply(v, v, v, v);
	}

	public Vector4d multiply(double x, double y, double z, double w)
	{
		return set(x() * x, y() * y, z() * z, w() * w);
	}

	public Vector4d multiply(Vector4d other)
	{
//		new Exception().printStackTrace();
		return multiply(other.x(), other.y(), other.z(), other.w());
	}

	public Vector4d divide(double v)
	{
		return divide(v, v, v, v);
	}

	public Vector4d divide(double x, double y, double z, double w)
	{
		return set(x() / x, y() / y, z() / z, w() / w);
	}

	public Vector4d divide(Vector4d other)
	{
		return divide(other.x(), other.y(), other.z(), other.w());
	}


	@Override
	public Vector4d clone()
	{
		return new Vector4d(this);
	}
	
	public boolean equals(Object o)
	{
		if(this == o) return true;
		if(!(o instanceof Vector4d)) return false;
		
		Vector4d vector4d = (Vector4d) o;
		
		if(Double.compare(vector4d.x, x) != 0) return false;
		if(Double.compare(vector4d.y, y) != 0) return false;
		if(Double.compare(vector4d.z, z) != 0) return false;
		return Double.compare(vector4d.w, w) == 0;
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
		temp = Double.doubleToLongBits(w);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	
	@Override
	public String toString()
	{
		return "Vector4d{" +
				"x=" + x +
				", y=" + y +
				", z=" + z +
				", w=" + w +
				'}';
	}
	
	public Vector4f toVector4f()
	{
		return new Vector4f((float)x, (float)y, (float)z, (float)w);
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

	public Vector2d ww()   { return new Vector2d(w, w); }
	public Vector2d wx()   { return new Vector2d(w, x); }
	public Vector2d wy()   { return new Vector2d(w, y); }
	public Vector2d wz()   { return new Vector2d(w, z); }
	public Vector2d xw()   { return new Vector2d(x, w); }
	public Vector2d xx()   { return new Vector2d(x, x); }
	public Vector2d xy()   { return new Vector2d(x, y); }
	public Vector2d xz()   { return new Vector2d(x, z); }
	public Vector2d yw()   { return new Vector2d(y, w); }
	public Vector2d yx()   { return new Vector2d(y, x); }
	public Vector2d yy()   { return new Vector2d(y, y); }
	public Vector2d yz()   { return new Vector2d(y, z); }
	public Vector2d zw()   { return new Vector2d(z, w); }
	public Vector2d zx()   { return new Vector2d(z, x); }
	public Vector2d zy()   { return new Vector2d(z, y); }
	public Vector2d zz()   { return new Vector2d(z, z); }
	public Vector3d www()  { return new Vector3d(w, w, w); }
	public Vector3d wwx()  { return new Vector3d(w, w, x); }
	public Vector3d wwy()  { return new Vector3d(w, w, y); }
	public Vector3d wwz()  { return new Vector3d(w, w, z); }
	public Vector3d wxw()  { return new Vector3d(w, x, w); }
	public Vector3d wxx()  { return new Vector3d(w, x, x); }
	public Vector3d wxy()  { return new Vector3d(w, x, y); }
	public Vector3d wxz()  { return new Vector3d(w, x, z); }
	public Vector3d wyw()  { return new Vector3d(w, y, w); }
	public Vector3d wyx()  { return new Vector3d(w, y, x); }
	public Vector3d wyy()  { return new Vector3d(w, y, y); }
	public Vector3d wyz()  { return new Vector3d(w, y, z); }
	public Vector3d wzw()  { return new Vector3d(w, z, w); }
	public Vector3d wzx()  { return new Vector3d(w, z, x); }
	public Vector3d wzy()  { return new Vector3d(w, z, y); }
	public Vector3d wzz()  { return new Vector3d(w, z, z); }
	public Vector3d xww()  { return new Vector3d(x, w, w); }
	public Vector3d xwx()  { return new Vector3d(x, w, x); }
	public Vector3d xwy()  { return new Vector3d(x, w, y); }
	public Vector3d xwz()  { return new Vector3d(x, w, z); }
	public Vector3d xxw()  { return new Vector3d(x, x, w); }
	public Vector3d xxx()  { return new Vector3d(x, x, x); }
	public Vector3d xxy()  { return new Vector3d(x, x, y); }
	public Vector3d xxz()  { return new Vector3d(x, x, z); }
	public Vector3d xyw()  { return new Vector3d(x, y, w); }
	public Vector3d xyx()  { return new Vector3d(x, y, x); }
	public Vector3d xyy()  { return new Vector3d(x, y, y); }
	public Vector3d xyz()  { return new Vector3d(x, y, z); }
	public Vector3d xzw()  { return new Vector3d(x, z, w); }
	public Vector3d xzx()  { return new Vector3d(x, z, x); }
	public Vector3d xzy()  { return new Vector3d(x, z, y); }
	public Vector3d xzz()  { return new Vector3d(x, z, z); }
	public Vector3d yww()  { return new Vector3d(y, w, w); }
	public Vector3d ywx()  { return new Vector3d(y, w, x); }
	public Vector3d ywy()  { return new Vector3d(y, w, y); }
	public Vector3d ywz()  { return new Vector3d(y, w, z); }
	public Vector3d yxw()  { return new Vector3d(y, x, w); }
	public Vector3d yxx()  { return new Vector3d(y, x, x); }
	public Vector3d yxy()  { return new Vector3d(y, x, y); }
	public Vector3d yxz()  { return new Vector3d(y, x, z); }
	public Vector3d yyw()  { return new Vector3d(y, y, w); }
	public Vector3d yyx()  { return new Vector3d(y, y, x); }
	public Vector3d yyy()  { return new Vector3d(y, y, y); }
	public Vector3d yyz()  { return new Vector3d(y, y, z); }
	public Vector3d yzw()  { return new Vector3d(y, z, w); }
	public Vector3d yzx()  { return new Vector3d(y, z, x); }
	public Vector3d yzy()  { return new Vector3d(y, z, y); }
	public Vector3d yzz()  { return new Vector3d(y, z, z); }
	public Vector3d zww()  { return new Vector3d(z, w, w); }
	public Vector3d zwx()  { return new Vector3d(z, w, x); }
	public Vector3d zwy()  { return new Vector3d(z, w, y); }
	public Vector3d zwz()  { return new Vector3d(z, w, z); }
	public Vector3d zxw()  { return new Vector3d(z, x, w); }
	public Vector3d zxx()  { return new Vector3d(z, x, x); }
	public Vector3d zxy()  { return new Vector3d(z, x, y); }
	public Vector3d zxz()  { return new Vector3d(z, x, z); }
	public Vector3d zyw()  { return new Vector3d(z, y, w); }
	public Vector3d zyx()  { return new Vector3d(z, y, x); }
	public Vector3d zyy()  { return new Vector3d(z, y, y); }
	public Vector3d zyz()  { return new Vector3d(z, y, z); }
	public Vector3d zzw()  { return new Vector3d(z, z, w); }
	public Vector3d zzx()  { return new Vector3d(z, z, x); }
	public Vector3d zzy()  { return new Vector3d(z, z, y); }
	public Vector3d zzz()  { return new Vector3d(z, z, z); }
	public Vector4d wwww() { return new Vector4d(w, w, w, w); }
	public Vector4d wwwx() { return new Vector4d(w, w, w, x); }
	public Vector4d wwwy() { return new Vector4d(w, w, w, y); }
	public Vector4d wwwz() { return new Vector4d(w, w, w, z); }
	public Vector4d wwxw() { return new Vector4d(w, w, x, w); }
	public Vector4d wwxx() { return new Vector4d(w, w, x, x); }
	public Vector4d wwxy() { return new Vector4d(w, w, x, y); }
	public Vector4d wwxz() { return new Vector4d(w, w, x, z); }
	public Vector4d wwyw() { return new Vector4d(w, w, y, w); }
	public Vector4d wwyx() { return new Vector4d(w, w, y, x); }
	public Vector4d wwyy() { return new Vector4d(w, w, y, y); }
	public Vector4d wwyz() { return new Vector4d(w, w, y, z); }
	public Vector4d wwzw() { return new Vector4d(w, w, z, w); }
	public Vector4d wwzx() { return new Vector4d(w, w, z, x); }
	public Vector4d wwzy() { return new Vector4d(w, w, z, y); }
	public Vector4d wwzz() { return new Vector4d(w, w, z, z); }
	public Vector4d wxww() { return new Vector4d(w, x, w, w); }
	public Vector4d wxwx() { return new Vector4d(w, x, w, x); }
	public Vector4d wxwy() { return new Vector4d(w, x, w, y); }
	public Vector4d wxwz() { return new Vector4d(w, x, w, z); }
	public Vector4d wxxw() { return new Vector4d(w, x, x, w); }
	public Vector4d wxxx() { return new Vector4d(w, x, x, x); }
	public Vector4d wxxy() { return new Vector4d(w, x, x, y); }
	public Vector4d wxxz() { return new Vector4d(w, x, x, z); }
	public Vector4d wxyw() { return new Vector4d(w, x, y, w); }
	public Vector4d wxyx() { return new Vector4d(w, x, y, x); }
	public Vector4d wxyy() { return new Vector4d(w, x, y, y); }
	public Vector4d wxyz() { return new Vector4d(w, x, y, z); }
	public Vector4d wxzw() { return new Vector4d(w, x, z, w); }
	public Vector4d wxzx() { return new Vector4d(w, x, z, x); }
	public Vector4d wxzy() { return new Vector4d(w, x, z, y); }
	public Vector4d wxzz() { return new Vector4d(w, x, z, z); }
	public Vector4d wyww() { return new Vector4d(w, y, w, w); }
	public Vector4d wywx() { return new Vector4d(w, y, w, x); }
	public Vector4d wywy() { return new Vector4d(w, y, w, y); }
	public Vector4d wywz() { return new Vector4d(w, y, w, z); }
	public Vector4d wyxw() { return new Vector4d(w, y, x, w); }
	public Vector4d wyxx() { return new Vector4d(w, y, x, x); }
	public Vector4d wyxy() { return new Vector4d(w, y, x, y); }
	public Vector4d wyxz() { return new Vector4d(w, y, x, z); }
	public Vector4d wyyw() { return new Vector4d(w, y, y, w); }
	public Vector4d wyyx() { return new Vector4d(w, y, y, x); }
	public Vector4d wyyy() { return new Vector4d(w, y, y, y); }
	public Vector4d wyyz() { return new Vector4d(w, y, y, z); }
	public Vector4d wyzw() { return new Vector4d(w, y, z, w); }
	public Vector4d wyzx() { return new Vector4d(w, y, z, x); }
	public Vector4d wyzy() { return new Vector4d(w, y, z, y); }
	public Vector4d wyzz() { return new Vector4d(w, y, z, z); }
	public Vector4d wzww() { return new Vector4d(w, z, w, w); }
	public Vector4d wzwx() { return new Vector4d(w, z, w, x); }
	public Vector4d wzwy() { return new Vector4d(w, z, w, y); }
	public Vector4d wzwz() { return new Vector4d(w, z, w, z); }
	public Vector4d wzxw() { return new Vector4d(w, z, x, w); }
	public Vector4d wzxx() { return new Vector4d(w, z, x, x); }
	public Vector4d wzxy() { return new Vector4d(w, z, x, y); }
	public Vector4d wzxz() { return new Vector4d(w, z, x, z); }
	public Vector4d wzyw() { return new Vector4d(w, z, y, w); }
	public Vector4d wzyx() { return new Vector4d(w, z, y, x); }
	public Vector4d wzyy() { return new Vector4d(w, z, y, y); }
	public Vector4d wzyz() { return new Vector4d(w, z, y, z); }
	public Vector4d wzzw() { return new Vector4d(w, z, z, w); }
	public Vector4d wzzx() { return new Vector4d(w, z, z, x); }
	public Vector4d wzzy() { return new Vector4d(w, z, z, y); }
	public Vector4d wzzz() { return new Vector4d(w, z, z, z); }
	public Vector4d xwww() { return new Vector4d(x, w, w, w); }
	public Vector4d xwwx() { return new Vector4d(x, w, w, x); }
	public Vector4d xwwy() { return new Vector4d(x, w, w, y); }
	public Vector4d xwwz() { return new Vector4d(x, w, w, z); }
	public Vector4d xwxw() { return new Vector4d(x, w, x, w); }
	public Vector4d xwxx() { return new Vector4d(x, w, x, x); }
	public Vector4d xwxy() { return new Vector4d(x, w, x, y); }
	public Vector4d xwxz() { return new Vector4d(x, w, x, z); }
	public Vector4d xwyw() { return new Vector4d(x, w, y, w); }
	public Vector4d xwyx() { return new Vector4d(x, w, y, x); }
	public Vector4d xwyy() { return new Vector4d(x, w, y, y); }
	public Vector4d xwyz() { return new Vector4d(x, w, y, z); }
	public Vector4d xwzw() { return new Vector4d(x, w, z, w); }
	public Vector4d xwzx() { return new Vector4d(x, w, z, x); }
	public Vector4d xwzy() { return new Vector4d(x, w, z, y); }
	public Vector4d xwzz() { return new Vector4d(x, w, z, z); }
	public Vector4d xxww() { return new Vector4d(x, x, w, w); }
	public Vector4d xxwx() { return new Vector4d(x, x, w, x); }
	public Vector4d xxwy() { return new Vector4d(x, x, w, y); }
	public Vector4d xxwz() { return new Vector4d(x, x, w, z); }
	public Vector4d xxxw() { return new Vector4d(x, x, x, w); }
	public Vector4d xxxx() { return new Vector4d(x, x, x, x); }
	public Vector4d xxxy() { return new Vector4d(x, x, x, y); }
	public Vector4d xxxz() { return new Vector4d(x, x, x, z); }
	public Vector4d xxyw() { return new Vector4d(x, x, y, w); }
	public Vector4d xxyx() { return new Vector4d(x, x, y, x); }
	public Vector4d xxyy() { return new Vector4d(x, x, y, y); }
	public Vector4d xxyz() { return new Vector4d(x, x, y, z); }
	public Vector4d xxzw() { return new Vector4d(x, x, z, w); }
	public Vector4d xxzx() { return new Vector4d(x, x, z, x); }
	public Vector4d xxzy() { return new Vector4d(x, x, z, y); }
	public Vector4d xxzz() { return new Vector4d(x, x, z, z); }
	public Vector4d xyww() { return new Vector4d(x, y, w, w); }
	public Vector4d xywx() { return new Vector4d(x, y, w, x); }
	public Vector4d xywy() { return new Vector4d(x, y, w, y); }
	public Vector4d xywz() { return new Vector4d(x, y, w, z); }
	public Vector4d xyxw() { return new Vector4d(x, y, x, w); }
	public Vector4d xyxx() { return new Vector4d(x, y, x, x); }
	public Vector4d xyxy() { return new Vector4d(x, y, x, y); }
	public Vector4d xyxz() { return new Vector4d(x, y, x, z); }
	public Vector4d xyyw() { return new Vector4d(x, y, y, w); }
	public Vector4d xyyx() { return new Vector4d(x, y, y, x); }
	public Vector4d xyyy() { return new Vector4d(x, y, y, y); }
	public Vector4d xyyz() { return new Vector4d(x, y, y, z); }
	public Vector4d xyzw() { return new Vector4d(x, y, z, w); }
	public Vector4d xyzx() { return new Vector4d(x, y, z, x); }
	public Vector4d xyzy() { return new Vector4d(x, y, z, y); }
	public Vector4d xyzz() { return new Vector4d(x, y, z, z); }
	public Vector4d xzww() { return new Vector4d(x, z, w, w); }
	public Vector4d xzwx() { return new Vector4d(x, z, w, x); }
	public Vector4d xzwy() { return new Vector4d(x, z, w, y); }
	public Vector4d xzwz() { return new Vector4d(x, z, w, z); }
	public Vector4d xzxw() { return new Vector4d(x, z, x, w); }
	public Vector4d xzxx() { return new Vector4d(x, z, x, x); }
	public Vector4d xzxy() { return new Vector4d(x, z, x, y); }
	public Vector4d xzxz() { return new Vector4d(x, z, x, z); }
	public Vector4d xzyw() { return new Vector4d(x, z, y, w); }
	public Vector4d xzyx() { return new Vector4d(x, z, y, x); }
	public Vector4d xzyy() { return new Vector4d(x, z, y, y); }
	public Vector4d xzyz() { return new Vector4d(x, z, y, z); }
	public Vector4d xzzw() { return new Vector4d(x, z, z, w); }
	public Vector4d xzzx() { return new Vector4d(x, z, z, x); }
	public Vector4d xzzy() { return new Vector4d(x, z, z, y); }
	public Vector4d xzzz() { return new Vector4d(x, z, z, z); }
	public Vector4d ywww() { return new Vector4d(y, w, w, w); }
	public Vector4d ywwx() { return new Vector4d(y, w, w, x); }
	public Vector4d ywwy() { return new Vector4d(y, w, w, y); }
	public Vector4d ywwz() { return new Vector4d(y, w, w, z); }
	public Vector4d ywxw() { return new Vector4d(y, w, x, w); }
	public Vector4d ywxx() { return new Vector4d(y, w, x, x); }
	public Vector4d ywxy() { return new Vector4d(y, w, x, y); }
	public Vector4d ywxz() { return new Vector4d(y, w, x, z); }
	public Vector4d ywyw() { return new Vector4d(y, w, y, w); }
	public Vector4d ywyx() { return new Vector4d(y, w, y, x); }
	public Vector4d ywyy() { return new Vector4d(y, w, y, y); }
	public Vector4d ywyz() { return new Vector4d(y, w, y, z); }
	public Vector4d ywzw() { return new Vector4d(y, w, z, w); }
	public Vector4d ywzx() { return new Vector4d(y, w, z, x); }
	public Vector4d ywzy() { return new Vector4d(y, w, z, y); }
	public Vector4d ywzz() { return new Vector4d(y, w, z, z); }
	public Vector4d yxww() { return new Vector4d(y, x, w, w); }
	public Vector4d yxwx() { return new Vector4d(y, x, w, x); }
	public Vector4d yxwy() { return new Vector4d(y, x, w, y); }
	public Vector4d yxwz() { return new Vector4d(y, x, w, z); }
	public Vector4d yxxw() { return new Vector4d(y, x, x, w); }
	public Vector4d yxxx() { return new Vector4d(y, x, x, x); }
	public Vector4d yxxy() { return new Vector4d(y, x, x, y); }
	public Vector4d yxxz() { return new Vector4d(y, x, x, z); }
	public Vector4d yxyw() { return new Vector4d(y, x, y, w); }
	public Vector4d yxyx() { return new Vector4d(y, x, y, x); }
	public Vector4d yxyy() { return new Vector4d(y, x, y, y); }
	public Vector4d yxyz() { return new Vector4d(y, x, y, z); }
	public Vector4d yxzw() { return new Vector4d(y, x, z, w); }
	public Vector4d yxzx() { return new Vector4d(y, x, z, x); }
	public Vector4d yxzy() { return new Vector4d(y, x, z, y); }
	public Vector4d yxzz() { return new Vector4d(y, x, z, z); }
	public Vector4d yyww() { return new Vector4d(y, y, w, w); }
	public Vector4d yywx() { return new Vector4d(y, y, w, x); }
	public Vector4d yywy() { return new Vector4d(y, y, w, y); }
	public Vector4d yywz() { return new Vector4d(y, y, w, z); }
	public Vector4d yyxw() { return new Vector4d(y, y, x, w); }
	public Vector4d yyxx() { return new Vector4d(y, y, x, x); }
	public Vector4d yyxy() { return new Vector4d(y, y, x, y); }
	public Vector4d yyxz() { return new Vector4d(y, y, x, z); }
	public Vector4d yyyw() { return new Vector4d(y, y, y, w); }
	public Vector4d yyyx() { return new Vector4d(y, y, y, x); }
	public Vector4d yyyy() { return new Vector4d(y, y, y, y); }
	public Vector4d yyyz() { return new Vector4d(y, y, y, z); }
	public Vector4d yyzw() { return new Vector4d(y, y, z, w); }
	public Vector4d yyzx() { return new Vector4d(y, y, z, x); }
	public Vector4d yyzy() { return new Vector4d(y, y, z, y); }
	public Vector4d yyzz() { return new Vector4d(y, y, z, z); }
	public Vector4d yzww() { return new Vector4d(y, z, w, w); }
	public Vector4d yzwx() { return new Vector4d(y, z, w, x); }
	public Vector4d yzwy() { return new Vector4d(y, z, w, y); }
	public Vector4d yzwz() { return new Vector4d(y, z, w, z); }
	public Vector4d yzxw() { return new Vector4d(y, z, x, w); }
	public Vector4d yzxx() { return new Vector4d(y, z, x, x); }
	public Vector4d yzxy() { return new Vector4d(y, z, x, y); }
	public Vector4d yzxz() { return new Vector4d(y, z, x, z); }
	public Vector4d yzyw() { return new Vector4d(y, z, y, w); }
	public Vector4d yzyx() { return new Vector4d(y, z, y, x); }
	public Vector4d yzyy() { return new Vector4d(y, z, y, y); }
	public Vector4d yzyz() { return new Vector4d(y, z, y, z); }
	public Vector4d yzzw() { return new Vector4d(y, z, z, w); }
	public Vector4d yzzx() { return new Vector4d(y, z, z, x); }
	public Vector4d yzzy() { return new Vector4d(y, z, z, y); }
	public Vector4d yzzz() { return new Vector4d(y, z, z, z); }
	public Vector4d zwww() { return new Vector4d(z, w, w, w); }
	public Vector4d zwwx() { return new Vector4d(z, w, w, x); }
	public Vector4d zwwy() { return new Vector4d(z, w, w, y); }
	public Vector4d zwwz() { return new Vector4d(z, w, w, z); }
	public Vector4d zwxw() { return new Vector4d(z, w, x, w); }
	public Vector4d zwxx() { return new Vector4d(z, w, x, x); }
	public Vector4d zwxy() { return new Vector4d(z, w, x, y); }
	public Vector4d zwxz() { return new Vector4d(z, w, x, z); }
	public Vector4d zwyw() { return new Vector4d(z, w, y, w); }
	public Vector4d zwyx() { return new Vector4d(z, w, y, x); }
	public Vector4d zwyy() { return new Vector4d(z, w, y, y); }
	public Vector4d zwyz() { return new Vector4d(z, w, y, z); }
	public Vector4d zwzw() { return new Vector4d(z, w, z, w); }
	public Vector4d zwzx() { return new Vector4d(z, w, z, x); }
	public Vector4d zwzy() { return new Vector4d(z, w, z, y); }
	public Vector4d zwzz() { return new Vector4d(z, w, z, z); }
	public Vector4d zxww() { return new Vector4d(z, x, w, w); }
	public Vector4d zxwx() { return new Vector4d(z, x, w, x); }
	public Vector4d zxwy() { return new Vector4d(z, x, w, y); }
	public Vector4d zxwz() { return new Vector4d(z, x, w, z); }
	public Vector4d zxxw() { return new Vector4d(z, x, x, w); }
	public Vector4d zxxx() { return new Vector4d(z, x, x, x); }
	public Vector4d zxxy() { return new Vector4d(z, x, x, y); }
	public Vector4d zxxz() { return new Vector4d(z, x, x, z); }
	public Vector4d zxyw() { return new Vector4d(z, x, y, w); }
	public Vector4d zxyx() { return new Vector4d(z, x, y, x); }
	public Vector4d zxyy() { return new Vector4d(z, x, y, y); }
	public Vector4d zxyz() { return new Vector4d(z, x, y, z); }
	public Vector4d zxzw() { return new Vector4d(z, x, z, w); }
	public Vector4d zxzx() { return new Vector4d(z, x, z, x); }
	public Vector4d zxzy() { return new Vector4d(z, x, z, y); }
	public Vector4d zxzz() { return new Vector4d(z, x, z, z); }
	public Vector4d zyww() { return new Vector4d(z, y, w, w); }
	public Vector4d zywx() { return new Vector4d(z, y, w, x); }
	public Vector4d zywy() { return new Vector4d(z, y, w, y); }
	public Vector4d zywz() { return new Vector4d(z, y, w, z); }
	public Vector4d zyxw() { return new Vector4d(z, y, x, w); }
	public Vector4d zyxx() { return new Vector4d(z, y, x, x); }
	public Vector4d zyxy() { return new Vector4d(z, y, x, y); }
	public Vector4d zyxz() { return new Vector4d(z, y, x, z); }
	public Vector4d zyyw() { return new Vector4d(z, y, y, w); }
	public Vector4d zyyx() { return new Vector4d(z, y, y, x); }
	public Vector4d zyyy() { return new Vector4d(z, y, y, y); }
	public Vector4d zyyz() { return new Vector4d(z, y, y, z); }
	public Vector4d zyzw() { return new Vector4d(z, y, z, w); }
	public Vector4d zyzx() { return new Vector4d(z, y, z, x); }
	public Vector4d zyzy() { return new Vector4d(z, y, z, y); }
	public Vector4d zyzz() { return new Vector4d(z, y, z, z); }
	public Vector4d zzww() { return new Vector4d(z, z, w, w); }
	public Vector4d zzwx() { return new Vector4d(z, z, w, x); }
	public Vector4d zzwy() { return new Vector4d(z, z, w, y); }
	public Vector4d zzwz() { return new Vector4d(z, z, w, z); }
	public Vector4d zzxw() { return new Vector4d(z, z, x, w); }
	public Vector4d zzxx() { return new Vector4d(z, z, x, x); }
	public Vector4d zzxy() { return new Vector4d(z, z, x, y); }
	public Vector4d zzxz() { return new Vector4d(z, z, x, z); }
	public Vector4d zzyw() { return new Vector4d(z, z, y, w); }
	public Vector4d zzyx() { return new Vector4d(z, z, y, x); }
	public Vector4d zzyy() { return new Vector4d(z, z, y, y); }
	public Vector4d zzyz() { return new Vector4d(z, z, y, z); }
	public Vector4d zzzw() { return new Vector4d(z, z, z, w); }
	public Vector4d zzzx() { return new Vector4d(z, z, z, x); }
	public Vector4d zzzy() { return new Vector4d(z, z, z, y); }
	public Vector4d zzzz() { return new Vector4d(z, z, z, z); }
}
