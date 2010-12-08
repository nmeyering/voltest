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

public class ViewPanel extends JPanel
{
	
	SampleModel sm;
	WritableRaster raster;
	
	public ViewPanel( Dimension size, DataBuffer data )
	{
		super();
		this.setSize( size );
		sm = new SinglePixelPackedSampleModel(
			DataBuffer.TYPE_BYTE,
			this.getWidth(),
			this.getHeight(),
			new int[] {0xFF}
			);
		raster = Raster.createWritableRaster(
			sm,
			data,
			null
			);
	}
	
	public void paint( Graphics g )
	{
		BufferedImage img = new BufferedImage(
				getWidth(),
				getHeight(),
				BufferedImage.TYPE_BYTE_GRAY
				);
		img.setData( raster );
		g.drawImage(
				img,
				0,
				0,
				null);
	}
	
	public void setPixel( int x, int y, int[] value )
	{
		raster.setPixel(
				x,
				y,
				value
				);
	}	
	
}
