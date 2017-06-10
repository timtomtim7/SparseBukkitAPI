package me.tom.sparse.old_math.vector.vec4.impl;

import me.tom.sparse.old_math.vector.vec3.Vector3f;
import me.tom.sparse.old_math.vector.vec4.Vector4f;

import java.util.concurrent.ThreadLocalRandom;

public class Color implements Vector4f
{
//	public static final Color SPARSE_RED = new Color(1f, 0.3764f, 0.0f);
//	public static final Color SPARSE_GREEN = new Color(0.0f, 1f, 0.3764f);
	public static final Color SPARSE_BLUE     = new Color(0.0f, 0.3764f, 1f);
//	public static final Color SPARSE_RED = new Color(0.8f, 0.2f, 0.3f);
//	public static final Color SPARSE_GREEN = new Color(0.2f, 0.8f, 0.3f);
//	public static final Color SPARSE_BLUE = new Color(0.2f, 0.3f, 0.8f);
	public static final Color WHITE           = new Color(1, 1, 1);
	public static final Color BLACK           = new Color(0, 0, 0);
	public static final Color BLUE            = new Color(0, 0, 1);
	public static final Color BABY_BLUE       = new Color(0.5f, 0.784f, 1);
	public static final Color LIGHT_BLUE      = new Color(0, 0.5f, 1);
	public static final Color CYAN            = new Color(0, 1, 1);
	public static final Color LIME_GREEN      = new Color(0, 1, 0);
	public static final Color GREEN           = new Color(0, 0.5f, 0);
	public static final Color YELLOW          = new Color(1, 1, 0);
	public static final Color YELLOW_GREEN    = new Color(0.5f, 1, 0);
	public static final Color RED             = new Color(1, 0, 0);
	public static final Color DARK_RED        = new Color(0.5f, 0, 0);
	public static final Color MAGENTA         = new Color(1, 0, 1);
	public static final Color PURPLE          = new Color(0.5f, 0, 0.5f);
	public static final Color PINK            = new Color(1, 0.5f, 1);
	public static final Color ORANGE          = new Color(1, 0.5f, 0);
	public static final Color VERY_LIGHT_GREY = new Color(0.9f, 0.9f, 0.9f);
	public static final Color LIGHT_GREY      = new Color(0.7f, 0.7f, 0.7f);
	public static final Color GREY            = new Color(0.5f, 0.5f, 0.5f);
	public static final Color GREY2           = new Color(0.4f, 0.4f, 0.4f);
	public static final Color DARK_GREY       = new Color(0.3f, 0.3f, 0.3f);
	public static final Color VERY_DARK_GREY  = new Color(0.2f, 0.2f, 0.2f);
	public static final Color DARKEST_GREY    = new Color(0.1f, 0.1f, 0.1f);
	public static final Color TORCH_YELLOW    = new Color(1, 0.6f, 0.05f);

	public static Color fromHSB(float h, float s, float b)
	{
		java.awt.Color c = java.awt.Color.getHSBColor(h, s, b);
		return fromIntRGB(c.getRed(), c.getGreen(), c.getBlue());
	}

	public static Color fromIntRGB(int r, int g, int b)
	{
		return new Color((float)r/255.0f, (float)g/255.0f, (float)b/255.0f);
	}

	public static Color fromIntRGB(int r, int g, int b, int a)
	{
		return new Color((float)r/255.0f, (float)g/255.0f, (float)b/255.0f, (float)a/255.0f);
	}

	public static Color fromHex(int rgb)
	{
		int alpha = (rgb >> 24) & 0xFF;
		int red = (rgb >> 16) & 0xFF;
		int green = (rgb >> 8) & 0xFF;
		int blue = rgb & 0xFF;
		return fromIntRGB(red, green, blue, alpha);
	}

	public static Color randomHue(float s, float b)
	{
		return fromHSB((float)Math.random(), s, b);
	}

	public float r, g, b, a;

	public Color(Vector4f other)
	{
		this(other.getX(), other.getY(), other.getZ(), other.getW());
	}

	public Color(Vector3f other)
	{
		this(other, 1);
	}

	public Color(Vector3f other, float a)
	{
		this(other.getX(), other.getY(), other.getZ(), a);
	}

	public Color(float r, float g, float b, float a)
	{
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	public Color(float r, float g, float b)
	{
		this(r, g, b, 1);
	}

//	public Color(byte r, byte g, byte b, byte a)
//	{
//		this(0x000000FF & r, 0x000000FF & g, 0x000000FF & b, 0x000000FF & a);
//	}
//
//	public Color(byte r, byte g, byte b)
//	{
//		this(r, g, b, Byte.MAX_VALUE);
//	}

//	public Color(int r, int g, int b, int a)
//	{
//
//	}
//
//	public Color(int r, int g, int b)
//	{
//		this(r, g, b, 255);
//	}

	public int getIntAlpha()
	{
		return (int)(a*255.0f);
	}

	public int getIntRed()
	{
		return (int)(r*255.0f);
	}

	public int getIntGreen()
	{
		return (int)(g*255.0f);
	}

	public int getIntBlue()
	{
		return (int)(b*255.0f);
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

	public Color clamp()
	{
		return (Color) set(Math.max(Math.min(getX(), 1), 0), Math.max(Math.min(getY(), 1), 0), Math.max(Math.min(getZ(), 1), 0), Math.max(Math.min(getW(), 1), 0));
	}

	public Color variateBrightness(float maxAmount)
	{
		float v = (ThreadLocalRandom.current().nextFloat() * maxAmount * 2) - maxAmount;
		return (Color) subtract(v,v,v,0);
	}

	public Color invert(boolean alpha)
	{
		set(1-r, 1-g, 1-b, alpha ? 1-a : a);
		return this;
	}

	@Override
	public float getX()
	{
		return r;
	}

	@Override
	public float getY()
	{
		return g;
	}

	@Override
	public float getZ()
	{
		return b;
	}

	@Override
	public float getW()
	{
		return a;
	}

	@Override
	public Vector4f setX(float x)
	{
		this.r = x;
		return this;
	}

	@Override
	public Vector4f setY(float y)
	{
		this.g = y;
		return this;
	}

	@Override
	public Vector4f setZ(float z)
	{
		this.b = z;
		return this;
	}

	@Override
	public Vector4f setW(float w)
	{
		this.a = w;
		return this;
	}

	public Vector3f rgb()
	{
		return new Vector3f()
		{
			@Override
			public float getX()
			{
				return r;
			}

			@Override
			public float getY()
			{
				return g;
			}

			@Override
			public float getZ()
			{
				return b;
			}

			@Override
			public Vector3f setX(float x)
			{
				r = x;
				return this;
			}

			@Override
			public Vector3f setY(float y)
			{
				g = y;
				return this;
			}

			@Override
			public Vector3f setZ(float z)
			{
				b = z;
				return this;
			}

			@Override
			public Vector3f clone()
			{
				return Vector3f.create(r, g, b);
			}
		};
	}

	@Override
	public Color clone()
	{
		return new Color(r, g, b, a);
	}
}
