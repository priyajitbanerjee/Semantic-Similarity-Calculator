/**
 * 
 */
package edu.ju.ssc.feature;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.cmu.lti.ws4j.RelatednessCalculator;
import edu.ju.ssc.Constants;
import edu.ju.ssc.Sentence;
import edu.ju.ssc.SentencePair;
import edu.ju.ssc.exception.SSCException;
import edu.ju.ssc.util.SSCUtils;

/**
 * @author Priyajit Banerjee
 *
 */
public class FeatureCalculator {
	private static final Logger logger = LogManager
			.getLogger(FeatureCalculator.class);

	private List<Sentence> sentenceList;
	private ConcurrentLinkedDeque<SentencePair> sentencePairConcurrDeque;
	private Map<String, RelatednessCalculator> featureNameRelCalcMap;

	/**
	 * @param sentenceList
	 */
	public FeatureCalculator(final List<Sentence> sentenceList,
			final Map<String, RelatednessCalculator> featureNameRelCalcMap) {
		this.sentenceList = sentenceList;
		this.featureNameRelCalcMap = featureNameRelCalcMap;
		this.sentencePairConcurrDeque = new ConcurrentLinkedDeque<>();
	}

	/**
	 * @return the sentenceList
	 */
	public List<Sentence> getSentenceList() {
		return sentenceList;
	}

	/**
	 * @param sentenceList
	 *            the sentenceList to set
	 */
	public void setSentenceList(List<Sentence> sentenceList) {
		this.sentenceList = sentenceList;
	}

	/**
	 * @return the sentencePairConcurrDeque
	 */
	public ConcurrentLinkedDeque<SentencePair> getSentencePairConcurrDeque() {
		return sentencePairConcurrDeque;
	}

	/**
	 * @param sentencePairConcurrDeque
	 *            the sentencePairConcurrDeque to set
	 */
	public void setSentencePairConcurrDeque(
			ConcurrentLinkedDeque<SentencePair> sentencePairConcurrDeque) {
		this.sentencePairConcurrDeque = sentencePairConcurrDeque;
	}

	/**
	 * 
	 */
	public void calculate() {
		preProcessSentences();

		sentenceList
				.parallelStream()
				.forEach(
						firstSentence -> {
							for (Sentence secondSentence : sentenceList) {
								if (!firstSentence.getId().equals(
										secondSentence.getId())
										&& firstSentence.getWordCount() <= secondSentence
												.getWordCount()) {
									logger.debug(
											"Computation started for sentence pair {} & {}",
											firstSentence.getId(),
											secondSentence.getId());

									final Feature feature = new Feature(
											firstSentence.getWords(),
											secondSentence.getWords());
									for (final Map.Entry<String, RelatednessCalculator> featureEntry : featureNameRelCalcMap
											.entrySet()) {
										try {
											if (featureEntry.getValue() == null) {
												Thread thread = new Thread(
														() -> feature
																.compute(featureEntry
																		.getKey()));
												thread.start();
												thread.join();
											} else {
												Thread thread = new Thread(
														() -> feature.compute(
																featureEntry
																		.getKey(),
																featureEntry
																		.getValue()));
												thread.start();
												thread.join();
											}
										} catch (InterruptedException e) {
											e.printStackTrace();
											logger.debug(
													Constants.DEBUG_EXCEPTION_MSG,
													e);
										}
									}

									final SentencePair sentencePair = new SentencePair(
											firstSentence, secondSentence);
									sentencePair.setFeature(feature);
									sentencePairConcurrDeque.add(sentencePair);

									logger.debug(
											"Computation completed for sentence pair {}",
											sentencePair);
								}
							}
						});
	}

	private void preProcessSentences() {
		sentenceList
				.parallelStream()
				.forEach(
						sentence -> {
							try {
								sentence.setWords(Arrays.asList(SSCUtils
										.getAllStopWordsFreeStemmedTokens(SSCUtils
												.getAllTokensWithoutPunctuation(StringUtils
														.lowerCase(sentence
																.getContent())))));
							} catch (SSCException e) {
								e.printStackTrace();
								logger.debug(Constants.DEBUG_EXCEPTION_MSG, e);
							}
						});
	}
}
