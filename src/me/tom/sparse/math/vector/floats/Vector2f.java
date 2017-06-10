package me.tom.sparse.math.vector.floats;

import me.tom.sparse.math.vector.doubles.Vector2d;
import me.tom.sparse.math.vector.integers.Vector2i;

import java.util.function.BiFunction;
import java.util.function.Function;

@SuppressWarnings({"SuspiciousNameCombination", "unused"})
public class Vector2f
{
	protected float x, y;
	
	public Vector2f(Vector2f other)
	{
		set(other);
	}
	
	public Vector2f(float x, float y)
	{
		set(x, y);
	}
	
	public Vector2f(float value)
	{
		set(value);
	}
	
	public Vector2f()
	{
	
	}
	
	public Vector2f set(Vector2f other)
	{
		return set(other.x, other.y);
	}

	public Vector2f set(float value)
	{
		return set(value, value);
	}

	public Vector2f set(float x, float y)
	{
		setX(x);
		setY(y);
		return this;
	}

	public Vector2f setX(float x)
	{
		this.x = x;
		return this;
	}

	public Vector2f setY(float y)
	{
		this.y = y;
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

	public float ratio()
	{
		return x()/y();
	}

	public Vector2f op(Function<Float, Float> function)
	{
		setX(function.apply(x()));
		setY(function.apply(y()));
		return this;
	}

	public Vector2f pairOp(Vector2f other, BiFunction<Float, Float, Float> function)
	{
		setX(function.apply(x(), other.x()));
		setY(function.apply(y(), other.y()));
		return this;
	}

	public Vector2f abs()
	{
		return set(Math.abs(x()), Math.abs(y()));
	}

	public Vector2f floor()
	{
		return set((float)Math.floor(x()), (float)Math.floor(y()));
	}

	public Vector2f ceil()
	{
		return set((float)Math.ceil(x()), (float)Math.ceil(y()));
	}

	public Vector2f round()
	{
		return set((float)Math.round(x()), (float)Math.round(y()));
	}

	public Vector2f rotate(float angle)
	{
//		double rad = Math.toRadians(angle);
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);
		
		float x = x();
		float y = y();
		float nx = (float) (x * cos - y * sin);
		float ny = (float) (x * sin + y * cos);
		return set(nx, ny);
	}
	
	public float min()
	{
		return Math.min(x(), y());
	}

	public Vector2f min(Vector2f other)
	{
		return min(other.x(), other.y());
	}

	public Vector2f min(float x, float y)
	{
		return set(Math.min(x(), x), Math.min(y(), y));
	}

	public float max()
	{
		return Math.max(x(), y());
	}

	public Vector2f max(Vector2f other)
	{
		return max(other.x(), other.y());
	}

	public Vector2f max(float x, float y)
	{
		return set(Math.max(x(), x), Math.max(y(), y));
	}
	
	public boolean withinMinMax(float minX, float minY, float maxX, float maxY)
	{
		float x = x();
		float y = y();
		return x >= minX && x <= maxX && y >= minY && y <= maxY;
	}

	public boolean withinMinMax(Vector2f min, Vector2f max)
	{
		return withinMinMax(min.x(), min.y(), max.x(), max.y());
	}
	
	public boolean within(Vector2f a, Vector2f b)
	{
		return withinMinMax(a.clone().min(b), a.clone().max(b));
	}
	
	public float sum()
	{
		return x() + y();
	}

	public float dot(Vector2f other)
	{
		return dot(other.x(), other.y());
	}

	public float dot(float x, float y)
	{
		return clone().multiply(x, y).sum();
	}

	public float angle(Vector2f other)
	{
		return angle(other.x(), other.y());
	}

	public float angle(float x, float y)
	{
		return (float) (Math.atan2(y, x) - Math.atan2(y(), x()));
	}

	public float angle()
	{
		return (float) (Math.atan2(y(), x()));
	}

	public float lengthSquared()
	{
		return dot(this);
	}
	
	public float length()
	{
		return (float) Math.sqrt(lengthSquared());
	}
	
	public float distance(Vector2f other)
	{
		return clone().subtract(other).length();
	}
	
	public float distance(float x, float y)
	{
		return clone().subtract(x, y).length();
	}

	public float distanceSquared(Vector2f other)
	{
		return clone().subtract(other).lengthSquared();
	}

	public float distanceSquared(float x, float y)
	{
		return clone().subtract(x, y).lengthSquared();
	}
	
	public Vector2f normalize()
	{
		float length = length();
		return length == 0 ? this : divide(length);
	}
	
	public Vector2f lerp(Vector2f target, float lerpFactor)
	{
		return target.clone().subtract(this).multiply(lerpFactor).add(this);
	}

	public float cross(Vector2f other)
	{
		return cross(other.x(), other.y());
	}

	public float cross(float x, float y)
	{
		float ax = x();
		float ay = y();
		
		return ax * y - ay * x;
	}

	public Vector2f cross()
	{
		return new Vector2f(y(), -x());
	}

	public Vector2f pow(float pow)
	{
		return set((float) Math.pow(x(), pow), (float) Math.pow(y(), pow));
	}

	public Vector2f add(float v)
	{
		return add(v, v);
	}

	public Vector2f add(float x, float y)
	{
		return set(x() + x, y() + y);
	}

	public Vector2f add(Vector2f other)
	{
		return add(other.x(), other.y());
	}
	
	public Vector2f subtract(float v)
	{
		return subtract(v, v);
	}

	public Vector2f subtract(float x, float y)
	{
		return add(-x, -y);
	}

	public Vector2f subtract(Vector2f other)
	{
		return subtract(other.x(), other.y());
	}
	
	public Vector2f multiply(float v)
	{
		return multiply(v, v);
	}

	public Vector2f multiply(float x, float y)
	{
		return set(x() * x, y() * y);
	}

	public Vector2f multiply(Vector2f other)
	{
		return multiply(other.x(), other.y());
	}
	
	public Vector2f divide(float v)
	{
		return divide(v, v);
	}

	public Vector2f divide(float x, float y)
	{
		return set(x() / x, y() / y);
	}

	public Vector2f divide(Vector2f other)
	{
		return divide(other.x(), other.y());
	}

	@Override
	public Vector2f clone()
	{
		return new Vector2f(this);
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Vector2f vector2f = (Vector2f) o;

		if (Float.compare(vector2f.x, x) != 0) return false;
		return Float.compare(vector2f.y, y) == 0;
	}

	@Override
	public int hashCode()
	{
		int result = (x != +0.0f ? Float.floatToIntBits(x) : 0);
		result = 31 * result + (y != +0.0f ? Float.floatToIntBits(y) : 0);
		return result;
	}

	@Override
	public String toString()
	{
		return "Vector2f{" +
				"x=" + x +
				", y=" + y +
				'}';
	}
	
	public Vector2d toVector2d()
	{
		return new Vector2d(x, y);
	}
	
	public Vector2i toVector2i()
	{
		return new Vector2i((int)x, (int)y);
	}
	
	/*
	--------------------------------------------------------------------------------------------
								Generated Vector Swizzling code
	--------------------------------------------------------------------------------------------
	 */

	public Vector2f xx()   { return new Vector2f(x, x); }
	public Vector2f xy()   { return new Vector2f(x, y); }
	public Vector2f yx()   { return new Vector2f(y, x); }
	public Vector2f yy()   { return new Vector2f(y, y); }
	public Vector3f xxx()  { return new Vector3f(x, x, x); }
	public Vector3f xxy()  { return new Vector3f(x, x, y); }
	public Vector3f xyx()  { return new Vector3f(x, y, x); }
	public Vector3f xyy()  { return new Vector3f(x, y, y); }
	public Vector3f yxx()  { return new Vector3f(y, x, x); }
	public Vector3f yxy()  { return new Vector3f(y, x, y); }
	public Vector3f yyx()  { return new Vector3f(y, y, x); }
	public Vector3f yyy()  { return new Vector3f(y, y, y); }
	public Vector4f xxxx() { return new Vector4f(x, x, x, x); }
	public Vector4f xxxy() { return new Vector4f(x, x, x, y); }
	public Vector4f xxyx() { return new Vector4f(x, x, y, x); }
	public Vector4f xxyy() { return new Vector4f(x, x, y, y); }
	public Vector4f xyxx() { return new Vector4f(x, y, x, x); }
	public Vector4f xyxy() { return new Vector4f(x, y, x, y); }
	public Vector4f xyyx() { return new Vector4f(x, y, y, x); }
	public Vector4f xyyy() { return new Vector4f(x, y, y, y); }
	public Vector4f yxxx() { return new Vector4f(y, x, x, x); }
	public Vector4f yxxy() { return new Vector4f(y, x, x, y); }
	public Vector4f yxyx() { return new Vector4f(y, x, y, x); }
	public Vector4f yxyy() { return new Vector4f(y, x, y, y); }
	public Vector4f yyxx() { return new Vector4f(y, y, x, x); }
	public Vector4f yyxy() { return new Vector4f(y, y, x, y); }
	public Vector4f yyyx() { return new Vector4f(y, y, y, x); }
	public Vector4f yyyy() { return new Vector4f(y, y, y, y); }
}
