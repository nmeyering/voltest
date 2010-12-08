package voltest;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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
	Texture3D tex;
	
	private final int[] SET = {0x00};
	
	public App()
	{
		size = new Dimension(
				512,
				512
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
					-2,
					-2,
					-25
					),
					4
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
		final int S = 256;
		Random r = new Random();
		byte[][][] ball = new byte[S][S][S];
		for(int i = 0; i < S; ++i )
			for(int j = 0; j < S; ++j )
				for(int k = 0; k < S; ++k )
				{
					int[] x = {Math.abs(i-S/2), Math.abs(j-S/2), Math.abs(k-S/2)};
					int value = (int)Math.sqrt(x[0]*x[0]+x[1]*x[1]+x[2]*x[2]);
					ball[i][j][k] = (byte)r.nextInt(32);
					if(value < S/2)
						ball[i][j][k] = (byte)0x20;
					if( x[0]< S/5 && x[1] < S/5 && x[2] < S/5)
						ball[i][j][k] = (byte)0x40;
					if( i < S/5 && j < S/5 && k < S/5)
						ball[i][j][k] = (byte)0x40;
				}
		tex = new Texture3D( ball );
		
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
						byte col = 
							Ray.collect(
									new Vector(i,j,0),
									box,
									inv,
									cam.view(),
									cam.pos(),
									tex
								);
						panel.setPixel(
							i,
							j,
							new int[] {col}
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
