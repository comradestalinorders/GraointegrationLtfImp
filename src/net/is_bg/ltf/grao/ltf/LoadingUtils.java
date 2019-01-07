
package net.is_bg.ltf.grao.ltf;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import grao.integration.ltf.address.structure.INameIdParent;
import net.is_bg.ltf.db.common.DBConfig;
import net.is_bg.ltf.db.common.DBExecutor;
import net.is_bg.ltf.grao.ltf.StoredProcedures.CityIds;
import net.is_bg.ltf.grao.ltf.StoredProcedures.CityStretIds;
import net.is_bg.ltf.grao.ltf.StoredProcedures.Countryid;
import net.is_bg.ltf.grao.ltf.StoredProcedures.MunicipalityProvinceIds;

/***
 * Loads stuff from database
 * @author lubo
 *
 */
class LoadingUtils {

	/**
	 * Load countries
	 * @return
	 */
	static List<INameIdParent> loadCountries(){
		IdNameSelect<INameIdParent> sel = IdNameSelect.getCountriesSelect();
		new DBExecutor(DBConfig.getConnectionFactory()).execute(sel);
		return sel.getReuslt();
	}
	
	/**Load city objects*/
	static List<INameIdParent> loadCities(){
		IdNameSelect<INameIdParent> sel = IdNameSelect.getCitySelect();
		new DBExecutor(DBConfig.getConnectionFactory()).execute(sel);
		return sel.getReuslt();
	}
	
	/**Load province objects */
	static List<INameIdParent> loadProvinces(){
		IdNameSelect<INameIdParent> sel = IdNameSelect.getProvinceSelect();
		new DBExecutor(DBConfig.getConnectionFactory()).execute(sel);
		return sel.getReuslt();
	}
	
	/**Load municipality objects*/
	static List<INameIdParent> loadMunicipalities(){
		IdNameSelect<INameIdParent> sel = IdNameSelect.getMunicipalitySelect();
		new DBExecutor(DBConfig.getConnectionFactory()).execute(sel);
		return sel.getReuslt();
	}
	
	/**Load admin regions**/
	static List<AdminRegion> loadAdmregions(){
		IdNameSelect<AdminRegion> sel = IdNameSelect.getAdmregionSelect();
		new DBExecutor(DBConfig.getConnectionFactory()).execute(sel);
		return sel.getReuslt();
	}
	/**Load unknown city*/
	static List<INameIdParent> loadCityUnknown(){
		IdNameSelect<INameIdParent> sel = IdNameSelect.getCityUnknown();
		new DBExecutor(DBConfig.getConnectionFactory()).execute(sel);
		return sel.getReuslt();
	}
	
	/***
	 * Try to find a street for city by street name expression!!!
	 * @param cityId
	 * @param streetExpression
	 * @return
	 */
	static List<INameIdParent> loadStreet(long cityId, String streetExpression){
		IdNameSelect<INameIdParent> sel = IdNameSelect.getStreetSelect(cityId, streetExpression);
		new DBExecutor(DBConfig.getConnectionFactory()).execute(sel);
		return sel.getReuslt();
	}
	
	/***
	 * Loads city & street as a parent child by city code & street code!
	 * @param cityCode
	 * @param streetCode
	 * @return
	 */
	static List<INameIdParent> loadStreetByStreetCityCode(String cityCode, String streetCode){
		IdNameSelect<INameIdParent> sel = IdNameSelect.getCityStreetSelectByCityStreetCode(cityCode, streetCode);
		new DBExecutor(DBConfig.getConnectionFactory()).execute(sel);
		return sel.getReuslt();
	}

	/***
	 * Load city admin regions map
	 * @return
	 */
	static Map<Long, CityAdminRegions> loadCityAdminRegions(){
		//init city admin regions
		AdminRegionSelect sel = new AdminRegionSelect();
		new DBExecutor(DBConfig.getConnectionFactory()).execute(sel);
		return CityAdminRegions.fromAdminRegions(sel.result);
	}
	
	/***
	 * Load RDVR 
	 * @return
	 */
	static List<Rdvr> loadRdvr(){
		RdvrSelect sel = new RdvrSelect();
		new DBExecutor(DBConfig.getConnectionFactory()).execute(sel);
		return sel.getResult();
	}
	
	/***
	 * Loads provinces names & code from property file
	 */
	static Map<String, ProvinceCodes> loadProvincePropertiesMap(){
		//load admin region map
		Map<String, ProvinceCodes> provinceNameCodeMap = new HashMap<String, ProvinceCodes>();
		ResourceBundle pMap = Bundles.provinceMap;
		//provinceNameCodeMap.clear();
		Enumeration<String> e =  pMap.getKeys();
		while(e.hasMoreElements()){
			String key = e.nextElement();
			provinceNameCodeMap.put(key, ProvinceCodes.fromPropertyString(pMap.getString(key)));
		}
		return provinceNameCodeMap;
	}
	

	static INameIdParent loadStreet(long streetId){
		  IdNameSelect<INameIdParent>  sel = IdNameSelect.getStreetSelect(streetId);
		  new DBExecutor(DBConfig.getConnectionFactory()).execute(sel);
		  return   sel.getReuslt().size() == 0 ? null : sel.getReuslt().get(0);
	}
	
	/**Load country BG*/
	static INameIdParent loadBGCountry(){
		  IdNameSelect<INameIdParent>  sel = IdNameSelect.getBulgarianSelect();
		  new DBExecutor(DBConfig.getConnectionFactory()).execute(sel);
		  return   sel.getReuslt().size() == 0 ? null : sel.getReuslt().get(0);
	}
	
	/***
	 * Load grao cities that are admin regions 
	 * @return
	 */
	/*static List<GraoCity>  loadGraoAdminRegionCities(){
		SelectSqlStatementGeneric<GraoCity> sel =  IdNameSelect.getGraoAdminRegionsSelect();
		new DBExecutor(DBConfig.getConnectionFactory()).execute(sel);
		return sel.getResult();
	}
	*/
	/***
	 * Gets the ids of ltf cities found under graoName
	 * @param graoCityName
	 * @return
	 */
	static String loadCityIds(String graoCityName, boolean verbose){
		CityIds cids = StoredProcedures.getCityIds(graoCityName, verbose);
		new DBExecutor(DBConfig.getConnectionFactory()).execute(cids);
		return cids.result;
	}
	
	/***
	 * 
	 * @param graoCityName
	 * @param graoMunicipalityName
	 * @param verbose
	 * @return
	 */
	static String loadCityIds(String graoCityName, String graoMunicipalityName, boolean verbose){
		CityIds cids = StoredProcedures.getCityIds(graoCityName, graoMunicipalityName, verbose);
		new DBExecutor(DBConfig.getConnectionFactory()).execute(cids);
		return cids.result;
	}
	
	
	/***
	 * Gets the ids of ltf cities found under graoName!!!
	 * @param graoCityName
	 * @return
	 */
	static String loadCityIds(String graoCityName){
		return loadCityIds(graoCityName, false);
	}
	
	
	static String loadCityIds(String graoCityName,  String graoMunicipalityName){
		return loadCityIds(graoCityName, graoMunicipalityName,  false);
	}
	
	/***
	 *
	 * @param graoCityName
	 * @param graoStreetName
	 * @param verbose
	 * @return
	 */
	static String loadCityStreetIds(String graoCityName, String graoStreetName,boolean verbose){
		CityStretIds cids = StoredProcedures.getCityStreetIds(graoCityName, graoStreetName, verbose);
		new DBExecutor(DBConfig.getConnectionFactory()).execute(cids);
		return cids.result;
	}
	
	static String loadCityStreetIds(String graoCityName, String graoStreetName){
		return loadCityStreetIds(graoCityName, graoStreetName, false);
	}
	
	static String loadCityStreetIds(long cityId, String graoStreetName ,boolean verbose){
		CityStretIds cids = StoredProcedures.getCityStreetIds(cityId, graoStreetName, verbose);
		new DBExecutor(DBConfig.getConnectionFactory()).execute(cids);
		return cids.result;
	}
	
	static String loadMunicipalityProvinceIds(String graomunicipalityName, boolean verbose){
		MunicipalityProvinceIds cids = StoredProcedures.getMunicipalityProvinceIds(graomunicipalityName, verbose);
		new DBExecutor(DBConfig.getConnectionFactory()).execute(cids);
		return cids.result;
	}
	
	static String loadCountryId(String graoCountryName, boolean verbose){
		Countryid cids = StoredProcedures.getCountryId(graoCountryName, verbose);
		new DBExecutor(DBConfig.getConnectionFactory()).execute(cids);
		return cids.result;
	}
	
	static String loadCityStreetIds(long cityId, String graoStreetName){
		return loadCityStreetIds(cityId, graoStreetName, false);
	}
	
	static String loadMunicipalityProvinceIds(String graomunicipalityName){
		return loadMunicipalityProvinceIds(graomunicipalityName, false);
	}
	
	static String loadCountryId(String graoCountryName){
		return loadCountryId(graoCountryName, false);
	}
	
	
	
}
