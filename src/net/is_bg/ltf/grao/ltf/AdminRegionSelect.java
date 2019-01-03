package net.is_bg.ltf.grao.ltf;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.is_bg.ltf.db.common.SelectSqlStatement;

class AdminRegionSelect extends SelectSqlStatement {
	List<AdminRegion> result = new ArrayList<AdminRegion>();

	@Override
	protected String getSqlString() {
		return "  select admregion_id, municipality_id, code, upper(\"name\") \"name\", upper(fullname) fullname, city_id from admregion  ";
	}
	
	
	@Override
	protected void retrieveResult(ResultSet rs) throws SQLException {
		while(rs.next()){
			AdminRegion adm = new AdminRegion();
			adm.setCityId(rs.getLong("city_id"));
			adm.setCode(rs.getString("code"));
			adm.setMunicipalityId(rs.getLong("municipality_id"));
			adm.id = rs.getLong("admregion_id");
			adm.parentId = rs.getLong("city_id");
			adm.name = rs.getString("name");
			result.add(adm);
		}
	}
	
	public List<AdminRegion> getResult() {
		return result;
	}

}
