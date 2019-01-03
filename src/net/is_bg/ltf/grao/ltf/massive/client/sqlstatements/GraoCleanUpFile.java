package net.is_bg.ltf.grao.ltf.massive.client.sqlstatements;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import net.is_bg.ltf.db.common.SelectSqlStatement;

public class GraoCleanUpFile extends SelectSqlStatement {
	
	private long fileId;
	
	public GraoCleanUpFile(long fileId) {
		this.fileId = fileId;
	}

	@Override
	protected String getSqlString() {
		return " select * from grao.cleanup(?) ";
	}
	
	@Override
	protected void setParameters(PreparedStatement prStmt) throws SQLException {
		bindVarData.setLong(fileId);
    	bindVarData.setParameters(prStmt);
	}

}

