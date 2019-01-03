package net.is_bg.ltf.grao.ltf;

import java.util.Date;

import grao.integration.ltf.address.structure.INameId;
import grao.integration.ltf.taxsubject.structure.IPerson;

class Person implements IPerson {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7247905283649396616L;
	long id;
	String name;
	String sirnName;
	String familyName;
	String firtsName;
	NameId country1 = new NameId();
	NameId country2 = new NameId();
	String docNo;
	String emmisionDate;
	String docPublisher;
	long rdvrId;
	Date deadDate;

	@Override
	public String getName() {
		return null;
	}

	@Override
	public long getId() {
		return id;
	}

	@Override
	public String getFirstName() {
		return firtsName;
	}

	@Override
	public String getSirName() {
		return sirnName;
	}

	@Override
	public String getFamilyName() {
		return familyName;
	}

	@Override
	public INameId getCountry1() {
		return country1;
	}

	@Override
	public INameId getCountry2() {
		return country2;
	}

	@Override
	public String getIdentDocNo() {
		return docNo;
	}

	@Override
	public String getEmissionDate() {
		return emmisionDate;
	}

	@Override
	public String getIdentDocPublisher() {
		return docPublisher;
	}

	@Override
	public long getRdvrId() {
		return rdvrId;
	}

	@Override
	public Date getDeadDate() {
		return deadDate;
	}

}
