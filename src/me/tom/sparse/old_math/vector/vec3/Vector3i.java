package me.tom.sparse.old_math.vector.vec3;

import me.tom.sparse.old_math.Matrix4f;
import me.tom.sparse.old_math.vector.IntVector;
import me.tom.sparse.old_math.vector.vec3.impl.PublicVector3i;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public interface Vector3i extends IntVector
{
	static Vector3i create(int x, int y, int z)
	{
		return new PublicVector3i(x, y, z);
	}

	default List<Consumer<Integer>> getSetters()
	{
		return Arrays.asList(this::setX, this::setY, this::setZ);
	}

	default List<Supplier<Integer>> getGetters()
	{
		return Arrays.asList(this::getX, this::getY, this::getZ);
	}

	default int getElementCount()
	{
		return 3;
	}

	int getX();
	int getY();
	int getZ();

	default Vector3i abs()
	{
		return set(Math.abs(getX()), Math.abs(getY()), Math.abs(getZ()));
	}

	default Matrix4f toTranslationMatrix()
	{
		return Matrix4f.translation(getX(), getY(), getZ());
	}

	default int min()
	{
		return Math.min(getX(), Math.min(getY(), getZ()));
	}
	default Vector3i min(Vector3i other)
	{
		return set(Math.min(getX(), other.getX()), Math.min(getY(), other.getY()), Math.min(getZ(), other.getZ()));
	}
	default int max()
	{
		return Math.max(getX(), Math.max(getY(), getZ()));
	}
	default Vector3i max(Vector3i other)
	{
		return set(Math.max(getX(), other.getX()), Math.max(getY(), other.getY()), Math.max(getZ(), other.getZ()));
	}

	default boolean withinMinMax(int minX, int minY, int minZ, int maxX, int maxY, int maxZ)
	{
		int x = getX();
		int y = getY();
		int z = getZ();
		return x >= minX && x <= maxX && y >= minY && y <= maxY && z >= minZ && z <= maxZ;
	}
	default boolean withinMinMax(Vector3i min, Vector3i max)
	{
		return withinMinMax(min.getX(), min.getY(), min.getZ(), max.getX(), max.getY(), max.getZ());
	}
	default boolean within(Vector3i a, Vector3i b)
	{
		return withinMinMax(a.clone().min(b), a.clone().max(b));
	}

	default int sum()
	{
		return getX() + getY() + getZ();
	}

	default int dot(Vector3i other)
	{
		return clone().multiply(other).sum();
	}

	default int dot(int x, int y, int z)
	{
		return clone().multiply(x, y, z).sum();
	}

	default int lengthSquared()
	{
		return dot(this);
	}
	default int length()
	{
		return (int) Math.sqrt(lengthSquared());
	}

	default int distance(Vector3i other)
	{
		return clone().subtract(other).length();
	}

	default int distance(int x, int y, int z)
	{
		return clone().subtract(x, y, z).length();
	}

	default int distanceSquared(Vector3i other)
	{
		return clone().subtract(other).lengthSquared();
	}

	default Vector3i normalize()
	{
		int length = length();
		return length == 0 ? this : divide(length);
	}

	default Vector3i lerp(Vector3i target, int lerpFactor)
	{
		return target.clone().subtract(this).multiply(lerpFactor).add(this);
	}

	default Vector3i cross(Vector3i other)
	{
		int ax = getX();
		int ay = getY();
		int az = getZ();
		int bx = other.getX();
		int by = other.getY();
		int bz = other.getZ();

		return create(ay * bz - az * by, az * bx - ax * bz, ax * by - ay * bx);
	}

	default Vector3i pow(int pow)
	{
		return set((int)Math.pow(getX(), pow), (int)Math.pow(getY(), pow), (int)Math.pow(getZ(), pow));
	}

	default Vector3i floor()
	{
		return set((int)Math.floor(getX()), (int)Math.floor(getY()), (int)Math.floor(getZ()));
	}

	default Vector3i ceil()
	{
		return set((int)Math.ceil(getX()), (int)Math.ceil(getY()), (int)Math.ceil(getZ()));
	}

	default Vector3i round()
	{
		return set((int)Math.round(getX()), (int)Math.round(getY()), (int)Math.round(getZ()));
	}

	default Vector3i forEach(Function<Integer, Integer> func)
	{
		setX(func.apply(getX()));
		setY(func.apply(getY()));
		setZ(func.apply(getZ()));
		return this;
	}

	Vector3i setX(int x);
	Vector3i setY(int y);
	Vector3i setZ(int z);
	default Vector3i set(int v)
	{
		return set(v, v, v);
	}
	default Vector3i set(int x, int y, int z)
	{
		return setX(x).setY(y).setZ(z);
	}
	default Vector3i set(Vector3i other)
	{
		return set(other.getX(), other.getY(), other.getZ());
	}

	default Vector3i add(int v)
	{
		return add(v, v, v);
	}
	default Vector3i add(int x, int y, int z)
	{
		return set(getX()+x, getY()+y, getZ()+z);
	}
	default Vector3i add(Vector3i other)
	{
		return add(other.getX(), other.getY(), other.getZ());
	}

	default Vector3i subtract(int v)
	{
		return subtract(v, v, v);
	}
	default Vector3i subtract(int x, int y, int z)
	{
		return add(-x, -y, -z);
	}
	default Vector3i subtract(Vector3i other)
	{
		return subtract(other.getX(), other.getY(), other.getZ());
	}

	default Vector3i multiply(int v)
	{
		return multiply(v, v, v);
	}
	default Vector3i multiply(int x, int y, int z)
	{
		return set(getX()*x, getY()*y, getZ()*z);
	}
	default Vector3i multiply(Vector3i other)
	{
		return multiply(other.getX(), other.getY(), other.getZ());
	}
	default Vector3i multiply(Matrix4f mat)
	{
		return null;
	}

	default Vector3i divide(int v)
	{
		return divide(v, v, v);
	}
	default Vector3i divide(int x, int y, int z)
	{
		return set(getX()/x, getY()/y, getZ()/z);
	}

	default Vector3i divide(Vector3i other)
	{
		return divide(other.getX(), other.getY(), other.getZ());
	}

	default boolean equals(Vector3i other)
	{
		return other != null && getX() == other.getX() && getY() == other.getY() && getZ() == other.getZ();
	}

	Vector3i clone();


}
