package me.tom.sparse.math;

import me.tom.sparse.math.vector.doubles.Vector3d;
import me.tom.sparse.math.vector.doubles.Vector4d;

import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.util.Arrays;

public class Matrix4d
{
	public static Matrix4d identity()
	{
		return new Matrix4d(new double[][]{
				new double[]{1, 0, 0, 0},
				new double[]{0, 1, 0, 0},
				new double[]{0, 0, 1, 0},
				new double[]{0, 0, 0, 1}
		});
	}

	public static Matrix4d translation(double x, double y, double z)
	{
		return new Matrix4d(new double[][]{
				new double[]{1, 0, 0, x},
				new double[]{0, 1, 0, y},
				new double[]{0, 0, 1, z},
				new double[]{0, 0, 0, 1}
		});
	}

	public static Matrix4d scale(double x, double y, double z)
	{
		return new Matrix4d(new double[][]{
				new double[]{x, 0, 0, 0},
				new double[]{0, y, 0, 0},
				new double[]{0, 0, z, 0},
				new double[]{0, 0, 0, 1}
		});
	}

	public static Matrix4d rotation(double x, double y, double z)
	{
		x = Math.toRadians(x);
		y = Math.toRadians(y);
		z = Math.toRadians(z);

		Matrix4d rz = new Matrix4d(new double[][]{
				new double[]{Math.cos(z), -Math.sin(z), 0, 0},
				new double[]{Math.sin(z), Math.cos(z), 0, 0},
				new double[]{0, 0, 1, 0},
				new double[]{0, 0, 0, 1}
		});

		Matrix4d rx = new Matrix4d(new double[][]{
				new double[]{1, 0, 0, 0},
				new double[]{0, Math.cos(x), -Math.sin(x), 0},
				new double[]{0, Math.sin(x), Math.cos(x), 0},
				new double[]{0, 0, 0, 1}
		});

		Matrix4d ry = new Matrix4d(new double[][]{
				new double[]{Math.cos(y), 0, -Math.sin(y), 0},
				new double[]{0, 1, 0, 0},
				new double[]{Math.sin(y), 0, Math.cos(y), 0},
				new double[]{0, 0, 0, 1}
		});

		return rz.multiply(ry.multiply(rx));
	}

	public static Matrix4d rotation(Vector3d forward, Vector3d up)
	{
		Vector3d f = forward.clone().normalize();
		Vector3d r = up.clone().normalize().cross(f).normalize();
		Vector3d u = f.cross(r).normalize();

		return rotation(f, u, r);
	}

	public static Matrix4d rotation(Vector3d f, Vector3d u, Vector3d r)
	{
		return new Matrix4d(new double[][]{
				new double[]{r.x(), r.y(), r.z(), 0},
				new double[]{u.x(), u.y(), u.z(), 0},
				new double[]{f.x(), f.y(), f.z(), 0},
				new double[]{0, 0, 0, 1}
		});
	}

	public static Matrix4d perspective(double fov, double aspectRatio, double zNear, double zFar)
	{
		double tanHalfFOV = Math.tan(Math.toRadians(fov / 2.0));
		double zRange = zNear - zFar;

		return new Matrix4d(new double[][]{
				new double[]{1.0f / (tanHalfFOV * aspectRatio), 0, 0, 0},
				new double[]{0, 1.0f / tanHalfFOV, 0, 0},
				new double[]{0, 0, (-zNear - zFar) / zRange, 2 * zFar * zNear / zRange},
				new double[]{0, 0, 1, 0}
		});
	}

	public static Matrix4d orthographic(double left, double right, double bottom, double top, double near, double far)
	{
		double width = right - left;
		double height = top - bottom;
		double depth = far - near;

		return new Matrix4d(new double[][]{
				new double[]{2f / width, 0, 0, -(right + left) / width},
				new double[]{0, 2f / height, 0, -(top + bottom) / height},
				new double[]{0, 0, -2f / depth, -(far + near) / depth},
				new double[]{0, 0, 0, 1}
		});
	}
	
	public static Matrix4d multiplyAll(Matrix4d... matrices)
	{
		if(matrices.length <= 1)
			throw new IllegalArgumentException("Not enough matrices to multiply");
		
		Matrix4d a = matrices[0];
		Matrix4d b = new Matrix4d();
		a.multiplyTo(matrices[1], b);
		
		if(matrices.length > 2)
		{
			a = new Matrix4d();
			for(int i = 2; i < matrices.length; i++)
			{
				b.multiplyTo(matrices[i], a);
				a.copyTo(b);
			}
		}
		return b;
	}
	
	private double[][] data;

	private Matrix4d(double[][] data)
	{
		this.data = data;
	}

	public Matrix4d()
	{
		this.data = new double[4][4];
	}

	public Matrix4d(double... values)
	{
		this();
		if (values.length != 16)
			throw new IllegalArgumentException("Invalid array size, expected 16");

		for (int i = 0; i < values.length; i++)
			data[i / 4][i % 4] = values[i];
	}
	
	public Matrix4d multiplyTo(Matrix4d other, Matrix4d destination)
	{
		if(destination == this || destination == other)
			throw new IllegalArgumentException("Cannot multiply to one of the operands.");
		
		double[][] rd = other.data;
		
		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				destination.set(i, j, data[i][0] * rd[0][j] +
						data[i][1] * rd[1][j] +
						data[i][2] * rd[2][j] +
						data[i][3] * rd[3][j]);
			}
		}
		
		return destination;
	}

	public Matrix4d multiply(Matrix4d other)
	{
		Matrix4d result = new Matrix4d();
		double[][] rd = other.data;

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

	public Vector4d multiply(Vector4d vec)
	{
		double[] vecValues = new double[]{vec.x(), vec.y(), vec.z(), vec.w()};
		double[] result = new double[4];

		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				result[i] += data[i][j] * vecValues[j];
			}
		}
		return new Vector4d(result[0], result[1], result[2], result[3]);
	}

	public Vector3d multiply(Vector3d vec)
	{
		return multiply(new Vector4d(vec.x(), vec.y(), vec.z(), 1)).xyz();//.swizzle("xyz");
	}
	
	public Matrix4d copyTo(Matrix4d other)
	{
		if(other == this)
			throw new IllegalArgumentException("Cannot copy to self.");
		for(int i = 0; i < data.length; i++)
			for(int j = 0; j < data[i].length; j++)
				other.data[i][j] = data[i][j];
		return other;
	}
	
	public double get(int x, int y)
	{
		return data[x][y];
	}

	public Matrix4d set(int x, int y, double value)
	{
		data[x][y] = value;
		return this;
	}
	
	public double determinant()
	{
		double f = data[0][0]
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

	public Matrix4d inverse()
	{
		double determinant = determinant();
		
		if (determinant != 0)
		{
			/*
			 * data[0][0] data[0][1] data[0][2] data[0][3]
			 * data[1][0] data[1][1] data[1][2] data[1][3]
			 * data[2][0] data[2][1] data[2][2] data[2][3]
			 * data[3][0] data[3][1] data[3][2] data[3][3]
			 */
			Matrix4d dest = new Matrix4d();
			double determinant_inv = 1f / determinant;
			
			// first row
			double t00 = determinant3x3(data[1][1], data[1][2], data[1][3], data[2][1], data[2][2], data[2][3], data[3][1], data[3][2], data[3][3]);
			double t01 = -determinant3x3(data[1][0], data[1][2], data[1][3], data[2][0], data[2][2], data[2][3], data[3][0], data[3][2], data[3][3]);
			double t02 = determinant3x3(data[1][0], data[1][1], data[1][3], data[2][0], data[2][1], data[2][3], data[3][0], data[3][1], data[3][3]);
			double t03 = -determinant3x3(data[1][0], data[1][1], data[1][2], data[2][0], data[2][1], data[2][2], data[3][0], data[3][1], data[3][2]);
			// second row
			double t10 = -determinant3x3(data[0][1], data[0][2], data[0][3], data[2][1], data[2][2], data[2][3], data[3][1], data[3][2], data[3][3]);
			double t11 = determinant3x3(data[0][0], data[0][2], data[0][3], data[2][0], data[2][2], data[2][3], data[3][0], data[3][2], data[3][3]);
			double t12 = -determinant3x3(data[0][0], data[0][1], data[0][3], data[2][0], data[2][1], data[2][3], data[3][0], data[3][1], data[3][3]);
			double t13 = determinant3x3(data[0][0], data[0][1], data[0][2], data[2][0], data[2][1], data[2][2], data[3][0], data[3][1], data[3][2]);
			// third row
			double t20 = determinant3x3(data[0][1], data[0][2], data[0][3], data[1][1], data[1][2], data[1][3], data[3][1], data[3][2], data[3][3]);
			double t21 = -determinant3x3(data[0][0], data[0][2], data[0][3], data[1][0], data[1][2], data[1][3], data[3][0], data[3][2], data[3][3]);
			double t22 = determinant3x3(data[0][0], data[0][1], data[0][3], data[1][0], data[1][1], data[1][3], data[3][0], data[3][1], data[3][3]);
			double t23 = -determinant3x3(data[0][0], data[0][1], data[0][2], data[1][0], data[1][1], data[1][2], data[3][0], data[3][1], data[3][2]);
			// fourth row
			double t30 = -determinant3x3(data[0][1], data[0][2], data[0][3], data[1][1], data[1][2], data[1][3], data[2][1], data[2][2], data[2][3]);
			double t31 = determinant3x3(data[0][0], data[0][2], data[0][3], data[1][0], data[1][2], data[1][3], data[2][0], data[2][2], data[2][3]);
			double t32 = -determinant3x3(data[0][0], data[0][1], data[0][3], data[1][0], data[1][1], data[1][3], data[2][0], data[2][1], data[2][3]);
			double t33 = determinant3x3(data[0][0], data[0][1], data[0][2], data[1][0], data[1][1], data[1][2], data[2][0], data[2][1], data[2][2]);
			
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

	public Matrix4f toMatrix4f()
	{
		float[] result = new float[4*4];
		for(int i = 0; i < data.length; i++)
			for(int j = 0; j < data.length; j++)
				result[(i * 4) + j] = (float) data[i][j];
		
		return new Matrix4f(result);
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Matrix4d matrix4d = (Matrix4d) o;

		return Arrays.deepEquals(data, matrix4d.data);
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

	private static double determinant3x3(double t00, double t01, double t02,
										double t10, double t11, double t12,
										double t20, double t21, double t22)
	{
		return t00 * (t11 * t22 - t12 * t21)
				+ t01 * (t12 * t20 - t10 * t22)
				+ t02 * (t10 * t21 - t11 * t20);
	}

	public double[][] getData()
	{
		return clone2DDoubleArray(data);
	}

	public DoubleBuffer asDoubleBuffer()
	{
		DoubleBuffer buffer = DoubleBuffer.allocate(4*4);

		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				buffer.put(data[i][j]);

		buffer.flip();
		return buffer;
	}

	public static double[][] clone2DDoubleArray(double[][] data)
	{
		double[][] clone = new double[data.length][];

		for (int i = 0; i < data.length; i++)
		{
			clone[i] = new double[data[i].length];
			System.arraycopy(data[i], 0, clone[i], 0, data[i].length);
		}

		return clone;
	}
}
