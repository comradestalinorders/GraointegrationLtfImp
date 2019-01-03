package net.is_bg.ltf.grao.ltf.massive.client.sqlstatements;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.is_bg.ltf.db.common.SelectSqlStatement;

public class GetLastFileDescriptorForStatus  extends SelectSqlStatement {

	private GraoFileDescription fileDescriptor;
	private String status;
	
	
	
	
	public GetLastFileDescriptorForStatus(String status){
		this.status = status;
	}
	
	
	
	public GraoFileDescription getFileDescriptor() {
		return fileDescriptor;
	}

	@Override
	protected String getSqlString() {
		String sql="   select  filedescriptor_id,\r\n" + 
				"				   uid,\r\n" + 
				"				   name,\r\n" + 
				"				   status,\r\n" + 
				"				   create_time  from grao.filedescriptor where 1=1\r\n" + 
				"		 and  filedescriptor_id = (select max(filedescriptor_id) from grao.filedescriptor where status = ?) ";
		return sql;
	}
	
	@Override
	protected void setParameters(PreparedStatement prStmt) throws SQLException {
		 prStmt.setString(1, status);
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
		}
	}
	
}