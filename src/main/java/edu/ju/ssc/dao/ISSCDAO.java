package edu.ju.ssc.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import edu.ju.ssc.exception.SSCException;

public interface ISSCDAO {
	void save(final JdbcTemplate jdbcTemplate) throws SSCException;

	default void load(final JdbcTemplate jdbcTemplate, Object primaryKey)
			throws SSCException {
	}

}
