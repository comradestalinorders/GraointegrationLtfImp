
package net.is_bg.ltf.grao.ltf.massive.client.sqlstatements;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.is_bg.ltf.db.common.SelectSqlStatement;

class GraoFileDescriptionSelect extends SelectSqlStatement {

	private GraoFileDescription fileDescriptor;
	private long fileId;
	private String uid;
	
	
	GraoFileDescriptionSelect(long fileId){
		this.fileId = fileId;
	}
	
	GraoFileDescriptionSelect(String uid){
		this.uid = uid;
	}
	
	
	
	public GraoFileDescription getFileDescriptor() {
		return fileDescriptor;
	}

	@Override
	protected String getSqlString() {
		String sql=" select  filedescriptor_id,\r\n" + 
				"   uid,\r\n" + 
				"   name,\r\n" + 
				"   status,\r\n" + 
				"   create_time, parent_filedescriptor_id  from grao.filedescriptor where 1=1 ";
		if(fileId > 0) sql +=" and  filedesriptor_id = ?";
		else sql+= " and uid = ? ";
		return sql;
	}
	
	@Override
	protected void setParameters(PreparedStatement prStmt) throws SQLException {
		if(fileId > 0) prStmt.setLong(1, fileId);
		else prStmt.setString(1, uid);
	}
	
	@Override
	protected void retrieveResult(ResultSet rs) throws SQLException {
		while(rs.next()) {
			fileDescriptor = new GraoFileDescription();
			fileDescriptor.setFileId(rs.getLong("filedescriptor_id"));
			fileDescriptor.setUid(rs.getString("uid"));
			fileDescriptor.setFname(rs.getString("name"));
			fileDescriptor.setStatus(rs.getString("status"));
			fileDescriptor.setCreateTime(rs.getDate("create_time"));
			fileDescriptor.setParentId(rs.getLong("parent_filedescriptor_id"));
		}
	}
}
