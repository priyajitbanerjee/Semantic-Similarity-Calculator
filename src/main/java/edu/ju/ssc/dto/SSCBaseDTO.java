package edu.ju.ssc.dto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import edu.ju.ssc.exception.SSCException;

@Component
public abstract class SSCBaseDTO {
	/**
	 * The Write Operation
	 */
	public void write(final JdbcTemplate jdbcTemplate) {
		write(jdbcTemplate, null);
	}

	public void write(final JdbcTemplate jdbcTemplate, final KeyHolder holder) {
		if (holder != null) {
			jdbcTemplate.update(createPrepareStatement(), holder);
		} else {
			jdbcTemplate.update(createPrepareStatement());
		}
	}

	private PreparedStatementCreator createPrepareStatement() {
		return new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				final PreparedStatement ps = connection.prepareStatement(
						getSQL(), Statement.RETURN_GENERATED_KEYS);
				try {
					writeImpl(ps);
				} catch (SSCException | SQLException e) {
					e.printStackTrace();
				}
				return ps;
			}
		};
	}

	protected abstract void writeImpl(final PreparedStatement ps)
			throws SQLException, SSCException;

	protected abstract String getSQL();
}
