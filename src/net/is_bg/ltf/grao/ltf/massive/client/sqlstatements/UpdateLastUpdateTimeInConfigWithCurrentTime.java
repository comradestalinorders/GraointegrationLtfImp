package net.is_bg.ltf.grao.ltf.massive.client.sqlstatements;


import java.sql.PreparedStatement;
import java.sql.SQLException;

import net.is_bg.ltf.db.common.UpdateSqlStatement;

public class UpdateLastUpdateTimeInConfigWithCurrentTime extends UpdateSqlStatement {

	@Override
	protected String getSqlString() {
		return " update grao.config set last_update_time = current_timestamp ";
	}

	@Override
	protected void setParameters(PreparedStatement prStmt) throws SQLException {
		super.setParameters(prStmt);
	}
	
}
