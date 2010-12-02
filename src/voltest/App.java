package voltest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JFrame;

public class App
{
	public static void main( String[] args )
	{
		JFrame frame = new JFrame();
		ViewPanel panel = new ViewPanel();
		panel.setSize( 320, 240 );
		
		frame.add( panel );
		frame.setVisible( true );
	}
	
	public static void draw()
	{
		Graphics g = new Graphics2D();
		Dimension size = getSize();
		Box box = new Box(
				new Vector(
						-5,
						-5,
						-50
						),
						10
						);
		Context context = new Context(
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
			g2d.setColor( Color.BLUE );
			g2d.drawLine(0, 0, 320, 240);
			g2d.drawLine(320, 0, 0, 240);
			g2d.setColor( Color.WHITE );
			g2d.setXORMode( Color.BLACK );
		
			for( int i = 0; i < size.height; ++i )
			{
				System.out.println("Rendering line " + i );
				for( int j = 0; j < size.width; ++j )
				{
					if(	Ray.intersect(
									new Vector(
											j,
											i,
											0
									),
									box,
									context
							)
						)
						setPixel(i, j);
				}
			}		
	}
}
