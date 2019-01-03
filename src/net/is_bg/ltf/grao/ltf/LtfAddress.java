
package net.is_bg.ltf.grao.ltf;

import grao.integration.ltf.address.structure.IAddress;
import grao.integration.ltf.address.structure.INameId;
import grao.integration.ltf.address.structure.INameKindId;

class LtfAddress implements IAddress{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8514495501703274704L;
	NameIdKind street = new NameIdKind();
	NameIdKind city = new NameIdKind();
	NameId municipality = new NameId();
	NameId adminRegion = new NameId();
	NameId province = new NameId();
	NameId country = new NameId();
	
	String entrance;
	String floor;
	String apartment;
	String streetNumber;
	String block;
	
	@Override
	public INameKindId getStreet() {
		return street;
	}

	@Override
	public INameKindId getCity() {
		return city;
	}

	@Override
	public INameId getMunicipality() {
		return municipality;
	}

	@Override
	public INameId getProvince() {
		return province;
	}

	@Override
	public INameId getCountry() {
		return country;
	}

	@Override
	public INameId getAdmRegion() {
		return adminRegion;
	}

	@Override
	public String getNo() {
		return streetNumber;
	}

	@Override
	public String getBlock() {
		return block;
	}

	@Override
	public String getEntry() {
		return  entrance;
	}

	@Override
	public String getFloor() {
		return floor;
	}

	@Override
	public String getApartment() {
		return apartment;
	}
	
	
	static String toStrinProvinceMunCityAdmr(IAddress ltfAddress){
		StringBuilder sb = new StringBuilder();
		sb.append("cityid:" + ltfAddress.getCity().getId());
		sb.append(" municipalityId:" + ltfAddress.getMunicipality().getId());
		sb.append(" provinceId:" + ltfAddress.getProvince().getId());
		sb.append(" admRegionId:" + ltfAddress.getAdmRegion().getId());
		return sb.toString();
	}
}
