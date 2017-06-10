package me.tom.sparse.math.vector.floats.impl;

import me.tom.sparse.math.vector.floats.Vector3f;
import me.tom.sparse.math.vector.floats.Vector4f;

public class Color extends Vector4f
{
	public static Color getI(int r, int g, int b)
	{
		return getI(r, g, b, 255);
	}
	
	public static Color getI(int r, int g, int b, int a)
	{
		return new Color((float)r/255.0f, (float)g/255.0f, (float)b/255.0f, (float)a/255.0f);
	}
	
	public static Color get(int rgb)
	{
		int alpha = (rgb >> 24) & 0xFF;
		int red = (rgb >> 16) & 0xFF;
		int green = (rgb >> 8) & 0xFF;
		int blue = rgb & 0xFF;
		return getI(red, green, blue, alpha);
	}
	
	public static Color getHSB(float h, float s, float b)
	{
		java.awt.Color c = java.awt.Color.getHSBColor(h, s, b);
		return getI(c.getRed(), c.getGreen(), c.getBlue());
	}
	
	public Color()
	{
	
	}
	
	public Color(Vector4f other)
	{
		set(other);
	}
	
	public Color(float r, float g, float b)
	{
		this(r, g, b, 1);
	}

	public Color(float r, float g, float b, float a)
	{
		set(r, g, b, a);
	}
	
	public Color(float value)
	{
		set(value);
	}
	
	public int getIntAlpha()
	{
		return (int)(w*255.0f);
	}

	public int getIntRed()
	{
		return (int)(x*255.0f);
	}

	public int getIntGreen()
	{
		return (int)(y*255.0f);
	}

	public int getIntBlue()
	{
		return (int)(z*255.0f);
	}

	public int toHex()
	{
		int rgb = getIntAlpha();
		rgb = (rgb << 8) + getIntRed();
		rgb = (rgb << 8) + getIntGreen();
		rgb = (rgb << 8) + getIntBlue();
		return rgb;
	}

	public static int toHex(int a, int r, int g, int b)
	{
		int rgb = a;
		rgb = (rgb << 8) + r;
		rgb = (rgb << 8) + g;
		rgb = (rgb << 8) + b;
		return rgb;
	}

	public Color variate(float maxAmount)
	{
		return (Color) subtract(
				(float) ((Math.random()*maxAmount*2)-maxAmount),
				(float) ((Math.random()*maxAmount*2)-maxAmount),
				(float) ((Math.random()*maxAmount*2)-maxAmount),
				0
		);
	}

	public Vector4f setR(float r)
	{
		return setX(r);
	}

	public Vector4f setG(float g)
	{
		return setY(g);
	}

	public Vector4f setB(float b)
	{
		return setZ(b);
	}

	public Vector4f setA(float a)
	{
		return setW(a);
	}

	public float r()
	{
		return x;
	}

	public float g()
	{
		return y;
	}

	public float b()
	{
		return z;
	}

	public float a()
	{
		return w;
	}

	public Color clamp()
	{
		return (Color) set(Math.max(Math.min(x(), 1), 0), Math.max(Math.min(y(), 1), 0), Math.max(Math.min(z(), 1), 0), Math.max(Math.min(w(), 1), 0));
	}

	public Color variateBrightness(float maxAmount)
	{
		float v = (float) ((Math.random() * maxAmount * 2) - maxAmount);
		return (Color) subtract(v,v,v,0);
	}

//	public void bind()
//	{
//		Sparse.getRenderer().setColor(this);
//		GL11.glColor4f(x, y, z, w);
//	}

	@Override
	public String toString()
	{
		return "Color{" +
				"r=" + x +
				", g=" + y +
				", b=" + z +
				", a=" + w +
				'}';
	}



	/*
	--------------------------------------------------------------------------------------------
								Generated Vector Swizzling code
	--------------------------------------------------------------------------------------------
	 */

	//TODO: Generate vector swizzling code
	public Vector3f rgb()
	{
		return new Vector3f(x, y, z);
	}
}
