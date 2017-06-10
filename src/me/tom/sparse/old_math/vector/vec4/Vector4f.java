package me.tom.sparse.old_math.vector.vec4;

import me.tom.sparse.old_math.Matrix4f;
import me.tom.sparse.old_math.vector.FloatVector;
import me.tom.sparse.old_math.vector.vec4.impl.PublicVector4f;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public interface Vector4f extends FloatVector
{
	static Vector4f create(float x, float y, float z, float w)
	{
		return new PublicVector4f(x, y, z, w);
	}

	default List<Consumer<Float>> getSetters()
	{
		return Arrays.asList(this::setX, this::setY, this::setZ, this::setW);
	}

	default List<Supplier<Float>> getGetters()
	{
		return Arrays.asList(this::getX, this::getY, this::getZ, this::getW);
	}

	default int getElementCount()
	{
		return 4;
	}

	float getX();
	float getY();
	float getZ();
	float getW();

	default float min()
	{
		return Math.min(getX(), Math.min(getY(), Math.min(getZ(), getW())));
	}
	default Vector4f min(Vector4f other)
	{
		return set(Math.min(getX(), other.getX()), Math.min(getY(), other.getY()), Math.min(getZ(), other.getZ()), Math.min(getW(), other.getW()));
	}
	default float max()
	{
		return Math.max(getX(), Math.max(getY(), Math.max(getZ(), getW())));
	}
	default Vector4f max(Vector4f other)
	{
		return set(Math.max(getX(), other.getX()), Math.max(getY(), other.getY()), Math.max(getZ(), other.getZ()), Math.max(getW(), other.getW()));
	}

	default boolean within(float minX, float minY, float minZ, float minW, float maxX, float maxY, float maxZ, float maxW)
	{
		float x = getX();
		float y = getY();
		float z = getZ();
		float w = getW();
		return x >= minX && x <= maxX && y >= minY && y <= maxY && z >= minZ && z <= maxZ && w >= minW && w <= maxW;
	}
	default boolean withinMinMax(Vector4f min, Vector4f max)
	{
		return within(min.getX(), min.getY(), min.getZ(), min.getW(), max.getX(), max.getY(), max.getZ(), max.getW());
	}
	default boolean within(Vector4f a, Vector4f b)
	{
		return withinMinMax(a.clone().min(b), a.clone().max(b));
	}

	default float sum()
	{
		return getX() + getY() + getZ() + getW();
	}

	default float dot(Vector4f other)
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

	default float distance(Vector4f other)
	{
		return clone().subtract(other).length();
	}

	default Vector4f normalize()
	{
		float length = length();
		return length == 0 ? this : divide(length);
	}

	default Vector4f lerp(Vector4f target, float lerpFactor)
	{
		return target.clone().subtract(this).multiply(lerpFactor).add(this);
	}

	default Vector4f pow(float pow)
	{
		return set((float)Math.pow(getX(), pow), (float)Math.pow(getY(), pow), (float)Math.pow(getZ(), pow), (float)Math.pow(getW(), pow));
	}

	default Vector4f forEach(Function<Float, Float> func)
	{
		setX(func.apply(getX()));
		setY(func.apply(getY()));
		setZ(func.apply(getZ()));
		setW(func.apply(getW()));
		return this;
	}

	//TODO: Cross product?

	Vector4f setX(float x);
	Vector4f setY(float y);
	Vector4f setZ(float z);
	Vector4f setW(float w);
	default Vector4f set(float v)
	{
		return set(v, v, v, v);
	}
	default Vector4f set(float x, float y, float z, float w)
	{
		return setX(x).setY(y).setZ(z).setW(w);
	}
	default Vector4f set(Vector4f other)
	{
		return set(other.getX(), other.getY(), other.getZ(), other.getW());
	}

	default Vector4f add(float v)
	{
		return add(v, v, v, v);
	}
	default Vector4f add(float x, float y, float z, float w)
	{
		return set(getX()+x, getY()+y, getZ()+z, getW()+w);
	}
	default Vector4f add(Vector4f other)
	{
		return add(other.getX(), other.getY(), other.getZ(), other.getW());
	}

	default Vector4f subtract(float v)
	{
		return subtract(v, v, v, v);
	}
	default Vector4f subtract(float x, float y, float z, float w)
	{
		return add(-x, -y, -z, -w);
	}
	default Vector4f subtract(Vector4f other)
	{
		return subtract(other.getX(), other.getY(), other.getZ(), other.getW());
	}

	default Vector4f multiply(float v)
	{
		return multiply(v, v, v, v);
	}
	default Vector4f multiply(float x, float y, float z, float w)
	{
		return set(getX()*x, getY()*y, getZ()*z, getW()*w);
	}
	default Vector4f multiply(Vector4f other)
	{
		return multiply(other.getX(), other.getY(), other.getZ(), other.getW());
	}
	default Vector4f multiply(Matrix4f mat)
	{

		return null;
	}

	default Vector4f divide(float v)
	{
		return divide(v, v, v, v);
	}
	default Vector4f divide(float x, float y, float z, float w)
	{
		return set(getX()/x, getY()/y, getZ()/z, getW()/w);
	}
	default Vector4f divide(Vector4f other)
	{
		return divide(other.getX(), other.getY(), other.getZ(), other.getW());
	}

	default boolean equals(Vector4f other)
	{
		return other != null && getX() == other.getX() && getY() == other.getY() && getZ() == other.getZ() && getW() == other.getW();
	}

	Vector4f clone();
}
