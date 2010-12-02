package voltest;

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
}
