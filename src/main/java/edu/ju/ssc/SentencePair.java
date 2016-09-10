/**
 * 
 */
package edu.ju.ssc;

import edu.ju.ssc.feature.Feature;

/**
 * @author Priyajit Banerjee
 *
 */
public class SentencePair {
	private Sentence firstSentence;
	private Sentence secondSentence;
	private Feature feature;

	/**
	 * @param firstSentence
	 * @param secondSentence
	 */
	public SentencePair(Sentence firstSentence, Sentence secondSentence) {
		this.firstSentence = firstSentence;
		this.secondSentence = secondSentence;
	}

	/**
	 * @return the firstSentence
	 */
	public Sentence getFirstSentence() {
		return firstSentence;
	}

	/**
	 * @param firstSentence
	 *            the firstSentence to set
	 */
	public void setFirstSentence(Sentence firstSentence) {
		this.firstSentence = firstSentence;
	}

	/**
	 * @return the secondSentence
	 */
	public Sentence getSecondSentence() {
		return secondSentence;
	}

	/**
	 * @param secondSentence
	 *            the secondSentence to set
	 */
	public void setSecondSentence(Sentence secondSentence) {
		this.secondSentence = secondSentence;
	}

	/**
	 * @return the features
	 */
	public Feature getFeature() {
		return feature;
	}

	/**
	 * @param features
	 *            the features to set
	 */
	public void setFeature(Feature feature) {
		this.feature = feature;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SentencePair [firstSentence = " + firstSentence.getId()
				+ ", secondSentence = " + secondSentence.getId()
				+ ", feature = " + feature + "]";
	}

}
