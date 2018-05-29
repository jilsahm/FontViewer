package system;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.event.MouseInputListener;

import gui.ToggleButton;

public class InstalledFont {

	public static Font icons = InstalledFont.loadFont( "/system/fa-solid-900.ttf", Font.PLAIN, 18f );
	
	private static final String    PREVIEW         = "abC 123!";
	private static final Dimension FONTNAMESIZE    = new Dimension( 200, 30 );
	private static final Dimension FONTPREVIEWSIZE = new Dimension( 100, 30 );
	
	private Font         font;
	private JLabel       fontname;
	private JLabel       fontpreview;
	private ToggleButton toggleLoadFont;
	
	public InstalledFont( String fontname, MouseInputListener listener ) {
		this.font = new Font( fontname, Font.PLAIN, 12 );
		
		this.fontname = new JLabel( fontname );
		this.fontname.setMinimumSize( InstalledFont.FONTNAMESIZE );
		this.fontname.setPreferredSize( InstalledFont.FONTNAMESIZE );
		this.fontname.setMaximumSize( InstalledFont.FONTNAMESIZE );
		
		this.fontpreview = new JLabel( InstalledFont.PREVIEW );
		this.fontpreview.setMinimumSize( InstalledFont.FONTPREVIEWSIZE );
		this.fontpreview.setPreferredSize( InstalledFont.FONTPREVIEWSIZE );
		this.fontpreview.setMaximumSize( InstalledFont.FONTPREVIEWSIZE );
		this.fontpreview.setFont( this.font );
		
		this.toggleLoadFont = new ToggleButton( "\uf06e" );
		this.toggleLoadFont.addMouseListener( listener );
		this.toggleLoadFont.setForeground( Color.GRAY );
	}
	
	public Box buildComponents() {
		Box hbox      = new Box( BoxLayout.X_AXIS );
		hbox.add( this.fontname );
		hbox.add( this.fontpreview );
		hbox.add( this.toggleLoadFont );
		return hbox;
	}
	
	public boolean isTriggered( MouseEvent e ) {
		return this.toggleLoadFont.isTriggered( e );
	}
	
	public Font getFont() {
		return this.font;
	}
	
	public ToggleButton getToggleButton(){
		return this.toggleLoadFont;
	}
	
	public static Font loadFont( String path, int style, float size ) {
		try ( InputStream is = InstalledFont.class.getResourceAsStream( path ) ) {
            Font font = Font.createFont( Font.TRUETYPE_FONT, is );
            font = font.deriveFont( style, size );
            return font;
    	} catch ( IOException | FontFormatException e ){
        	e.printStackTrace();
        }
		return null;
	}	
	
	public static Font loadAbsoluteFont( String path, int style, float size ) {
		try ( InputStream is = new FileInputStream( path ) ) {
            Font font = Font.createFont( Font.TRUETYPE_FONT, is );
            font = font.deriveFont( style, size );
            return font;
    	} catch ( IOException | FontFormatException e ){
        	e.printStackTrace();
        }
		return null;
	}	
}
