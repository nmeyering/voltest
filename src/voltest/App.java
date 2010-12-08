package voltest;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;

public class App implements KeyListener
{
	ViewPanel panel;
	Camera cam;
	Box box;
	byte[] clearData;
	int[] clearDataInt;
	Dimension size;
	DataBuffer data;
	int inc;
	boolean drawing;
	
	private final int[] SET = {0x00};
	
	public App()
	{
		size = new Dimension(
				256,
				256
				);
		clearData = new byte[ size.width * size.height ];
		clearDataInt = new int[ size.width * size.height ];
		Arrays.fill(
				clearDataInt,
				46
				);
		Arrays.fill(
				clearData,
				(byte) 46
				);
		data = new DataBufferByte( 
				clearData,
				size.width
				* size.height
				);
		JFrame frame = new JFrame();
		panel = new ViewPanel(
				size,
				data
				);
		frame.addKeyListener(this);
		box = new Box(
			new Vector(
					-5,
					-5,
					-25
					),
					10
				);
		cam = new Camera(
				size.width,
				size.height,
				Math.toRadians(60),
				3.0/4.0,
				1,
				100
				);
		frame.add( panel );
		frame.setVisible( true );
		byte[][][] ball = new byte[64][64][64];
		for(int i = 0; i < 64; ++i )
			for(int j = 0; j < 64; ++j )
				for(int k = 0; k < 64; ++k )
				{
					int[] x = {Math.abs(i-32), Math.abs(j-32), Math.abs(k-32)};
					int value = x[0]*x[0]+x[1]*x[1]+x[2]*x[2];
					if(value < 32)
						ball[i][j][k] = (byte)0xFF;
				}
		Texture3D tex = new Texture3D(64);
		
		draw();
		while(true)
		{
//			try
//			{
//				Thread.sleep(500);
//			}
//			catch(InterruptedException e)
//			{
//			}
		}
	}
	
	public static void main( String[] args )
	{
		new App();
	}
	public void redraw()
	{
		if(!drawing)
			draw();
	}
	public void draw()
	{
		drawing = true;
		data = new DataBufferByte(
			clearData,
			size.width
			* size.height
			);
		panel.raster.setPixels(
				0,
				0,
				size.width,
				size.height,
				clearDataInt);
		Matrix inv = cam.mvp().invert();
		System.out.println(cam.pos());
		
		long tmp = System.currentTimeMillis();
		for( int i = 0; i < size.width; ++i )
		{
			for( int j = 0; j < size.height; ++j )
			{
//					if(	
//						box.intersects(
//						new Ray(
//							cam.pos(),
//								MathUtil.unproject(
//								new Vector(
//										i,
//										j,
//										0),
//								inv,
//								cam.view())
//							),
//						-10000,
//						10000)
//						)
					{
						List<Vector> points = 
							Ray.intersections(
									new Vector(i,j,0),
									box,
									inv,
									cam.view(),
									cam.pos()
								);
						int col = 0;
						if(points.size() == 2)
						{
							col = (int) 
									(
									Vector.minus(
									points.get(1),
									points.get(0)
									).norm()
									*	box.dim
									);
						}
						panel.setPixel(
							i,
							j,
							new int[] {col}
//							new int[] {len*127}
//							SET
							);
					}
			}
		}		
		System.out.println(
				System.currentTimeMillis() - tmp);
		panel.repaint();
		drawing = false;
	}
	public void keyPressed( KeyEvent e )
	{
		int code = e.getKeyCode();
		switch( code )
		{
			case KeyEvent.VK_RIGHT:
				cam.rotateY(Math.toRadians(-5));
				break;
			case KeyEvent.VK_LEFT: 
				cam.rotateY(Math.toRadians(5));
				break;
			case KeyEvent.VK_UP: 
				cam.rotateX(Math.toRadians(5));
				break;
			case KeyEvent.VK_DOWN: 
				cam.rotateX(Math.toRadians(-5));
				break;
			case KeyEvent.VK_W: 
				cam.translateZ(new Vector(0,0,-.05));
				break;
			case KeyEvent.VK_S: 
				cam.translateZ(new Vector(0,0,.05));
				break;
			case KeyEvent.VK_A: 
				cam.translateX(new Vector(-.05,0,0));
				break;
			case KeyEvent.VK_D: 
				cam.translateX(new Vector(.05,0,0));
				break;
			case KeyEvent.VK_ESCAPE: 
				System.exit(0);
				break;
			default:
		}
		redraw();
	}
	public void keyReleased( KeyEvent e ){}
	public void keyTyped( KeyEvent e ){}
}
