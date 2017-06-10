package me.tom.sparse.old_math.vector.vec2;

import me.tom.sparse.old_math.vector.IntVector;
import me.tom.sparse.old_math.vector.vec2.impl.PublicVector2i;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public interface Vector2i extends IntVector
{
	static Vector2i create(int x, int y)
	{
		return new PublicVector2i(x, y);
	}
	
	default List<Consumer<Integer>> getSetters()
	{
		return Arrays.asList(this::setX, this::setY);
	}
	
	default List<Supplier<Integer>> getGetters()
	{
		return Arrays.asList(this::getX, this::getY);
	}
	
	default int getElementCount()
	{
		return 2;
	}
	
	int getX();
	int getY();

	default int min()
	{
		return Math.min(getX(), getY());
	}
	default Vector2i min(Vector2i other)
	{
		return set(Math.min(getX(), other.getX()), Math.min(getY(), other.getY()));
	}
	default int max()
	{
		return Math.max(getX(), getY());
	}
	default Vector2i max(Vector2i other)
	{
		return set(Math.max(getX(), other.getX()), Math.max(getY(), other.getY()));
	}

	default boolean within(int minX, int minY, int maxX, int maxY)
	{
		int x = getX();
		int y = getY();
		return x >= minX && x <= maxX && y >= minY && y <= maxY;
	}
	default boolean withinMinMax(Vector2i min, Vector2i max)
	{
		return within(min.getX(), min.getY(), max.getX(), max.getY());
	}
	default boolean within(Vector2i a, Vector2i b)
	{
		return withinMinMax(a.clone().min(b), a.clone().max(b));
	}

	default int sum()
	{
		return getX() + getY();
	}

	default int dot(Vector2i other)
	{
		return clone().multiply(other).sum();
	}

	default int lengthSquared()
	{
		return dot(this);
	}
	default int length()
	{
		return (int) Math.sqrt(lengthSquared());
	}

	default int distance(Vector2i other)
	{
		return clone().subtract(other).length();
	}
	
	default Vector2i normalize()
	{
		int length = length();
		return length == 0 ? this : divide(length);
	}

	Vector2i setX(int x);
	Vector2i setY(int y);
	default Vector2i set(int v)
	{
		return set(v, v);
	}
	default Vector2i set(int x, int y)
	{
		return setX(x).setY(y);
	}
	default Vector2i set(Vector2i other)
	{
		return set(other.getX(), other.getY());
	}

	default Vector2i add(int v)
	{
		return add(v, v);
	}
	default Vector2i add(int x, int y)
	{
		return set(getX()+x, getY()+y);
	}
	default Vector2i add(Vector2i other)
	{
		return add(other.getX(), other.getY());
	}

	default Vector2i subtract(int v)
	{
		return subtract(v, v);
	}
	default Vector2i subtract(int x, int y)
	{
		return add(-x, -y);
	}
	default Vector2i subtract(Vector2i other)
	{
		return subtract(other.getX(), other.getY());
	}

	default Vector2i multiply(int v)
	{
		return multiply(v, v);
	}
	default Vector2i multiply(int x, int y)
	{
		return set(getX()*x, getY()*y);
	}
	default Vector2i multiply(Vector2i other)
	{
		return multiply(other.getX(), other.getY());
	}

	default Vector2i divide(int v)
	{
		return divide(v, v);
	}
	default Vector2i divide(int x, int y)
	{
		return set(getX()/x, getY()/y);
	}
	default Vector2i divide(Vector2i other)
	{
		return divide(other.getX(), other.getY());
	}
	
	default boolean equals(Vector2i other)
	{
		return other != null && getX() == other.getX() && getY() == other.getY();
	}
	
	Vector2i clone();
}
