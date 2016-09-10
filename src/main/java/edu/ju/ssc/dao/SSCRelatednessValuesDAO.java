package edu.ju.ssc.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import edu.ju.ssc.dto.SSCRelatednessValuesDTO;
import edu.ju.ssc.exception.SSCException;

public class SSCRelatednessValuesDAO implements ISSCDAO {
	/**
	 * The SSCRelatednessValuesDTO list
	 */
	private List<SSCRelatednessValuesDTO> sscRelatednessValuesDTOList;

	/**
	 * @return the sscRelatednessValuesDTOList
	 */
	public List<SSCRelatednessValuesDTO> getSscRelatednessValuesDTOList() {
		return sscRelatednessValuesDTOList;
	}

	/**
	 * @param sscRelatednessValuesDTOList
	 *            the sscRelatednessValuesDTOList to set
	 */
	public void setSscRelatednessValuesDTOList(
			List<SSCRelatednessValuesDTO> sscRelatednessValuesDTOList) {
		this.sscRelatednessValuesDTOList = sscRelatednessValuesDTOList;
	}

	@Override
	public void save(JdbcTemplate jdbcTemplate) throws SSCException {
		for (SSCRelatednessValuesDTO dto : sscRelatednessValuesDTOList) {
			dto.write(jdbcTemplate);
		}
	}
}
