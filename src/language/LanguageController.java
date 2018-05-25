package language;

import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageController {

	public static final String DE = "de_DE";
	public static final String EN = "en_US";
	
	private Locale         currentLocale;
	private ResourceBundle texts;
	
	public LanguageController( String locale ) {
		this.setLanguage( locale );
	}
	
	public LanguageController() {
		this( "en_US" );
	}
	
	public String getText( String key ) {
		return this.texts.getString( key );
	}
	
	public void setLanguage( String locale ) {
		String[] language  = locale.split( "_" );
		this.currentLocale = new Locale( language[0], language[1] );
		this.texts         = ResourceBundle.getBundle( "language/MessageBundle", this.currentLocale );
	}
	
}
