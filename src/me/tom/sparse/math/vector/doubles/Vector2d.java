package me.tom.sparse.math.vector.doubles;

import me.tom.sparse.math.vector.floats.Vector2f;
import me.tom.sparse.math.vector.integers.Vector2i;

import java.util.function.BiFunction;
import java.util.function.Function;

@SuppressWarnings({"SuspiciousNameCombination", "unused"})
public class Vector2d
{
	protected double x, y;
	
	public Vector2d()
	{
	
	}
	
	public Vector2d(Vector2d other)
	{
		set(other);
	}
	
	public Vector2d(double x, double y)
	{
		set(x, y);
	}
	
	public Vector2d(double value)
	{
		set(value);
	}

	public Vector2d set(Vector2d other)
	{
		return set(other.x, other.y);
	}

	public Vector2d set(double value)
	{
		return set(value, value);
	}

	public Vector2d set(double x, double y)
	{
		setX(x);
		setY(y);
		return this;
	}

	public Vector2d setX(double x)
	{
		this.x = x;
		return this;
	}

	public Vector2d setY(double y)
	{
		this.y = y;
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

	public double ratio()
	{
		return x()/y();
	}

	public Vector2d op(Function<Double, Double> function)
	{
		setX(function.apply(x()));
		setY(function.apply(y()));
		return this;
	}

	public Vector2d pairOp(Vector2d other, BiFunction<Double, Double, Double> function)
	{
		setX(function.apply(x(), other.x()));
		setY(function.apply(y(), other.y()));
		return this;
	}

	public Vector2d abs()
	{
		return set(Math.abs(x()), Math.abs(y()));
	}

	public Vector2d floor()
	{
		return set(Math.floor(x()), Math.floor(y()));
	}

	public Vector2d ceil()
	{
		return set(Math.ceil(x()), Math.ceil(y()));
	}

	public Vector2d round()
	{
		return set(Math.round(x()), Math.round(y()));
	}

	public Vector2d rotate(double angle)
	{
//		double rad = Math.toRadians(angle);
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);
		
		double x = x();
		double y = y();
		double nx = (x * cos - y * sin);
		double ny = (x * sin + y * cos);
		return set(nx, ny);
	}
	
	public double min()
	{
		return Math.min(x(), y());
	}

	public Vector2d min(Vector2d other)
	{
		return min(other.x(), other.y());
	}

	public Vector2d min(double x, double y)
	{
		return set(Math.min(x(), x), Math.min(y(), y));
	}

	public double max()
	{
		return Math.max(x(), y());
	}

	public Vector2d max(Vector2d other)
	{
		return max(other.x(), other.y());
	}

	public Vector2d max(double x, double y)
	{
		return set(Math.max(x(), x), Math.max(y(), y));
	}
	
	public boolean withinMinMax(double minX, double minY, double maxX, double maxY)
	{
		double x = x();
		double y = y();
		return x >= minX && x <= maxX && y >= minY && y <= maxY;
	}

	public boolean withinMinMax(Vector2d min, Vector2d max)
	{
		return withinMinMax(min.x(), min.y(), max.x(), max.y());
	}
	
	public boolean within(Vector2d a, Vector2d b)
	{
		return withinMinMax(a.clone().min(b), a.clone().max(b));
	}
	
	public double sum()
	{
		return x() + y();
	}

	public double dot(Vector2d other)
	{
		return dot(other.x(), other.y());
	}

	public double dot(double x, double y)
	{
		return clone().multiply(x, y).sum();
	}

	public double angle(Vector2d other)
	{
		return angle(other.x(), other.y());
	}

	public double angle(double x, double y)
	{
		return (Math.atan2(y, x) - Math.atan2(y(), x()));
	}

	public double angle()
	{
		return (Math.atan2(y(), x()));
	}

	public double lengthSquared()
	{
		return dot(this);
	}
	
	public double length()
	{
		return Math.sqrt(lengthSquared());
	}
	
	public double distance(Vector2d other)
	{
		return clone().subtract(other).length();
	}
	
	public double distance(double x, double y)
	{
		return clone().subtract(x, y).length();
	}

	public double distanceSquared(Vector2d other)
	{
		return clone().subtract(other).lengthSquared();
	}

	public double distanceSquared(double x, double y)
	{
		return clone().subtract(x, y).lengthSquared();
	}
	
	public Vector2d normalize()
	{
		double length = length();
		return length == 0 ? this : divide(length);
	}
	
	public Vector2d lerp(Vector2d target, double lerpFactor)
	{
		return target.clone().subtract(this).multiply(lerpFactor).add(this);
	}

	public double cross(Vector2d other)
	{
		return cross(other.x(), other.y());
	}

	public double cross(double x, double y)
	{
		double ax = x();
		double ay = y();
		
		return ax * y - ay * x;
	}

	public Vector2d cross()
	{
		return new Vector2d(y(), -x());
	}

	public Vector2d pow(double pow)
	{
		return set(Math.pow(x(), pow), Math.pow(y(), pow));
	}

	public Vector2d add(double v)
	{
		return add(v, v);
	}

	public Vector2d add(double x, double y)
	{
		return set(x() + x, y() + y);
	}

	public Vector2d add(Vector2d other)
	{
		return add(other.x(), other.y());
	}
	
	public Vector2d subtract(double v)
	{
		return subtract(v, v);
	}

	public Vector2d subtract(double x, double y)
	{
		return add(-x, -y);
	}

	public Vector2d subtract(Vector2d other)
	{
		return subtract(other.x(), other.y());
	}
	
	public Vector2d multiply(double v)
	{
		return multiply(v, v);
	}

	public Vector2d multiply(double x, double y)
	{
		return set(x() * x, y() * y);
	}

	public Vector2d multiply(Vector2d other)
	{
		return multiply(other.x(), other.y());
	}
	
	public Vector2d divide(double v)
	{
		return divide(v, v);
	}

	public Vector2d divide(double x, double y)
	{
		return set(x() / x, y() / y);
	}

	public Vector2d divide(Vector2d other)
	{
		return divide(other.x(), other.y());
	}

	@Override
	public Vector2d clone()
	{
		return new Vector2d(this);
	}

	public boolean equals(Object o)
	{
		if(this == o) return true;
		if(!(o instanceof Vector2d)) return false;
		
		Vector2d vector2d = (Vector2d) o;
		
		if(Double.compare(vector2d.x, x) != 0) return false;
		return Double.compare(vector2d.y, y) == 0;
	}
	
	public int hashCode()
	{
		int result;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	
	@Override
	public String toString()
	{
		return "Vector2d{" +
				"x=" + x +
				", y=" + y +
				'}';
	}
	
	public Vector2f toVector2f()
	{
		return new Vector2f((float)x, (float)y);
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

	public Vector2d xx()   { return new Vector2d(x, x); }
	public Vector2d xy()   { return new Vector2d(x, y); }
	public Vector2d yx()   { return new Vector2d(y, x); }
	public Vector2d yy()   { return new Vector2d(y, y); }
	public Vector3d xxx()  { return new Vector3d(x, x, x); }
	public Vector3d xxy()  { return new Vector3d(x, x, y); }
	public Vector3d xyx()  { return new Vector3d(x, y, x); }
	public Vector3d xyy()  { return new Vector3d(x, y, y); }
	public Vector3d yxx()  { return new Vector3d(y, x, x); }
	public Vector3d yxy()  { return new Vector3d(y, x, y); }
	public Vector3d yyx()  { return new Vector3d(y, y, x); }
	public Vector3d yyy()  { return new Vector3d(y, y, y); }
	public Vector4d xxxx() { return new Vector4d(x, x, x, x); }
	public Vector4d xxxy() { return new Vector4d(x, x, x, y); }
	public Vector4d xxyx() { return new Vector4d(x, x, y, x); }
	public Vector4d xxyy() { return new Vector4d(x, x, y, y); }
	public Vector4d xyxx() { return new Vector4d(x, y, x, x); }
	public Vector4d xyxy() { return new Vector4d(x, y, x, y); }
	public Vector4d xyyx() { return new Vector4d(x, y, y, x); }
	public Vector4d xyyy() { return new Vector4d(x, y, y, y); }
	public Vector4d yxxx() { return new Vector4d(y, x, x, x); }
	public Vector4d yxxy() { return new Vector4d(y, x, x, y); }
	public Vector4d yxyx() { return new Vector4d(y, x, y, x); }
	public Vector4d yxyy() { return new Vector4d(y, x, y, y); }
	public Vector4d yyxx() { return new Vector4d(y, y, x, x); }
	public Vector4d yyxy() { return new Vector4d(y, y, x, y); }
	public Vector4d yyyx() { return new Vector4d(y, y, y, x); }
	public Vector4d yyyy() { return new Vector4d(y, y, y, y); }
}
