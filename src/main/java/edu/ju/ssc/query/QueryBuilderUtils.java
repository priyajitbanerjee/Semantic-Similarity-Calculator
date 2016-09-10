package edu.ju.ssc.query;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class QueryBuilderUtils {
	/**
	 * The Logging Instance for the Class
	 */
	private static final Logger LOG = LogManager
			.getLogger(QueryBuilderUtils.class);

	/**
	 * This method will return the list of objects of type T based on the query
	 * 
	 * @param jdbcTemplate
	 *            The JDBC Template
	 * @param queryStr
	 *            The Query string
	 * @param rowMapper
	 *            The RowMapper object
	 * @param objects
	 *            The array of objects
	 * @return The List of Objects
	 */
	public static final <T> List<T> query(final JdbcTemplate jdbcTemplate,
			final String queryStr, final RowMapper<T> rowMapper,
			final Object... objects) {

		if (LOG.isDebugEnabled()) {
			LOG.debug("Query String : " + queryStr);
			for (final Object obj : objects) {
				LOG.debug("Params : " + obj);
			}
		}

		return jdbcTemplate.query(queryStr, objects, rowMapper);
	}

	/**
	 * This method will return the list of objects of type T based on the query
	 * 
	 * @param jdbcTemplate
	 *            The JDBC Template
	 * @param queryStr
	 *            The Query string
	 * @param objects
	 *            The array of objects
	 * @return The List of Objects
	 */
	public static final <T> List<T> query(final JdbcTemplate jdbcTemplate,
			final String queryStr, final Class<T> requiredType,
			final Object... objects) {

		if (LOG.isDebugEnabled()) {
			LOG.debug("Query String : " + queryStr);
			for (final Object obj : objects) {
				LOG.debug("Params : " + obj);
			}
		}

		return jdbcTemplate.query(queryStr, objects,
				new BeanPropertyRowMapper<T>(requiredType));
	}

	/**
	 * This method will return the objects of type T based on the query
	 * 
	 * @param jdbcTemplate
	 *            The JDBC Template
	 * @param queryStr
	 *            The Query String
	 * @param requiredType
	 *            The Required Type
	 * @param objects
	 *            The List of Objects
	 * @return The Object of Type T
	 */
	public static final <T> T queryForObject(final JdbcTemplate jdbcTemplate,
			final String queryStr, final RowMapper<T> rowMapper,
			final Object... objects) {

		if (LOG.isDebugEnabled()) {
			LOG.debug("Query String : " + queryStr);
			for (final Object obj : objects) {
				LOG.debug("Params : " + obj);
			}
		}

		return jdbcTemplate.queryForObject(queryStr, rowMapper, objects);
	}

	public static final <T> T queryForObject(final JdbcTemplate jdbcTemplate,
			final String queryStr, final Class<T> requiredType,
			final Object... objects) {

		if (LOG.isDebugEnabled()) {
			LOG.debug("Query String : " + queryStr);
			for (final Object obj : objects) {
				LOG.debug("Params : " + obj);
			}
		}

		return jdbcTemplate.queryForObject(queryStr,
				new BeanPropertyRowMapper<T>(requiredType), objects);
	}
}
