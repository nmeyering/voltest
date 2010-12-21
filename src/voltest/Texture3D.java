package voltest;

import static java.lang.Math.*;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class Texture3D
{
	private byte[][][] data;
	private int dim;

	public Texture3D( int dim ){
		this.dim = dim;
		this.data = new byte[dim][dim][dim];
	}
	
	public Texture3D( byte[][][] in )
	{
		this.data = in;
		this.dim = in.length;
	}
	
	public Texture3D( Texture3D copy )
	{
		this( copy.data );
	}
	public Texture3D(byte[] in, int dim) {
		if (in.length > dim * dim * dim)
			throw new IllegalArgumentException(
					"not enough space!");
		
		this.dim = dim;
		
		this.data = new byte[dim][dim][dim];
		int x = 0, y = 0, z = 0;
		for(int i = 0; i < in.length; ++i)
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
	public Texture3D fromFile(File file, int dim) throws FileNotFoundException
	{
		byte[] buffer = new byte[dim*dim*dim];
		BufferedInputStream filein;
		try
		{
			filein = new BufferedInputStream( new FileInputStream( file ));
			int b1, b2, z = 0, len = (int)file.length();
			for(int i = 0; i < len; ++i)
			{
				b1 = filein.read();
				b2 = filein.read();
				int result = (b1 << 8) | b2;
				buffer[z++] = (byte)(((double)result)/Short.MAX_VALUE * 255 );
				if(i%100000==0)
					System.out.println("read " + (float)100*i/len  + " percent");
			}
		}
		catch( IOException e )
		{
			e.printStackTrace();
		}
		
		return new Texture3D(buffer, dim);
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
						[ (int)((dim - 1) * u)]
						[ (int)((dim - 1) * v)]
						[ (int)((dim - 1) * w)];
	}
	public void setData( byte[][][] in )
	{
		this.data = in;
	}
	public int size()
	{
		return dim;
	}
}
