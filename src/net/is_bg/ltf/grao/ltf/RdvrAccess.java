package net.is_bg.ltf.grao.ltf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import grao.integration.structure.impl.GraoUtils;

/***
 * Provides acess to RDVR based on rdv name
 * @author lubo
 *
 */
class RdvrAccess {
	
	private List<Rdvr> rdvrs = new ArrayList<Rdvr>();
	
	/**Rdv name is the key & Rdvr object is value*/
	private Map<String, Rdvr> nameMap = new HashMap<String, Rdvr>();
	
	/**City id is the key & Rdvr object is value**/
	private Map<Long, Rdvr> cityIdMap = new HashMap<Long, Rdvr>();
	
	private RdvrAccess(){}
	
	/**
	 * Creates Rdvr Access object from RDV result liset
	 * @param regions
	 * @return
	 */
	static  RdvrAccess fromRdvrs(List<Rdvr> rdvrs){
		RdvrAccess rdvr = new RdvrAccess();
		rdvr.rdvrs = rdvrs;
		
		//init maps
		for(Rdvr r: rdvrs){
			rdvr.cityIdMap.put(r.getCityId(), r);
			rdvr.nameMap.put(r.getName(), r);
		}
		return rdvr;
	}
	
	void printRdvrs(){
		GraoUtils.printListObject(rdvrs);
	}
	
	@Override
	public String toString() {
		return GraoUtils.listToString(rdvrs);
	}
	
	Rdvr getRdvrByRdvrName(String name){
		return nameMap.get(name);
	}
	
	long getSize(){
		return rdvrs.size();
	}
}
