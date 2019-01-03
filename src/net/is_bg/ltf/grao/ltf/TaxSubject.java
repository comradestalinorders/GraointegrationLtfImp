package net.is_bg.ltf.grao.ltf;

import grao.integration.ltf.taxsubject.structure.IPerson;
import grao.integration.ltf.taxsubject.structure.ITaxSubject;


class TaxSubject implements ITaxSubject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5320084845393971827L;
	long id;
	Person person = new Person();
	String name;
	String idn;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public long getId() {
		return id;
	}

	@Override
	public IPerson getPerson() {
		return person;
	}

	@Override
	public String getIdn() {
		return idn;
	}

}
