package voltest;

import static java.lang.Math.*;

public class Texture3D
{
	private byte data[][][];
	private int size;

	public Texture3D( int size ){
		this.size = size;
		data = new byte[ size ][ size ][ size ];
	}
	public Texture3D( byte[][][] in )
	{
		this.size = in.length;
		this.data = in;
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
