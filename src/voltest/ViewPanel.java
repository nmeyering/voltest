package voltest;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentSampleModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.awt.image.Raster;
import java.awt.image.SampleModel;
import java.awt.image.SinglePixelPackedSampleModel;
import java.awt.image.WritableRaster;

public class ViewPanel extends JPanel
{
	SampleModel sm = new SinglePixelPackedSampleModel(
			DataBuffer.TYPE_BYTE,
			320,
			240,
			new int[] {0xFF}
			);
	DataBuffer data =
		new DataBufferByte(
				320*240
				);
	WritableRaster raster =
		Raster.createWritableRaster(
				sm,
				data,
				null
				);
	private int[] SET = {0xFF};
	
	public void paint( Graphics g )
	{
		BufferedImage img = new BufferedImage(
				320,
				240,
				BufferedImage.TYPE_BYTE_GRAY
				);
		App.draw();
		img.setData( raster );
		g.drawImage(
				img,
				0,
				0,
				null);
	}
	
	public void setPixel( int x, int y )
	{
		raster.setPixel(
				x,
				y,
				SET
				);
	}	
}
