package voltest;

public class Ray
{
	public static Vector intersection(
			Vector win,
			Box box,
			Camera c
			)
	{
		Vector ray = Util.unproject(
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