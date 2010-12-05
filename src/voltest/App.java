package voltest;

import java.awt.Dimension;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.util.Arrays;

import javax.swing.JFrame;

public class App
{
	static ViewPanel panel;
	static Camera cam;
	static Box box;
	static byte[] clearData;
	static int[] clearDataInt;
	static Dimension size;
	static DataBuffer data;
	static int inc;
	
	private static final int[] SET = {0x00};
	
	public static void main( String[] args )
	{
		size = new Dimension(
				320,
				240
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
		
		box = new Box(
			new Vector(
					-5,
					-5,
					-20
					),
					10
				);
		
		cam = new Camera(
				size.width,
				size.height
				);
		
		cam.translate(new Vector(2,-1,3));
		
		frame.add( panel );
		frame.setVisible( true );
		
		System.out.println(box);
//		System.out.println(	box.intersects(
//				new Ray(
//						cam.pos(),
//						MathUtil.unproject(
//								new Vector(200,200,0),
//								cam)
//						),
//				-10000,
//				10000)
//			);
//		System.out.println("Intersections: ");
//		for ( Vector i : Ray.intersections(new Vector(200,200,0), box, cam))
//			System.out.println( i );
		inc = 5;
//		cam.printGizmo();
//		cam.rotateY( Math.toRadians( 45 ) );
//		draw();
		while (true){
			draw();
			try{
				Thread.sleep(1000);
			}
			catch(InterruptedException e)
			{
				
			}
			cam.translate( new Vector(-1,1.5,-0.5));
			cam.rotateX( Math.toRadians(15) );
		}
	}
	

	public static void draw()
	{
//		if (box.src.z > -15 || box.src.z < -50)
//			inc = -inc;
//		box.src.z += inc;
		
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
		
		for( int i = 0; i < size.width; ++i )
		{
			for( int j = 0; j < size.height; ++j )
			{
				if(	
					box.intersects(
					new Ray(
						cam.pos(),
							MathUtil.unproject(
							new Vector(i,j,0),
							cam)
						),
					-10000,
					10000)
					)
				{
					int len = 
						Ray.intersections(
								new Vector(i,j,0),
								box,
								cam);
					panel.setPixel(
						i,
						j,
						new int[] {len*127}
//						SET
						);
					
				}
			}
		}		
		panel.repaint();
	}
}
