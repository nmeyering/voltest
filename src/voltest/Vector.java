package voltest;

public class Vector
{

	public final static double EPSILON = 0.0000000001;

	public final static Vector[] versor = {
		new Vector(1,0,0),
		new Vector(0,1,0),
		new Vector(0,0,1)
		};
	public double x;
	public double y;
	public double z;
	public double w;

	public Vector(double x, double y, double z, double w)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	public Vector(double x, double y, double z)
	{
		this(
				x,
				y,
				z,
				1.0
				);
	}
	
	public Vector()
	{
		this(
				0.0,
				0.0,
				0.0
				);
	}
	
	public Vector(Vector copy) {
		this(
				copy.x,
				copy.y,
				copy.z,
				copy.w
				);
	}

	public static Vector plus(
			Vector a,
			Vector b
			)
	{
		return new Vector(
				a.x + b.x,
				a.y + b.y,
				a.z + b.z
				);
	}
	
	public static Vector minus(
			Vector a,
			Vector b
			)
	{
		return new Vector(
				a.x - b.x,
				a.y - b.y,
				a.z - b.z
				);
	}
	public static Vector minus(
			Vector v
			)
	{
		return minus(
				new Vector(),
				v
				);
	}
	
	public void homogenize()
	{
		if (Math.abs(w) < EPSILON) {
			throw new RuntimeException("Failed to homogenize: w is zero");
		}

		this.x = x / w;
		this.y = y / w;
		this.z = z / w;
		this.w = 1;

	}

	public void normalize()
	{
		if (norm() < EPSILON) {
			throw new RuntimeException("Failed to normalize: length is zero");
		}

		double norm = norm();

		this.x = this.x / norm;
		this.y = this.y / norm;
		this.z = this.z / norm;
	}

	public double norm()
	{
		homogenize();
		return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
	}

	public static double scalarProduct(
			Vector a,
			Vector b
			)
	{
		return a.x * b.x + a.y * b.y + a.z * b.z;
	}

	public static Vector crossProduct(
			Vector a,
			Vector b
			)
	{
		return new Vector(
				a.y * b.z - a.z * b.y,
				a.z * b.x - a.x * b.z,
				a.x * b.y - a.y * b.x
				);
	}

	public static Vector multiply(
			double f,
			Vector v
			)
	{
		return new Vector(
				v.x * f,
				v.y * f,
				v.z * f
				);
	}

	public String toString()
	{
		return "(" + x + "," + y + "," + z + "," + w + ")";
	}

	public void printFormatted()
	{
		System.out.printf("(%2.4f, %2.4f, %2.4f, %2.4f)", x, y, z, w);
	}
	
	public boolean isVersor()
	{
		for( int i = 0; i < versor.length; ++i )
			if (this.equals( versor[i] ))
				return true;
		return false;
	}
	
}
