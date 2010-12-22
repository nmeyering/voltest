package voltest;

import java.awt.Rectangle;
import java.util.Arrays;

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
			Matrix mvp,
			Rectangle view
			)
	{
		return mvp.multiply( 
				new Vector(
					(2.0 * (win.x - view.x)) / view.width - 1,
					(2.0 * (win.y - view.y)) / view.height - 1,
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
	public static Matrix translationMatrix(
			Vector a
			)
	{
		Matrix ret = Matrix.createIdentity(4);
		ret.setValue(0, 3, a.x);
		ret.setValue(1, 3, a.y);
		ret.setValue(2, 3, a.z);
		
		return ret;
	}

	public static Matrix toTransformMatrix(Vector forward, Vector right,
			Vector up, Vector pos) {
		double[][] elements =
			new double[][]{
			{right.x,right.y,right.z,0},
			{up.x,up.y,up.z,0},
			{forward.x,forward.y,forward.z,0},
			{0,0,0,1}};
		return 
			new Matrix( elements ).multiply(
				translationMatrix(
					Vector.minus(
						pos)));
	}
}
