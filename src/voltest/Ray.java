package voltest;

public class Ray
{
	public Vector origin, direction, inv_direction;
	public int[] sign;
	
	public Ray(
			Vector org,
			Vector dir)
	{
		origin = org;
		direction = dir;
		inv_direction = new Vector(
				1/dir.x,
				1/dir.y,
				1/dir.y);
		sign = new int[3];
		sign[0] = (inv_direction.x < 0) ? 1 : 0;
		sign[1] = (inv_direction.y < 0) ? 1 : 0;
		sign[2] = (inv_direction.z < 0) ? 1 : 0;
	}
	
	public static Vector intersection(
			Vector win,
			Box box,
			Camera c
			)
	{
		Vector ray = MathUtil.unproject(
						win,
						c
						);
		
		Vector n = new Vector(
								0,
								0,
								1);
		
		ray = Vector.multiply(
				Vector.scalarProduct(
						n,
						box.max()
				)
				/
				Vector.scalarProduct(
						n,
						ray
				),
				ray
				);
		return ray;
	}
	
	public static boolean intersects(
			Vector win,
			Box box,
			Camera c
			)
	{
		Vector ray = intersection(
				win,
				box,
				c
				);
		
		return (
				ray.x > box.src.x
				&& ray.y > box.src.y
				&& ray.x < box.max().x
				&& ray.y < box.max().y
				);
		
	}
}
