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
				0x88
				);
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
		
		cam = new Camera(
				size.width,
				size.height
				);
		
		frame.add( panel );
		frame.setVisible( true );
		
		inc = 5;
		draw();
		cam.printGizmo();
		cam.rotateY( Math.toRadians( 15 ) );
		cam.printGizmo();
//		draw();
		cam.rotateX( Math.toRadians( 15 ));
		cam.printGizmo();
//		System.out.println(MathUtil.rotationMatrix(new Vector(1,0,0), Math.toRadians(45)));
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
