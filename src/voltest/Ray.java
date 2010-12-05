package voltest;

import java.util.ArrayList;
import java.util.List;

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
				1/dir.z);
		sign = new int[3];
		sign[0] = (inv_direction.x < 0) ? 1 : 0;
		sign[1] = (inv_direction.y < 0) ? 1 : 0;
		sign[2] = (inv_direction.z < 0) ? 1 : 0;
	}
	
	public static List<Vector> intersections(
			Vector win,
			Box box,
			Camera c
			)
	{
		List<Vector> ret = new ArrayList<Vector>();
		
		Vector ray = Vector.minus( MathUtil.unproject(
						win,
						c.mvp(),
						c.view()
						),
						c.pos()
						);
		
		Vector on_face, tmp;
		Vector[] normals = {
				new Vector(-1,0,0),
				new Vector(1,0,0),
				new Vector(0,-1,0),
				new Vector(0,1,0),
				new Vector(0,0,-1),
				new Vector(0,0,1)
		};
		int len = 0;
		for(int i = 0; i < 6; ++i)
		{
			on_face = ( i % 2 == 0 ) ? box.min() : box.max();
			tmp = Vector.plus( c.pos(), Vector.multiply(
					( Vector.scalarProduct( on_face, normals[i] ) 
					- Vector.scalarProduct( c.pos(), normals[i] ) )
					/ Vector.scalarProduct( ray, normals[i] ),
					ray
					)
					);
			if (box.contains(tmp))
			{
				len++;
				ret.add(tmp);
			}
		}
		return ret;
	}
	
}
