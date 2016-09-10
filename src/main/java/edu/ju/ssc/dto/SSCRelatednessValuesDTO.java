package edu.ju.ssc.dto;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import edu.ju.ssc.exception.SSCException;
import edu.ju.ssc.query.QueryBuilderUtils;

public class SSCRelatednessValuesDTO extends SSCBaseDTO {
	/**
	 * The SemanticRelatednessValues Pk
	 */
	private long semanticRelatednessValuesPk = -1;
	/**
	 * The first sentence id
	 */
	private String firstSentenceId = null;
	/**
	 * The second sentence id
	 */
	private String secondSentenceId = null;
	/**
	 * The Simple feature value
	 */
	private double simpleFeatureValue = Double.NEGATIVE_INFINITY;
	/**
	 * The Lesk feature value
	 */
	private double leskFeatureValue = Double.NEGATIVE_INFINITY;
	/**
	 * The Lin feature value
	 */
	private double linFeatureValue = Double.NEGATIVE_INFINITY;
	/**
	 * The Path feature value
	 */
	private double pathFeatureValue = Double.NEGATIVE_INFINITY;
	/**
	 * The matched word count
	 */
	private int matchedWordCount = -1;
	/**
	 * The mismatched word count
	 */
	private int mismatchedWordCount = -1;
	/**
	 * The minimum word count
	 */
	private int minimumWordCount = -1;
	/**
	 * The INSERT SQL
	 */
	private static final String INSERT_SQL = "insert into ssc_relatedness_values "
			+ "(first_sentence_id, second_sentence_id, simple_feature_value, "
			+ "lesk_feature_value, lin_feature_value, path_feature_value, "
			+ "matched_word_count, mismatched_word_count, minimum_word_count) values "
			+ "( ?, ?, ?, ?, ?, ?, ? ,? ,? )";

	/**
	 * @return the semanticRelatednessValuesPk
	 */
	public long getSemanticRelatednessValuesPk() {
		return semanticRelatednessValuesPk;
	}

	/**
	 * @param semanticRelatednessValuesPk
	 *            the semanticRelatednessValuesPk to set
	 */
	public void setSemanticRelatednessValuesPk(long semanticRelatednessValuesPk) {
		this.semanticRelatednessValuesPk = semanticRelatednessValuesPk;
	}

	/**
	 * @return the firstSentenceId
	 */
	public String getFirstSentenceId() {
		return firstSentenceId;
	}

	/**
	 * @param firstSentenceId
	 *            the firstSentenceId to set
	 */
	public void setFirstSentenceId(String firstSentenceId) {
		this.firstSentenceId = firstSentenceId;
	}

	/**
	 * @return the secondSentenceId
	 */
	public String getSecondSentenceId() {
		return secondSentenceId;
	}

	/**
	 * @param secondSentenceId
	 *            the secondSentenceId to set
	 */
	public void setSecondSentenceId(String secondSentenceId) {
		this.secondSentenceId = secondSentenceId;
	}

	/**
	 * @return the simpleFeatureValue
	 */
	public double getSimpleFeatureValue() {
		return simpleFeatureValue;
	}

	/**
	 * @param simpleFeatureValue
	 *            the simpleFeatureValue to set
	 */
	public void setSimpleFeatureValue(double simpleFeatureValue) {
		this.simpleFeatureValue = simpleFeatureValue;
	}

	/**
	 * @return the leskFeatureValue
	 */
	public double getLeskFeatureValue() {
		return leskFeatureValue;
	}

	/**
	 * @param leskFeatureValue
	 *            the leskFeatureValue to set
	 */
	public void setLeskFeatureValue(double leskFeatureValue) {
		this.leskFeatureValue = leskFeatureValue;
	}

	/**
	 * @return the linFeatureValue
	 */
	public double getLinFeatureValue() {
		return linFeatureValue;
	}

	/**
	 * @param linFeatureValue
	 *            the linFeatureValue to set
	 */
	public void setLinFeatureValue(double linFeatureValue) {
		this.linFeatureValue = linFeatureValue;
	}

	/**
	 * @return the pathFeatureValue
	 */
	public double getPathFeatureValue() {
		return pathFeatureValue;
	}

	/**
	 * @param pathFeatureValue
	 *            the pathFeatureValue to set
	 */
	public void setPathFeatureValue(double pathFeatureValue) {
		this.pathFeatureValue = pathFeatureValue;
	}

	/**
	 * @return the matchedWordCount
	 */
	public int getMatchedWordCount() {
		return matchedWordCount;
	}

	/**
	 * @param matchedWordCount
	 *            the matchedWordCount to set
	 */
	public void setMatchedWordCount(int matchedWordCount) {
		this.matchedWordCount = matchedWordCount;
	}

	/**
	 * @return the mismatchedWordCount
	 */
	public int getMismatchedWordCount() {
		return mismatchedWordCount;
	}

	/**
	 * @param mismatchedWordCount
	 *            the mismatchedWordCount to set
	 */
	public void setMismatchedWordCount(int mismatchedWordCount) {
		this.mismatchedWordCount = mismatchedWordCount;
	}

	/**
	 * @return the minimumWordCount
	 */
	public int getMinimumWordCount() {
		return minimumWordCount;
	}

	/**
	 * @param minimumWordCount
	 *            the minimumWordCount to set
	 */
	public void setMinimumWordCount(int minimumWordCount) {
		this.minimumWordCount = minimumWordCount;
	}

	@Override
	protected void writeImpl(PreparedStatement ps) throws SQLException,
			SSCException {
		int i = 1;
		ps.setString(i++, this.getFirstSentenceId());
		ps.setString(i++, this.getSecondSentenceId());
		ps.setDouble(i++, this.getSimpleFeatureValue());
		ps.setDouble(i++, this.getLeskFeatureValue());
		ps.setDouble(i++, this.getLinFeatureValue());
		ps.setDouble(i++, this.getPathFeatureValue());
		ps.setInt(i++, this.getMatchedWordCount());
		ps.setInt(i++, this.getMismatchedWordCount());
		ps.setInt(i++, this.getMinimumWordCount());
	}

	/**
	 * 
	 * @param jdbcTemplate
	 * @return
	 */
	public static final List<SSCRelatednessValuesDTO> getSSCRelatednessValuesDTOList(
			final JdbcTemplate jdbcTemplate) {
		final StringBuilder queryStr = new StringBuilder(
				"select first_sentence_id, second_sentence_id, simple_feature_value, ");
		queryStr.append(" lesk_feature_value, lin_feature_value, path_feature_value ");
		queryStr.append(" matched_word_count, mismatched_word_count, minimum_word_count from ssc_relatedness_values");

		return QueryBuilderUtils.query(jdbcTemplate, queryStr.toString(),
				SSCRelatednessValuesDTO.class);
	}

	@Override
	protected String getSQL() {
		return INSERT_SQL;
	}

}
