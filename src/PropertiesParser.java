import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.JOptionPane;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

public class PropertiesParser {
	private static final String JDBC = "jdbc";

	private static final String CONFIG_PROPERTIES = "config.properties";

	private String propertiesFileName;

	private Properties m_configuration;

	public PropertiesParser(String propertiesFileName) throws IOException {
		this.propertiesFileName = propertiesFileName;
		getConfig();
	}

	private void getConfig() throws IOException {
		InputStream is = getClass().getClassLoader().getResourceAsStream(
				propertiesFileName);
		if (is == null) {
			throw new FileNotFoundException("Cannot find " + propertiesFileName
					+ " file in classpath");
		}

		Properties p = new Properties();
		p.load(is);

		synchronized (this) {
			m_configuration = p;
		}
	}

	public String getInfoFromConfiguration(String key) throws IOException {
		//TODO getConfig();
		synchronized (this) {
			return m_configuration.getProperty(key);
		}
	}

	private static void oldMethod() throws IOException {
		PropertiesParser paser = new PropertiesParser(CONFIG_PROPERTIES);
		JOptionPane.showInputDialog(paser.getInfoFromConfiguration(JDBC));
		//TODO paser = new PropertiesParser(CONFIG_PROPERTIES);
		JOptionPane.showInputDialog(paser.getInfoFromConfiguration(JDBC));
	}
	
	@SuppressWarnings("unused")
	private static void newMethod() throws ConfigurationException {
		PropertiesConfiguration config = new PropertiesConfiguration(CONFIG_PROPERTIES);
		//TODO Automatic Reloading : A common issue with properties file is to handle the reloading of the file when it changes. 
		config.setReloadingStrategy(new FileChangedReloadingStrategy());
		JOptionPane.showInputDialog(config.getProperty(JDBC));
		JOptionPane.showInputDialog(config.getProperty(JDBC));
	}

	public static void main(String[] args) throws ConfigurationException, IOException  {
		newMethod();
	}
}
