package me.tom.sparse.old_math.vector.vec2;

import me.tom.sparse.old_math.vector.FloatVector;
import me.tom.sparse.old_math.vector.vec2.impl.PublicVector2f;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public interface Vector2f extends FloatVector
{
	static Vector2f create(float x, float y)
	{
		return new PublicVector2f(x, y);
	}

	default List<Consumer<Float>> getSetters()
	{
		return Arrays.asList(this::setX, this::setY);
	}

	default List<Supplier<Float>> getGetters()
	{
		return Arrays.asList(this::getX, this::getY);
	}

	default int getElementCount()
	{
		return 2;
	}

	float getX();
	float getY();
	
	default Vector2f abs()
	{
		return set(Math.abs(getX()), Math.abs(getY()));
	}
	
	default Vector2f rotate(float angle)
	{
		double rad = Math.toRadians(angle);
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);

		float x = getX();
		float y = getY();
		float nx = (float) (x * cos - y * sin);
		float ny = (float) (x * sin + y * cos);
		return set(nx, ny);
	}

	default float min()
	{
		return Math.min(getX(), getY());
	}
	default Vector2f min(Vector2f other)
	{
		return set(Math.min(getX(), other.getX()), Math.min(getY(), other.getY()));
	}
	default float max()
	{
		return Math.max(getX(), getY());
	}
	default Vector2f max(Vector2f other)
	{
		return set(Math.max(getX(), other.getX()), Math.max(getY(), other.getY()));
	}

	default boolean withinMinMax(float minX, float minY, float maxX, float maxY)
	{
		float x = getX();
		float y = getY();
		return x >= minX && x <= maxX && y >= minY && y <= maxY;
	}
	default boolean withinMinMax(Vector2f min, Vector2f max)
	{
		return withinMinMax(min.getX(), min.getY(), max.getX(), max.getY());
	}

	default boolean within(Vector2f a, Vector2f b)
	{
		return withinMinMax(a.clone().min(b), a.clone().max(b));
	}

	default float sum()
	{
		return getX() + getY();
	}

	default float dot(Vector2f other)
	{
		return clone().multiply(other).sum();
	}

	default float lengthSquared()
	{
		return dot(this);
	}

	default float length()
	{
		return (float) Math.sqrt(lengthSquared());
	}

	default float distance(Vector2f other)
	{
		return clone().subtract(other).length();
	}

	default float distance(float x, float y)
	{
		return clone().subtract(x, y).length();
	}

	default Vector2f normalize()
	{
		float length = length();
		return length == 0 ? this : divide(length);
	}

	default Vector2f lerp(Vector2f target, float lerpFactor)
	{
		return target.clone().subtract(this).multiply(lerpFactor).add(this);
	}

	default float cross(Vector2f other)
	{
		float ax = getX();
		float ay = getY();
		float bx = other.getX();
		float by = other.getY();

		return ax * by - ay * bx;
	}

	Vector2f setX(float x);
	Vector2f setY(float y);
	default Vector2f set(float v)
	{
		return set(v, v);
	}
	default Vector2f set(float x, float y)
	{
		return setX(x).setY(y);
	}
	default Vector2f set(Vector2f other)
	{
		return set(other.getX(), other.getY());
	}

	default Vector2f add(float v)
	{
		return add(v, v);
	}
	default Vector2f add(float x, float y)
	{
		return set(getX()+x, getY()+y);
	}
	default Vector2f add(Vector2f other)
	{
		return add(other.getX(), other.getY());
	}

	default Vector2f subtract(float v)
	{
		return subtract(v, v);
	}
	default Vector2f subtract(float x, float y)
	{
		return add(-x, -y);
	}
	default Vector2f subtract(Vector2f other)
	{
		return subtract(other.getX(), other.getY());
	}

	default Vector2f multiply(float v)
	{
		return multiply(v, v);
	}
	default Vector2f multiply(float x, float y)
	{
		return set(getX()*x, getY()*y);
	}
	default Vector2f multiply(Vector2f other)
	{
		return multiply(other.getX(), other.getY());
	}

	default Vector2f divide(float v)
	{
		return divide(v, v);
	}
	default Vector2f divide(float x, float y)
	{
		return set(getX()/x, getY()/y);
	}
	default Vector2f divide(Vector2f other)
	{
		return divide(other.getX(), other.getY());
	}

	default boolean equals(Vector2f other)
	{
		return other != null && getX() == other.getX() && getY() == other.getY();
	}

	Vector2f clone();

	default Vector2f swizzleYX()
	{
		return create(getY(), getX());
	}
}
