package net.is_bg.ltf.grao.ltf.massive.client;


import net.is_bg.ltf.DataBaseInfo;
import net.is_bg.ltf.update.log.UpdaterReg;
import net.is_bg.ltf.update.log.UpdaterRegSelect;
import net.is_bg.ltf.update.register.dao.RegisterCommonDao;

public class GraoDao extends RegisterCommonDao{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3411346285577873918L;


	public GraoDao(DataBaseInfo info) {
		super(info);
	}
	
	
	public UpdaterReg getGraoUpdaterReg(String kind) {
		riseUpdbSelect();
		UpdaterRegSelect sel = new UpdaterRegSelect(kind);
		execute(sel);
		return sel.getResult().get(0);		
	}
	
	

}
