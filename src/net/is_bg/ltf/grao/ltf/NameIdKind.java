package net.is_bg.ltf.grao.ltf;

import grao.integration.ltf.address.structure.INameKindId;

class NameIdKind extends NameIdParent implements INameKindId{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6060480404017552731L;
	String kind;

	@Override
	public String getKind() {
		return kind;
	}
	
	
}
