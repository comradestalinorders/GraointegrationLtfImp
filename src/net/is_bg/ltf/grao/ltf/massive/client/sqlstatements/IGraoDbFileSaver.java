package net.is_bg.ltf.grao.ltf.massive.client.sqlstatements;

import net.is_bg.ltf.db.common.DBExecutor;

public interface IGraoDbFileSaver {
	public void savetoDataBase(DBExecutor ex);
	public void savetoDataBase(String dataSourceName);
}
