package net.is_bg.ltf.grao.ltf.massive.client;
import net.is_bg.ltf.db.common.DBStatement;
import net.is_bg.ltf.db.common.UpdateSqlStatement;
import net.is_bg.ltf.db.common.interfaces.IConnectionFactory;
import net.is_bg.ltf.db.dao.AbstractDao;
import net.is_bg.ltf.init.InitGraoClient;
import net.is_bg.ltf.update.log.UpdaterReg;
import net.is_bg.ltf.update.log.UpdaterRegSelect;
import taskscheduler.Hour;

public class GraoConfigDao extends AbstractDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3443874554301645092L;

	public GraoConfigDao(IConnectionFactory connectionFactory) {
		super(connectionFactory);
	}
	
	public UpdaterReg getGraoUpdaterReg() {
		UpdaterRegSelect sel = new UpdaterRegSelect(InitGraoClient.GRAO_MASSIVE_MODE);
		execute(sel);
		return sel.getResult().get(0);		
	}
	
	
/*	public void udateConfig() {
		
	}*/
	
	public void updateConfig(GraoConfigData cdata ) {
		DBStatement st [] = new DBStatement[2];
		st[0] = new GraoConfigUpdate(cdata);
		st[1] = new RegUpdate(cdata.getStartFrom(), cdata.getStartTo());
		execute(st);
	}
	
	
	private static class RegUpdate extends UpdateSqlStatement{
		private String updaterName;
		private Hour from; private   Hour to;
		RegUpdate(Hour from, Hour to){
			this.updaterName = InitGraoClient.GRAO_MASSIVE_MODE;
			this.from = from;
			this.to = to;
		}

		@Override
		protected String getSqlString() {
			return  " update  updaterreg set \r\n" + 
					" startfrom = time '" + hToString(from)+ "',\r\n" + 
					" startto = time '" + hToString(to)+ "' \r\n" + 
					" where updatername = '" + updaterName +"'";
		}
		
		private static String hToString(Hour h) {
			return h.getHour() + ":" + h.getMinute();
		}
	}
	
	private static class GraoConfigUpdate extends  UpdateSqlStatement {
		GraoConfigData cdata;
		int flags = 0;
		GraoConfigUpdate(GraoConfigData cdata ){
			this.cdata = cdata;
			if( cdata.getConfig().isUpdateAddress()) flags = flags | 1;
			if( cdata.getConfig().isUpdateDead()) flags = flags | 2;
		}
		
		@Override
		protected String getSqlString() {
			return " update  grao.config set mindays_before_next_update = " + cdata.getConfig().getMindaysBeaforeUpd()  +" , flags =  " + flags;

		}
	}
	
	
}
