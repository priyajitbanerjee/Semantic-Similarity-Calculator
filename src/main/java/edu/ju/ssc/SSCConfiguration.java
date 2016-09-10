package edu.ju.ssc;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.ju.ssc.Constants;

public final class SSCConfiguration {
	private static final Logger logger = LogManager
			.getLogger(SSCConfiguration.class);

	private static volatile SSCConfiguration instance;
	private Properties properties;
	private String inputDataFile;
	private int linesToSkipFromTop;

	private SSCConfiguration() {

		try (InputStream inputStream = SSCConfiguration.class.getClassLoader()
				.getResourceAsStream(Constants.CONFIG_PROPERTIES_FILE)) {
			properties = new Properties();
			properties.load(inputStream);
			inputDataFile = properties
					.getProperty(Constants.INPUT_DATA_FILE_NAME);
			linesToSkipFromTop = Integer.parseInt(properties
					.getProperty(Constants.LINES_TO_SKIP_FROM_TOP));
		} catch (IOException e) {
			e.printStackTrace();
			logger.debug(e);
		}
	}

	public static SSCConfiguration getInstance() {
		if (instance == null) {
			instance = new SSCConfiguration();
		}
		return instance;
	}

	public Properties getProperties() {
		return properties;
	}

	/**
	 * @return the inputDataFile
	 */
	public String getInputDataFile() {
		return inputDataFile;
	}

	/**
	 * @param inputDataFile
	 *            the inputDataFile to set
	 */
	public void setInputDataFile(String inputDataFile) {
		this.inputDataFile = inputDataFile;
	}

	/**
	 * @return the linesToSkipFromTop
	 */
	public int getLinesToSkipFromTop() {
		return linesToSkipFromTop;
	}

	/**
	 * @param linesToSkipFromTop
	 *            the linesToSkipFromTop to set
	 */
	public void setLinesToSkipFromTop(int linesToSkipFromTop) {
		this.linesToSkipFromTop = linesToSkipFromTop;
	}

}
