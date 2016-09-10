package edu.ju.ssc.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.springframework.jdbc.core.JdbcTemplate;

import edu.ju.ssc.Constants;
import edu.ju.ssc.SentencePair;
import edu.ju.ssc.dto.SSCRelatednessValuesDTO;
import edu.ju.ssc.exception.SSCException;
import edu.ju.ssc.feature.Feature;

public class SentencePairDAO implements ISSCDAO {
	/**
	 * The SentencePair list
	 */
	private ConcurrentLinkedDeque<SentencePair> sentencePairList;

	/**
	 * @return the sentencePairList
	 */
	public ConcurrentLinkedDeque<SentencePair> getSentencePairList() {
		return sentencePairList;
	}

	/**
	 * @param sentencePairList
	 *            the sentencePairList to set
	 */
	public void setSentencePairList(
			ConcurrentLinkedDeque<SentencePair> sentencePairList) {
		this.sentencePairList = sentencePairList;
	}

	@Override
	public void save(JdbcTemplate jdbcTemplate) throws SSCException {
		List<SSCRelatednessValuesDTO> dtos = new ArrayList<>();
		for (SentencePair pair : sentencePairList) {
			Feature feature = pair.getFeature();
			SSCRelatednessValuesDTO relatednessValuesDTO = new SSCRelatednessValuesDTO();
			relatednessValuesDTO.setFirstSentenceId(pair.getFirstSentence()
					.getId());
			relatednessValuesDTO.setSecondSentenceId(pair.getSecondSentence()
					.getId());
			relatednessValuesDTO.setSimpleFeatureValue(feature
					.getValue(Constants.RELATEDNESS_CALCULATORS[0]));
			relatednessValuesDTO.setLeskFeatureValue(feature
					.getValue(Constants.RELATEDNESS_CALCULATORS[1]));
			relatednessValuesDTO.setLinFeatureValue(feature
					.getValue(Constants.RELATEDNESS_CALCULATORS[2]));
			relatednessValuesDTO.setPathFeatureValue(feature
					.getValue(Constants.RELATEDNESS_CALCULATORS[3]));
			relatednessValuesDTO.setMatchedWordCount(feature
					.getSimpleWordRelCount());
			relatednessValuesDTO.setMismatchedWordCount(feature
					.getSimpleWordNoRelCount());
			relatednessValuesDTO.setMinimumWordCount(pair.getFirstSentence()
					.getWordCount());
			dtos.add(relatednessValuesDTO);
		}
		SSCRelatednessValuesDAO relatednessValuesDAO = new SSCRelatednessValuesDAO();
		relatednessValuesDAO.setSscRelatednessValuesDTOList(dtos);
		relatednessValuesDAO.save(jdbcTemplate);

	}

}
