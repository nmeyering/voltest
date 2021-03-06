package voltest;

import java.awt.Rectangle;

public class Camera {
	private Matrix mvp, perspective;
	private Rectangle view;
	private Vector pos, up, right, forward;
	
	public Camera()
	{
		this(
			640,
			480,
			Math.toRadians( 60 ),
			4.0/3.0,
			1,
			100
			);
	}

	private void
	regenerateMVP()
	{
		mvp = 
			perspective.multiply(
				MathUtil.toTransformMatrix(
					forward, 
					right, 
					up, 
					pos));
	}

	public Camera(
			int width,
			int height
			)
	{
		this(
			width,
			height,
			Math.toRadians(60),
			(double)width/height,
			1,
			100
			);
	}
	public Camera(
			int width,
			int height,
			double fovy,
			double aspect,
			int zNear,
			int zFar
			)
	{
		perspective = 
			MathUtil.perspectiveMatrix(
				fovy,
				aspect,
				zNear,
				zFar);
		view = new Rectangle(
				0,
				0,
				width,
				height);
		pos = new Vector(0, 0, 0);
		right = new Vector(1, 0, 0);
		up = new Vector(0, 1, 0);
		forward = new Vector(0, 0, -1);
		regenerateMVP();
	}

	public void translate(
			Vector v)
	{
		pos = Vector.plus(pos, v);
		regenerateMVP();
	}
	
	public void translateX(
			double amount)
	{
		translate(
			Vector.multiply(
				amount,
				right));
	}

	public void translateZ(
			double amount)
	{
		translate(
			Vector.multiply(
				amount,
				forward));
	}

	public void rotateAzimuthal(
			double angle)
	{
		Vector oldpos = new Vector( pos );
		pos = 
			MathUtil.rotationMatrix(
				Vector.Y, 
				angle).multiply(pos);
		forward = Vector.minus(
				pos);
		right = Vector.crossProduct(
				Vector.Y,
				forward);
		up = Vector.crossProduct(
				forward, 
				right);
		forward.normalize();
		right.normalize();
		up.normalize();
		regenerateMVP();
	}
	
	public void rotateElevational(
			double angle)
	{
		Matrix rot = MathUtil.rotationMatrix(
				right,
				angle);
		pos = rot.multiply(pos);
		forward = Vector.minus(
				pos);
		right = Vector.crossProduct(
				Vector.Y,
				forward);
		up = Vector.crossProduct(
				forward, 
				right);
		forward.normalize();
		right.normalize();
		up.normalize();
		regenerateMVP();
	}
	public Vector pos()
	{
		return pos;
	}
	public int x()
	{
		return view.x;
	}
	public int y()
	{
		return view.y;
	}
	public int w()
	{
		return view.width;
	}
	public int h()
	{
		return view.height;
	}
	public Matrix mvp()
	{
		return mvp;
	}
	public Rectangle view()
	{
		return view;
	}
}
