package voltest;
public class Box
{
	public Vector vecs[]; //[0] - min, [1] - max
	public double dim;
	
	public Box(
			Vector min,
			Vector max
			)
	{
		this.vecs = new Vector[] {min,max};
		this.dim = Vector.minus(vecs[1], vecs[0]).norm();
	}
	
	public Box(
			Vector src,
			double dim
			)
	{
		this(
			src,
			new Vector(
				src.x + dim,
				src.y + dim, 
				src.z + dim
				)
			);
		this.dim = dim;
	}
	public Vector min()
	{
		return vecs[0];
	}
	public Vector max()
	{
		return vecs[1];
	}
	public boolean contains( Vector v )
	{
		double e = 0.0001;
		Vector epsilon = new Vector(e, e, e);
		Vector min = Vector.minus(min(), epsilon);
		Vector max = Vector.plus(max(), epsilon);
		return (
			v.x >= min.x
			&& v.y >= min.y
			&& v.z >= min.z
			&& v.x <= max.x
			&& v.y <= max.y
			&& v.z <= max.z 
			);
	}
	public String toString()
	{
		return "min: " + vecs[0] + " max: " + vecs[1];
	}
	
	public boolean intersects(
			Ray r,
			double t0,
			double t1
			)
	{
		double tmin, tmax, tymin, tymax, tzmin, tzmax;
        
		tmin = (vecs[r.sign[0]].x - r.origin.x) * r.inv_direction.x;                                             
		tmax = (vecs[1-r.sign[0]].x - r.origin.x) * r.inv_direction.x;                                           
		tymin = (vecs[r.sign[1]].y - r.origin.y) * r.inv_direction.y;                                           
		tymax = (vecs[1-r.sign[1]].y - r.origin.y) * r.inv_direction.y;                                          
//		System.out.printf("tmin: %f, tmax: %f \n", tmin, tmax);
//		System.out.printf("tymin: %f, tymax: %f \n", tymin, tymax);
		if ( (tmin > tymax) || (tymin > tmax) )                                                                              
		  return false;
		if (tymin > tmin) 
		  tmin = tymin;                                                                                                      
		if (tymax < tmax)
		  tmax = tymax;
//		System.out.printf("tmin: %f, tmax: %f \n", tmin, tmax);
//		System.out.printf("tymin: %f, tymax: %f \n", tymin, tymax);
		tzmin = (vecs[r.sign[2]].z - r.origin.z) * r.inv_direction.z;                                            
		tzmax = (vecs[1-r.sign[2]].z - r.origin.z) * r.inv_direction.z;                                          
//		System.out.printf("vecs[r.sign[2]].z: %f, r.origin.z: %f, r.inv_direction.z: %f \n", vecs[r.sign[2]].z, r.origin.z, r.inv_direction.z);
//		System.out.printf("vecs[1 - r.sign[2]].z: %f, r.origin.z: %f, r.inv_direction.z: %f \n", vecs[1 - r.sign[2]].z, r.origin.z, r.inv_direction.z);
//		System.out.printf("tzmin: %f, tzmax: %f \n", tzmin, tzmax);
		if ( (tmin > tzmax) || (tzmin > tmax) )                                                                              
		  return false;                                                                                                      
		if (tzmin > tmin)                                                                                                    
		  tmin = tzmin;                                                                                                      
		if (tzmax < tmax)
		  tmax = tzmax;
//		System.out.printf("tmin: %f, tmax: %f \n", tmin, tmax);
		return ( (tmin < t1) && (tmax > t0) );
	}

	public Vector center()
	{
		return Vector.multiply(0.5, Vector.minus(max(), min()) );
	}

}