package voltest;

import java.awt.Rectangle;

public class MathUtil
{
	public static Matrix perspectiveMatrix(
			double fovy,
			double aspect,
			double zNear,
			double zFar
			)
	{
		double f = 1.0 / Math.tan( fovy * 0.5 );
		double[][] ret = new double[4][4];
		ret[0][0] = f / aspect;
		ret[1][1] = f;
		ret[2][2] = (zFar + zNear) / (zNear - zFar);
		ret[2][3] = (2.0 * zFar * zNear) / (zNear - zFar);
		ret[3][2] = -1.0;
		
		return new Matrix( ret );
	}
	
	public static Vector unproject(
			Vector win,
			Camera c
			)
	{
		return c.mvp().invert().multiply( 
				new Vector(
					(2.0 * (win.x - c.x())) / c.w() - 1,
					(2.0 * (win.y - c.y())) / c.h() - 1,
					2.0 * (win.z) - 1,
					1
					)
				);
	}
	public static Matrix rotationMatrix(
			Vector a,
			double angle
			)
	{
		System.out.printf("trying to rotate %f about (%f,%f,%f)\n", angle, a.x, a.y, a.z);
	    double sinx = Math.sin(angle),
	    cosx = Math.cos(angle),
	    cosxc = 1 - cosx,
	    x = a.x,
	    y = a.y,
	    z = a.z;

		Matrix ret = new Matrix(
			new double[][]
			{{cosx + x*x*cosxc,   x*y*cosxc - z*sinx, x*z*cosxc + y*sinx, 0},
			{x*y*cosxc + z*sinx, cosx + y*y*cosxc,   y*z*cosxc - x*sinx, 0},
			{x*z*cosxc - y*sinx, y*z*cosxc + x*sinx, cosx + z*z*cosxc,   0},
			{0,               0,               0,               1}}
		);

		return ret;
	
	}
}