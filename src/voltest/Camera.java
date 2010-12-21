package voltest;

import java.awt.Rectangle;

public class Camera {
	private Matrix mvp;
	private Rectangle view;
	private Vector pos, up, right, forward, move;
	private Double azimuth, inclination;
	
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
	public void translate(
			Vector v)
	{
		pos = Vector.plus(pos, v);
		mvp = mvp.multiply(
				MathUtil.translationMatrix( 
						Vector.minus(v)));
	}
	
	public void translateX(
			double amount)
	{
		Vector tmp = Vector.multiply(
						amount,
						right
						);
		pos = Vector.plus(pos, tmp);
		mvp = mvp.multiply(MathUtil.translationMatrix( Vector.minus(tmp) ));
	}
	public void translateZ(
			double amount)
	{
		Vector tmp = Vector.multiply(
						amount,
						forward
						);
		pos = Vector.plus(pos, tmp);
		mvp = mvp.multiply(MathUtil.translationMatrix( Vector.minus(tmp) ));
	}
	public void rotateAzimuthal(
			double angle)
	{
		Matrix rot = MathUtil.rotationMatrix(
				Vector.Y, 
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
		mvp = mvp.multiply( rot );
		System.out.println("position:" + "{"+pos.x+","+pos.y+","+pos.z+"}");
		System.out.println("orientation:");
		System.out.println(forward);
		System.out.println(right);
		System.out.println(up);
		System.out.println( Vector.scalarProduct(forward, right));
		System.out.println( Vector.scalarProduct(forward, up));
		System.out.println( Vector.scalarProduct(right, up));
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
		mvp = mvp.multiply( rot );
		System.out.println("position:" + "{"+pos.x+","+pos.y+","+pos.z+"}");
		System.out.println("orientation:");
		System.out.println(forward);
		System.out.println(right);
		System.out.println(up);
		System.out.println( Vector.scalarProduct(forward, right));
		System.out.println( Vector.scalarProduct(forward, up));
		System.out.println( Vector.scalarProduct(right, up));
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
}
