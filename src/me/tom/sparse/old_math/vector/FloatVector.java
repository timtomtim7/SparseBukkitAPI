package me.tom.sparse.old_math.vector;

import me.tom.sparse.old_math.vector.vec2.Vector2f;
import me.tom.sparse.old_math.vector.vec3.Vector3f;
import me.tom.sparse.old_math.vector.vec4.Vector4f;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public interface FloatVector
{
	List<Consumer<Float>> getSetters();
	List<Supplier<Float>> getGetters();

	int getElementCount();
	
	default FloatVector swizzleSelf(String string)
	{
		int elementCount = getElementCount();
		if(string.length() != elementCount)
			throw new IllegalArgumentException("swizzleSelf must have exactly the same number of elements as the original.");

		String order = string.toLowerCase().matches("[uvst01]+") ? "uvst" : string.toLowerCase().matches("[rgba01]+") ? "rgba" : "xyzw";
		char[] chars = string.toCharArray();

		List<Integer> indices = new ArrayList<>();

		for (char c : chars)
		{
			int index = order.indexOf(c);
			if(index == -1 && c != '0' && c != '1')
				throw new IllegalArgumentException("Unknown swizzle character \""+c+"\" in swizzle layout \""+order+"\"");
			if(c == '1')
				index--;

			if(index > elementCount)
				throw new IllegalArgumentException("Cannot swizzle "+c+" on a "+ elementCount +" element vector");

			indices.add(index);
		}

//		List<Supplier<Float>> getters = getGetters();
		List<Float> values = getGetters().stream().<Float>map(Supplier::get).collect(Collectors.toList());
		List<Consumer<Float>> setters = getSetters();
		for (int i = 0; i < indices.size(); i++)
		{
			Integer index = indices.get(i);
			setters.get(i).accept(index < 0 ? (float)(index * -1 - 1) : values.get(index));
		}

		return this;
	}

	default FloatVector swizzle(String string)
	{
		if(string.length() < 2)
			throw new IllegalArgumentException("Swizzle string must be at least 2 characters.");
		if(string.length() > 4)
			throw new IllegalArgumentException("Swizzle string cannot contain more than 4 characters.");
		
		String order = string.toLowerCase().matches("[uvst01]+") ? "uvst" : string.toLowerCase().matches("[rgba01]+") ? "rgba" : "xyzw";
		char[] chars = string.toCharArray();
		
		List<Integer> indices = new ArrayList<>();
		int elementCount = getElementCount();
		
		for (char c : chars)
		{
			int index = order.indexOf(c);
			if(index == -1 && c != '0' && c != '1')
				throw new IllegalArgumentException("Unknown swizzle character \""+c+"\" in swizzle layout \""+order+"\"");
			if(c == '1')
				index--;
//			System.out.println(c+": "+index);

			if(index > elementCount)
				throw new IllegalArgumentException("Cannot swizzle "+c+" on a "+ elementCount +" element vector");

			indices.add(index);
		}
		
		List<Supplier<Float>> getters = getGetters();
		Function<Integer, Float> get = i -> {
			int index = indices.get(i);
			if(index >= 0)
				return getters.get(index).get();
			return (float)(index * -1 - 1);
		};
		
		if(indices.size() == 2)
			return Vector2f.create(get.apply(0), get.apply(1));
		else if(indices.size() == 3)
			return Vector3f.create(get.apply(0), get.apply(1), get.apply(2));
		else if(indices.size() == 4)
			return Vector4f.create(get.apply(0), get.apply(1), get.apply(2), get.apply(3));
		return null;
	}

	default FloatVector swizzleReference(String string)
	{
		if(string.length() < 2)
			throw new IllegalArgumentException("Swizzle string must be at least 2 characters.");
		if(string.length() > 4)
			throw new IllegalArgumentException("Swizzle string cannot contain more than 4 characters.");

		String order = string.toLowerCase().matches("[uvst]+") ? "uvst" : string.toLowerCase().matches("[rgba]+") ? "rgba" : "xyzw";
		char[] chars = string.toCharArray();

		List<Integer> indices = new ArrayList<>();
		int elementCount = getElementCount();

		for (char c : chars)
		{
			int index = order.indexOf(c);
			if(index == -1)
				throw new IllegalArgumentException("Unknown swizzle character \""+c+"\" in swizzle layout \""+order+"\"");

			if(index > elementCount)
				throw new IllegalArgumentException("Cannot swizzle "+c+" on a "+ elementCount +" element vector");

			indices.add(index);
		}

		List<Supplier<Float>> getters = getGetters();
		List<Consumer<Float>> setters = getSetters();
		if(indices.size() == 2)
		{
			return new Vector2f()
			{
				public float getX() { return getters.get(indices.get(0)).get(); }
				public float getY() { return getters.get(indices.get(1)).get(); }

				public Vector2f setX(float x) { setters.get(indices.get(0)).accept(x); return this; }
				public Vector2f setY(float y) { setters.get(indices.get(1)).accept(y); return this; }

				public Vector2f clone() { return Vector2f.create(getX(), getY()); }
			};
		}else if(indices.size() == 3)
		{
			return new Vector3f()
			{
				public float getX() { return getters.get(indices.get(0)).get(); }
				public float getY() { return getters.get(indices.get(1)).get(); }
				public float getZ() { return getters.get(indices.get(2)).get(); }

				public Vector3f setX(float x) { setters.get(indices.get(0)).accept(x); return this; }
				public Vector3f setY(float y) { setters.get(indices.get(1)).accept(y); return this; }
				public Vector3f setZ(float z) { setters.get(indices.get(2)).accept(z); return this; }

				public Vector3f clone() { return Vector3f.create(getX(), getY(), getZ()); }
			};
		}else if(indices.size() == 4)
		{
			return new Vector4f()
			{
				public float getX() { return getters.get(indices.get(0)).get(); }
				public float getY() { return getters.get(indices.get(1)).get(); }
				public float getZ() { return getters.get(indices.get(2)).get(); }
				public float getW() { return getters.get(indices.get(3)).get(); }

				public Vector4f setX(float x) { setters.get(indices.get(0)).accept(x); return this; }
				public Vector4f setY(float y) { setters.get(indices.get(1)).accept(y); return this; }
				public Vector4f setZ(float z) { setters.get(indices.get(2)).accept(z); return this; }
				public Vector4f setW(float w) { setters.get(indices.get(3)).accept(w); return this; }

				public Vector4f clone() { return Vector4f.create(getX(), getY(), getZ(), getW()); }
			};
		}
		return null;
	}
}
