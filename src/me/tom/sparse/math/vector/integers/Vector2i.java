package me.tom.sparse.math.vector.integers;

import me.tom.sparse.math.vector.doubles.Vector2d;
import me.tom.sparse.math.vector.floats.Vector2f;
import me.tom.sparse.math.vector.floats.Vector4f;

import java.util.function.BiFunction;
import java.util.function.Function;

@SuppressWarnings({"SuspiciousNameCombination", "unused"})
public class Vector2i
{
	protected int x, y;
	
	public Vector2i(Vector2i other)
	{
		set(other);
	}
	
	public Vector2i(int x, int y)
	{
		set(x, y);
	}
	
	public Vector2i(int value)
	{
		set(value);
	}
	
	public Vector2i()
	{
	
	}


	public Vector2i set(Vector2i other)
	{
		return set(other.x, other.y);
	}

	public Vector2i set(int value)
	{
		return set(value, value);
	}

	public Vector2i set(int x, int y)
	{
		setX(x);
		setY(y);
		return this;
	}

	public Vector2i setX(int x)
	{
		this.x = x;
		return this;
	}

	public Vector2i setY(int y)
	{
		this.y = y;
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

	public double ratio()
	{
		return x()/y();
	}

	public Vector2i op(Function<Integer, Integer> function)
	{
		setX(function.apply(x()));
		setY(function.apply(y()));
		return this;
	}

	public Vector2i pairOp(Vector2i other, BiFunction<Integer, Integer, Integer> function)
	{
		setX(function.apply(x(), other.x()));
		setY(function.apply(y(), other.y()));
		return this;
	}

	public Vector2i abs()
	{
		return set(Math.abs(x()), Math.abs(y()));
	}

	public Vector2i rotate(double angle)
	{
//		double rad = Math.toRadians(angle);
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);
		
		double x = x();
		double y = y();
		double nx = x * cos - y * sin;
		double ny = x * sin + y * cos;
		return set((int)Math.floor(nx), (int)Math.floor(ny));
	}
	
	public float min()
	{
		return Math.min(x(), y());
	}

	public Vector2i min(Vector2i other)
	{
		return min(other.x(), other.y());
	}

	public Vector2i min(int x, int y)
	{
		return set(Math.min(x(), x), Math.min(y(), y));
	}

	public float max()
	{
		return Math.max(x(), y());
	}

	public Vector2i max(Vector2i other)
	{
		return max(other.x(), other.y());
	}

	public Vector2i max(int x, int y)
	{
		return set(Math.max(x(), x), Math.max(y(), y));
	}
	
	public boolean withinMinMax(int minX, int minY, int maxX, int maxY)
	{
		float x = x();
		float y = y();
		return x >= minX && x <= maxX && y >= minY && y <= maxY;
	}

	public boolean withinMinMax(Vector2i min, Vector2i max)
	{
		return withinMinMax(min.x(), min.y(), max.x(), max.y());
	}
	
	public boolean within(Vector2i a, Vector2i b)
	{
		return withinMinMax(a.clone().min(b), a.clone().max(b));
	}
	
	public int sum()
	{
		return x() + y();
	}

	public int dot(Vector2i other)
	{
		return dot(other.x(), other.y());
	}

	public int dot(int x, int y)
	{
		return clone().multiply(x, y).sum();
	}

	public double angle(Vector2i other)
	{
		return angle(other.x(), other.y());
	}

	public double angle(int x, int y)
	{
		return Math.atan2(y, x) - Math.atan2(y(), x());
	}

	public double angle()
	{
		return Math.atan2(y(), x());
	}

	public double lengthSquared()
	{
		return dot(this);
	}
	
	public double length()
	{
		return (float) Math.sqrt(lengthSquared());
	}
	
	public double distance(Vector2i other)
	{
		return clone().subtract(other).length();
	}
	
	public double distance(int x, int y)
	{
		return clone().subtract(x, y).length();
	}

	public double distanceSquared(Vector2i other)
	{
		return clone().subtract(other).lengthSquared();
	}

	public double distanceSquared(int x, int y)
	{
		return clone().subtract(x, y).lengthSquared();
	}
	
	public int cross(Vector2i other)
	{
		return cross(other.x(), other.y());
	}

	public int cross(int x, int y)
	{
		return x() * y - y() * x;
	}

	public Vector2i cross()
	{
		return new Vector2i(y(), -x());
	}

	public Vector2i pow(double pow)
	{
		return set((int) Math.pow(x(), pow), (int) Math.pow(y(), pow));
	}

	public Vector2i add(int v)
	{
		return add(v, v);
	}

	public Vector2i add(int x, int y)
	{
		return set(x() + x, y() + y);
	}

	public Vector2i add(Vector2i other)
	{
		return add(other.x(), other.y());
	}
	
	public Vector2i subtract(int v)
	{
		return subtract(v, v);
	}

	public Vector2i subtract(int x, int y)
	{
		return add(-x, -y);
	}

	public Vector2i subtract(Vector2i other)
	{
		return subtract(other.x(), other.y());
	}
	
	public Vector2i multiply(int v)
	{
		return multiply(v, v);
	}

	public Vector2i multiply(int x, int y)
	{
		return set(x() * x, y() * y);
	}

	public Vector2i multiply(Vector2i other)
	{
		return multiply(other.x(), other.y());
	}
	
	public Vector2i divide(int v)
	{
		return divide(v, v);
	}

	public Vector2i divide(int x, int y)
	{
		return set(x() / x, y() / y);
	}

	public Vector2i divide(Vector2i other)
	{
		return divide(other.x(), other.y());
	}

	@Override
	public Vector2i clone()
	{
		return new Vector2i(this);
	}

	public boolean equals(Object o)
	{
		if(this == o) return true;
		if(!(o instanceof Vector2i)) return false;
		
		Vector2i vector2i = (Vector2i) o;
		
		if(x != vector2i.x) return false;
		return y == vector2i.y;
	}
	
	public int hashCode()
	{
		int result = x;
		result = 31 * result + y;
		return result;
	}
	
	@Override
	public String toString()
	{
		return "Vector2i{" +
				"x=" + x +
				", y=" + y +
				'}';
	}
	
	public Vector2f toVector2f()
	{
		return new Vector2f(x, y);
	}
	
	public Vector2d toVector2d()
	{
		return new Vector2d(x, y);
	}
	
	/*
	--------------------------------------------------------------------------------------------
								Generated Vector Swizzling code
	--------------------------------------------------------------------------------------------
	 */

	public Vector2i xx()   { return new Vector2i(x, x); }
	public Vector2i xy()   { return new Vector2i(x, y); }
	public Vector2i yx()   { return new Vector2i(y, x); }
	public Vector2i yy()   { return new Vector2i(y, y); }
	public Vector3i xxx()  { return new Vector3i(x, x, x); }
	public Vector3i xxy()  { return new Vector3i(x, x, y); }
	public Vector3i xyx()  { return new Vector3i(x, y, x); }
	public Vector3i xyy()  { return new Vector3i(x, y, y); }
	public Vector3i yxx()  { return new Vector3i(y, x, x); }
	public Vector3i yxy()  { return new Vector3i(y, x, y); }
	public Vector3i yyx()  { return new Vector3i(y, y, x); }
	public Vector3i yyy()  { return new Vector3i(y, y, y); }
	public Vector4i xxxx() { return new Vector4i(x, x, x, x); }
	public Vector4i xxxy() { return new Vector4i(x, x, x, y); }
	public Vector4i xxyx() { return new Vector4i(x, x, y, x); }
	public Vector4i xxyy() { return new Vector4i(x, x, y, y); }
	public Vector4i xyxx() { return new Vector4i(x, y, x, x); }
	public Vector4i xyxy() { return new Vector4i(x, y, x, y); }
	public Vector4i xyyx() { return new Vector4i(x, y, y, x); }
	public Vector4i xyyy() { return new Vector4i(x, y, y, y); }
	public Vector4i yxxx() { return new Vector4i(y, x, x, x); }
	public Vector4i yxxy() { return new Vector4i(y, x, x, y); }
	public Vector4i yxyx() { return new Vector4i(y, x, y, x); }
	public Vector4i yxyy() { return new Vector4i(y, x, y, y); }
	public Vector4i yyxx() { return new Vector4i(y, y, x, x); }
	public Vector4i yyxy() { return new Vector4i(y, y, x, y); }
	public Vector4i yyyx() { return new Vector4i(y, y, y, x); }
	public Vector4i yyyy() { return new Vector4i(y, y, y, y); }
}
