package net.is_bg.ltf.grao.ltf.massive.client.sqlstatements;

import java.io.Serializable;
import java.util.Date;

public class GraoConfig implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6496311122833331117L;
	private Date lastUpdateTime;
	private int mindaysBeaforeUpd;
	private int maxDaysFileValidity;
	private int flags;
	private boolean updateAddress;
	private boolean updateDead;
	
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public int getMindaysBeaforeUpd() {
		return mindaysBeaforeUpd;
	}
	public void setMindaysBeaforeUpd(int mindaysBeaforeUpd) {
		this.mindaysBeaforeUpd = mindaysBeaforeUpd;
	}
	public int getMaxDaysFileValidity() {
		return maxDaysFileValidity;
	}
	public void setMaxDaysFileValidity(int maxDaysFileValidity) {
		this.maxDaysFileValidity = maxDaysFileValidity;
	}
	public int getFlags() {
		return flags;
	}
	public void setFlags(int flags) {
		this.flags = flags;
	}
	public boolean isUpdateAddress() {
		return updateAddress;
	}
	public void setUpdateAddress(boolean updateAddress) {
		this.updateAddress = updateAddress;
	}
	public boolean isUpdateDead() {
		return updateDead;
	}
	public void setUpdateDead(boolean updateDead) {
		this.updateDead = updateDead;
	}
}
