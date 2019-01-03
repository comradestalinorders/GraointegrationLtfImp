package net.is_bg.ltf.grao.ltf.massive.client.sqlstatements;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import net.is_bg.ltf.db.common.SelectSqlStatement;

public class GraoSpMassive extends SelectSqlStatement {
	
	private long fileId;
	private long flags;
	
	public GraoSpMassive(long fileId, long flags) {
		this.fileId = fileId;
		this.flags = flags;
	}

	@Override
	protected String getSqlString() {
		return " select * from grao.processfile(?, ?) ";
	}
	
	@Override
	protected void setParameters(PreparedStatement prStmt) throws SQLException {
		bindVarData.setLong(fileId);
    	bindVarData.setInt((int)flags);
    	bindVarData.setParameters(prStmt);
	}

}
