package voltest;

public class Ray
{
	public static boolean intersect(
			Vector win,
			Box box,
			Context c
			)
	{
		Vector ray = Context.unproject(
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
		return (
				ray.x > box.src.x
				&& ray.y > box.src.y
				&& ray.x < box.max().x
				&& ray.y < box.max().y
				);
	}
	
}