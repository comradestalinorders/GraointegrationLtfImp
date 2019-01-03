package net.is_bg.ltf.grao.ltf.massive.client.sqlstatements;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import net.is_bg.ltf.db.common.UpdateSqlStatement;

public class GraoFileDescriptionUpdate extends UpdateSqlStatement {
	GraoFileDescription fd;
	
	public GraoFileDescriptionUpdate(GraoFileDescription fd){
		this.fd = fd;
	}
	
	@Override
	protected String getSqlString() {
		return " update grao.filedescriptor set   status = ? " + 
			   " where  filedescriptor_id = ? ";
	}
	
	@Override
	protected void setParameters(PreparedStatement prStmt) throws SQLException {
		prStmt.setString(1, fd.getStatus());
		prStmt.setLong(2, fd.getFileId());
	}
}
