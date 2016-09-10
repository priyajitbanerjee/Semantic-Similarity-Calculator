package edu.ju.ssc;

import java.util.List;

/**
 * @author Priyajit Banerjee
 *
 */
public class Sentence {
	/**
	 * The Id of the sentence
	 */
	private String Id;
	/**
	 * The content of the sentence
	 */
	private String content;
	/**
	 * The array of words in the sentence
	 */
	private List<String> words;

	/**
	 * @return the id
	 */
	public String getId() {
		return Id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		Id = id;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the words
	 */
	public List<String> getWords() {
		return words;
	}

	/**
	 * @param words
	 *            the words to set
	 */
	public void setWords(List<String> words) {
		this.words = words;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Sentence [Id = " + Id + ", content = " + content + "]";
	}

	public int getWordCount() {
		return words.size();
	}
}
