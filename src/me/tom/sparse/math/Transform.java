package me.tom.sparse.math;

import me.tom.sparse.math.vector.floats.Vector3f;
import me.tom.sparse.math.vector.floats.impl.Quaternion4f;

public class Transform
{
	private Matrix4f cachedMatrix;
	private int      cachedHashCode;

	private Vector3f     translation;
	private Quaternion4f rotation;
	private Vector3f     scale;

	public Transform()
	{
		this(new Vector3f(), new Quaternion4f(), new Vector3f(1, 1, 1));
	}

	public Transform(Vector3f translation, Quaternion4f rotation, Vector3f scale)
	{
		this.translation = new Vector3f(translation);
		this.rotation = new Quaternion4f(rotation);
		this.scale = new Vector3f(scale);

		calculateMatrix();
	}

	public Matrix4f getMatrix()
	{
		if(hashCode() == cachedHashCode)
			return cachedMatrix;
		return calculateMatrix();
	}

	public Matrix4f calculateMatrix()
	{
		Matrix4f translationMatrix = Matrix4f.translation(translation.x(), translation.y(), translation.z());
		Matrix4f rotationMatrix = rotation.toRotationMatrix();
		Matrix4f scaleMatrix = Matrix4f.scale(scale.x(), scale.y(), scale.z());

//		Matrix4f parentMatrix = parent == null ? Matrix4f.identity() : parent.calculateMatrix();
//		lastMatrix = parentMatrix.multiply(translationMatrix.multiply(rotationMatrix.multiply(scaleMatrix)));
		cachedMatrix = translationMatrix.multiply(rotationMatrix.multiply(scaleMatrix));
//		System.out.println(cachedMatrix);
		cachedHashCode = hashCode();
		return cachedMatrix;
	}

	public Vector3f getTranslation()
	{
		return translation;
	}

	public Quaternion4f getRotation()
	{
		return rotation;
	}

	public Vector3f getScale()
	{
		return scale;
	}

	public Transform setTranslation(float x, float y, float z)
	{
		this.translation.set(x, y, z);
		return this;
	}

	public Transform setTranslation(Vector3f translation)
	{
		this.translation.set(translation);
		return this;
	}

	public Transform lookAt(Vector3f pos, Vector3f up)
	{
		Vector3f vec = pos.clone().subtract(translation).normalize();
		setRotation(vec, up);
		return this;
	}

	public Transform lookAt(Vector3f pos)
	{
		Vector3f vec = pos.clone().subtract(translation).normalize();
		setRotation(vec);
		return this;
	}

	public Transform setRoll(Vector3f up)
	{
		//TODO: Check if this works
		Vector3f forward = getRotation().getForward();
		up = up.clone().subtract(forward.clone().multiply(up.dot(forward)));
		setRotation(new Quaternion4f(Matrix4f.rotation(forward, up)));

		return this;
	}

	public Transform setRotation(Vector3f forward, Vector3f up)
	{
		up = up.clone().subtract(forward.clone().multiply(up.clone().dot(forward)));
		setRotation(new Quaternion4f(Matrix4f.rotation(forward, up)));
		return this;
	}

	public Transform setRotation(Vector3f forward)
	{
		rotation.set(forward);
		return this;
	}

	public Transform setRotation(float x, float y, float z)
	{
		this.rotation = new Quaternion4f(x, y, z);
		return this;
	}

	public Transform setRotation(Quaternion4f rotation)
	{
		this.rotation.set(rotation);
		return this;
	}

	public Transform setScale(float v)
	{
		this.scale.set(v, v, v);
		return this;
	}

	public Transform setScale(float x, float y, float z)
	{
		this.scale.set(x, y, z);
		return this;
	}

	public Transform setScale(Vector3f scale)
	{
		this.scale.set(scale);
		return this;
	}

	public Transform translate(Vector3f translation)
	{
		this.translation.add(translation);
		return this;
	}

	public Transform translate(float x, float y, float z)
	{
		this.translation.add(x, y, z);
		return this;
	}

	public Transform rotate(Quaternion4f quaternion)
	{
		rotation.multiply(quaternion);
		return this;
	}

	public Transform rotate(Vector3f axis, float angle)
	{
//		rotation.free();
		Quaternion4f oldRotation = rotation;
		rotation = (Quaternion4f) new Quaternion4f(axis, angle).multiply(oldRotation).normalize();
		return this;
	}

	public Transform rotate(float x, float y, float z)
	{
		rotate(new Quaternion4f(x, y, z));
		return this;
	}

	public Transform scale(float x, float y, float z)
	{
		this.scale.multiply(x, y, z);
		return this;
	}

	public Transform scale(float v)
	{
		return scale(v, v, v);
	}

	public Vector3f transform(Vector3f vector)
	{
		return getMatrix().multiply(vector);
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Transform transform = (Transform) o;

		if (!translation.equals(transform.translation)) return false;
		if (!rotation.equals(transform.rotation)) return false;
		return scale.equals(transform.scale);

	}

	@Override
	public int hashCode()
	{
		int result = translation.hashCode();
		result = 31 * result + rotation.hashCode();
		result = 31 * result + scale.hashCode();
		return result;
	}
}
