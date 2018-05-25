package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolTip;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.MouseInputListener;

import language.LanguageController;
import system.InstalledFont;

@SuppressWarnings( "serial" )
public class FontPreviewPanel extends JPanel implements MouseInputListener, MouseWheelListener{
	
	private LanguageController lc;	
	
	private JLabel  labelInstalledFonts           = new JLabel();
	private JLabel  labelFontView                 = new JLabel();
	private JButton buttonChangeLanguageToGerman  = new JButton();
	private JButton buttonChangeLanguageToEnglish = new JButton();
	private JButton toggleBold                    = new JButton( "\uf032" );
	private JButton toggleItalic                  = new JButton( "\uf033" );
	private JTextArea previewTextArea             = new JTextArea();
	
	private JScrollPane scollPaneInstalledFonts;
	private Box boxInstalledFonts;
	private ArrayList<InstalledFont> installedFonts = new ArrayList<>();
	
	public FontPreviewPanel( LanguageController lc ) {		
		this.lc = lc;
		this.setLayout( new GridBagLayout() );
		
		this.buildComponents();
		this.setAllTexts();
		this.registerEventListener();
	}
	
	private void registerEventListener() {
		this.buttonChangeLanguageToEnglish.addMouseListener( this );
		this.buttonChangeLanguageToGerman.addMouseListener( this );
		this.toggleBold.addMouseListener( this );
		this.toggleItalic.addMouseListener( this );
		this.previewTextArea.addMouseWheelListener( this );
	}
	
	private void buildComponents() {
		GridBagConstraints c = new GridBagConstraints();
		
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets( 10, 0, 10, 0 );
		this.add( this.labelInstalledFonts, c );
		
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 2;
		c.insets = new Insets( 10, 0, 10, 0 );
		this.add( this.labelFontView, c );
		
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 2;
		c.weightx = 200;
		c.weighty = 100;
		this.previewTextArea.setLineWrap( true );
		this.add( this.previewTextArea, c );
		
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.EAST;
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 200;
		this.add( this.toggleItalic, c );
		
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.EAST;
		c.gridx = 2;
		c.gridy = 1;
		this.add( this.toggleBold, c );
		
		this.fillInstalledFontsBox();
	}
	
	private void fillInstalledFontsBox() {
		String fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();		
		this.boxInstalledFonts = new Box( BoxLayout.Y_AXIS );
		this.scollPaneInstalledFonts = new JScrollPane( this.boxInstalledFonts );
		this.scollPaneInstalledFonts.getViewport().setMinimumSize( new Dimension( 370, 300 ) );
		this.scollPaneInstalledFonts.getViewport().setMaximumSize( new Dimension( 370, 300 ) );
		this.scollPaneInstalledFonts.createVerticalScrollBar();
		this.scollPaneInstalledFonts.getVerticalScrollBar().setUnitIncrement( 25 );
		this.scollPaneInstalledFonts.setVerticalScrollBarPolicy( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
		
		for ( String font : fonts ) {
			InstalledFont current = new InstalledFont( font, this );
			this.boxInstalledFonts.add( current.buildComponents() );
			this.installedFonts.add( current );
		}
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 1;
		c.gridheight = 2;
		c.weightx = 1;
		c.weighty = 100;
		this.add( this.scollPaneInstalledFonts, c );
	}
	
	private void setAllTexts() {
		this.labelInstalledFonts.setText( lc.getText( "label_installedFonts" ) );
		this.labelFontView.setText( lc.getText( "label_fontView" ) );
		this.previewTextArea.setText( this.lc.getText( "example_text" ) );
		this.buttonChangeLanguageToEnglish.setText( lc.getText( "button_english" ) );
		this.buttonChangeLanguageToGerman.setText( lc.getText( "button_german" ) );
		this.toggleBold.setToolTipText( lc.getText( "bold" ) );
		this.toggleItalic.setToolTipText( lc.getText( "italic" ) );
	}	
	
	@Override
	public void mouseClicked( MouseEvent e ) {
		if ( e.getSource() == this.buttonChangeLanguageToEnglish ) {
			this.lc.setLanguage( LanguageController.EN );
			this.setAllTexts();
		} else if ( e.getSource() == this.buttonChangeLanguageToGerman ) {
			this.lc.setLanguage( LanguageController.DE );
			this.setAllTexts();
		} else if ( e.getSource() == this.toggleBold ){
			int style = this.previewTextArea.getFont().getStyle() ^ Font.BOLD;
			this.previewTextArea.setFont( this.previewTextArea.getFont().deriveFont( style ) );
		} else if ( e.getSource() == this.toggleItalic ){
			int style = this.previewTextArea.getFont().getStyle() ^ Font.ITALIC;
			this.previewTextArea.setFont( this.previewTextArea.getFont().deriveFont( style ) );
		} else {
			for ( InstalledFont installedFont : this.installedFonts ) {
				if ( installedFont.isTriggered( e ) ) {
					this.previewTextArea.setFont( installedFont.getFont().deriveFont( this.previewTextArea.getFont().getSize2D() ) );
					return;
				}
			}
		}
	}

	@Override
	public void mouseEntered( MouseEvent e ) {}

	@Override
	public void mouseExited( MouseEvent e ) {}

	@Override
	public void mousePressed( MouseEvent e ) {}

	@Override
	public void mouseReleased( MouseEvent e ) {}

	@Override
	public void mouseDragged( MouseEvent e ) {}

	@Override
	public void mouseMoved( MouseEvent e ) {}

	@Override
	public void mouseWheelMoved( MouseWheelEvent e ) {
		if ( e.isControlDown() && e.getSource() == this.previewTextArea ) {
			if ( 0 > e.getWheelRotation() ) {
				this.previewTextArea.setFont( this.previewTextArea.getFont().deriveFont( this.previewTextArea.getFont().getSize2D() + 1 ) );
			} else if ( 0 < e.getWheelRotation() ) {
				this.previewTextArea.setFont( this.previewTextArea.getFont().deriveFont( this.previewTextArea.getFont().getSize2D() - 1 ) );
			}
		}
	}

}
