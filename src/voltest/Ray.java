package voltest;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Ray
{
	public Vector origin, direction, inv_direction;
	public int[] sign;
	public static final int MAX = 128;
	
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
	
	public static byte collect(
			Vector win,
			Box box,
			Matrix m,
			Rectangle view,
			Vector pos,
			Texture3D tex
			)
	{
		List<Vector> points = intersections(win,box,m,view,pos);
		if(points.size() != 2)
			return 0;
		Vector start = points.get(0);
		Vector dir = Vector.minus(points.get(1),start);
		int samples = (int)(MAX * (dir.norm()/(Math.sqrt(3)*box.dim)));
		double ret = 0.0;
		byte b;
		for(int i = 0; i < samples; ++i)
		{
			Vector sample = Vector.plus(
					start,
					Vector.multiply(
							(double)i/MAX,
							dir
							)
					);
			b = tex.sample(
						(sample.x - box.min().x)/box.dim,
						(sample.y - box.min().y)/box.dim,
						(sample.z - box.min().z)/box.dim
						);
			ret += b;
		}
		b = (byte)(Math.max(Math.min(ret * 0.15, 255), 0));
		return b;
	}
	public static List<Vector> intersections(
			Vector win,
			Box box,
			Matrix m,
			Rectangle view,
			Vector pos
			)
	{
		List<Vector> ret = new ArrayList<Vector>();
		
		Vector ray = Vector.minus( MathUtil.unproject(
						win,
						m,
						view
						),
						pos
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
		for(int i = 0; i < 6; ++i)
		{
			on_face = ( i % 2 == 0 ) ? box.min() : box.max();
			tmp = Vector.plus( pos, Vector.multiply(
					( Vector.scalarProduct( on_face, normals[i] ) 
					- Vector.scalarProduct( pos, normals[i] ) )
					/ Vector.scalarProduct( ray, normals[i] ),
					ray
					)
					);
			if (box.contains(tmp))
				ret.add(tmp);
		}
		return ret;
	}
	
}
