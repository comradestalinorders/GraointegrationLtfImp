package net.is_bg.ltf.grao.ltf.massive.client.sqlstatements;

import java.sql.ResultSet;
import java.sql.SQLException;

import net.is_bg.ltf.db.common.SelectSqlStatement;

public class GraoConfigSelect extends SelectSqlStatement {
	private GraoConfig config;

	@Override
	protected String getSqlString() {
		String sql = "select last_update_time, mindays_before_next_update, maxdays_filevalidity, flags"
				+ " from  grao.config " ;
		return sql;
	}
	
	
	@Override
	protected void retrieveResult(ResultSet rs) throws SQLException {
		while(rs.next()) {
			config = new GraoConfig();
			config.setLastUpdateTime( rs.getDate("last_update_time"));
			config.setMindaysBeaforeUpd(rs.getInt("mindays_before_next_update"));
			config.setMaxDaysFileValidity(rs.getInt("maxdays_filevalidity"));
			config.setFlags(rs.getInt("flags"));
			config.setUpdateAddress((config.getFlags() & 1) != 0 );
			config.setUpdateDead((config.getFlags() & 2) != 0 );
		}
	}
	
	public GraoConfig getConfig() {
		return config;
	}
}
