package edu.ju.ssc.dto;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import edu.ju.ssc.exception.SSCException;

public class SentenceDetailsDTO extends SSCBaseDTO {
	/**
	 * The SentenceDetails Pk
	 */
	private long sentenceDetailsPk = -1;
	/**
	 * The Sentence Id
	 */
	private String sentenceId = null;
	/**
	 * The Sentence content
	 */
	private String sentenceContent = null;
	/**
	 * The words of the sentence content
	 */
	private String[] sentenceContentWords = null;
	/**
	 * The Sentence content word count
	 */
	private int sentenceContentWordCount = -1;
	/**
	 * The INSERT SQL
	 */
	private static final String INSERT_SQL = "insert into sentence_details "
			+ "(sentence_id, sentence_content, sentence_content_words, "
			+ "sentence_content_word_count) values (?, ?, ?, ?)";

	/**
	 * @return the sentenceContentWords
	 */
	public String[] getSentenceContentWords() {
		return sentenceContentWords;
	}

	/**
	 * @param sentenceContentWords
	 *            the sentenceContentWords to set
	 */
	public void setSentenceContentWords(String[] sentenceContentWords) {
		this.sentenceContentWords = sentenceContentWords;
	}

	/**
	 * @return the sentenceContentWordCount
	 */
	public int getSentenceContentWordCount() {
		return sentenceContentWordCount;
	}

	/**
	 * @param sentenceContentWordCount
	 *            the sentenceContentWordCount to set
	 */
	public void setSentenceContentWordCount(int sentenceContentWordCount) {
		this.sentenceContentWordCount = sentenceContentWordCount;
	}

	/**
	 * @return the sentenceDetailsPk
	 */
	public long getSentenceDetailsPk() {
		return sentenceDetailsPk;
	}

	/**
	 * @param sentenceDetailsPk
	 *            the sentenceDetailsPk to set
	 */
	public void setSentenceDetailsPk(long sentenceDetailsPk) {
		this.sentenceDetailsPk = sentenceDetailsPk;
	}

	/**
	 * @return the sentenceId
	 */
	public String getSentenceId() {
		return sentenceId;
	}

	/**
	 * @param sentenceId
	 *            the sentenceId to set
	 */
	public void setSentenceId(String sentenceId) {
		this.sentenceId = sentenceId;
	}

	/**
	 * @return the sentenceContent
	 */
	public String getSentenceContent() {
		return sentenceContent;
	}

	/**
	 * @param sentenceContent
	 *            the sentenceContent to set
	 */
	public void setSentenceContent(String sentenceContent) {
		this.sentenceContent = sentenceContent;
	}

	@Override
	protected void writeImpl(PreparedStatement ps) throws SQLException,
			SSCException {
		int i = 1;
		ps.setString(i++, this.getSentenceId());
		ps.setString(i++, this.getSentenceContent());
		ps.setObject(i++, this.getSentenceContentWords());
		ps.setInt(i++, this.getSentenceContentWordCount());
	}

	@Override
	protected String getSQL() {
		return INSERT_SQL;
	}

}
