package voltest;
public class Box
{
	public Vector src;
	public Vector dim;
	
	public Box(
			Vector src,
			Vector dim
			)
	{
		this.src = src;
		this.dim = dim;
	}
	public Box( 
			double x,
			double y,
			double z,
			double dx,
			double dy,
			double dz
			)
	{
		this(
				new Vector(
						x,
						y,
						z
						),
				new Vector(
						dx,
						dy,
						dz
						)
				);
	}
	
	public Box(
			Vector src,
			double dim
			)
	{
		this(
				src,
				new Vector(
						dim,
						dim,
						dim
						)
				);
	}
	public Vector min()
	{
		return src;
	}
	public Vector max()
	{
		return Vector.plus(
				src,
				dim
				);
	}
	
}
