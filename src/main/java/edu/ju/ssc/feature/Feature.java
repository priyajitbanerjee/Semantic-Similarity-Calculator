/**
 * 
 */
package edu.ju.ssc.feature;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.stat.StatUtils;

import edu.cmu.lti.ws4j.RelatednessCalculator;
import edu.ju.ssc.util.SSCUtils;

/**
 * @author Priyajit Banerjee
 *
 */
public class Feature {
	/**
	 * The first sentence tokens without punctuation
	 */
	private List<String> firstSentenceTokens;
	/**
	 * The second sentence tokens without punctuation
	 */
	private List<String> secondSentenceTokens;
	/**
	 * The map of feature's name and their corresponding value
	 */
	private ConcurrentMap<String, Number> featureNameValueMap;
	/**
	 * The no. of exact matches after stemming
	 */
	private int simpleWordRelCount;
	/**
	 * The no. of words that do not match exactly after stemming
	 */
	private int simpleWordNoRelCount;

	/**
	 * @param firstSentenceTokens
	 * @param secondSentenceTokens
	 */
	public Feature(final List<String> firstSentenceTokens,
			final List<String> secondSentenceTokens) {
		this.firstSentenceTokens = firstSentenceTokens;
		this.secondSentenceTokens = secondSentenceTokens;
		featureNameValueMap = new ConcurrentHashMap<>();
	}

	/**
	 * The default constructor
	 */
	public Feature() {
		this(new ArrayList<>(), new ArrayList<>());
	}

	/**
	 * @return the firstSentenceTokens
	 */
	public List<String> getFirstSentenceTokens() {
		return firstSentenceTokens;
	}

	/**
	 * @param firstSentenceTokens
	 *            the firstSentenceTokens to set
	 */
	public void setFirstSentenceTokens(List<String> firstSentenceTokens) {
		this.firstSentenceTokens = firstSentenceTokens;
	}

	/**
	 * @return the secondSentenceTokens
	 */
	public List<String> getSecondSentenceTokens() {
		return secondSentenceTokens;
	}

	/**
	 * @param secondSentenceTokens
	 *            the secondSentenceTokens to set
	 */
	public void setSecondSentenceTokens(List<String> secondSentenceTokens) {
		this.secondSentenceTokens = secondSentenceTokens;
	}

	/**
	 * @return the featureNameValueMap
	 */
	public ConcurrentMap<String, Number> getFeatureNameValueMap() {
		return featureNameValueMap;
	}

	/**
	 * @param featureNameValueMap
	 *            the featureNameValueMap to set
	 */
	public void setFeatureNameValueMap(
			ConcurrentMap<String, Number> featureNameValueMap) {
		this.featureNameValueMap = featureNameValueMap;
	}

	/**
	 * @return the simpleWordRelCount
	 */
	public int getSimpleWordRelCount() {
		return simpleWordRelCount;
	}

	/**
	 * @param simpleWordRelCount
	 *            the simpleWordRelCount to set
	 */
	public void setSimpleWordRelCount(int simpleWordRelCount) {
		this.simpleWordRelCount = simpleWordRelCount;
	}

	/**
	 * @return the simpleWordNoRelCount
	 */
	public int getSimpleWordNoRelCount() {
		return simpleWordNoRelCount;
	}

	/**
	 * @param simpleWordNoRelCount
	 *            the simpleWordNoRelCount to set
	 */
	public void setSimpleWordNoRelCount(int simpleWordNoRelCount) {
		this.simpleWordNoRelCount = simpleWordNoRelCount;
	}

	public void compute(final String featureName) {
		int count = 0;
		for (final String firstToken : firstSentenceTokens) {
			for (final String secondToken : secondSentenceTokens) {
				if (StringUtils.equalsIgnoreCase(firstToken, secondToken)) {
					count++;
				}
			}
		}

		simpleWordRelCount = count;
		simpleWordNoRelCount = firstSentenceTokens.size() - count;
		featureNameValueMap.put(featureName, count);
	}

	public void compute(final String featureName,
			RelatednessCalculator relatednessCalculator) {
		double[] semanticMatchValues = new double[firstSentenceTokens.size()];
		double[] leskValues = new double[secondSentenceTokens.size()];

		int index = 0;
		for (final String firstToken : firstSentenceTokens) {
			int count = 0;
			if (!secondSentenceTokens.contains(firstToken)) {
				for (final String secondToken : secondSentenceTokens) {
					leskValues[count++] = relatednessCalculator
							.calcRelatednessOfWords(firstToken, secondToken);
				}
				if (count > 0) {
					semanticMatchValues[index++] = StatUtils.max(leskValues, 0,
							count);
				}
			}
		}

		if (index > 0) {
			featureNameValueMap.put(featureName, StatUtils.sum(SSCUtils
					.normalize(semanticMatchValues, index)));
		}
	}

	@Override
	public String toString() {
		return featureNameValueMap.toString();
	}

	public double getValue(final String featureName) {
		if (featureNameValueMap.containsKey(featureName)) {
			return featureNameValueMap.get(featureName).doubleValue();
		} else {
			return Double.NEGATIVE_INFINITY;
		}
	}

}
