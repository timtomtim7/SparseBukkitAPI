package me.tom.sparse.math;

import me.tom.sparse.math.vector.floats.Vector3f;
import me.tom.sparse.math.vector.floats.Vector4f;

import java.nio.FloatBuffer;
import java.util.Arrays;

public class Matrix4f
{
	public static Matrix4f identity()
	{
		return new Matrix4f(new float[][]{
				new float[]{1, 0, 0, 0},
				new float[]{0, 1, 0, 0},
				new float[]{0, 0, 1, 0},
				new float[]{0, 0, 0, 1}
		});
	}

	public static Matrix4f translation(float x, float y, float z)
	{
		return new Matrix4f(new float[][]{
				new float[]{1, 0, 0, x},
				new float[]{0, 1, 0, y},
				new float[]{0, 0, 1, z},
				new float[]{0, 0, 0, 1}
		});
	}

	public static Matrix4f scale(float x, float y, float z)
	{
		return new Matrix4f(new float[][]{
				new float[]{x, 0, 0, 0},
				new float[]{0, y, 0, 0},
				new float[]{0, 0, z, 0},
				new float[]{0, 0, 0, 1}
		});
	}

	public static Matrix4f rotation(float x, float y, float z)
	{
//		x = (float) Math.toRadians(x);
		x = (float) Math.toRadians(x);
		y = (float) Math.toRadians(y);
		z = (float) Math.toRadians(z);

		Matrix4f rz = new Matrix4f(new float[][]{
				new float[]{(float) Math.cos(z), (float) -Math.sin(z), 0, 0},
				new float[]{(float) Math.sin(z), (float) Math.cos(z), 0, 0},
				new float[]{0, 0, 1, 0},
				new float[]{0, 0, 0, 1}
		});

		Matrix4f rx = new Matrix4f(new float[][]{
				new float[]{1, 0, 0, 0},
				new float[]{0, (float) Math.cos(x), (float) -Math.sin(x), 0},
				new float[]{0, (float) Math.sin(x), (float) Math.cos(x), 0},
				new float[]{0, 0, 0, 1}
		});

		Matrix4f ry = new Matrix4f(new float[][]{
				new float[]{(float) Math.cos(y), 0, (float) -Math.sin(y), 0},
				new float[]{0, 1, 0, 0},
				new float[]{(float) Math.sin(y), 0, (float) Math.cos(y), 0},
				new float[]{0, 0, 0, 1}
		});

		return rz.multiply(ry.multiply(rx));
	}

	public static Matrix4f rotation(Vector3f forward, Vector3f up)
	{
		Vector3f f = forward.clone().normalize();
		Vector3f r = up.clone().normalize().cross(f).normalize();
		Vector3f u = f.cross(r).normalize();

		return rotation(f, u, r);
	}

	public static Matrix4f rotation(Vector3f f, Vector3f u, Vector3f r)
	{
		return new Matrix4f(new float[][]{
				new float[]{r.x(), r.y(), r.z(), 0},
				new float[]{u.x(), u.y(), u.z(), 0},
				new float[]{f.x(), f.y(), f.z(), 0},
				new float[]{0, 0, 0, 1}
		});
	}

	public static Matrix4f perspective(float fov, float aspectRatio, float zNear, float zFar)
	{
		float tanHalfFOV = (float) Math.tan(Math.toRadians(fov / 2.0));
		float zRange = zNear - zFar;

		return new Matrix4f(new float[][]{
				new float[]{1.0f / (tanHalfFOV * aspectRatio), 0, 0, 0},
				new float[]{0, 1.0f / tanHalfFOV, 0, 0},
				new float[]{0, 0, (-zNear - zFar) / zRange, 2 * zFar * zNear / zRange},
				new float[]{0, 0, 1, 0}
		});
	}

	public static Matrix4f orthographic(float left, float right, float bottom, float top, float near, float far)
	{
		float width = right - left;
		float height = top - bottom;
		float depth = far - near;

		return new Matrix4f(new float[][]{
				new float[]{2f / width, 0, 0, -(right + left) / width},
				new float[]{0, 2f / height, 0, -(top + bottom) / height},
				new float[]{0, 0, -2f / depth, -(far + near) / depth},
				new float[]{0, 0, 0, 1}
		});
	}

	private float[][] data;

	private Matrix4f(float[][] data)
	{
		this.data = data;
	}

	public Matrix4f()
	{
		this.data = new float[4][4];
	}

	public Matrix4f(float... values)
	{
		this();
		if (values.length != 16)
			throw new IllegalArgumentException("Invalid array size, expected 16");

		for (int i = 0; i < values.length; i++)
			data[i / 4][i % 4] = values[i];
	}

	public Matrix4f multiply(Matrix4f other)
	{
		Matrix4f result = new Matrix4f();
		float[][] rd = other.data;

		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				result.set(i, j,
						data[i][0] * rd[0][j] +
						data[i][1] * rd[1][j] +
						data[i][2] * rd[2][j] +
						data[i][3] * rd[3][j]);
			}
		}

		return result;
	}

	public Vector4f multiply(Vector4f vec)
	{
		float[] vecValues = new float[]{vec.x(), vec.y(), vec.z(), vec.w()};
		float[] result = new float[4];

		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				result[i] += data[i][j] * vecValues[j];
			}
		}
		return new Vector4f(result[0], result[1], result[2], result[3]);
	}

	public Vector3f multiply(Vector3f vec)
	{
		return multiply(new Vector4f(vec.x(), vec.y(), vec.z(), 1)).xyz();//.swizzle("xyz");
	}

	public float get(int x, int y)
	{
		return data[x][y];
	}

	public Matrix4f set(int x, int y, float value)
	{
		data[x][y] = value;
		return this;
	}
	
	public float determinant()
	{
		float f = data[0][0]
				* ((data[1][1] * data[2][2] * data[3][3] + data[1][2] * data[2][3] * data[3][1] + data[1][3] * data[2][1] * data[3][2])
				- data[1][3] * data[2][2] * data[3][1]
				- data[1][1] * data[2][3] * data[3][2]
				- data[1][2] * data[2][1] * data[3][3]);
		f -= data[0][1]
				* ((data[1][0] * data[2][2] * data[3][3] + data[1][2] * data[2][3] * data[3][0] + data[1][3] * data[2][0] * data[3][2])
				- data[1][3] * data[2][2] * data[3][0]
				- data[1][0] * data[2][3] * data[3][2]
				- data[1][2] * data[2][0] * data[3][3]);
		f += data[0][2]
				* ((data[1][0] * data[2][1] * data[3][3] + data[1][1] * data[2][3] * data[3][0] + data[1][3] * data[2][0] * data[3][1])
				- data[1][3] * data[2][1] * data[3][0]
				- data[1][0] * data[2][3] * data[3][1]
				- data[1][1] * data[2][0] * data[3][3]);
		f -= data[0][3]
				* ((data[1][0] * data[2][1] * data[3][2] + data[1][1] * data[2][2] * data[3][0] + data[1][2] * data[2][0] * data[3][1])
				- data[1][2] * data[2][1] * data[3][0]
				- data[1][0] * data[2][2] * data[3][1]
				- data[1][1] * data[2][0] * data[3][2]);
		return f;
	}

	public Matrix4f inverse()
	{
		float determinant = determinant();
		
		if (determinant != 0)
		{
			/*
			 * data[0][0] data[0][1] data[0][2] data[0][3]
			 * data[1][0] data[1][1] data[1][2] data[1][3]
			 * data[2][0] data[2][1] data[2][2] data[2][3]
			 * data[3][0] data[3][1] data[3][2] data[3][3]
			 */
			Matrix4f dest = new Matrix4f();
			float determinant_inv = 1f / determinant;
			
			// first row
			float t00 = determinant3x3(data[1][1], data[1][2], data[1][3], data[2][1], data[2][2], data[2][3], data[3][1], data[3][2], data[3][3]);
			float t01 = -determinant3x3(data[1][0], data[1][2], data[1][3], data[2][0], data[2][2], data[2][3], data[3][0], data[3][2], data[3][3]);
			float t02 = determinant3x3(data[1][0], data[1][1], data[1][3], data[2][0], data[2][1], data[2][3], data[3][0], data[3][1], data[3][3]);
			float t03 = -determinant3x3(data[1][0], data[1][1], data[1][2], data[2][0], data[2][1], data[2][2], data[3][0], data[3][1], data[3][2]);
			// second row
			float t10 = -determinant3x3(data[0][1], data[0][2], data[0][3], data[2][1], data[2][2], data[2][3], data[3][1], data[3][2], data[3][3]);
			float t11 = determinant3x3(data[0][0], data[0][2], data[0][3], data[2][0], data[2][2], data[2][3], data[3][0], data[3][2], data[3][3]);
			float t12 = -determinant3x3(data[0][0], data[0][1], data[0][3], data[2][0], data[2][1], data[2][3], data[3][0], data[3][1], data[3][3]);
			float t13 = determinant3x3(data[0][0], data[0][1], data[0][2], data[2][0], data[2][1], data[2][2], data[3][0], data[3][1], data[3][2]);
			// third row
			float t20 = determinant3x3(data[0][1], data[0][2], data[0][3], data[1][1], data[1][2], data[1][3], data[3][1], data[3][2], data[3][3]);
			float t21 = -determinant3x3(data[0][0], data[0][2], data[0][3], data[1][0], data[1][2], data[1][3], data[3][0], data[3][2], data[3][3]);
			float t22 = determinant3x3(data[0][0], data[0][1], data[0][3], data[1][0], data[1][1], data[1][3], data[3][0], data[3][1], data[3][3]);
			float t23 = -determinant3x3(data[0][0], data[0][1], data[0][2], data[1][0], data[1][1], data[1][2], data[3][0], data[3][1], data[3][2]);
			// fourth row
			float t30 = -determinant3x3(data[0][1], data[0][2], data[0][3], data[1][1], data[1][2], data[1][3], data[2][1], data[2][2], data[2][3]);
			float t31 = determinant3x3(data[0][0], data[0][2], data[0][3], data[1][0], data[1][2], data[1][3], data[2][0], data[2][2], data[2][3]);
			float t32 = -determinant3x3(data[0][0], data[0][1], data[0][3], data[1][0], data[1][1], data[1][3], data[2][0], data[2][1], data[2][3]);
			float t33 = determinant3x3(data[0][0], data[0][1], data[0][2], data[1][0], data[1][1], data[1][2], data[2][0], data[2][1], data[2][2]);
			
			// transpose and divide by the determinant
			dest.data[0][0] = t00 * determinant_inv;
			dest.data[1][1] = t11 * determinant_inv;
			dest.data[2][2] = t22 * determinant_inv;
			dest.data[3][3] = t33 * determinant_inv;
			dest.data[0][1] = t10 * determinant_inv;
			dest.data[1][0] = t01 * determinant_inv;
			dest.data[2][0] = t02 * determinant_inv;
			dest.data[0][2] = t20 * determinant_inv;
			dest.data[1][2] = t21 * determinant_inv;
			dest.data[2][1] = t12 * determinant_inv;
			dest.data[0][3] = t30 * determinant_inv;
			dest.data[3][0] = t03 * determinant_inv;
			dest.data[1][3] = t31 * determinant_inv;
			dest.data[3][1] = t13 * determinant_inv;
			dest.data[3][2] = t23 * determinant_inv;
			dest.data[2][3] = t32 * determinant_inv;
			return dest;
		} else
			return null;
	}
	
	public Matrix4d toMatrix4d()
	{
		double[] result = new double[4*4];
		for(int i = 0; i < data.length; i++)
			for(int j = 0; j < data.length; j++)
				result[(i * 4) + j] = data[i][j];
		
		return new Matrix4d(result);
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Matrix4f matrix4f = (Matrix4f) o;

		return Arrays.deepEquals(data, matrix4f.data);
	}

	@Override
	public int hashCode()
	{
		return Arrays.deepHashCode(data);
	}

	@Override
	public String toString()
	{
		String result =
				Arrays.toString(data[0])+"\n"+
				Arrays.toString(data[1])+"\n"+
				Arrays.toString(data[2])+"\n"+
				Arrays.toString(data[3]);
		return result;
	}

	private static float determinant3x3(float t00, float t01, float t02,
										float t10, float t11, float t12,
										float t20, float t21, float t22)
	{
		return t00 * (t11 * t22 - t12 * t21)
				+ t01 * (t12 * t20 - t10 * t22)
				+ t02 * (t10 * t21 - t11 * t20);
	}

	public float[][] getData()
	{
		return clone2DFloatArray(data);
	}

	public FloatBuffer asFloatBuffer()
	{
		FloatBuffer buffer = FloatBuffer.allocate(4 * 4);

		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				buffer.put(data[i][j]);

		buffer.flip();
		return buffer;
	}

	public static float[][] clone2DFloatArray(float[][] data)
	{
		float[][] clone = new float[data.length][];

		for (int i = 0; i < data.length; i++)
		{
			clone[i] = new float[data[i].length];
			System.arraycopy(data[i], 0, clone[i], 0, data[i].length);
		}

		return clone;
	}
}
