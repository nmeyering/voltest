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
	static Dimension size;
	static DataBuffer data;
	
	private static final int[] SET = {0x00};
	
	public static void main( String[] args )
	{
		size = new Dimension(
				320,
				240
				);
		clearData = new byte[ size.width * size.height ];
		Arrays.fill(
				clearData,
				(byte) 0xFF
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
					-50
					),
					10
				);
		
		System.out.println( box );
		cam = new Camera(
				size.width,
				size.height,
				Math.toRadians( 60 ),
				(double)size.width/size.height,
				1,
				100
				);
		
		frame.add( panel );
		frame.setVisible( true );
		
		System.out.println(
				Ray.intersection(new Vector(0,0,0), box, cam)
				);
		System.out.println(
				Util.unproject(new Vector(0,0,0), cam)
				);
		System.out.println(
				Ray.intersects(new Vector(0,0,0), box, cam)
				);
		
		while(true)
			draw();
	}
	

	public static void draw()
	{
//		box.src.z += 5;
		data = new DataBufferByte(
			clearData,
			size.width
			* size.height
			);
		
		for( int i = 0; i < size.width; ++i )
		{
			for( int j = 0; j < size.height; ++j )
			{
				if(	Ray.intersects(
								new Vector(
										i,
										j,
										0
								),
								box,
								cam
						)
					)
//					System.out.printf("setting pixel (%d,%d)\n", i, j);
					panel.setPixel(
							i,
							j,
							SET
							);
			}
		}		
		panel.repaint();
	}
}
