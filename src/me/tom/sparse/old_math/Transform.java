package me.tom.sparse.old_math;

import me.tom.sparse.old_math.vector.vec3.Vector3f;
import me.tom.sparse.old_math.vector.vec4.impl.Quaternion;

public class Transform
{
	private Transform parent;

	private Vector3f translation;
	private Quaternion rotation;
	private Vector3f scale;

	private int lastHashCode;
	private Matrix4f lastMatrix;

	public Transform()
	{
		this(Vector3f.create(0, 0, 0), new Quaternion(0, 0, 0, 1), Vector3f.create(1, 1, 1));
	}

	public Transform(Vector3f translation, Quaternion rotation, Vector3f scale)
	{
		this.translation = translation;
		this.rotation = rotation;
		this.scale = scale;
	}

	public Matrix4f calculateMatrix()
	{
		if(lastHashCode != hashCode() || lastMatrix == null)
		{
			Matrix4f translationMatrix = Matrix4f.translation(translation.getX(), translation.getY(), translation.getZ());
			Matrix4f rotationMatrix = rotation.toRotationMatrix();
			Matrix4f scaleMatrix = Matrix4f.scale(scale.getX(), scale.getY(), scale.getZ());

			Matrix4f parentMatrix = parent == null ? Matrix4f.identity() : parent.calculateMatrix();

			lastMatrix = parentMatrix.multiply(translationMatrix.multiply(rotationMatrix.multiply(scaleMatrix)));
			lastHashCode = hashCode();
			return lastMatrix;
		}
		return lastMatrix;
	}

	public Vector3f getTranslation()
	{
		return translation;
	}

	public Quaternion getRotation()
	{
		return rotation;
	}

	public Vector3f getScale()
	{
		return scale;
	}

	public Transform lookAt(Vector3f pos, Vector3f up)
	{
		Vector3f forward = pos.clone().subtract(translation).normalize();
		up = up.clone().subtract(forward.clone().multiply(up.clone().dot(forward)));
		return setRotation(new Quaternion(Matrix4f.rotation(forward, up)));
	}

	public Transform setUp(Vector3f up)
	{
		//TODO: Check if this works
		Vector3f forward = getRotation().getForward();
		up = up.clone().subtract(forward.clone().multiply(up.clone().dot(forward)));
		return setRotation(new Quaternion(Matrix4f.rotation(forward, up)));
	}

	public Transform translate(Vector3f translation)
	{
		this.translation.add(translation);
		return this;
	}

	public Transform rotate(Vector3f axis, float angle)
	{
		rotation = (Quaternion) new Quaternion(axis, angle).multiply(rotation).normalize();
		return this;
	}

	public Transform rotate(Quaternion quaternion)
	{
		rotation.multiply(quaternion);
		return this;
	}

	public Transform rotateEuler(float x, float y, float z)
	{
		rotate(Quaternion.euler(x, y, z));
		return this;
	}

	public Transform translate(float x, float y, float z)
	{
		this.translation.add(x, y, z);
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

	public Transform setTranslation(Vector3f translation)
	{
		return setTranslation(translation.getX(), translation.getY(), translation.getZ());
	}

	public Transform setTranslation(float x, float y, float z)
	{
		translation.set(x, y, z);
		return this;
	}

	public Transform setRotation(Quaternion rotation)
	{
		return setRotation(rotation.getX(), rotation.getY(), rotation.getZ(), rotation.getW());
	}

	public Transform setAxisRotation(Vector3f axis, float angle)
	{
		return setRotation(new Quaternion(axis, angle));
	}

	public Transform setAxisRotation(float axisX, float axisY, float axisZ, float angle)
	{
		return setAxisRotation(Vector3f.create(axisX, axisY, axisZ), angle);
	}

	public Transform setEulerRotation(float x, float y, float z)
	{
		return setRotation(Quaternion.euler(x, y, z));
	}

	public Transform setRotation(float x, float y, float z, float w)
	{
		rotation.set(x, y, z, w);
		return this;
	}

	public Transform setScale(Vector3f scale)
	{
		return setScale(scale.getX(), scale.getY(), scale.getZ());
	}

	public Transform setScale(float x, float y, float z)
	{
		scale.set(x, y, z);
		return this;
	}

	public Transform setScale(float v)
	{
		return setScale(v, v, v);
	}

	public Transform getParent()
	{
		return parent;
	}

	public Transform setParent(Transform parent)
	{
		this.parent = parent;
		return this;
	}

	public Vector3f getStackedTranslation()
	{
		if(parent == null)
			return translation.clone();
		return parent.getStackedTranslation().add(translation);
	}

	public Quaternion getStackedRotation()
	{
		if(parent == null)
			return (Quaternion) rotation.clone();
		return (Quaternion) parent.getStackedRotation().multiply(rotation);
	}

	public Vector3f transform(Vector3f vector)
	{
		return calculateMatrix().multiply(vector);
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Transform transform = (Transform) o;

		if (parent != null ? !parent.equals(transform.parent) : transform.parent != null) return false;
		if (!translation.equals(transform.translation)) return false;
		if (!rotation.equals(transform.rotation)) return false;
		return scale.equals(transform.scale);

	}

	@Override
	public int hashCode()
	{
		int result = parent != null ? parent.hashCode() : 0;
		result = 31 * result + translation.hashCode();
		result = 31 * result + rotation.hashCode();
		result = 31 * result + scale.hashCode();
		return result;
	}
}
