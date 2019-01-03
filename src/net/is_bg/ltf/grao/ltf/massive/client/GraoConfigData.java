package net.is_bg.ltf.grao.ltf.massive.client;

import net.is_bg.ltf.AbstractModel;
import net.is_bg.ltf.grao.ltf.massive.client.sqlstatements.GraoConfig;
import net.is_bg.ltf.update.log.UpdaterReg;
import taskscheduler.Hour;

public class GraoConfigData extends AbstractModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -248505837503256633L;
	private GraoConfig config;
	private UpdaterReg reg;
	
	/*private Hour startFrom = new Hour(0,0,0, 0);
	private Hour startTo =  new  Hour(0,0,0, 0);*/
	
	private int startFromH;
	private int startFromM;
	private int startToH;
	private int startToM;
	
	
	public GraoConfig getConfig() {
		return config;
	}
	public void setConfig(GraoConfig config) {
		this.config = config;
	}
	public UpdaterReg getReg() {
		return reg;
	}
	public void setReg(UpdaterReg reg) {
		this.reg = reg;
		if(reg != null && reg.getStartFrom()!=null) {
			startFromH = reg.getStartFrom().getHourOfDay();
			startFromM = reg.getStartFrom().getMinuteOfHour();
		}
		if(reg != null && reg.getStartTo()!= null) {
			startToH = reg.getStartTo().getHourOfDay();
			startToM = reg.getStartTo().getMinuteOfHour();
		}
	}
	public int getStartFromH() {
		return startFromH;
	}
	public void setStartFromH(int startFromH) {
		this.startFromH = startFromH;
	}
	public int getStartFromM() {
		return startFromM;
	}
	public void setStartFromM(int startFromM) {
		this.startFromM = startFromM;
	}
	public int getStartToH() {
		return startToH;
	}
	public void setStartToH(int startToH) {
		this.startToH = startToH;
	}
	public int getStartToM() {
		return startToM;
	}
	public void setStartToM(int startToM) {
		this.startToM = startToM;
	}
	public Hour getStartFrom() {
		return new Hour(startFromH, startFromM, 0, 0);
	}
	public Hour getStartTo() {
		return  new Hour(startToH, startToM, 0, 0);
	}
	
	
	
}
