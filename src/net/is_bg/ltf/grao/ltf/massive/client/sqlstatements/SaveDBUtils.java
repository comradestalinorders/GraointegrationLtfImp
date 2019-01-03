package net.is_bg.ltf.grao.ltf.massive.client.sqlstatements;

import java.util.List;

import net.is_bg.ltf.db.common.DBStatement;

public class SaveDBUtils {

	
	public static IGraoDbFileSaver createFileLinesSaver(List<String> fileLines, String uid, List<DBStatement> addedStatements) {
		//data base saver
		GraoSaveFileLinesUnderCommonUid dbSaver = new GraoSaveFileLinesUnderCommonUid(uid);
		for(DBStatement s :addedStatements)dbSaver.addDbStatement(s);
		dbSaver.addFileLines(fileLines);
		return dbSaver;
	}
	
}
