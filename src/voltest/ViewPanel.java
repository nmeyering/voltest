package voltest;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

public class ViewPanel extends JPanel
{
	private WritableRaster data;
	
	public void paint( Graphics g )
	{
		g.drawImage(
				new BufferedImage(
						,
						data,
						false,
						null
						),
				0,
				0,
				null);
	}
	
	public void setPixel( int x, int y )
	{
		data.setPixel(
				x,
				y,
				new int[1]
				        );
	}
}
