
package net.is_bg.ltf.grao.ltf;

import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import grao.integration.ltf.address.structure.IAddress;
import grao.integration.ltf.address.structure.INameId;
import grao.integration.ltf.address.structure.INameIdParent;
import grao.integration.structure.iface.IAddressResolver;
import grao.integration.structure.iface.IGraoAddress;
import grao.integration.structure.impl.GraoUtils;
import net.is_bg.ltf.grao.ltf.Constants.MATCHING_LEVEL;
import net.is_bg.ltf.grao.ltf.Constants.QUALIFIER_MAPPINGS;


class AddressTree implements IAddressResolver {
		private boolean loaded;
		private List<String> cityQulifiers;          
		private List<String> streetQualifiers;
		private List<String> admregionQualifiers;
		
		//lists 
		private List<INameIdParent> countries;
		private List<INameIdParent> provinces;
		private List<INameIdParent> municipalities;
		private List<INameIdParent> cities;
		private List<AdminRegion>   admregions;
		
		//tree maps
		private IdObjectTreeMap<? extends INameIdParent> countriesMap;
		private IdObjectTreeMap<? extends INameIdParent> provincesMap;
		private IdObjectTreeMap<? extends INameIdParent> municipalitiesMap;
		private IdObjectTreeMap<? extends INameIdParent> citiesMap;
		private IdObjectTreeMap<AdminRegion> admregionsMap;
		private IdObjectTreeMap<? extends INameId> cityUnknown; 
		
		/***Hash map that has key province Name as id FROM  grao and value  3 letter province code*/
		private Map<String, ProvinceCodes> provinceNameCodeMap = new HashMap<String, ProvinceCodes>();
		
		//map that has city id as key & admin regions as value
		private Map<Long, CityAdminRegions>  cityAdminRegions;
		private RdvrAccess rdvrAccess;
		
		//ids of cities that have admin regions -these are cities ekatte
		final static long SOFIA_CITY_ID = 68134;
		final static long PLOVDIV_CITY_ID = 56784;
		final static long VARNA_CITY_ID = 10135;
		private INameIdParent BG_COUNTRY;
		
		//a map with id s & names  of sofia plovdiv & varna
		private static Map<Long, String>  sfPldVarnaidNameMap = new HashMap<Long, String>(); 
		private GraoCities graoCities;
		
		private AddressTree() {
		}
		
		public static AddressTree getAddressResolver() {
			AddressTree atree =	new AddressTree();
			atree.loadAddressTree();
			return atree;
		}
	
		/**loads necessary tree maps*/
		private void loadAddressTree(){
			long times = new Date().getTime();
			
			//init bundles
			Bundles.load();
			
			//
			sfPldVarnaidNameMap.put(SOFIA_CITY_ID, Bundles.admRegionCities.getString(String.valueOf(SOFIA_CITY_ID)));
			sfPldVarnaidNameMap.put(PLOVDIV_CITY_ID, Bundles.admRegionCities.getString(String.valueOf(PLOVDIV_CITY_ID)));
			sfPldVarnaidNameMap.put(VARNA_CITY_ID, Bundles.admRegionCities.getString(String.valueOf(VARNA_CITY_ID)));
			
			//init cities from previously loaded table in data base
			graoCities = new GraoCities();
			graoCities.initGraoCities();
			
			//init provinces codes map
			LoadingUtils.loadProvincePropertiesMap();
		    BG_COUNTRY = LoadingUtils.loadBGCountry();
		    if(BG_COUNTRY == null)  throw new IllegalStateException("BG_COUNTRY is not found... ");
			
			//init rdvrs
			rdvrAccess = RdvrAccess.fromRdvrs(LoadingUtils.loadRdvr());
			
			//load city admin regions map - key is the city Id & value is  AdminRegion
			cityAdminRegions = LoadingUtils.loadCityAdminRegions();
			
			//get upper case qualifiers
			cityQulifiers = QUALIFIER_MAPPINGS.CITY_MAPPINGS.getMappingsListUpperCase();
			streetQualifiers =  QUALIFIER_MAPPINGS.STREET_MAPPINGS.getMappingsListUpperCase();
			admregionQualifiers =  QUALIFIER_MAPPINGS.ADMREGION_MAPPINGS.getMappingsListUpperCase();
			
			//lists - load countries, provinces, cities, admregions
			countries = LoadingUtils.loadCountries();
			provinces = LoadingUtils.loadProvinces();
			municipalities = LoadingUtils.loadMunicipalities();
			cities = LoadingUtils.loadCities();
			admregions = LoadingUtils.loadAdmregions();
			
			
			//construct tree maps from lists
			countriesMap = IdObjectTreeMap.listToTreeMap(countries);
			provincesMap = IdObjectTreeMap.listToTreeMap(provinces);
			municipalitiesMap = IdObjectTreeMap.listToTreeMap(municipalities);
			citiesMap = IdObjectTreeMap.listToTreeMap(cities);
			admregionsMap = IdObjectTreeMap.listToTreeMap(admregions);
			
			
			//load city unknown 
			List<? extends INameIdParent> unknownCityList = LoadingUtils.loadCityUnknown();
			cityUnknown = (unknownCityList.size() > 0 && unknownCityList.get(0)!=null ) ? citiesMap.getElementdByKey(unknownCityList.get(0).getParentId()) : null;
			if(cityUnknown == null)  throw new IllegalStateException("City unknown is not found city with id  does  not exists... ");
					
			constructAddressTree();
			
			System.out.println("=================== " + rdvrAccess.getSize() + " RDVRS loaded =====================\n");
			System.out.println(getLoadedRdvr());
			System.out.println("\n");
			
			System.out.println("======== "+ countries.size() + " COUNTRIES loaded ======");
			System.out.println(getLoadedCountries());
			System.out.println("\n");
			
			System.out.println("======== " + provinces.size() + " PROVINCES loaded ======");
			System.out.println(getLoadedProvinces());
			System.out.println("\n");
			
			
			System.out.println("======== " + municipalities.size() + " MUNICIPLAITIES loaded ======");
			System.out.println(getLoadedMunicipalities());
			System.out.println("\n");
			
			System.out.println("======== " + cities.size() + " CITIES loaded ======");
			System.out.println(getLoadedCities());
			System.out.println("\n");
			
			System.out.println("======== " + provinceNameCodeMap.values().size() + " PROVINCE LATIN CODES loaded ======");
			System.out.println(getProvinceCodes());
			System.out.println("\n");
			
			System.out.println("======== " + streetQualifiers.size() + " STREET QUALIFIERS loaded ======");
			System.out.println(getStreetQualifiers());
			System.out.println("\n");
			
			System.out.println("======== " + cityQulifiers.size() + " CITY QUALIFIERS loaded ======");
			System.out.println(getCityQualifiers());
			System.out.println("\n");
			
			System.out.println("======== " + admregionQualifiers.size() + " REGION QUALIFIERS   loaded ======");
			System.out.println(getAdmregionQualifiers());
			System.out.println("\n");
			
			loaded = true;
			System.out.println("Address tree maps initialized... for " + (new Date().getTime() - times) + " milliseconds");
		}
		
		
		//interface for providing info about loaded stuff
		String getLoadedRdvr(){
			return rdvrAccess.toString();
		}
		
		String getLoadedAdminRegions(){
			return GraoUtils.listToString(admregions);
		}
		
		String getLoadedCountries(){
			return GraoUtils.listToString(countries);
		}
		
		String getLoadedProvinces(){
			return GraoUtils.listToString(provinces);
		}
		
		String getLoadedMunicipalities(){
			return GraoUtils.listToString(municipalities);
		}
		
		String getLoadedCities(){
			return GraoUtils.listToString(cities);
		}
		
		//qualifiers
		String getProvinceCodes(){
			StringBuilder bd =new StringBuilder();
			for(ProvinceCodes ps: provinceNameCodeMap.values()){ bd.append(ps);  bd.append("\n");}
			return bd.toString();
		}
		
		String getStreetQualifiers(){
			return streetQualifiers.toString();
		}
		
		String getCityQualifiers(){
			return cityQulifiers.toString();
		}
		String getAdmregionQualifiers(){
			return admregionQualifiers.toString();
		}
		
		
		
		/**if address is invalid this means there is missing province municipality or city  - try to find country abroad!!!*/
		private IdObjectTreeMap<? extends INameIdParent>  resolveCountry(IGraoAddress graoaddress){
			//there must be a country in the city field
			if(graoaddress == null || GraoUtils.nulling(graoaddress.getCity()) == null) return null;
			
			
			
			
			//check in countries map
			return countriesMap.getElementByName(graoaddress.getCity());
		}

		
		
		/***
		 * Try to extract admin region for city by municipalityName!!!
		 * @param graoMuniciplaityName
		 * @return
		 */
		private AdminRegion resolveAdminRegion(long cityId, String regionNameSt){
			if(regionNameSt == null || regionNameSt.equals("")) return null;
			String regionCode = Utils.extractAdmRegionCode(regionNameSt);
			//System.out.println("region code for " + regionNameSt + " is " + regionCode);
			if(regionCode != null)regionCode = regionCode.length() < 2 ? "0" + regionCode : regionCode;
			String name = Utils.extractAdmRegionName(regionNameSt, admregionQualifiers);
			//System.out.println("region name for " + regionNameSt + " is " + name);
			//cityAdminRegions.get(cityId); 
			//load admin region by admin region code & cityId
			if(name == null) return null;
			CityAdminRegions cr = cityAdminRegions.get(cityId);  if(cr ==null) return null;
			return cr.getAdminRegionByCode(regionCode);
		}
		
		
		
		/***
		 * Resolves grao address to a minimized ltf address!!!
		 * @param graoAddress
		 * @return
		 */
		public IAddress resolveAddress(IGraoAddress graoAddress){
			if(!loaded)  throw new IllegalStateException(" Tree maps are not loaded... ");
			
			//convert address properties to upper case!!!
			graoAddress = GraoUtils.toUpperCase(graoAddress);
			LtfAddress adr = new LtfAddress();
			
			IdObjectTreeMap<? extends INameIdParent> city = cityUnknown;
			adr.streetNumber = graoAddress.getStreetNumber();
			adr.entrance = graoAddress.getEntrance();
			adr.floor = graoAddress.getFloor();
			adr.apartment = graoAddress.getApartment();
			
			//check address validity level
			boolean bValidStreet, bValidCity, bValidMunicipality, bValidProvince;
			bValidCity = GraoUtils.isValidAddressCity(graoAddress);
			bValidStreet = GraoUtils.isValidAddressStreet(graoAddress);
			bValidMunicipality = GraoUtils.isValidAddressMunicipality(graoAddress);
			bValidProvince = GraoUtils.isValidAddressProvince(graoAddress);
			
			//valid up to street including street
			if(bValidCity && bValidStreet){
				//resolve street & city by direct match
				INameIdParent cityStreet = resolveCityStreet(graoAddress.getCity(), graoAddress.getStreet());
				if(cityStreet!= null){
					fillAddressByStreetIdCityId(cityStreet.getId(), cityStreet.getParentId(), adr);
					fillAdminRegion(graoAddress.getCity(), graoAddress.getMunicipality(), cityStreet.getId(), adr);
					return adr;
				}
				
				//try to find only city
				MatchingLevelResult cityMatchLevel =  resolveCityByPatterns(graoAddress.getCity(), graoAddress.getMunicipality());
				String res = cityMatchLevel.result;
				if(res!=null &&!StoredProcedures.hasLineSeparator(res)){
					INameIdParent p = StoredProcedures.strToNameIdParent(res);
					fillAddressByCityId(p.getId(), adr);
					fillAdminRegion(graoAddress.getCity(), graoAddress.getMunicipality(), p.getId(), adr);
					return adr;
				}
				
				//try to find street
				System.out.println("City Street " + cityStreet);
			}
			
			if(bValidCity){ //valid city
				//resolve only city
				MatchingLevelResult cityMatchLevel =  resolveCityByPatterns(graoAddress.getCity(), graoAddress.getMunicipality());
				System.out.println("City matchLevel " + cityMatchLevel.level + " result is " + cityMatchLevel.result);
			}
			
			
			if(bValidMunicipality){
				//resolve municipality
				MatchingLevelResult cityMatchLevel = resolveMunicipality(graoAddress.getMunicipality());
				System.out.println("Municipality matchLevel " + cityMatchLevel.level + " result is " + cityMatchLevel.result);
			}
			if(bValidProvince){
				//resolve province
				INameIdParent province = resolveProvince(graoAddress.getProvince());
				System.out.println("Province " + province );
			}
			
			return adr;
		}
		
		/***
		 * try to fill admin region if city has admin region
		 * @param graocityName
		 * @param cityId
		 * @param address
		 */
		private void fillAdminRegion(String graocityName, String graoProvinceName, long cityId, LtfAddress address) {
			if(hasAdmRegion(graocityName)){
			   AdminRegion reg  = resolveAdminRegion(cityId, graoProvinceName);
			   if(reg != null) {
				   address.adminRegion.id = reg.id;
				   address.adminRegion.name = reg.name;
			   }
			}
		}
		
		
		private void fillAddressByStreetIdCityId(long streetId, long cityId, LtfAddress addressTofill){
			if(addressTofill == null) return ;
			addressTofill.street.id = streetId;
			addressTofill.street.parentId = cityId;
			
			INameIdParent p = LoadingUtils.loadStreet(streetId);
			if(p!=null) addressTofill.street.name = p.getName();
			//fill city
			fillAddressByCityId(cityId, addressTofill);
		}
		
		
		/***
		 * fills in  the addressTofill argument (city, municipality, province & country id)
		 * @param cityId
		 * @param addressTofill
		 */
		private void fillAddressByCityId(long cityId, LtfAddress addressTofill){
			if(addressTofill == null) return ;
			IdObjectTreeMap<? extends INameIdParent> city =  citiesMap.getElementdByKey(cityId);
			if(city != null && city.getData() != null ){
				//fill city
				addressTofill.city.id =  city.getData().getId();
				addressTofill.city.name = city.getData().getName();
				if(city.getData().getParentId() > 0){
					//fill municipality
					fillAddressByMunicipalityId(city.getData().getParentId(), addressTofill);
				}
			}
		}
		
		
		private void fillAddressByMunicipalityId(long municipalityId, LtfAddress addressTofill){
			if(addressTofill == null) return ;
			//fill municipality
			IdObjectTreeMap<? extends INameIdParent> muni = municipalitiesMap.getElementdByKey(municipalityId);
			if(muni != null && muni.getData() !=null){
				addressTofill.municipality.id = muni.getData().getId();
				addressTofill.municipality.name = muni.getData().getName();
			}
			
			//fill province
			if(muni.getData().getParentId() > 0){
				fillAddressByProvinceId(muni.getData().getParentId(), addressTofill);
			}
		}
		
		
		private void fillAddressByProvinceId(long provinceId, LtfAddress addressTofill){
			if(addressTofill == null) return ;
			IdObjectTreeMap<? extends INameIdParent> province = provincesMap.getElementdByKey(provinceId);
			if(province !=null && province.getData() != null){
				addressTofill.province.id = province.getData().getId();
				addressTofill.province.name = province.getData().getName();
			}
			
			//fill country
			if(province.getData().getParentId() > 0){
				IdObjectTreeMap<? extends INameIdParent> country = countriesMap.getElementdByKey(province.getData().getParentId());
				if(country !=null &&  country.getData() != null) addressTofill.country.id = country.getData().getId();
			}
		}
		
		
		/***
		 * Fills in  the addressTofill argument (admregion, city, municipality, province & country id)
		 * @param cityId
		 * @param addressTofill
		 */
		private void fillAddressByAdmRegionId(long admRegionId, LtfAddress addressTofill){
			if(addressTofill == null) return ;
			IdObjectTreeMap<? extends INameIdParent> admreg =  admregionsMap.getElementdByKey(admRegionId);
			if(admreg!= null && admreg.getData()!=null){
				addressTofill.adminRegion.id = admreg.getData().getId();
				addressTofill.adminRegion.parentId = admreg.getData().getParentId();
				long cityId = admreg.getData().getParentId();
				
				//fills properties from city to country
				fillAddressByCityId(cityId, addressTofill);
			}
		}
		
		
		/***
		 * Resolve admin region for city id & address
		 * @param cityId
		 * @param graoAddress
		 * @return
		 */
		private AdminRegion resolveAdminRegion(long cityId, IGraoAddress graoAddress){
			String cityName = graoAddress.getCity();
			
			//check for admin region for city
			AdminRegion admr = (cityId == SOFIA_CITY_ID) ? resolveSofiaAdminRegion(graoAddress.getMunicipality()) :  resolveAdminRegion(cityId, graoAddress.getMunicipality());
			
			//try to resolve admin region for city name 
			if(admr == null)  admr = (cityId == SOFIA_CITY_ID) ? resolveSofiaAdminRegion(cityName):resolveAdminRegion(cityId, cityName);
			
			return admr;
		}
		
		/***
		 * Resolve sofia admin region
		 * @param name
		 * @return
		 */
		private AdminRegion resolveSofiaAdminRegion(String name){
			//load admin regions that are united
			AdminRegion nadejda = CityAdminRegions.getAdminRegionNadejda();
			AdminRegion sredets = CityAdminRegions.getAdminRegionSredets();
			AdminRegion triadica = CityAdminRegions.getAdminRegionTriaditsa();
			AdminRegion vrabnica = CityAdminRegions.getAdminRegionVrabnitsa();
			
			if(CityAdminRegions.isVarbnitsa(name)) return vrabnica;
			if(CityAdminRegions.isNadejda(name)) return nadejda;
			if(CityAdminRegions.isSredets(name)) return sredets;
			if(CityAdminRegions.isTriaditsa(name)) return triadica;
			
			return null;
		}
		
		
		
	    /***
	     * Try to find city by cityName in the graocity table
		 * & return the matching city id from city table! If municipality is null or empty the search is based only on cityName
	     * @param graoCityName
	     * @param graoMunicipalityName
	     * @return
	     */
	    private	MatchingLevelResult resolveCity(String graoCityName, String graoMunicipalityName){
 			MatchingLevelResult mres = new MatchingLevelResult();
 			graoMunicipalityName = GraoUtils.safeTrim(GraoUtils.nulling(graoMunicipalityName));
 			
 			for(MATCHING_LEVEL l : MATCHING_LEVEL.values()){
 				if(l == MATCHING_LEVEL.NOT_FOUND)break;
 				mres.result  =  (graoMunicipalityName == null) ? LoadingUtils.loadCityIds(l.addPercentSign(graoCityName)) : LoadingUtils.loadCityIds(l.addPercentSign(graoCityName), graoMunicipalityName);
 				mres.level = l;
 				if(mres.result!= null) return mres;
 			}
 			return mres;
	 	}
	    
	    /***
	     * Try to resolve municipality by graoMunicipalityName!!!
	     * @param graoMunicipalityName
	     * @return
	     */
	    private MatchingLevelResult resolveMunicipality(String graoMunicipalityName){
	    	MatchingLevelResult mres = new MatchingLevelResult();
 			graoMunicipalityName = GraoUtils.safeTrim(GraoUtils.nulling(graoMunicipalityName));
 			
 			for(MATCHING_LEVEL l : MATCHING_LEVEL.values()){
 				if(l == MATCHING_LEVEL.NOT_FOUND)break;
 				mres.result  =   LoadingUtils.loadMunicipalityProvinceIds(l.addPercentSign(graoMunicipalityName));
 				mres.level = l;
 				if(mres.result!= null) return mres;
 			}
 			return mres;
	    }
	    
	    
	    
	    /***
	     * Try to find city with municipality
	     * Try to find city without municipality 
	     * Try to find city without qualifier
	     * @param graoCityName
	     * @param graoMunicipalityName
	     * @return
	     */
	    private MatchingLevelResult resolveCityByPatterns(String graoCityName, String graoMunicipalityName){
	    	MatchingLevelResult  mres = new MatchingLevelResult();
	    	
	    	//try to find city by city name & municipalityName first directly
	    	mres = resolveCity(graoCityName, graoMunicipalityName);
	    	if(mres.result !=null) return mres;
	    	
	    	//ignore municipality pattern & search again
	    	mres = resolveCity(graoCityName, null);
	    	if(mres.result !=null) return mres;
	    	
	    	//ignore city qualifier pattern & search again
	    	graoCityName = Utils.removeQualifier(graoCityName, QUALIFIER_MAPPINGS.CITY_MAPPINGS.getMappingsListUpperCase(), "1").getName();
	    	mres = resolveCity(graoCityName, graoMunicipalityName);
	    	if(mres.result !=null) return mres;
	    	
	    	//finally ignore municipality
	    	mres = resolveCity(graoCityName, null);
	    	if(mres.result !=null) return mres;
	    	
	    	return mres;
	    }
	    
		/***
		 * Try to find both street name & city name all together in grao tables.
		 * The most optimistic search performed first if there is valid street name in address!
		 * @param graoCityName
		 * @param graoStreetName
		 * @return
		 */
		private INameIdParent resolveCityStreet(String graoCityName, String graoStreetName){
			graoCityName = Utils.trimWhiteSpacesBetweenNameQuailifier(graoCityName, QUALIFIER_MAPPINGS.CITY_MAPPINGS.getMappingsListUpperCase());
			graoStreetName = Utils.trimWhiteSpacesBetweenNameQuailifier(graoStreetName, QUALIFIER_MAPPINGS.STREET_MAPPINGS.getMappingsListUpperCase());
			String res =  LoadingUtils.loadCityStreetIds(graoCityName, graoStreetName);
			if(res == null) return null;
			return StoredProcedures.strToNameIdParent(res);
		}
		
	    
	    /**
	     * Try to find street for cityId by graoStreetName!
	     * @param cityId
	     * @param graoStreetName
	     * @return
	     */
	    private	MatchingLevelResult resolveStreet(long cityId, String graoStreetName){
 			MatchingLevelResult mres = new MatchingLevelResult();
 			graoStreetName = GraoUtils.safeTrim(GraoUtils.nulling(graoStreetName));
 			
 			for(MATCHING_LEVEL l : MATCHING_LEVEL.values()){
 				if(l == MATCHING_LEVEL.NOT_FOUND)break;
 				mres.result = LoadingUtils.loadCityStreetIds(cityId, (l.addPercentSign(graoStreetName)));
 				mres.level = l;
 				if(mres.result!= null) return mres;
 			}
 			return mres;
	    }
	    
	    /**
	     * Try to find street in city with street qualifier first.
	     * If not found try searching without qualifier.
	     * @param cityId
	     * @param graoStreetName
	     * @return
	     */
	    @SuppressWarnings("unused")
		private MatchingLevelResult resolveStreetByPatterns(long cityId, String graoStreetName){
	    	MatchingLevelResult  mres = new MatchingLevelResult();
	    	
	    	//try to find street by graoStreetName
	    	mres = resolveStreet(cityId, graoStreetName);
	    	if(mres.result !=null) return mres;
	    	
	    	//ignore street qualifier pattern & search again
	    	graoStreetName = Utils.removeQualifier(graoStreetName, QUALIFIER_MAPPINGS.STREET_MAPPINGS.getMappingsListUpperCase(), "1").getName();
	    	mres = resolveStreet(cityId, graoStreetName);
	    	if(mres.result !=null) return mres;
	    	
	    	return mres;
	    }
	    
	    
	    /***
	     * Loads country ids by graoCountry name!
	     * @param graoCountryName
	     * @return
	     */
	    MatchingLevelResult resolveCountry(String graoCountryName){
	    	MatchingLevelResult mres = new MatchingLevelResult();
	    	graoCountryName = GraoUtils.safeTrim(GraoUtils.nulling(graoCountryName));
 			
 			for(MATCHING_LEVEL l : MATCHING_LEVEL.values()){
 				if(l == MATCHING_LEVEL.NOT_FOUND)break;
 				mres.result =  LoadingUtils.loadCountryId(l.addPercentSign(graoCountryName));
 				mres.level = l;
 				if(mres.result!= null) return mres;
 			}
 			return mres;
	    }
	    
	    
	    
	    /***
	     * Try to resolve multiple city matches!
	     * @param matches
	     * @param graoMunicipality
	     * @return
	     */
	    private INameIdParent resolveMultipleCityMatch(List<INameIdParent> matches, String graoMunicipality){
	    	INameIdParent r = new NameIdParent();
	    	
	    	for(INameIdParent c : matches){
	    		
	    	}
	    	return r;
	    }
	    
	    
	    /***
	     * Try to find  province by grao province name!!!
	     * @param graoProvinceName
	     * @return
	     */
	    private INameIdParent resolveProvince(String graoProvinceName){
	    	INameIdParent r = new NameIdParent();
	    	graoProvinceName = trimProvince(graoProvinceName);
	    	if(graoProvinceName == null) return r;
	    	for(INameIdParent p: provinces) {
	    		if(p.getName().contains(graoProvinceName) || graoProvinceName.contains(p.getName())){
	    			return p;
	    		}
	    	}
	    	return r;
	    }
	    
	    /**
	     * If province contains TARNOVO or  ZAGORA
	     * province name is set to either TARNOVO or ZAGORA
	     * @param graoProvinceName
	     * @return
	     */
	    private String trimProvince(String graoProvinceName){
	    	if(GraoUtils.nulling(graoProvinceName) == null) return graoProvinceName;
	    	Enumeration<String> keys = Bundles.twoWordProvinces.getKeys();
	    	while(keys.hasMoreElements()){
	    		 String val = Bundles.twoWordProvinces.getString(keys.nextElement());
	    		 if(graoProvinceName.contains(val)) return val;
	    	}
	    	return graoProvinceName;
	    }
	    
	
		/**/
		private boolean isSofiaCity(String cityName){
			return cityName.contains(sfPldVarnaidNameMap.get(SOFIA_CITY_ID));
		}
		
		private boolean isPlovdivCity(String cityName){
			return cityName.contains(sfPldVarnaidNameMap.get(PLOVDIV_CITY_ID));
		}
		
		private boolean isVarnaCity(String cityName){
			return cityName.contains(sfPldVarnaidNameMap.get(VARNA_CITY_ID));
		}
		
		/**
		 * Check if sity is PLD SF or VARNA
		 * @param cityName
		 * @return
		 */
		private boolean hasAdmRegion(String cityName) {
			if(cityName == null) return false;
			return isSofiaCity(cityName) || isPlovdivCity(cityName) || isVarnaCity(cityName);
		}
		
		private void constructAddressTree(){
			//CONSTRUCT TREE
			//tie admregions to cities
			/*for(IdObjectTreeMap<? extends INameIdParent> admregion : admregionsMap.getChildren()){
				IdObjectTreeMap<? extends INameIdParent> parent = citiesMap.getElementdByKey(admregion.getData().getParentId());
			    if(parent!=null) parent.addChild(admregion);
			}
			
			//tie cities to municipalities
			for(IdObjectTreeMap<? extends INameIdParent> city : citiesMap.getChildren()){
				IdObjectTreeMap<?extends INameIdParent> parent = municipalitiesMap.getElementdByKey(city.getData().getParentId());
			    if(parent!=null) parent.addChild(city);
			}
			
			//tie municipalities to provinces
			for(IdObjectTreeMap<? extends INameIdParent> muni : municipalitiesMap.getChildren()){
				IdObjectTreeMap<? extends INameIdParent> parent = provincesMap.getElementdByKey(muni.getData().getParentId());
				if(parent!=null) parent.addChild(muni);
			}
			
			//tie provinces to countries
			for(IdObjectTreeMap<? extends INameIdParent> admregion : provincesMap.getChildren()){
				IdObjectTreeMap<? extends INameIdParent> parent = countriesMap.getElementdByKey(admregion.getData().getParentId());
			    if(parent!=null) parent.addChild(admregion);
			}*/
		}
		
		
		
		
		/**
		 * Try to find street from data base by city id & cityName
		 * @param cityId
		 * @param name
		 * @return
		 *//*
		private INameIdParent resolveStreet(long cityId, String name){
			if(GraoUtils.nulling(name) == null) return null;
			
			INameIdParent  street = null;
			INameKindId streetNameRemovedQulifier = null;
			
			//remove leading street qualifiers first
			streetNameRemovedQulifier = Utils.removeQualifier(name, streetQualifiers, "1");
			
			//try direct match first
			List<INameIdParent> l = LoadingUtils.loadStreet(cityId, streetNameRemovedQulifier.getName());
			if(l.size() == 1){
				street = l.get(0);
			}
			
			return street;
		}*/
	
	    /*
		private INameIdParent resolveCity(String graoCityName, String graoMunicipalityName){
			MatchingLevelResult mres =	resolveCity(graoCityName);
			if(mres.result == null) mres = resolveCity(Utils.removeQualifier(graoCityName, quantifiers, defaultKind));
			return null;
		}*/
		
		/*
		private IdNameSelect<INameIdParent> resolveCity(String graoCityName){
			graoCityName =	Utils.trimWhiteSpacesBetweenNameQuailifier(graoCityName, QUALIFIER_MAPPINGS.CITY_MAPPINGS.getMappingsListUpperCase());
		}*/
		
		
		
		/*//end of info interface
		*//***
		 * Try to resolve grao city by the city name that is coming straight from grao without modification!!!
		 * @param cityName
		 * @return
		 *//*
		private IdObjectTreeMap<? extends INameIdParent> resolveGraoCity(String cityName, String graoMunicipalityName){
			IdObjectTreeMap<? extends INameIdParent> city =  cityUnknown;
			if(cityName == null) return city;
			
			//String city
			
			
			//check if city is unique & is in database
			if(graoCities.isInDatabaseGraoCityName(cityName)){
				//there is unique city in grao that is present in database  select city id from data base & resolve from city map
				//if(graoCities.isUniqueGraoCityName(cityName) ) return citiesMap.getElementdByKey(LoadingUtils.loadCityIdByCityGraoName(cityName));
				
				//duplicate grao cityname in data base - try to load by city name & municipality name
				if(graoCities.isDuplicateGraoCityName(cityName)){
					List<Long> ll = LoadingUtils.loadCityIdByCityGraoNameGraoMunicipalityName(cityName, graoMunicipalityName);
					if(ll.size() > 0 && ll.get(0) !=null) return citiesMap.getElementdByKey(ll.get(0));
				}
			}
			return city;
		}*/
		
		
}
