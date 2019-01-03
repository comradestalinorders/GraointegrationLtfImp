package net.is_bg.ltf.grao.ltf;

import grao.integration.ltf.address.structure.INameId;

class NameId implements INameId{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8720007993669645345L;
	String name;
	long id;
	long parentId =-1;
	

	@Override
	public String getName() {
		return name;
	}

	@Override
	public long getId() {
		return id;
	}

	public long getParentId() {
		return parentId;
	}
}
