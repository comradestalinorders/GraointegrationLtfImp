package net.is_bg.ltf.grao.ltf;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import grao.integration.ltf.address.structure.INameIdParent;
import net.is_bg.ltf.db.common.StoredProcedure;
/**
 * Calling proceudures for matching grao city & street names!!!
 * @author lubo
 *
 */
class StoredProcedures implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7661946469104244542L;
	private static final String fieldSeparator = ";";
	private static final String lineSeparator = "&";
	
	private abstract static class common extends StoredProcedure{
		String procName;
		String graoCityName;
		String graomunicipalityName;
		boolean verbose;
		String result = null;
		int parameterIndex;

		@Override
		protected String getProcedureName() {
			return procName;
		}
		@Override
		protected void retrieveResult(CallableStatement callableStatement) throws SQLException {
			result = callableStatement.getString(parameterIndex);
		}
	}
	

	static class CityIds extends common  {
		
		@Override
		protected String getProcedureName() {
			return procName;
		}

		@Override
		protected void setParameters(CallableStatement callStmt) throws SQLException {
			bindVarData.setString(graoCityName);
			if(graomunicipalityName!= null) bindVarData.setString(graomunicipalityName);
			bindVarData.registerOutParameter(Types.VARCHAR);
			bindVarData.setBoolean(verbose);
			
			
			int index =1;
			callStmt.setString(index++,graoCityName);
			if(graomunicipalityName!= null) callStmt.setString(index++, graomunicipalityName);
			parameterIndex = index;
			callStmt.registerOutParameter(index++, Types.VARCHAR);
			callStmt.setBoolean(index++, verbose);
		}
	}
	
	
	static class CityStretIds extends common{
		long cityId;
		String graoStreetName;
		
		CityStretIds(){
			
		}
		
		@Override
		protected void setParameters(CallableStatement callStmt) throws SQLException {
			if(cityId > 0) bindVarData.setLong(cityId);   else bindVarData.setString(graoCityName);
			bindVarData.setString(graoStreetName);
			bindVarData.registerOutParameter(Types.VARCHAR);
			bindVarData.setBoolean(verbose);
			
			
			int index =1;
			if(cityId > 0) callStmt.setLong(index++, cityId); else callStmt.setString(index++,graoCityName);
			callStmt.setString(index++, graoStreetName);
			parameterIndex = index;
			callStmt.registerOutParameter(index++, Types.VARCHAR);
			callStmt.setBoolean(index++, verbose);
		}
	}
	
	static class MunicipalityProvinceIds extends common{
		
		MunicipalityProvinceIds(){
			
		}

		@Override
		protected void setParameters(CallableStatement callStmt) throws SQLException {
			if(graomunicipalityName!= null) bindVarData.setString(graomunicipalityName);
			bindVarData.registerOutParameter(Types.VARCHAR);
			bindVarData.setBoolean(verbose);
			
			int index =1;
			callStmt.setString(index++,graomunicipalityName);
			parameterIndex = index;
			callStmt.registerOutParameter(index++, Types.VARCHAR);
			callStmt.setBoolean(index++, verbose);
		}
		
	}
	
	static class Countryid extends common{
		String graoCountryName;
		Countryid(){
			
		}
		

		@Override
		protected void setParameters(CallableStatement callStmt) throws SQLException {
		    bindVarData.setString(graoCountryName);
			bindVarData.registerOutParameter(Types.VARCHAR);
			bindVarData.setBoolean(verbose);
			
			int index =1;
			callStmt.setString(index++,graoCountryName);
			parameterIndex = index;
			callStmt.registerOutParameter(index++, Types.VARCHAR);
			callStmt.setBoolean(index++, verbose);
		}
		
	}
	
	
	static CityIds  getCityIds(String graoCityName, boolean verbose){
		CityIds cids = new CityIds();
		cids.graoCityName = graoCityName;
		cids.verbose = verbose;
		cids.procName = "{call grao.getcityids(?, ?, ?)}";
		return cids;
	}
	
	static CityIds  getCityIds(String graoCityName, String graomunicipalityName, boolean verbose){
		CityIds cids = new CityIds();
		cids.graoCityName = graoCityName;
		cids.graomunicipalityName = graomunicipalityName;
		cids.verbose = verbose;
		cids.procName = "{call grao.getcityids(?, ?, ?, ?)}";
		return cids;
	}
	
	
	static CityStretIds  getCityStreetIds(String graoCityName, String graoStreetName, boolean verbose){
		CityStretIds cids = new CityStretIds();
		cids.graoCityName = graoCityName;
		cids.graoStreetName = graoStreetName;
		cids.verbose = verbose;
		cids.procName = "{call grao.getcitystreetids(?, ?, ?, ?)}";
		return cids;
	}
	
	static CityStretIds  getCityStreetIds(long cityId, String graoStreetName, boolean verbose){
		CityStretIds cids = new CityStretIds();
		cids.cityId = cityId;
		cids.graoStreetName = graoStreetName;
		cids.verbose = verbose;
		cids.procName = "{call grao.getcitystreetids(?, ?, ?, ?)}";
		return cids;
	}
	
	static MunicipalityProvinceIds  getMunicipalityProvinceIds(String graomunicipalityName, boolean verbose){
		MunicipalityProvinceIds mids = new MunicipalityProvinceIds();
		mids.graomunicipalityName = graomunicipalityName;
		mids.verbose = verbose;
		mids.procName = "{call grao.getmunicipalityid(?, ?, ?)}";
		return mids;
	}
	
	static Countryid  getCountryId(String graoCountryName, boolean verbose){
		Countryid mids = new Countryid();
		mids.graoCountryName = graoCountryName;
		mids.verbose = verbose;
		mids.procName = "{call grao.getcountryid(?, ?, ?)}";
		return mids;
	}
	
	
	static CityStretIds  getCityStreetIds(long cityId, String graoStreetName){
		return getCityStreetIds(cityId, graoStreetName, false);
	}
	
	static CityStretIds getCityStreetIds(String graoCityName, String graoStreetName){
		return getCityStreetIds(graoCityName, graoStreetName, false);
	}
	
	static MunicipalityProvinceIds  getMunicipalityProvinceIds(String graomunicipalityName){
		return getMunicipalityProvinceIds(graomunicipalityName, false);
	}
	
	static Countryid getCountryid(String graoCountryName){
		return getCountryId(graoCountryName, false);
	}
	
	
	static INameIdParent strToNameIdParent(String str){
		if(str == null) return null;
		NameIdParent namep = new NameIdParent();
		String tp [] =  str.split(fieldSeparator);
		namep.parentId = Long.valueOf(tp[0]);
		namep.id = Long.valueOf(tp[1]);
		return namep;
	}
	
	static List<INameIdParent> strToNameIdParentList(String str){
		List<INameIdParent>  l = new ArrayList<INameIdParent>();
		String tp [] =  str.split(lineSeparator);
		for(String line:tp){
			l.add(strToNameIdParent(line));
		}
		return l;
	}
	
	static boolean hasLineSeparator(String res){
		if(res== null) return false;
		if(res.contains(lineSeparator)) return true;
		return false;
	}
	
}
