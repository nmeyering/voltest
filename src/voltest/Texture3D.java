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
	private int[] data;
	private int dim;
	private static final int depth = 3;

	public Texture3D( int dim ){
		this.dim = dim * dim * dim * depth;
		this.data = new int[ this.dim ];
	}
	
	public Texture3D( int[] in )
	{
		this.dim = (int)Math.pow( (in.length / depth + 1), 1.0/3 );
		if( this.dim < 1 || this.dim > 512 )
			throw new IllegalArgumentException( "texture too small or too big ("
					+ dim
					+ ")."
					);
		this.data = in;
	}
	
	public Texture3D( Texture3D copy )
	{
		this( copy.data );
	}
	
	public Texture3D(int[] in, int dim) {
		this.dim = (int)Math.pow( (in.length / depth + 1), 1.0/3 );
		if( this.dim < 1 || this.dim > 512 )
			throw new IllegalArgumentException( "texture too small or too big ("
					+ dim
					+ ")."
					);
		this.dim = dim;
		
		this.data = new int[ dim * dim * dim * depth ];
		int x = 0, y = 0, z = 0;
		this.data = Arrays.copyOf(in, dim);
	}
	public Texture3D fromFile(File file) throws FileNotFoundException
	{
		int size = (int)file.length();
		int[] buffer = new int[size];
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
		
		return new Texture3D( buffer );
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
