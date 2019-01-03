package net.is_bg.ltf.grao.ltf.massive.client;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;
import org.richfaces.event.UploadEvent;

import net.is_bg.ltf.AbstractManagedBean;
import net.is_bg.ltf.AppConstants;
import net.is_bg.ltf.FileUploadBaseBean;
import net.is_bg.ltf.Semaphore;
import net.is_bg.ltf.db.common.DBConfig;
import net.is_bg.ltf.grao.ltf.massive.client.sqlstatements.GraoConfig;
import net.is_bg.ltf.grao.ltf.massive.client.sqlstatements.GraoConfigSelect;
import net.is_bg.ltf.update.log.UpdaterReg;

public class GraoConfigBean extends AbstractManagedBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	UpdaterReg reg;
	private GraoConfigDao dao;
	private GraoConfig config;
	List<GraoConfigData> configData = new ArrayList<GraoConfigData>();
	private long manualFlags=0;
	private FileUploadGraoBean graoFileUploadGraoBean;
	protected File f;
	
	//manual spawn manager
	private GraoMassiveManualSpawn manSpawn = new GraoMassiveManualSpawn();
	private boolean spawnProc;
	private long mniutesToWait = 20;
	private int manualMode = 0;
	
	//private int 
	
	public GraoConfigBean() {
		dao = new GraoConfigDao(DBConfig.getConnectionFactory());
		GraoConfigSelect csel = new GraoConfigSelect();
		dao.execute(csel);
		config = csel.getConfig();
		reg = dao.getGraoUpdaterReg();
		
		GraoConfigData data = new GraoConfigData();
		data.setConfig(config); data.setReg(reg);
		configData.add(data);
	}
	
	public String btnOk() {
		updateGraoConfiguration();
		return null;
	}
	
	public String btnCancel() {
		return AppConstants.EXIT_OUTCOME;
	}
	
	
	private void updateGraoConfiguration() {
		dao.updateConfig(configData.get(0));
	}
	
	
	
	
	public void btnManual() throws UnsupportedEncodingException, IOException, InterruptedException {
		//run on separate thread
		try {
			String dsName = visit.getDefDbConn();
			manSpawn.graoMassiveUpdateNow(dsName, manualFlags, mniutesToWait, spawnProc);
		}catch (Exception e) {
			modalDialog.setErrMsg(e.getMessage());
		}
	}
	
	
	
	
    public class FileUploadGraoBean extends FileUploadBaseBean{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = -4084498466544892901L;
		private String engName;
		
		public FileUploadGraoBean(GraoConfigBean parent){
			setCmpId("upload111");
			setAcceptedTypes("txt, grao");
			setListWidth("800px");
			setContainerPanelId("filePanel111");
		}

		@Override
		public void fileUploadListener(UploadEvent event) throws Exception {
			// TODO Auto-generated method stub
			itemFile = event.getUploadItem();
		}

		@Override
		public void clearFile(ActionEvent e) {
			itemFile = null;
		}

		public String getEngName() {
			return engName;
		}

		public void setEngName(String engName) {
			this.engName = engName;
		}
		
	}
     
    public List<GraoConfigData> getConfigData() {
 		return configData;
 	}
 	
 	public void setSpawnProc(boolean spawnProc) {
 		this.spawnProc = spawnProc;
 	}
 	
 	public void setMniutesToWait(long mniutesToWait) {
 		this.mniutesToWait = mniutesToWait;
 	}
 	
 	public long getMniutesToWait() {
 		return mniutesToWait;
 	}
 	
 	public boolean isSpawnProc() {
 		return spawnProc;
 	}

    public void processUploadedFile() throws IOException {
    	try {
	    	byte [] data = getFileUploadGraoBean().getItemFile().getData();
	    	String dsName = visit.getDefDbConn();
	    	manSpawn.processFileData(dsName, data, manualFlags, spawnProc);
    	}
		catch (Exception e) {
			modalDialog.setErrMsg(e.getMessage());
		}
    }

    public FileUploadGraoBean getFileUploadGraoBean() {
		if(graoFileUploadGraoBean == null) {
			graoFileUploadGraoBean = new FileUploadGraoBean(this);
		}
		return graoFileUploadGraoBean;
	}
    
    public String  getInfo(){
    	return manSpawn.getInfo();
    }
    
    public long getManualFlags() {
		return manualFlags;
	}
	
	public void setManualFlags(long manualFlags) {
		this.manualFlags = manualFlags;
	}
    
    public static Semaphore getSemaphore() {
    	return GraoMassiveManualSpawn.getSemaphore();
    }
    
    public int getManualMode() {
		return manualMode;
	}
    
    public void setManualMode(int manualMode) {
		this.manualMode = manualMode;
	}
    
    public boolean isManualUpdateRunning() {
    	return getSemaphore().isSignalled();
    }
    
    public boolean isUploadFile() {
    	return manualMode == 1 ? true : false;
    }
    
    public void refresh() {
    	
    }
}
