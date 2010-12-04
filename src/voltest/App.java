package voltest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

import javax.swing.JFrame;

public class App
{
	static ViewPanel panel;
	static Context context;
	static Box box;
	static byte[][] CLEAR_DATA = new byte[320][240];
	
	public static void main( String[] args )
	{
		Dimension size = new Dimension(
				320,
				240
				);
		
		JFrame frame = new JFrame();
		panel = new ViewPanel();
		panel.setSize( size );
		
		box = new Box(
				new Vector(
						-5,
						-5,
						-50
						),
						10
						);
		context = new Context(
				new Rectangle(
						0,
						0,
						size.width,
						size.height
						),
				Context.perspectiveMatrix(
						Math.toRadians( 60 ),
						(double) size.width / size.height,
						1,
						100)
				);
		
		frame.add( panel );
		frame.setVisible( true );
		draw();
	}
	

	public static void draw()
	{
		if(box.src.z < 0)
			box.src.z++;
		panel.data = new DataBufferByte(
				CLEAR_DATA,
				320*240
				);
		for( int i = 0; i < 240; ++i )
		{
			for( int j = 0; j < 320; ++j )
			{
				if(	Ray.intersect(
								new Vector(
										i,
										j,
										0
								),
								box,
								context
						)
					)
					panel.setPixel(i, j);
			}
		}		
}
}
