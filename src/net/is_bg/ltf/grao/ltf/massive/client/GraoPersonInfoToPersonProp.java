package net.is_bg.ltf.grao.ltf.massive.client;

import java.io.IOException;

import com.cc.rest.client.Requester.MEDIA_TYPE;
import com.cc.rest.jersey.ObjectMapperProvider;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import grao.integration.IPersonInfoProperties;
import grao.integration.regix.IRegixAddress;
import grao.integration.regix.IRegixGrao;

class GraoPersonInfoToPersonProp  {
	
	
	static IPersonInfoProperties getPersonInfoProperties(String wsjsonString) throws JsonParseException, JsonMappingException, IOException {
		return ObjectMapperProvider.getObjectMapper(MEDIA_TYPE.JSON)
				   .readValue(wsjsonString,
						   PersonProp.class);
	}
	
	
	static String regixGraoToJsonString(IRegixGrao rGrao) throws JsonProcessingException {
		if(rGrao == null) return null;
		PersonProp p = new PersonProp();
		p.setErrorCode("0");
		p.setEin(rGrao.getEin());
		p.setFirstName(rGrao.getFirstName());
		p.setMiddleName(rGrao.getMiddleName());
		p.setLastName(rGrao.getLastName());
		
		IRegixAddress pAdd =  rGrao.getPaddress();
		if(pAdd!=null) {
			p.setPaddressDistrict(pAdd.getDistrictName());
			p.setPaddressDistrictCode(pAdd.getDistrictCode());
			p.setPaddressMunicipality(pAdd.getMunicipalityName());
			p.setPaddressMunicipalityCode(pAdd.getMunicipalityCode());
			p.setPaddressPopulatedPlace(pAdd.getSettlementName());
			p.setPaddressPopulatedPlaceCode(pAdd.getSettlementCode());
			p.setPaddressStreet(pAdd.getLocationName());
			p.setPaddressStreetCode(pAdd.getLocationCode());
			p.setPaddressNumber(pAdd.getBuildingNumber());
			p.setPaddressEntrance(pAdd.getEntrance());
			p.setPaddressFloor(pAdd.getFloor());
			p.setPaddressApartment(pAdd.getApartment());
		}
		IRegixAddress cAdd =  rGrao.getCaddress();
		if(cAdd != null) {
			p.setCaddressDistrict(cAdd.getDistrictName());
			p.setCaddressDistrictCode(cAdd.getDistrictCode());
			p.setCaddressMunicipality(cAdd.getMunicipalityName());
			p.setCaddressMunicipalityCode(cAdd.getMunicipalityCode());
			p.setCaddressPopulatedPlace(cAdd.getSettlementName());
			p.setCaddressPopulatedPlaceCode(cAdd.getSettlementCode());
			p.setCaddressStreet(cAdd.getLocationName());
			p.setCaddressStreetCode(cAdd.getLocationCode());
			p.setCaddressNumber(cAdd.getBuildingNumber());
			p.setCaddressEntrance(cAdd.getEntrance());
			p.setCaddressFloor(cAdd.getFloor());
			p.setCaddressApartment(cAdd.getApartment());
		}
		
		p.setCitizenship(rGrao.getNationality());
		p.setCitizenship2(rGrao.getNationality2());
		p.setIdDocNumber(rGrao.getIdentityDocumentNumber());
		p.setIdDocRPU(rGrao.getIssuerName());
		p.setIdDocRDVR(rGrao.getIssuerName());
		//p.setIdDocDate(idDocDate);
		p.setbAddressPopulatedPlace(rGrao.getBirthPlace());
		p.setDeathDate(rGrao.getDeathDate());
		
		return  ObjectMapperProvider.getObjectMapper(MEDIA_TYPE.JSON).writeValueAsString(p);
	}
	
	public static void main(String [] args) throws IOException {
		System.out.println(regixGraoToJsonString(null));
		String tSt = "{\r\n" + 
				"  \"ERROR_CODE\" : \"0\",\r\n" + 
				"  \"Egn\" : \"6704015690\",\r\n" + 
				"  \"FirstName\" : \"КРАСИМИРА\",\r\n" + 
				"  \"MiddleName\" : \"АЛЕКСАНДРОВА\",\r\n" + 
				"  \"LastName\" : \"ДИМИТРОВА\",\r\n" + 
				"  \"PAddrDistrict\" : \"СЛИВЕН\",\r\n" + 
				"  \"PAddrDistrictCode\" : \"20\",\r\n" + 
				"  \"PAddrMunicipality\" : \"СЛИВЕН\",\r\n" + 
				"  \"PAddrMunicipalityCode\" : \"20\",\r\n" + 
				"  \"PAddrPopulatedPlace\" : \"СЛИВЕН\",\r\n" + 
				"  \"PAddrPopulatedPlaceCode\" : \"67338\",\r\n" + 
				"  \"PAddrStreet\" : \"БУЛ.ПАНАЙОТ ХИТОВ\",\r\n" + 
				"  \"PAddrStreetCode\" : \"02453\",\r\n" + 
				"  \"PAddrNumber\" : \"057А\",\r\n" + 
				"  \"CAddrDistrict\" : \"СЛИВЕН\",\r\n" + 
				"  \"CAddrDistrictCode\" : \"20\",\r\n" + 
				"  \"CAddrMunicipality\" : \"СЛИВЕН\",\r\n" + 
				"  \"CAddrMunicipalityCode\" : \"20\",\r\n" + 
				"  \"CAddrPopulatedPlace\" : \"СЛИВЕН\",\r\n" + 
				"  \"CAddrPopulatedPlaceCode\" : \"67338\",\r\n" + 
				"  \"CAddrStreet\" : \"БУЛ.ПАНАЙОТ ХИТОВ\",\r\n" + 
				"  \"CAddrStreetCode\" : \"02453\",\r\n" + 
				"  \"CAddrNumber\" : \"057А\",\r\n" + 
				"  \"Sitizenship\" : \"БЪЛГАРИЯ\",\r\n" + 
				"  \"IdDocNumber\" : \"645546637\",\r\n" + 
				"  \"IdDocRPU\" : \"СЛИВЕН\",\r\n" + 
				"  \"IdDocRDVR\" : \"СЛИВЕН\",\r\n" + 
				"  \"BAddrPopulatedPlace\" : \"ГР.ТУТРАКАН\"\r\n" + 
				"}";
		
		String tSt1 = "{\"ERROR_CODE\":\"0\",\r\n" + 
				"\"PAddrHash\":\"-1512019372\",\r\n" + 
				"\"CAddrHash\":\"-1512019372\",\r\n" + 
				"\"PersonHash\":\"1158899738\",\r\n" + 
				"\"Egn\":\"6704015690\",\r\n" + 
				"\"FirstName\":\"КРАСИМИРА\",\r\n" + 
				"\"MiddleName\":\"АЛЕКСАНДРОВА\",\r\n" + 
				"\"LastName\":\"ДИМИТРОВА\",\r\n" + 
				"\"PAddrDistrict\":\"СЛИВЕН\",\r\n" + 
				"\"PAddrMunicipality\":\"СЛИВЕН\",\r\n" + 
				"\"PAddrPopulatedPlace\":\"ГР.СЛИВЕН\",\r\n" + 
				"\"PAddrStreet\":\"БУЛ.ПАНАЙОТ ХИТОВ\",\r\n" + 
				"\"PAddrNumber\":\"057А\",\r\n" + 
				"\"CAddrDistrict\":\"СЛИВЕН\",\r\n" + 
				"\"CAddrMunicipality\":\"СЛИВЕН\",\r\n" + 
				"\"CAddrPopulatedPlace\":\"ГР.СЛИВЕН\",\r\n" + 
				"\"CAddrStreet\":\"БУЛ.ПАНАЙОТ ХИТОВ\",\r\n" + 
				"\"CAddrNumber\":\"057А\",\r\n" + 
				"\"Sitizenship\":\"БЪЛГАРИЯ\",\r\n" + 
				"\"IdDocNumber\":\"645546637\",\r\n" + 
				"\"IdDocDate\":\"20141125\",\r\n" + 
				"\"IdDocRDVR\":\"СЛИВЕН\",\r\n" + 
				"\"BAddrPopulatedPlace\":\"ГР.ТУТРАКАН\"\r\n" + 
				"}";
		
		IPersonInfoProperties prr = getPersonInfoProperties(tSt);
		prr = getPersonInfoProperties(tSt1);
		System.out.println(prr);
	}
	
	
	
	static class  PersonProp implements IPersonInfoProperties{
	    private String errorCode;
	    private String errorDescription;
	    private String ein;
	    private String firstName;
	    private String middleName;
	    private String lastName;
	    private String PaddressDistrict;
	    private String PaddressDistrictCode;
	    private String PaddressMunicipality;
	    private String PaddressMunicipalityCode;
	    private String PaddressPopulatedPlace;
	    private String PaddressPopulatedPlaceCode;
	    private String PaddressPopulatedPlaceAreaCode;
	    private String PaddressStreet;
	    private String PaddressStreetCode;
	    private String PaddressNumber;
	    private String PaddressEntrance;
	    private String PaddressFloor;
	    private String PaddressApartment;
	    private String CaddressDistrict;
	    private String CaddressDistrictCode;
	    private String CaddressMunicipality;
	    private String CaddressMunicipalityCode;
	    private String CaddressPopulatedPlace;
	    private String CaddressPopulatedPlaceCode;
	    private String CaddressPopulatedPlaceAreaCode;
	    private String CaddressStreet;
	    private String CaddressStreetCode;
	    private String CaddressNumber;
	    private String CaddressEntrance;
	    private String CaddressFloor;
	    private String CaddressApartment;
	    private String citizenship;
	    private String citizenship2;
	    private String idDocNumber;
	    private String idDocDate;
	    private String idDocRPU;
	    private String idDocRDVR;
	    private String idPopulatedPlace;
	    private String bAddressPopulatedPlace;
	    private String deathDate;
		private String PaddressHash;
		private String CaddressHash;
		private String personHash;
	    

		public PersonProp() {
		super();
	    }
		
		
	    @JsonCreator
	    public PersonProp(@JsonProperty("ERROR_CODE") String errorCode,
			      @JsonProperty("errorDescription") String errorDescription,
			      @JsonProperty("PAddrHash") String pAddrHash,
			      @JsonProperty("CAddrHash") String cAddrHash,
			      @JsonProperty("PersonHash") String personHash,
			      @JsonProperty("Egn") String ein,
			      @JsonProperty("FirstName") String firstName,
			      @JsonProperty("MiddleName") String middleName,
			      @JsonProperty("LastName") String lastName,
			      @JsonProperty("PAddrDistrict") String PaddressDistrict,
			      @JsonProperty("PAddrDistrictCode") String PaddressDistrictCode,
			      @JsonProperty("PAddrMunicipality") String PaddressMunicipality,
			      @JsonProperty("PAddrMunicipalityCode") String PaddressMunicipalityCode,
			      @JsonProperty("PAddrPopulatedPlace") String PaddressPopulatedPlace,
			      @JsonProperty("PAddrPopulatedPlaceCode") String PaddressPopulatedPlaceCode,
			      @JsonProperty("PAddrPopulatedPlaceAreaCode") String PaddressPopulatedPlaceAreaCode,
			      @JsonProperty("PAddrStreet") String PaddressStreet,
			      @JsonProperty("PAddrStreetCode") String PaddressStreetCode,
			      @JsonProperty("PAddrNumber") String PaddressNumber,
			      @JsonProperty("PAddrEntrance") String PaddressEntrance,
			      @JsonProperty("PAddrFloor") String PaddressFloor,
			      @JsonProperty("PAddrAppartment") String PaddressApartment,
			      @JsonProperty("CAddrDistrict") String CaddressDistrict,
			      @JsonProperty("CAddrDistrictCode") String CaddressDistrictCode,
			      @JsonProperty("CAddrMunicipality") String CaddressMunicipality,
			      @JsonProperty("CAddrMunicipalityCode") String CaddressMunicipalityCode,
			      @JsonProperty("CAddrPopulatedPlace") String CaddressPopulatedPlace,
			      @JsonProperty("CAddrPopulatedPlaceCode") String CaddressPopulatedPlaceCode,
			      @JsonProperty("CAddrPopulatedPlaceAreaCode") String CaddressPopulatedPlaceAreaCode,
			      @JsonProperty("CAddrStreet") String CaddressStreet,
			      @JsonProperty("CAddrStreetCode") String CaddressStreetCode,
			      @JsonProperty("CAddrNumber") String CaddressNumber,
			      @JsonProperty("CAddrEntrance") String CaddressEntrance,
			      @JsonProperty("CAddrFloors") String CaddressFloor,
			      @JsonProperty("CAddrAppartment") String CaddressApartment,
			      @JsonProperty("Sitizenship") String citizenship,
			      @JsonProperty("Sitizenship2") String citizenship2,
			      @JsonProperty("IdDocNumber") String idDocNumber,
			      @JsonProperty("IdDocDate") String idDocDate,
			      @JsonProperty("IdDocRPU") String idDocRPU,
			      @JsonProperty("IdDocRDVR") String idDocRDVR,
			      @JsonProperty("IdDocPopulatedPlace") String idPopulatedPlace,
			      @JsonProperty("BAddrPopulatedPlace") String bAddressPopulatedPlace,
			      @JsonProperty("DeathDate") String deathDate) {
		super();
		this.errorCode = errorCode;
		this.ein = ein;
		this.PaddressHash = pAddrHash;
		this.CaddressHash = pAddrHash;
		this.personHash = personHash;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.PaddressDistrict = PaddressDistrict;
		this.PaddressDistrictCode = PaddressDistrictCode;
		this.PaddressMunicipality = PaddressMunicipality;
		this.PaddressMunicipalityCode = PaddressMunicipalityCode;
		this.PaddressPopulatedPlace = PaddressPopulatedPlace;
		this.PaddressPopulatedPlaceCode = PaddressPopulatedPlaceCode;
		this.PaddressPopulatedPlaceAreaCode = PaddressPopulatedPlaceAreaCode;
		this.PaddressStreet = PaddressStreet;
		this.PaddressStreetCode = PaddressStreetCode;
		this.PaddressNumber = PaddressNumber;
		this.PaddressEntrance = PaddressEntrance;
		this.PaddressFloor = PaddressFloor;
		this.PaddressApartment = PaddressApartment;
		this.CaddressDistrict = CaddressDistrict;
		this.CaddressDistrictCode = CaddressDistrictCode;
		this.CaddressMunicipality = CaddressMunicipality;
		this.CaddressMunicipalityCode = CaddressMunicipalityCode;
		this.CaddressPopulatedPlace = CaddressPopulatedPlace;
		this.CaddressPopulatedPlaceCode = CaddressPopulatedPlaceCode;
		this.CaddressPopulatedPlaceAreaCode = CaddressPopulatedPlaceAreaCode;
		this.CaddressStreet = CaddressStreet;
		this.CaddressStreetCode = CaddressStreetCode;
		this.CaddressNumber = CaddressNumber;
		this.CaddressEntrance = CaddressEntrance;
		this.CaddressFloor = CaddressFloor;
		this.CaddressApartment = CaddressApartment;
		this.citizenship = citizenship;
		this.citizenship2 = citizenship2;
		this.idDocNumber = idDocNumber;
		this.idDocDate = idDocDate;
		this.idDocRPU = idDocRPU;
		this.idDocRDVR = idDocRDVR;
		this.idPopulatedPlace = idPopulatedPlace;
		this.bAddressPopulatedPlace = bAddressPopulatedPlace;
		this.deathDate = deathDate;
	    }
	    
	    @JsonProperty("ERROR_CODE")
	    public String getErrorCode() {
		return errorCode;
	    }

	    @JsonProperty("ERROR_CODE")
	    public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	    }

	    @JsonProperty("errorDescription")
	    public String getErrorDescription() {
		return errorDescription;
	    }

	    @JsonProperty("errorDescription")
	    public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	    }
	    
	    @JsonProperty("Egn")
	    public String getEin() {
		return ein;
	    }
	    @JsonProperty("Egn")
	    public void setEin(String ein) {
		this.ein = ein;
	    }

	    @JsonProperty("FirstName")
	    public String getFirstName() {
		return firstName;
	    }

	    @JsonProperty("FirstName")
	    public void setFirstName(String firstName) {
		this.firstName = firstName;
	    }

	    @JsonProperty("MiddleName")
	    public String getMiddleName() {
		return middleName;
	    }

	    @JsonProperty("MiddleName")
	    public void setMiddleName(String middleName) {
		this.middleName = middleName;
	    }

	    @JsonProperty("LastName")
	    public String getLastName() {
		return lastName;
	    }

	    @JsonProperty("LastName")
	    public void setLastName(String lastName) {
		this.lastName = lastName;
	    }

	    @JsonProperty("PAddrDistrict")
	    public String getPaddressDistrict() {
		return PaddressDistrict;
	    }

	    @JsonProperty("PAddrDistrict")
	    public void setPaddressDistrict(String PaddressDistrict) {
		this.PaddressDistrict = PaddressDistrict;
	    }
	    
	    @JsonProperty("PAddrDistrictCode")
	    public String getPaddressDistrictCode() {
			return PaddressDistrictCode;
		}

	    @JsonProperty("PAddrDistrictCode")
		public void setPaddressDistrictCode(String paddressDistrictCode) {
			PaddressDistrictCode = paddressDistrictCode;
		}
	    
	    
	    @JsonProperty("PAddrMunicipality")
	    public String getPaddressMunicipality() {
		return PaddressMunicipality;
	    }

		@JsonProperty("PAddrMunicipalityCode")
		public String getPaddressMunicipalityCode() {
			return PaddressMunicipalityCode;
		}
		
		@JsonProperty("PAddrMunicipalityCode")
		public void setPaddressMunicipalityCode(String paddressMunicipalityCode) {
			PaddressMunicipalityCode = paddressMunicipalityCode;
		}

	    @JsonProperty("PAddrMunicipality")
	    public void setPaddressMunicipality(String PaddressMunicipality) {
		this.PaddressMunicipality = PaddressMunicipality;
	    }

	    @JsonProperty("PAddrPopulatedPlace")
	    public String getPaddressPopulatedPlace() {
		return PaddressPopulatedPlace;
	    }

	    @JsonProperty("PAddrPopulatedPlace")
	    public void setPaddressPopulatedPlace(String PaddressPopulatedPlace) {
		this.PaddressPopulatedPlace = PaddressPopulatedPlace;
	    }
	    
	    @JsonProperty("PAddrPopulatedPlaceCode")
	    public String getPaddressPopulatedPlaceCode() {
			return PaddressPopulatedPlaceCode;
		}

	    @JsonProperty("PAddrPopulatedPlaceCode")
		public void setPaddressPopulatedPlaceCode(String paddressPopulatedPlaceCode) {
			PaddressPopulatedPlaceCode = paddressPopulatedPlaceCode;
		}

	    @JsonProperty("PAddrStreet")
	    public String getPaddressStreet() {
		return PaddressStreet;
	    }

	    @JsonProperty("PAddrStreet")
	    public void setPaddressStreet(String PaddressStreet) {
		this.PaddressStreet = PaddressStreet;
	    }
	    
	    @JsonProperty("PAddrStreetCode")
		public String getPaddressStreetCode() {
			return PaddressStreetCode;
		}

	    @JsonProperty("PAddrStreetCode")
		public void setPaddressStreetCode(String paddressStreetCode) {
			PaddressStreetCode = paddressStreetCode;
		}

	    @JsonProperty("PAddrNumber")
	    public String getPaddressNumber() {
		return PaddressNumber;
	    }
	    @JsonProperty("PAddrNumber")
	    public void setPaddressNumber(String PaddressNumber) {
		this.PaddressNumber = PaddressNumber;
	    }

	    @JsonProperty("PAddrEntrance")
	    public String getPaddressEntrance() {
		return PaddressEntrance;
	    }
	    
	    @JsonProperty("PAddrEntrance")
	    public void setPaddressEntrance(String PaddressEntrance) {
		this.PaddressEntrance = PaddressEntrance;
	    }

	    @JsonProperty("PAddrFloor")
	    public String getPaddressFloor() {
		return PaddressFloor;
	    }

	    @JsonProperty("PAddrFloor")
	    public void setPaddressFloor(String PaddressFloor) {
		this.PaddressFloor = PaddressFloor;
	    }

	    @JsonProperty("PAddrAppartment")
	    public String getPaddressApartment() {
		return PaddressApartment;
	    }

	    @JsonProperty("PAddrAppartment")
	    public void setPaddressApartment(String PaddressApartment) {
		this.PaddressApartment = PaddressApartment;
	    }

	    @JsonProperty("CAddrDistrict")
	    public String getCaddressDistrict() {
		return CaddressDistrict;
	    }

	    @JsonProperty("CAddrDistrict")
	    public void setCaddressDistrict(String CaddressDistrict) {
		this.CaddressDistrict = CaddressDistrict;
	    }
	    
	    @JsonProperty("CAddrDistrictCode")
	    public String getCaddressDistrictCode() {
			return CaddressDistrictCode;
		}

	    @JsonProperty("CAddrDistrictCode")
		public void setCaddressDistrictCode(String caddressDistrictCode) {
			CaddressDistrictCode = caddressDistrictCode;
		}

	    @JsonProperty("CAddrMunicipality")
	    public String getCaddressMunicipality() {
		return CaddressMunicipality;
	    }

	    @JsonProperty("CAddrMunicipality")
	    public void setCaddressMunicipality(String CaddressMunicipality) {
		this.CaddressMunicipality = CaddressMunicipality;
	    }
	    
	    @JsonProperty("CAddrMunicipalityCode")
	    public String getCaddressMunicipalityCode() {
			return CaddressMunicipalityCode;
		}

	    @JsonProperty("CAddrMunicipalityCode")
		public void setCaddressMunicipalityCode(String caddressMunicipalityCode) {
			CaddressMunicipalityCode = caddressMunicipalityCode;
		}


	    @JsonProperty("CAddrPopulatedPlace")
	    public String getCaddressPopulatedPlace() {
		return CaddressPopulatedPlace;
	    }

	    @JsonProperty("CAddrPopulatedPlace")
	    public void setCaddressPopulatedPlace(String CaddressPopulatedPlace) {
		this.CaddressPopulatedPlace = CaddressPopulatedPlace;
	    }
	    
	    @JsonProperty("CAddrPopulatedPlaceCode")
	    public String getCaddressPopulatedPlaceCode() {
			return CaddressPopulatedPlaceCode;
		}
	    
	    @JsonProperty("CAddrPopulatedPlaceCode")
		public void setCaddressPopulatedPlaceCode(String caddressPopulatedPlaceCode) {
			CaddressPopulatedPlaceCode = caddressPopulatedPlaceCode;
		}

	    @JsonProperty("CAddrStreet")
	    public String getCaddressStreet() {
		return CaddressStreet;
	    }

	    @JsonProperty("CAddrStreet")
	    public void setCaddressStreet(String CaddressStreet) {
		this.CaddressStreet = CaddressStreet;
	    }
	    
	    @JsonProperty("CAddrStreetCode")
	    public String getCaddressStreetCode() {
			return CaddressStreetCode;
		}

	    @JsonProperty("CAddrStreetCode")
		public void setCaddressStreetCode(String caddressStreetCode) {
			CaddressStreetCode = caddressStreetCode;
		}


	    @JsonProperty("CAddrNumber")
	    public String getCaddressNumber() {
		return CaddressNumber;
	    }

	    @JsonProperty("CAddrNumber")
	    public void setCaddressNumber(String CaddressNumber) {
		this.CaddressNumber = CaddressNumber;
	    }
	   
	    @JsonProperty("CAddrEntrance")
	    public String getCaddressEntrance() {
		return CaddressEntrance;
	    }

	    @JsonProperty("CAddrEntrance")
	    public void setCaddressEntrance(String CaddressEntrance) {
		this.CaddressEntrance = CaddressEntrance;
	    }

	    @JsonProperty("CAddrFloors")
	    public String getCaddressFloor() {
		return CaddressFloor;
	    }

	    @JsonProperty("CAddrFloors")
	    public void setCaddressFloor(String CaddressFloor) {
		this.CaddressFloor = CaddressFloor;
	    }

	    @JsonProperty("CAddrAppartment")
	    public String getCaddressApartment() {
		return CaddressApartment;
	    }

	    @JsonProperty("CAddrAppartment")
	    public void setCaddressApartment(String CaddressApartment) {
		this.CaddressApartment = CaddressApartment;
	    }

	    @JsonProperty("Sitizenship")
	    public String getCitizenship() {
		return citizenship;
	    }

	    @JsonProperty("Sitizenship")
	    public void setCitizenship(String citizenship) {
		this.citizenship = citizenship;
	    }

	    @JsonProperty("Sitizenship2")
	    public String getCitizenship2() {
		return citizenship2;
	    }

	    @JsonProperty("Sitizenship2")
	    public void setCitizenship2(String citizenship2) {
		this.citizenship2 = citizenship2;
	    }

	    @JsonProperty("IdDocNumber")
	    public String getIdDocNumber() {
		return idDocNumber;
	    }

	    @JsonProperty("IdDocNumber")
	    public void setIdDocNumber(String idDocNumber) {
		this.idDocNumber = idDocNumber;
	    }

	    @JsonProperty("IdDocDate")
	    public String getIdDocDate() {
		return idDocDate;
	    }

	    @JsonProperty("IdDocDate")
	    public void setIdDocDate(String idDocDate) {
		this.idDocDate = idDocDate;
	    }

	    @JsonProperty("IdDocRPU")
	    public String getIdDocRPU() {
		return idDocRPU;
	    }

	    @JsonProperty("IdDocRPU")
	    public void setIdDocRPU(String idDocRPU) {
		this.idDocRPU = idDocRPU;
	    }

	    @JsonProperty("IdDocRDVR")
	    public String getIdDocRDVR() {
		return idDocRDVR;
	    }

	    @JsonProperty("IdDocRDVR")
	    public void setIdDocRDVR(String idDocRDVR) {
		this.idDocRDVR = idDocRDVR;
	    }

	    @JsonProperty("IdDocPopulatedPlace")
	    public String getIdPopulatedPlace() {
		return idPopulatedPlace;
	    }
	    @JsonProperty("IdDocPopulatedPlace")
	    public void setIdPopulatedPlace(String idPopulatedPlace) {
		this.idPopulatedPlace = idPopulatedPlace;
	    }
	    @JsonProperty("BAddrPopulatedPlace")
	    public String getBaddressPopulatedPlace() {
		return bAddressPopulatedPlace;
	    }
	    @JsonProperty("BAddrPopulatedPlace")
	    public void setbAddressPopulatedPlace(String bAddressPopulatedPlace) {
		this.bAddressPopulatedPlace = bAddressPopulatedPlace;
	    }
	    @JsonProperty("DeathDate")
	    public String getDeathDate() {
		return deathDate;
	    }

	    @JsonProperty("DeathDate")
	    public void setDeathDate(String deathDate) {
		this.deathDate = deathDate;
	    }
	    @JsonProperty("PAddrHash")
	    public String getPaddressHash() {
			return PaddressHash;
		}

	    @JsonProperty("PAddrHash")
		public void setPaddressHash(String PaddressHash) {
			this.PaddressHash = PaddressHash;
		}

	    @JsonProperty("CAddrHash")
		public String getCaddressHash() {
			return CaddressHash;
		}

	    @JsonProperty("CAddrHash")
		public void setCaddressHash(String CaddressHash) {
			this.CaddressHash = CaddressHash;
		}

	    @JsonProperty("PersonHash")
		public String getPersonHash() {
			return personHash;
		}

	    @JsonProperty("PersonHash")
	    public void setPersonHash(String personHash) {
			this.personHash = personHash;
		}


		@JsonProperty("PAddrPopulatedPlaceAreaCode")
		public String getPaddressPopulatedPlaceAreaCode() {
			return PaddressPopulatedPlaceAreaCode;
		}
		
		@JsonProperty("PAddrPopulatedPlaceAreaCode")
		public void setPaddressPopulatedPlaceAreaCode(String paddressPopulatedPlaceAreaCode) {
			PaddressPopulatedPlaceAreaCode = paddressPopulatedPlaceAreaCode;
		}


		@Override
		@JsonProperty("CAddrPopulatedPlaceAreaCode")
		public String getCaddressPopulatedPlaceAreaCode() {
			return CaddressPopulatedPlaceAreaCode;
		}
		
		@JsonProperty("CAddrPopulatedPlaceAreaCode")
		public void setCaddressPopulatedPlaceAreaCode(String caddressPopulatedPlaceAreaCode) {
			CaddressPopulatedPlaceAreaCode = caddressPopulatedPlaceAreaCode;
		}
	}

}
