package net.is_bg.ltf.grao.ltf;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;



class GraoCities {
	
	Set<String> allGraoCityNames;
	Set<String> uniquGraoCityNames;
	Set<String> duplicateGraoCityNames;
	
	List<GraoCity> graoCities;
	List<GraoCity> graoAdminRegionCities;
	
	//private Map<String, Long>   provinceCodeCityIdMap = new Hashtable<String, Long>();
	//private Map<Long, Map<String, GraoCity>> 
	private Map<String, GraoCity>  adminRegionNameMap = new Hashtable<String, GraoCity>(); 
	//private Map<String, Map<String, GraoCity>> adminRegionProvinceCodeMap = 
	
	
	void initGraoCities(){
		//provinceCodeCityIdMap.put("SOF", AddressTree.SOFIA_CITY_ID);
		//provinceCodeCityIdMap.put("PDV", AddressTree.PLOVDIV_CITY_ID);
		//provinceCodeCityIdMap.put("VAR", AddressTree.VARNA_CITY_ID);
		
		/*graoCities = LoadingUtils.loadGraoCities();
		uniquGraoCityNames = LoadingUtils.loadUniqueGraoCity();
		duplicateGraoCityNames = LoadingUtils.loadDuplicateGraoCity();
		graoAdminRegionCities = LoadingUtils.loadGraoAdminRegionCities();*/
		
		/*for(GraoCity gc : graoAdminRegionCities){
			adminRegionNameMap.put(gc.getCityName(), gc);
		}*/
	}
	
	boolean isUniqueGraoCityName(String name){
		return uniquGraoCityNames.contains(name);
	}
	
	boolean isDuplicateGraoCityName(String name){
		return duplicateGraoCityNames.contains(name);
	}
	
}
