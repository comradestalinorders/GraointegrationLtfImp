package net.is_bg.ltf.grao.ltf.massive.client.sqlstatements;

import java.io.Serializable;
import java.util.Date;

public class GraoFileDescription implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4587098235680816032L;
	private long fileId =-1;
	private Date createTime;
	private String uid;  //file unique identifier
	private String fname;
	private String status;
	private long parentId;
	
	
	public long getFileId() {
		return fileId;
	}
	public void setFileId(long fileId) {
		this.fileId = fileId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public long getParentId() {
		return parentId;
	}
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
}
