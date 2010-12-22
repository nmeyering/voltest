package voltest;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;

import javax.swing.JFrame;

public class App implements KeyListener
{
	private ViewPanel panel;
	private Camera cam;
	private Box box;
	private Dimension size;
	private boolean drawing;
	private Texture3D tex;
	private long lasttime;
	
	private final int[] SET = {0x00};
	
	public App()
	{
		size = new Dimension(
				400,
				300
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
					-5
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
		cam.translate(
				new Vector(0,0,20));
		
		frame.add( panel );
		frame.pack();
		frame.setVisible( true );
		
		
		final int S = 128;
		Random r = new Random();
		byte[][][] ball = new byte[S][S][S];
		for(int i = 0; i < S; ++i )
			for(int j = 0; j < S; ++j )
				for(int k = 0; k < S; ++k )
				{
//						ball[i][j][k] = (byte)0x50;
					int[] x = {Math.abs(i-S/2), Math.abs(j-S/2), Math.abs(k-S/2)};
					int value = (int)Math.sqrt(x[0]*x[0]+x[1]*x[1]+x[2]*x[2]);
					// sphere
					if(value < S/2)
						ball[i][j][k] = (byte)0x20;
					// cube
					if( x[0]< S/5 && x[1] < S/5 && x[2] < S/5)
						ball[i][j][k] = (byte)0x40;
					//cube
					if( i < S/5 && j < S/5 && k < S/5)
						ball[i][j][k] = (byte)0x40;
					x = new int[] {Math.abs(i-S/3), Math.abs(j-S/3), Math.abs(k-S/3)};
					value = (int)Math.sqrt(x[0]*x[0]+x[1]*x[1]+x[2]*x[2]);
					//another sphere
					if (value < S/3)
						ball[i][j][k] = (byte)0x10;
				}
		tex = new Texture3D( ball );
//		Texture3D tex2 = new Texture3D(256);
//		try
//		{
//			System.out.println("trying to read from file");
//			tex2 = tex2.fromFile(new File("mrbrain"), 256);
//			tex = tex2;
//			System.out.println("successfully read from file");
//		}
//		catch( FileNotFoundException e )
//		{
//		}
		
		draw();
		while(true);
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
		panel.clear( 0 );
		Matrix inv = cam.mvp().invert();
		lasttime = System.currentTimeMillis();
		byte col = 0;
		for( int i = 0; i < size.width; ++i )
		{
			for( int j = 0; j < size.height; ++j )
			{
				col = 
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
		System.out.println(
				System.currentTimeMillis() - lasttime);
		panel.repaint();
		drawing = false;
	}
	public void keyPressed( KeyEvent e )
	{
		int code = e.getKeyCode();
		switch( code )
		{
			case KeyEvent.VK_RIGHT:
				cam.rotateAzimuthal(Math.toRadians(5));
				break;
			case KeyEvent.VK_LEFT: 
				cam.rotateAzimuthal(Math.toRadians(-5));
				break;
			case KeyEvent.VK_UP: 
				cam.rotateElevational(Math.toRadians(5));
				break;
			case KeyEvent.VK_DOWN: 
				cam.rotateElevational(Math.toRadians(-5));
				break;
			case KeyEvent.VK_W: 
				cam.translateZ(-1);
				break;
			case KeyEvent.VK_S: 
				cam.translateZ(1);
				break;
			case KeyEvent.VK_A: 
				cam.translateX(1);
				break;
			case KeyEvent.VK_D: 
				cam.translateX(-1);
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
