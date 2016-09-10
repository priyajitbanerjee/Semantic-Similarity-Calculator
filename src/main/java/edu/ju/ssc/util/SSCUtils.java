/**
 * 
 */
package edu.ju.ssc.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.apache.commons.math3.stat.StatUtils;

import edu.cmu.lti.lexical_db.NictWordNet;
import edu.cmu.lti.ws4j.RelatednessCalculator;
import edu.cmu.lti.ws4j.util.PorterStemmer;
import edu.cmu.lti.ws4j.util.StopWordRemover;
import edu.ju.ssc.Constants;
import edu.ju.ssc.SSCConfiguration;
import edu.ju.ssc.Sentence;
import edu.ju.ssc.exception.SSCException;

/**
 * @author Priyajit Banerjee
 *
 */
public class SSCUtils {

	/**
	 * Returns the array of tokens in a given string
	 * 
	 * @param string
	 * @return the array of token in a given string
	 * @throws SSCException
	 */
	public static String[] getAllTokens(final String string)
			throws SSCException {
		try (final InputStream modelIn = SSCUtils.class.getClassLoader()
				.getResourceAsStream(
						Constants.OPEN_NLP_MODELS_RESOURCE_DIRECTORY + "/"
								+ Constants.OPEN_NLP_TOKEN_MODEL)) {
			TokenizerModel model = new TokenizerModel(modelIn);
			Tokenizer tokenizer = new TokenizerME(model);
			return tokenizer.tokenize(string);
		} catch (IOException e) {
			throw new SSCException(e.getMessage(), e);
		}
	}

	/**
	 * 
	 * @return
	 * @throws SSCException
	 */
	public static List<Sentence> getAllLinesAsSentenceList(String fileName,
			int linesToSkipFromTop) throws SSCException {
		try {
			List<String> lines = FileUtils.readLines(
					FileUtils.getFile(fileName), Constants.CHARACTER_ENCODING);
			List<Sentence> sentences = new ArrayList<>();

			for (String line : lines) {
				if (linesToSkipFromTop > 0) {
					linesToSkipFromTop--;
					continue;
				}
				line = StringUtils.trimToEmpty(line);
				int firstOccurrenceOfWhiteSpace = StringUtils.indexOf(line,
						StringUtils.SPACE);
				if (firstOccurrenceOfWhiteSpace == StringUtils.INDEX_NOT_FOUND) {
					firstOccurrenceOfWhiteSpace = StringUtils.indexOf(line,
							Constants.TAB_CHAR);
					if (firstOccurrenceOfWhiteSpace == StringUtils.INDEX_NOT_FOUND) {
						continue;
					}
				}
				Sentence sentence = new Sentence();
				String sentenceId = StringUtils.substring(line, 0,
						firstOccurrenceOfWhiteSpace);
				String content = StringUtils.trimToEmpty(StringUtils.substring(
						line, firstOccurrenceOfWhiteSpace + 1));
				sentence.setId(sentenceId);
				sentence.setContent(content);
				sentences.add(sentence);
			}

			return sentences;
		} catch (IOException e) {
			throw new SSCException(e.getMessage(), e);
		}
	}

	/**
	 * Returns the array of tokens after removing all non-word characters in a
	 * given string
	 * 
	 * @param string
	 * @return the array of tokens after removing all non-word characters in a
	 *         given string
	 * @throws SSCException
	 */
	public static String[] getAllTokensWithoutPunctuation(final String string)
			throws SSCException {
		return SSCUtils.getAllTokens(StringUtils.replacePattern(string, "\\W",
				" "));
	}

	/**
	 * Returns the array of stemmed tokens
	 * 
	 * @param strArr
	 * @return The array of stemmed tokens
	 */
	public static String[] getAllStemmedTokens(final String[] strArr) {
		final PorterStemmer porterStemmer = new PorterStemmer();
		final String[] tokens = new String[strArr.length];
		for (int i = 0; i < strArr.length; i++) {
			tokens[i] = porterStemmer.stemWord(strArr[i]);
		}
		return tokens;
	}

	/**
	 * Returns the array of stop words free tokens
	 * 
	 * @param strArr
	 * @return The array of stop words free tokens
	 */
	public static String[] getAllStopWordsFreeTokens(final String[] strArr) {
		return StopWordRemover.getInstance().removeStopWords(strArr);
	}

	/**
	 * Returns the array of stop words free stemmed tokens
	 * 
	 * @param strArr
	 * @return The array of stop words free stemmed tokens
	 */
	public static String[] getAllStopWordsFreeStemmedTokens(
			final String[] strArr) {
		return SSCUtils.getAllStemmedTokens(SSCUtils
				.getAllStopWordsFreeTokens(strArr));
	}

	/**
	 * 
	 * @param arr
	 * @param length
	 * @return
	 */
	public static double[] normalize(double[] arr, int length) {
		double[] normalized = new double[length];
		double min = StatUtils.min(arr, 0, length);
		double max = StatUtils.max(arr, 0, length);
		for (int i = 0; i < length; i++) {
			if (max - min > 0) {
				normalized[i] = (arr[i] - min) / (max - min);
			} else {
				normalized[i] = arr[i] - min;
			}
		}
		return normalized;
	}

	public static Map<String, RelatednessCalculator> getFeatureNameRelatednessCalcMap()
			throws SSCException {
		Map<String, RelatednessCalculator> featureNameRelatednessCalcMap = new HashMap<>();
		Properties properties = SSCConfiguration.getInstance().getProperties();
		for (final String featureName : Constants.RELATEDNESS_CALCULATORS) {
			try {
				if (featureName.equals(Constants.RELATEDNESS_CALCULATORS[0])) {
					featureNameRelatednessCalcMap.put(featureName, null);
					continue;
				}
				featureNameRelatednessCalcMap.put(featureName,
						(RelatednessCalculator) ConstructorUtils
								.invokeConstructor(Class.forName(properties
										.getProperty(featureName)),
										new NictWordNet()));
			} catch (NoSuchMethodException | IllegalAccessException
					| InvocationTargetException | InstantiationException
					| ClassNotFoundException e) {
				throw new SSCException(e.getMessage(), e);
			}
		}
		return featureNameRelatednessCalcMap;
	}

	public static String getDuration(long duration) {
		StringBuilder timeStr = new StringBuilder();
		long sec = duration % 60;
		duration /= 60;
		long min = duration % 60;
		duration /= 60;
		long hr = duration % 24;
		duration /= 24;
		long day = duration;
		if (day > 0) {
			timeStr.append(day + " d, ");
		}
		if (hr > 0) {
			timeStr.append(hr + " hr, ");
		}
		if (min > 0) {
			timeStr.append(min + " min, ");
		}
		if (sec > 0) {
			timeStr.append(sec + " sec");
		}
		return timeStr.toString();
	}
}
