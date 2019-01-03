package net.is_bg.ltf.grao.ltf.massive.client;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.is_bg.ltf.db.common.SelectSqlStatement;

class SelectEin  extends SelectSqlStatement{
	
	private List<String> list = new ArrayList<String>();
	
	@Override
	protected String getSqlString() {
		return " select idn from taxsubject ";
	}

	
	@Override
	protected void retrieveResult(ResultSet rs) throws SQLException {
		while(rs.next()) {
			list.add(rs.getString("idn"));
		}
	}
	
	public List<String> getList() {
		return list;
	}
}
