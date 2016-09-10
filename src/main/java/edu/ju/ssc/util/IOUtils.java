package edu.ju.ssc.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import edu.ju.ssc.Constants;
import edu.ju.ssc.SentencePair;
import edu.ju.ssc.exception.SSCException;
import edu.ju.ssc.feature.Feature;

public class IOUtils {
	/**
	 * 
	 * @param sentencePairList
	 * @param fileName
	 * @throws SSCException
	 */
	public static void storeSentencePairList(
			ConcurrentLinkedDeque<SentencePair> sentencePairList, String fileName)
			throws SSCException {

		SimpleDateFormat dateFormat = new SimpleDateFormat(
				Constants.DATE_TIME_PATTERN, Locale.US);
		String outputFileName = fileName + dateFormat.format(new Date())
				+ FilenameUtils.EXTENSION_SEPARATOR
				+ Constants.OUTPUT_FILE_EXTENSION;

		StringBuilder sb = new StringBuilder();

		String str = String.format(Constants.OUT_FILE_COL_HEAD_PATTERN,
				(Object[]) Constants.OUT_FILE_COL_NAMES);
		str = str
				+ String.format(Constants.FORMAT_STR, StringUtils.leftPad(
						StringUtils.EMPTY, str.length(), Constants.DASH_STR));
		sb.append(str);

		for (SentencePair pair : sentencePairList) {
			Feature feature = pair.getFeature();

			sb.append(String.format(Constants.OUTPUT_FILE_PATTERN, pair
					.getFirstSentence().getId(), pair.getSecondSentence()
					.getId(), feature
					.getValue(Constants.RELATEDNESS_CALCULATORS[0]), feature
					.getValue(Constants.RELATEDNESS_CALCULATORS[1]), feature
					.getValue(Constants.RELATEDNESS_CALCULATORS[2]), feature
					.getValue(Constants.RELATEDNESS_CALCULATORS[3]), feature
					.getSimpleWordRelCount(),
					feature.getSimpleWordNoRelCount(), pair.getFirstSentence()
							.getWordCount()));
		}
		try {
			FileUtils.write(FileUtils.getFile(outputFileName), sb,
					Constants.CHARACTER_ENCODING, true);
		} catch (IOException e) {
			throw new SSCException(e.getMessage(), e);
		}

	}
}
