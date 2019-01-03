package net.is_bg.ltf.grao.ltf.massive.client.sqlstatements;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import net.is_bg.ltf.db.common.UpdateSqlStatement;


/**
 * Insert into graofile table one row per file!
 * @author Lubo
 *
 */
public class GraoFileDescriptionInsert extends UpdateSqlStatement{
	GraoFileDescription fd;
	public GraoFileDescriptionInsert(GraoFileDescription fd) {
		this.fd = fd;
	}

	@Override
	protected String getSqlString() {
		String sql = " insert into grao.filedescriptor ( filedescriptor_id ,\r\n" + 
				"   uid ,\r\n" + 
				"   name,\r\n" + 
				"   status, create_time, parent_filedescriptor_id ) values ( "
				+  (fd.getFileId() > 0 ? " ?, " : " nextval('grao.s_filedescriptor') , ");
				sql +=" ?,?,?, current_timestamp, ?)";
		return sql;
	}
	
	@Override
	protected void setParameters(PreparedStatement prStmt) throws SQLException {
		if(fd.getFileId() > 0) bindVarData.setLong(fd.getFileId());
		bindVarData.setString(fd.getUid());
		bindVarData.setString(fd.getFname());
		bindVarData.setString(fd.getStatus());
		if(fd.getParentId() > 0) bindVarData.setLong(fd.getParentId()); else bindVarData.setNull(Types.NUMERIC);
		bindVarData.setParameters(prStmt);
	}
	
}
