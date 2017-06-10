package me.tom.sparse.old_math;

public class SquareSpiralCoordinate implements Comparable
{
	private int i;
	private int x,y;
	private int dx,dy;
	
	public SquareSpiralCoordinate()
	{
		this(0, 0, 0, 0, -1);
	}
	
	SquareSpiralCoordinate(int i, int x, int y, int dx, int dy)
	{
		this.i = i;
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
	}
	
	public int getIndex()
	{
		return i;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public SquareSpiralCoordinate next()
	{
		return next(false);
	}
	
	public SquareSpiralCoordinate next(boolean mutate)
	{
		int ndx = dx;
		int ndy = dy;
		if(x == y || (x < 0 && x == -y) || (x > 0 && x == 1-y))
		{
			ndx = -dy;
			ndy = dx;
		}
		if(mutate)
		{
			this.i++;
			this.dx = ndx;
			this.dy = ndy;
			this.x += ndx;
			this.y += ndy;
			return this;
		}else{
			return new SquareSpiralCoordinate(i + 1, x + ndx, y + ndy, ndx, ndy);
		}
	}
	
	@Override
	public String toString()
	{
		return "SquareSpiralCoordinate{" +
				"x=" + x +
				", y=" + y +
				'}';
	}
	
	@Override
	public int compareTo(Object o)
	{
		if(o == null)
			return 1;
		if(o instanceof Number)
			return Integer.compare(i, ((Number) o).intValue());
		if(o instanceof SquareSpiralCoordinate)
			return Integer.compare(i, ((SquareSpiralCoordinate) o).i);
		throw new IllegalArgumentException("Cannot compare ${this.class.name} and ${o.class.name}");
	}
}