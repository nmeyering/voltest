package voltest;

import java.awt.Rectangle;

public class Camera {
	private Matrix mvp;
	private Rectangle view;
	
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
			60,
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
				Math.toRadians( fovy ),
				aspect,
				zNear,
				zFar
				);
		view = new Rectangle(
				0,
				0,
				width,
				height
				);
				
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
}
