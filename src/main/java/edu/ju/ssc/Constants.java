package edu.ju.ssc;

public class Constants {
	/**
	 * Author info
	 */
	public static final String AUTHOR_INFO = "\nDeveloped by - "
			+ "\nPriyajit Banerjee"
			+ "\nMaster of Computer Application(M.C.A.) [2012-2015]"
			+ "\nJadavpur University";
	/**
	 * The Application title
	 */
	public static final String APP_TITLE = "Semantic Similarity Calculator";
	/**
	 * The Application's version
	 */
	public static final String APP_VERSION = "1.0.0";
	/**
	 * The Spring Context Configuration XML
	 */
	public static final String CONTEXT_CONFIG_FILE = "/ssc-sping-module.xml";
	/**
	 * The stop words list file
	 */
	public static final String STOP_WORDS_LIST_FILE = "StopWordsListFile";
	/**
	 * The Open NLP models directory
	 */
	public static final String OPEN_NLP_MODELS_RESOURCE_DIRECTORY = "OpenNlpModels";
	/**
	 * The Open NLP Token Model
	 */
	public static final String OPEN_NLP_TOKEN_MODEL = "en-token.bin";
	/**
	 * Debug message string for exception
	 */
	public static final String DEBUG_EXCEPTION_MSG = "An Exception Occurred!";
	/**
	 * The character encoding used in all files
	 */
	public static final String CHARACTER_ENCODING = "UTF-8";
	/**
	 * The date time pattern in output directory name
	 */
	public static final String DATE_TIME_PATTERN = "dd.MM.yyyy_HH.mm.ss";
	/**
	 * The output file extension
	 */
	public static final String OUTPUT_FILE_EXTENSION = "txt";
	/**
	 * The output file column header pattern
	 */
	public static final String OUT_FILE_COL_HEAD_PATTERN = "%-35s%-35s%-35s%-35s%-35s%-35s%-35s%-35s%s%n";
	/**
	 * The first output file output pattern
	 */
	public static final String OUTPUT_FILE_PATTERN = "%-35s%-35s%-35.12g%-35.12g%-35.12g%-35.12g%-35d%-35d%d%n";
	/**
	 * The input file name
	 */
	public static final String INPUT_DATA_FILE_NAME = "InputDataFile";
	/**
	 * The output file name
	 */
	public static final String OUTPUT_DATA_FILE_NAME = "OutputDataFile";
	/**
	 * The configuration file
	 */
	public static final String CONFIG_PROPERTIES_FILE = "config.properties";
	/**
	 * The no. of lines to skip from top while reading the input file
	 */
	public static final String LINES_TO_SKIP_FROM_TOP = "NumberOfLinesToSkipFromTop";
	/**
	 * The output file column header content
	 */
	public static final String OUT_FILE_COL_NAMES[] = { "First-Sentence-Id",
			"Second-Sentence-Id", "Simple-Relatedness-Feature",
			"Lesk-Relatedness-Feature", "Lin-Relatedness-Feature",
			"Path-Relatedness-Feature", "Matched-Word-Count",
			"Mismatched-Word-Count", "Minimum-Word-Count" };
	/**
	 * The String Dash
	 */
	public static final String DASH_STR = "-";
	/**
	 * The Tab character
	 */
	public static final char TAB_CHAR = '\t';
	/**
	 * The format String
	 */
	public static final String FORMAT_STR = "%s%n";
	/**
	 * The Relatedness Calculator list
	 */
	public static final String[] RELATEDNESS_CALCULATORS = {
			"Simple-Relatedness", "Lesk-Relatedness", "Lin-Relatedness",
			"Path-Relatedness" };

}
