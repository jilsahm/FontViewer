package gui;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import language.LanguageController;
import system.InstalledFont;

@SuppressWarnings( "serial" )
public final class MainFrame extends JFrame{
	
	private static final MainFrame instance = new MainFrame();

    public static final int FRAME_WIDTH  = 1024;
    public static final int FRAME_HEIGHT = 768;
    
    private LanguageController lc;
    
   //private final Font FONT = new Font( "Serif", Font.PLAIN, 21 );
   
    public MainFrame() {
        super( "FontViewer" );
        this.lc = new LanguageController();
        
        try {
            for ( LookAndFeelInfo info : UIManager.getInstalledLookAndFeels() ) {
                if ( "Nimbus".equals( info.getName() ) ) {
                    UIManager.setLookAndFeel( info.getClassName() );
                    break;
                }
            }
        } catch ( Exception e ) {
            System.out.println( "Error 01, no nimbus found on system." );
            System.exit( 1 );
        }
       
        this.configureLook();
        this.setSize( new Dimension( FRAME_WIDTH, FRAME_HEIGHT ) );
        this.setMinimumSize( new Dimension( FRAME_HEIGHT, FRAME_HEIGHT / 2 ) );
        this.setLocationRelativeTo( null );
        this.add( new FontPreviewPanel( this.lc ) );
        this.setDefaultCloseOperation( EXIT_ON_CLOSE );
    }	
    
    private void configureLook() {
        UIManager.put( "Button.font", InstalledFont.icons );
        UIManager.put( "ToolTip.background", Color.RED );
    }
    
    public void display() {
    	this.setVisible( true );
    }
    
    public static void loadPanel( JPanel panel ) {       
        MainFrame.instance.getContentPane().removeAll();       
        MainFrame.instance.getContentPane().add( panel );
        MainFrame.instance.getContentPane().validate();
    }
   
    public static synchronized MainFrame getInstance() {
        return MainFrame.instance;
    }
   
    public static void main( String[] args ) {
        MainFrame.getInstance().display();
	}
}
