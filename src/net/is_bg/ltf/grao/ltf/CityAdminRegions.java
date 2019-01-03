package net.is_bg.ltf.grao.ltf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import grao.integration.structure.impl.GraoUtils;

/**Container for city admin regions*/
class CityAdminRegions implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4058297318041951564L;
	private List<AdminRegion> regions;   // regions for this city
	private Map<String, AdminRegion>  codeAdminRegionMap;   //code , AdminRegion hashMap for this city id
	
	private long cityId;                //the city id of the current admin regions map
	private static String NAD;
	private static String VRB;
	private static String TRS;
	private static String SRD;
	
	private static AdminRegion nadejda;
	private static AdminRegion vrabnitsa;
	
	private static AdminRegion sredets;
	private static AdminRegion triaditsa;
	
	private static void initAdminRegionNames(){
		 NAD =  (NAD == null ? Bundles.unitedAdminRegions.getString("NAD") : NAD);
		 VRB =	(VRB == null ? Bundles.unitedAdminRegions.getString("VRB") : VRB);
		 TRS =	(TRS == null ? Bundles.unitedAdminRegions.getString("TRS") : TRS);
		 SRD =	(SRD == null ? Bundles.unitedAdminRegions.getString("SRD") : SRD);
	}
	
	private CityAdminRegions(){}
	
	/**Transforms admin region list into a map with key city Id & value  CityAdminRegions.*/
	static  Map<Long, CityAdminRegions> fromAdminRegions(List<AdminRegion> regions){
		Map<Long, CityAdminRegions> map = new HashMap<Long, CityAdminRegions>();
		initAdminRegionNames();
		
		for(AdminRegion r :regions){
			CityAdminRegions c;
			
			initUnitedRegions(r);
			
			if(map.containsKey(r.getCityId())) c = map.get(r.getCityId());
			else{
				c= new CityAdminRegions();
				c.regions = new ArrayList<AdminRegion>();
				c.codeAdminRegionMap = new HashMap<String, AdminRegion>();
				c.cityId = r.getCityId();
				map.put(r.getCityId(), c);
			}
			c.regions.add(r);
			c.codeAdminRegionMap.put(r.getCode(), r);
		}
		return map;
	}
	
	private static void initUnitedRegions(AdminRegion r){
		if(r.name == null) return;
		if( r.name.contains(NAD)) nadejda = r;
		if( r.name.contains(VRB)) vrabnitsa = r;
		if( r.name.contains(SRD)) sredets = r;
		if( r.name.contains(TRS)) triaditsa = r;
		
	}
	
	/**This method is intended to be called if region is not found by code 
	 * This will be the case for united admin regions as Sredets - triaditsa & nadejda - vrabnitsa
	 * **/
	AdminRegion getAdminRegionByName(String name){
		 AdminRegion region= null;
		 for(AdminRegion r: codeAdminRegionMap.values()){
			 r.getName().contains(name);
			 region = r;
			 break;
		 }
		 return region;
	}
	
	long getCityId() {
		return cityId;
	}
	
	/**
	 * Returns admin region for city by Admin region code!
	 * @return
	 */
	AdminRegion getAdminRegionByCode(String adminRegionCode){
		return codeAdminRegionMap.get(adminRegionCode);
	}
	
	@Override
	public String toString() {
		return GraoUtils.listToString(regions);
	}
	
	static AdminRegion getAdminRegionSredets(){
		return sredets;
	}
	
	static AdminRegion getAdminRegionNadejda(){
		return nadejda;
	}
	
	static AdminRegion getAdminRegionTriaditsa(){
		return triaditsa;
	}
	static AdminRegion getAdminRegionVrabnitsa(){
		return vrabnitsa;
	}
	
	static boolean isNadejda(String name){
		if(name == null) return false;
		if(name.contains(Bundles.unitedAdminRegions.getString("NAD"))) return true;
		return false;
	}
	
	static boolean isSredets(String name){
		if(name == null) return false;
		if(name.contains(Bundles.unitedAdminRegions.getString("SRD"))) return true;
		return false;
	}

	static boolean isTriaditsa(String name){
		if(name == null) return false;
		if(name.contains(Bundles.unitedAdminRegions.getString("TRS"))) return true;
		return false;
	}
	
	static boolean isVarbnitsa(String name){
		if(name == null) return false;
		if(name.contains(Bundles.unitedAdminRegions.getString("VRB"))) return true;
		return false;
	}
}
