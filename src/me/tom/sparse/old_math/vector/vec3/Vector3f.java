package me.tom.sparse.old_math.vector.vec3;

import me.tom.sparse.old_math.Matrix4f;
import me.tom.sparse.old_math.vector.FloatVector;
import me.tom.sparse.old_math.vector.vec3.impl.PublicVector3f;
import me.tom.sparse.old_math.vector.vec4.impl.Quaternion;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public interface Vector3f extends FloatVector
{
	static Vector3f create(float x, float y, float z)
	{
		return new PublicVector3f(x, y, z);
	}

	default List<Consumer<Float>> getSetters()
	{
		return Arrays.asList(this::setX, this::setY, this::setZ);
	}

	default List<Supplier<Float>> getGetters()
	{
		return Arrays.asList(this::getX, this::getY, this::getZ);
	}

	default int getElementCount()
	{
		return 3;
	}

	float getX();
	float getY();
	float getZ();

	default Vector3f abs()
	{
		return set(Math.abs(getX()), Math.abs(getY()), Math.abs(getZ()));
	}

	default Vector3f rotate(Vector3f axis, float angle)
	{
		return rotate(new Quaternion(axis, angle));
//		float sinHalf = (float) Math.sin(Math.toRadians(angle / 2));
//		float cosHalf = (float) Math.cos(Math.toRadians(angle / 2));
//
//		Quaternion rotation = new Quaternion(axis.getX() * sinHalf, axis.getY() * sinHalf, axis.getZ() * sinHalf, cosHalf);
//		Quaternion conjugate = rotation.conjugate();
//
//		rotation.multiply(this).multiply(conjugate);
//
//		return set(rotation.getX(), rotation.getY(), rotation.getZ());
	}

	default Vector3f rotate(Quaternion rotation)
	{
		Quaternion conjugate = rotation.conjugate();
		Quaternion w = (Quaternion) ((Quaternion) rotation.clone()).multiply(this).multiply(conjugate);

		return set(w.getX(), w.getY(), w.getZ());
//		set((Vector3f) w.swizzle("xyz"));

//		return (Vector3f) w.swizzle("xyz");
//		rotation = (Quaternion) rotation.clone();
//		Quaternion conjugate = rotation.conjugate();
//		rotation = (Quaternion) rotation.multiply(this).multiply(conjugate);
//
//		return set((Vector3f) rotation.swizzle("xyz"));
//		return (Vector3f) rotation.swizzle("xyz");
	}

	default Matrix4f toTranslationMatrix()
	{
		return Matrix4f.translation(getX(), getY(), getZ());
	}

	default float min()
	{
		return Math.min(getX(), Math.min(getY(), getZ()));
	}
	default Vector3f min(Vector3f other)
	{
		return set(Math.min(getX(), other.getX()), Math.min(getY(), other.getY()), Math.min(getZ(), other.getZ()));
	}
	default float max()
	{
		return Math.max(getX(), Math.max(getY(), getZ()));
	}
	default Vector3f max(Vector3f other)
	{
		return set(Math.max(getX(), other.getX()), Math.max(getY(), other.getY()), Math.max(getZ(), other.getZ()));
	}

	default boolean withinMinMax(float minX, float minY, float minZ, float maxX, float maxY, float maxZ)
	{
		float x = getX();
		float y = getY();
		float z = getZ();
		return x >= minX && x <= maxX && y >= minY && y <= maxY && z >= minZ && z <= maxZ;
	}
	default boolean withinMinMax(Vector3f min, Vector3f max)
	{
		return withinMinMax(min.getX(), min.getY(), min.getZ(), max.getX(), max.getY(), max.getZ());
	}
	default boolean within(Vector3f a, Vector3f b)
	{
		return withinMinMax(a.clone().min(b), a.clone().max(b));
	}

	default float sum()
	{
		return getX() + getY() + getZ();
	}

	default float dot(Vector3f other)
	{
		return clone().multiply(other).sum();
	}

	default float dot(float x, float y, float z)
	{
		return clone().multiply(x, y, z).sum();
	}

	default float lengthSquared()
	{
		return dot(this);
	}
	default float length()
	{
		return (float) Math.sqrt(lengthSquared());
	}

	default float distance(Vector3f other)
	{
		return clone().subtract(other).length();
	}

	default float distance(float x, float y, float z)
	{
		return clone().subtract(x, y, z).length();
	}

	default float distanceSquared(Vector3f other)
	{
		return clone().subtract(other).lengthSquared();
	}

	default Vector3f normalize()
	{
		float length = length();
		return length == 0 ? this : divide(length);
	}

	default Vector3f lerp(Vector3f target, float lerpFactor)
	{
		return target.clone().subtract(this).multiply(lerpFactor).add(this);
	}

	default Vector3f cross(Vector3f other)
	{
		float ax = getX();
		float ay = getY();
		float az = getZ();
		float bx = other.getX();
		float by = other.getY();
		float bz = other.getZ();

		return create(ay * bz - az * by, az * bx - ax * bz, ax * by - ay * bx);
	}

	default Vector3f pow(float pow)
	{
		return set((float)Math.pow(getX(), pow), (float)Math.pow(getY(), pow), (float)Math.pow(getZ(), pow));
	}

	default Vector3f floor()
	{
		return set((float)Math.floor(getX()), (float)Math.floor(getY()), (float)Math.floor(getZ()));
	}

	default Vector3f ceil()
	{
		return set((float)Math.ceil(getX()), (float)Math.ceil(getY()), (float)Math.ceil(getZ()));
	}

	default Vector3f round()
	{
		return set((float)Math.round(getX()), (float)Math.round(getY()), (float)Math.round(getZ()));
	}

	default Vector3f forEach(Function<Float, Float> func)
	{
		setX(func.apply(getX()));
		setY(func.apply(getY()));
		setZ(func.apply(getZ()));
		return this;
	}

	Vector3f setX(float x);
	Vector3f setY(float y);
	Vector3f setZ(float z);
	default Vector3f set(float v)
	{
		return set(v, v, v);
	}
	default Vector3f set(float x, float y, float z)
	{
		return setX(x).setY(y).setZ(z);
	}
	default Vector3f set(Vector3f other)
	{
		return set(other.getX(), other.getY(), other.getZ());
	}

	default Vector3f add(float v)
	{
		return add(v, v, v);
	}
	default Vector3f add(float x, float y, float z)
	{
		return set(getX()+x, getY()+y, getZ()+z);
	}
	default Vector3f add(Vector3f other)
	{
		return add(other.getX(), other.getY(), other.getZ());
	}

	default Vector3f subtract(float v)
	{
		return subtract(v, v, v);
	}
	default Vector3f subtract(float x, float y, float z)
	{
		return add(-x, -y, -z);
	}
	default Vector3f subtract(Vector3f other)
	{
		return subtract(other.getX(), other.getY(), other.getZ());
	}

	default Vector3f multiply(float v)
	{
		return multiply(v, v, v);
	}
	default Vector3f multiply(float x, float y, float z)
	{
		return set(getX()*x, getY()*y, getZ()*z);
	}
	default Vector3f multiply(Vector3f other)
	{
		return multiply(other.getX(), other.getY(), other.getZ());
	}
	default Vector3f multiply(Matrix4f mat)
	{
		return null;
	}

	default Vector3f divide(float v)
	{
		return divide(v, v, v);
	}
	default Vector3f divide(float x, float y, float z)
	{
		return set(getX()/x, getY()/y, getZ()/z);
	}

	default Vector3f divide(Vector3f other)
	{
		return divide(other.getX(), other.getY(), other.getZ());
	}

	default boolean equals(Vector3f other)
	{
		return other != null && getX() == other.getX() && getY() == other.getY() && getZ() == other.getZ();
	}

	Vector3f clone();


}
