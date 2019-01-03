package net.is_bg.ltf.grao.ltf;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.is_bg.ltf.db.common.SelectSqlStatement;


class RdvrSelect  extends SelectSqlStatement{
	
	List<Rdvr> result = new ArrayList<Rdvr>();
	
	
	@Override
	protected String getSqlString() {
		return "  select rdvr_id, code, city_id, upper(name) n from rdvr order by code ";
	}
	@Override
	protected void retrieveResult(ResultSet rs) throws SQLException {
		while (rs.next()) {
			Rdvr rdvr = new Rdvr();
			rdvr.setRdvrId(rs.getLong("rdvr_id"));
			rdvr.setName(rs.getString("n"));
			rdvr.setCityId(rs.getLong("city_id"));
			rdvr.setCode(rs.getString("code"));
			result.add(rdvr);
		}
	}
	
	List<Rdvr> getResult() {
		return result;
	}
}
