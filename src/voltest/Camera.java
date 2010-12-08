package voltest;

import java.awt.Rectangle;

public class Camera {
	private Matrix mvp;
	private Rectangle view;
	private Vector pos, up, right, forward, move;
	
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
		mvp = MathUtil.perspectiveMatrix(
				fovy,
				aspect,
				zNear,
				zFar);
		view = new Rectangle(
				0,
				0,
				width,
				height);
		move = new Vector(0, 0, 0);
		pos = new Vector(0, 0, 0);
		right = new Vector(1, 0, 0);
		up = new Vector(0, 1, 0);
		forward = new Vector(0, 0, -1);
	}
	
	public void translateX(
			Vector v)
	{
		Vector tmp = Vector.plus(pos,
				Vector.multiply(
						v.x,
						right
						)
				);
		pos = tmp;
		mvp = mvp.multiply(MathUtil.translationMatrix( tmp ));
	}
	public void translateZ(
			Vector v)
	{
		Vector tmp = Vector.plus(pos,
				Vector.multiply(
						v.z,
						forward
						)
				);
		pos = tmp;
		mvp = mvp.multiply(MathUtil.translationMatrix( tmp ));
	}
	
	public void rotateX(
			double angle)
	{
		Matrix mat = MathUtil.rotationMatrix(
				right,
				-angle);
		forward = mat.multiply( forward );
		up = Vector.crossProduct(
				forward,
				right);
		right = Vector.crossProduct(
				up,
				forward);
		forward.normalize();
		right.normalize();
		up.normalize();
		mvp = mvp.multiply(mat);
		
	}
	public void rotateY(
			double angle)
	{
		Matrix mat = MathUtil.rotationMatrix(
				new Vector(0, 1, 0),
				-angle);
		forward = mat.multiply( forward );
		right = Vector.crossProduct(
				new Vector(0, 1, 0),
				forward);
		up = Vector.crossProduct(
				forward,
				right);
		forward.normalize();
		right.normalize();
		up.normalize();
		mvp = mvp.multiply(mat);
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
	public void printGizmo()
	{
		System.out.println("up: " + up);
		System.out.println("right: " + right);
		System.out.println("forward: " + forward);
	}
	public void rotateYaround(Vector center, double angle)
	{
		Matrix mat = MathUtil.rotationMatrix(
				new Vector(0, 1, 0),
				-angle);
		forward = mat.multiply( forward );
		right = Vector.crossProduct(
				new Vector(0, 1, 0),
				forward);
		up = Vector.crossProduct(
				forward,
				right);
		forward.normalize();
		right.normalize();
		up.normalize();
		mvp = mvp.multiply(mat);
	}
}
