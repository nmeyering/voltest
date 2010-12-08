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
	private ViewPanel panel;
	private Camera cam;
	private Box box;
	private Dimension size;
	private int inc;
	private boolean drawing;
	private Texture3D tex;
	
	private final int[] SET = {0x00};
	
	public App()
	{
		size = new Dimension(
				320,
				240
				);
		JFrame frame = new JFrame();
		panel = new ViewPanel(
				size
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
				size.width/size.height,
				1,
				100
				);
		cam.rotateY(0.005);
		frame.add( panel );
		frame.setVisible( true );
		final int S = 256;
		Random r = new Random();
		byte[][][] ball = new byte[S][S][S];
		for(int i = 0; i < S; ++i )
			for(int j = 0; j < S; ++j )
				for(int k = 0; k < S; ++k )
				{
						ball[i][j][k] = (byte)0x50;
//					int[] x = {Math.abs(i-S/2), Math.abs(j-S/2), Math.abs(k-S/2)};
//					int value = (int)Math.sqrt(x[0]*x[0]+x[1]*x[1]+x[2]*x[2]);
//					ball[i][j][k] = (byte)r.nextInt(32);
//					if(value < S/2)
//						ball[i][j][k] = (byte)0x20;
//					if( x[0]< S/5 && x[1] < S/5 && x[2] < S/5)
//						ball[i][j][k] = (byte)0x40;
//					if( i < S/5 && j < S/5 && k < S/5)
//						ball[i][j][k] = (byte)0x40;
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
		panel.clear( 46 );
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
							col
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
