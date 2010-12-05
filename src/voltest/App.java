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
					-30
					),
					10
				);
		
		cam = new Camera(
				size.width,
				size.height
				);
		
		frame.add( panel );
		frame.setVisible( true );
		
//		Vector v = new Vector(160,120,0);
//		System.out.println(box);
//		System.out.println(	box.intersects(
//				new Ray(
//						cam.pos(),
//						MathUtil.unproject(
//								v,
//								cam)
//						),
//				-10000,
//				10000)
//			);
//		System.out.println("Intersections: ");
//		for ( Vector i : Ray.intersections( v, box, cam))
//		{
//			System.out.print( i + ": " + box.contains(i) + "\n" );
//		}
		inc = 5;
//		cam.printGizmo();
//		cam.rotateY( Math.toRadians( 45 ) );
//		draw();
		while (true){
			draw();
			cam.translate( new Vector(-.01,0,0));
			//cam.rotateX( Math.toRadians(15) );
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
		Matrix inv = cam.mvp().invert();
		
		long tmp = System.currentTimeMillis();
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
							inv,
							cam.view())
						),
					-10000,
					10000)
					)
				{
//					int len = 
//						Ray.intersections(
//								new Vector(i,j,0),
//								box,
//								cam);
					panel.setPixel(
						i,
						j,
//						new int[] {len*127}
						SET
						);
					
				}
			}
		}		
		System.out.println(
				System.currentTimeMillis() - tmp);
		panel.repaint();
	}
}
