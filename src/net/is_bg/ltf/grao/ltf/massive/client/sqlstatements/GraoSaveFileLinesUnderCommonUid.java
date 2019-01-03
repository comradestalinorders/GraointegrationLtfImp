package net.is_bg.ltf.grao.ltf.massive.client.sqlstatements;

import java.util.ArrayList;
import java.util.List;
import net.is_bg.ltf.db.common.DBExecutor;
import net.is_bg.ltf.db.common.DBStatement;

class GraoSaveFileLinesUnderCommonUid implements IGraoDbFileSaver {
	
	private List<String> fileLines = new ArrayList<String>();
	private String uid;
	List<DBStatement> addedSt = new ArrayList<DBStatement>();
	
	public GraoSaveFileLinesUnderCommonUid(String uid){
		this.uid = uid;
	}
	
	public void addDbStatement(DBStatement statement) {
		addedSt.add(statement);
	}
	
	/***Add more file lines to file Content list */
	public void addFileLines(List<String> fLines) {
		fileLines.addAll(fLines);
	}
	
	/***
	 * Saves file lines to database!!
	 * @param ex
	 */
	public void savetoDataBase(DBExecutor ex) {
		GraoFileContentInsert fileCntInsert = null;
		GraoFileDescription fd = getFileDescriptor(ex, uid);
		
		List<DBStatement> dbList = new ArrayList<DBStatement>();
		// add new insert statement to dblist if no file in data base
		if(fd.getFileId() <=0) {
		   long fid = SelSeq.getNextVal(ex, "grao.s_filedescriptor");
		   fd.setFileId(fid);
		   dbList.add(new GraoFileDescriptionInsert(fd));  //add insert statement to list
		}
		
		//create batch statement
		fileCntInsert =	new GraoFileContentInsert(fd, new ArrayList<String>(), "\\|");

		//add file lines
		fileCntInsert.addFileLines(fileLines);
		
		//create insert statement for file lines
		dbList.add(fileCntInsert);
		dbList.addAll(addedSt);
		ex.execute((dbList.toArray(new DBStatement[0])));
	}
	
	
	/**Gets file descriptor for file unique identifier*/
	private static GraoFileDescription getFileDescriptor(DBExecutor ex, String uid) {
		GraoFileDescriptionSelect sel =	new GraoFileDescriptionSelect(uid);
		ex.execute(sel);
		GraoFileDescription fd = sel.getFileDescriptor();
		if(fd == null) {
			fd = new GraoFileDescription();
			fd.setStatus("CREATED");
			fd.setUid(uid);
		}
		return fd;
	}

	
	@Override
	public void savetoDataBase(String dataSourceName) {
		throw new UnsupportedOperationException();
	}
	
}
