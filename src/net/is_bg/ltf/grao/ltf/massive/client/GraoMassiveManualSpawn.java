package net.is_bg.ltf.grao.ltf.massive.client;

import java.io.Serializable;
import net.is_bg.ltf.AppUtil;
import net.is_bg.ltf.ApplicationGlobals;
import net.is_bg.ltf.Semaphore;
import net.is_bg.ltf.db.common.DBExecutor;
import net.is_bg.ltf.grao.InitGraoClientLib;
import net.is_bg.ltf.init.InitGraoClient;
import net.is_bg.ltf.init.ContextParamLoader.CONTEXTPARAMS;
import net.is_bg.ltf.update.register.dao.DataSourceServiceConnectionFactory;
import taskscheduler.ScheduledTask;

/***
 * Grao Massive manual Spawn manager!!!
 * @author Lubo
 *
 */
class GraoMassiveManualSpawn implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5831053578386287109L;
	private static Semaphore s = new Semaphore(false); //semaphore  - initial state non signaled nothing is running
	
	public GraoMassiveManualSpawn() {
		
	}
	
	
	/**manually process an uploaded grao data file*/
	void processFileData(final String dsName, final byte [] fileContent, final long flags, final boolean spawnProc) {
		synchronized(GraoConfigBean.class) {
			//get automatic task for current database
			ScheduledTask tt = InitGraoClient.getScheduledTaskbyDsName(AppUtil.getVisit().getDefDbConn());
			if((tt == null || !tt.isRunning()) && !s.isSignalled()) {
				//start processing thread
				Thread t = new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							//sets the semaphore to signaled 
							s.set();
							ApplicationGlobals gl = ApplicationGlobals.getApplicationGlobals();
							DBExecutor ex = new DBExecutor(new DataSourceServiceConnectionFactory(gl.getDataBaseInfoByDSName(dsName)));
							GraoMassiveModeClient.processUploadedFileContent(ex, fileContent, flags, spawnProc);
						} catch (Exception e) {
							e.printStackTrace();
							GraoMassiveModeClient.info.addInfo(AppUtil.exceptionToString(e));
						}finally {
							s.reset();  //resets the semaphore 
						}
					}
				});
				t.start();
			}else {
				throw new RuntimeException("Processing is already running...");
			}
		}
	}
	
	/**starts automatic grao update manually now */
	void graoMassiveUpdateNow(final String dsName, final long flags, final long timeInMinutesTowaitForFileGeneration, final boolean spawnProc) {
		synchronized(GraoConfigBean.class) {
			//get automatic task for current database
			ScheduledTask tt = InitGraoClientLib.getScheduledTaskbyDsName(AppUtil.getVisit().getDefDbConn());
			if((tt == null || !tt.isRunning()) && !s.isSignalled()) {
				
				//start processing thread
				Thread t =	new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							//sets the semaphore to signaled 
							s.set();
							ApplicationGlobals gl = ApplicationGlobals.getApplicationGlobals();
							DBExecutor ex = new DBExecutor(new DataSourceServiceConnectionFactory(gl.getDataBaseInfoByDSName(dsName)));
							GraoMassiveModeClient.graoMassiveUpdateNow(ex, 
									(String)CONTEXTPARAMS.GRAO_CLIENT_CONFIGURATION_NAME.getValue(), flags, timeInMinutesTowaitForFileGeneration, false);
						} catch (Exception e) {
							e.printStackTrace();
							GraoMassiveModeClient.info.addInfo(AppUtil.exceptionToString(e));
						}finally {
							s.reset();
						}
					}
				});
				t.start();
			}else {
				throw new RuntimeException("Processing is already running...");
			}
		}
	}
	
	public String getInfo() {
		return GraoMassiveModeClient.getInfo();
	}
	
	
	public static Semaphore getSemaphore() {
		return s;
	}
	
}
