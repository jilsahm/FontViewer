package gui;

import java.awt.Color;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class ToggleButton extends JButton{

	private static final Color ACTIVE   = new Color( 66, 176, 0 );
	private static final Color INACTIVE = Color.GRAY;
	
	private boolean toggled;
	
	public ToggleButton( String text ){
		super( text );
		this.toggled = false;
		this.setForeground( INACTIVE );
	}
	
	public boolean isTriggered( MouseEvent e ) {
		return ( e.getSource() == this ) ? true : false;
	}
	
	public void activate(){
		this.setForeground( ACTIVE );
	}
	
	public void deactivate(){
		this.setForeground( INACTIVE );
	}
	
	public void toggle(){
		if ( this.toggled ){
			this.setForeground( INACTIVE );
		} else {
			this.setForeground( ACTIVE );
		}
		this.toggled = !this.toggled;
	}
	
}
