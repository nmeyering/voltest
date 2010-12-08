package voltest;

import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.Raster;
import java.awt.image.SampleModel;
import java.awt.image.SinglePixelPackedSampleModel;
import java.awt.image.WritableRaster;	
import java.util.Arrays;

public class ViewPanel extends JPanel
{
	
	private int[] raster;
	private BufferedImage img;
	private int width, height;
	
	public ViewPanel( Dimension size )
	{
		super();
		this.setSize( size );
		this.width = size.width;
		this.height = size.height;
		this.img = new BufferedImage(
				size.width,
				size.height,
				BufferedImage.TYPE_INT_RGB
				);
		this.raster = new int[size.width*size.height*3];
		this.setPreferredSize(size);
	}
//	public synchronized void swapBuffers()
//	{
//		img.getRaster().setPixels(0, 0, getWidth(), getHeight(), raster);
//		this.repaint();
//	}
	
	public void clear( int col )
	{
		Arrays.fill( raster, col );
	}
	
	public void paint( Graphics g )
	{
		img.getRaster().setPixels(0, 0, width, height, raster);
		g.drawImage(
				img,
				0,
				0,
				this);
	}
	
	public void setPixel( int x, int y, int value )
	{
		if( x > 0 && x < width && y > 0 && y < height)
		{
			int index = 3 * (width * y + x);
			raster[index++] = value;
			raster[index++] = value;
			raster[index] = value;
		}
	}	
}
