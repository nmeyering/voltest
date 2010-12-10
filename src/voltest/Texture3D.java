package voltest;

import static java.lang.Math.*;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Texture3D
{
	private byte data[][][];
	private int size;

	public Texture3D( int size ){
		this.size = size;
		this.data = new byte[ size ][ size ][ size ];
	}
	public Texture3D( byte[][][] in )
	{
		this.size = in.length;
		this.data = in;
	}
	public Texture3D( Texture3D copy )
	{
		this( copy.data );
	}
	public Texture3D(byte[] in, int dim) {
		
		int len = (int)Math.sqrt(dim*dim*dim);
		if(len < in.length)
			throw new IllegalArgumentException("Not enough space for given data.");
		
		this.size = dim;
		
		this.data = new byte[ dim ][ dim ][ dim ]; 
		int x = 0, y = 0, z = 0;
		for(int i = 0; i < len; ++i)
		{
			if(z>=dim)
			{
				z = 0;
				y++;
			}
			if(y>=dim)
			{
				y = 0;
				x++;
			}
			this.data[x][y][z] = in[i];
			z++;
		}
	}
	public Texture3D fromFile(File file) throws FileNotFoundException
	{
		int size = (int)file.length();
		byte[] buffer = new byte[size];
		BufferedInputStream filein;
		try
		{
			filein = new BufferedInputStream( new FileInputStream( file ));
			filein.read(buffer);
		}
		catch( IOException e )
		{
			e.printStackTrace();
		}
		
		return new Texture3D(buffer,(int)Math.pow(size, 2.0/3.0) + 1);
	}
	public void writeFile(File file) throws FileNotFoundException
	{
		BufferedOutputStream fileout;
		try
		{
			fileout = new BufferedOutputStream(
					new FileOutputStream(
							file
						)
					);
			fileout.write( flatten( this.data ) );
			fileout.close();
		}
		catch( IOException e )
		{
			e.printStackTrace();
		}
	}
	private byte[] flatten( byte[][][] in )
	{
		int len0 = in.length,
			len1 = in[0].length,
			len2 = in[1].length;
		
		byte[] ret = new byte[ len0 * len1 * len2 ];
		for(int i = 0; i < len0; ++i)
			for(int j = 0; j < len1; ++j)
				for(int k = 0; k < len2; ++k)
					ret[i * len1 * len2 + j * len2 + k] = in[i][j][k];
		return ret;
	}
	public byte sample(
			double u,
			double v,
			double w
			)
	{
		u = max(0, min(1, u));
		v = max(0, min(1, v));
		w = max(0, min(1, w));
		return data
						[ (int)((size - 1) * u)]
						[ (int)((size - 1) * v)]
						[ (int)((size - 1) * w)];
	}
	public void setData( byte[][][] in )
	{
		this.data = in;
	}
	public int size()
	{
		return size;
	}
}
