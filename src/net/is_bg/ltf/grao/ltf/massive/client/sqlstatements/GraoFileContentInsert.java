package net.is_bg.ltf.grao.ltf.massive.client.sqlstatements;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import net.is_bg.ltf.db.common.BatchUpdateSqlStatement;


class GraoFileContentInsert  extends BatchUpdateSqlStatement{

	private List<String> fileContent;
	private String rowDelimiter;
	private GraoFileDescription fd;
	private static final int fieldCnt = 28;
	
	public GraoFileContentInsert(GraoFileDescription fd, List<String> fileContent, String rowDelimiter){
		this.fileContent = fileContent;
		this.rowDelimiter = rowDelimiter;
		this.fd = fd;
	}
	
	
	/*** add more file lines to file Content list */
	public void addFileLines(List<String> fLines) {
		fileContent.addAll(fLines);
	}
	

	@Override
	protected String getSqlString() {
		String sql = " insert into  grao.filerow (\r\n" + 
				"  filerow_id         ,\r\n" + 
				"  filedescriptor_id   ,\r\n" + 
				"  ein                ,\r\n" + 
				"  firstname          ,\r\n" + 
				"  middlename         ,\r\n" + 
				"  lastname           ,\r\n" + 
				"  pcitycode          ,\r\n" + 
				"  pregioncode        ,\r\n" + 
				"  pstreetcode        ,\r\n" + 
				"  paddressnumber     ,\r\n" + 
				"  paddressentrance   ,\r\n" + 
				"  paddressfloor      ,\r\n" + 
				"  paddressapartment  ,\r\n" + 
				"  pdate              ,\r\n" + 
				"  ccitycode          ,\r\n" + 
				"  cregioncode        ,\r\n" + 
				"  cstreetcode        ,\r\n" + 
				"  caddressnumber     ,\r\n" + 
				"  caddressentrance   ,\r\n" + 
				"  caddressfloor      ,\r\n" + 
				"  caddressapartment  ,\r\n" + 
				"  cdate              ,\r\n" + 
				"  docnumber          ,\r\n" + 
				"  docdate            ,\r\n" + 
				"  doccity            ,\r\n" + 
				"  rpu                ,\r\n" + 
				"  doccitycode        ,\r\n" + 
				"  birthcity          ,\r\n" + 
				"  dead_date          \r\n" + 
				") values (nextval('grao.s_filerow'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		return sql;
	}
	
	
	private void addBatchStatement(String [] fileRow, PreparedStatement pst) throws SQLException {
		int i=1;
		pst.setLong(i++, fd.getFileId());
		for(String v: fileRow) {pst.setString(i++, "".equals(v) ? null : v);}
		while(i <= fieldCnt) {
			pst.setString(i++, null);
		}
		pst.addBatch();
	}
	
	
	//set parameters
	protected void setParameters(PreparedStatement prStmt) throws SQLException {
		//read string line by line
		try {
			//split each line by field delimiter
			for(String l : fileContent) {
			    String [] lineData = l.split(rowDelimiter);
			    
			    //create batch statement for each row
			    addBatchStatement(lineData, prStmt);
			}
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public String sqlForLog() {
		return "";//return super.sqlForLog();
	}
}
