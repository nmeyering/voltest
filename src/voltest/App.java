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
	static int inc;
	
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
				(byte) 0x88
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
				size.height
				);
		
		frame.add( panel );
		frame.setVisible( true );
		
		cam.mvp().printFormatted();
		
		inc = 5;
		while(true)
			draw();
	}
	

	public static void draw()
	{
		if (box.src.z > -25 || box.src.z < -80)
			inc = -inc;
		box.src.z += inc;
		System.out.println(box.src.z);
		
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
