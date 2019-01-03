package net.is_bg.ltf.grao.ltf;

class AdminRegion extends NameIdParent {
	private static final long serialVersionUID = -3556327032481530585L;
	private String code;
	private long cityId; 
	private long municipalityId;
	private String trimmedName;   //without region qualifier
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public long getCityId() {
		return cityId;
	}
	public void setCityId(long cityId) {
		this.cityId = cityId;
	}
	public long getMunicipalityId() {
		return municipalityId;
	}
	public void setMunicipalityId(long municipalityId) {
		this.municipalityId = municipalityId;
	}
	public String getTrimmedName() {
		return trimmedName;
	}
	public void setTrimmedName(String trimmedName) {
		this.trimmedName = trimmedName;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public String toString() {
		return "id:" + id +",code:"+ code +  ",name:"+ name +  ",municipalityId:" + municipalityId;
	}
}
