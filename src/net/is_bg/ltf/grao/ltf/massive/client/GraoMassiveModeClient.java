package net.is_bg.ltf.grao.ltf.massive.client;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import grao.integration.structure.ws.iface.IWsServiceMethods;
import net.is_bg.ltf.db.common.ConnectionProperties;
import net.is_bg.ltf.db.common.DBConfig;
import net.is_bg.ltf.db.common.DBExecutor;
import net.is_bg.ltf.db.common.DBStatement;
import net.is_bg.ltf.db.common.impl.DataSourceConnectionFactoryDrManager;
import net.is_bg.ltf.db.common.impl.logging.LogFactorySystemOut;
import net.is_bg.ltf.db.common.impl.timer.ElapsedTimer;
import net.is_bg.ltf.db.common.impl.visit.VisitEmpty;
import net.is_bg.ltf.db.common.interfaces.IConnectionFactory;
import net.is_bg.ltf.db.common.interfaces.IConnectionFactoryX;
import net.is_bg.ltf.db.common.interfaces.timer.IElaplsedTimer;
import net.is_bg.ltf.db.common.interfaces.timer.IElaplsedTimerFactory;
import net.is_bg.ltf.db.common.interfaces.visit.IVisit;
import net.is_bg.ltf.db.common.interfaces.visit.IVisitFactory;
import net.is_bg.ltf.grao.ltf.massive.client.sqlstatements.GetLastFileDescriptorForStatus;
import net.is_bg.ltf.grao.ltf.massive.client.sqlstatements.GraoConfig;
import net.is_bg.ltf.grao.ltf.massive.client.sqlstatements.GraoConfigSelect;
import net.is_bg.ltf.grao.ltf.massive.client.sqlstatements.GraoFileDescription;
import net.is_bg.ltf.grao.ltf.massive.client.sqlstatements.GraoFileDescriptionInsert;
import net.is_bg.ltf.grao.ltf.massive.client.sqlstatements.GraoFileDescriptionUpdate;
import net.is_bg.ltf.grao.ltf.massive.client.sqlstatements.GraoSpMassive;
import net.is_bg.ltf.grao.ltf.massive.client.sqlstatements.IGraoDbFileSaver;
import net.is_bg.ltf.grao.ltf.massive.client.sqlstatements.SaveDBUtils;
import net.is_bg.ltf.grao.ltf.massive.client.sqlstatements.UpdateLastUpdateTimeInConfigWithCurrentTime;
import net.is_bg.ltf.init.ContextParamLoader.CONTEXTPARAMS;


/***
 * Sends file to grao comservice for processing
 * @author Lubo
 *
 */
public class GraoMassiveModeClient {
	/** drivers. */
	private final static String ORCL_DRIVER = "oracle.jdbc.OracleDriver"; 
	
	/** The Constant PGR_DRIVER. */
	private final static String PGR_DRIVER = "org.postgresql.Driver"; 
	
	/** The Constant PGR_DRIVER_DIGEST. */
	private final static String PGR_DRIVER_DIGEST = "com.is.util.db.driver.digestdriver.DigestDriver";
	
	/** data bases url's. */
	public final static String URL_PGR_BRC = "digest:jdbc:postgresql://10.240.110.120:5432/brc";
	
	/** The Constant URL_PGR_MDT. */
	public final static String URL_PGR_MDT = "digest:jdbc:postgresql://10.240.44.129:5432/mdt";
	
	/** The Constant URL_ORC_SF_129. */
	public final static String URL_ORC_SF_129 = "jdbc:oracle:thin:@10.240.44.129:1521:ORCL";
	
	/** The Constant URL_ORC_SF_146. */
	public final static String URL_ORC_SF_146 = "jdbc:oracle:thin:@10.240.44.146:1521:orcl";
	
	/** The Constant URL_LOCAL. */
	public final static String URL_LOCAL = "jdbc:oracle:thin:@localhost:5432:ltf";
	
	/** ���. */
	public final static String URL_PGR_149 = /*digest:*/ "jdbc:postgresql://10.240.44.149:5432/bnk11";
	
	/** The Constant URL_PGR_LOCLHOST. */
	public final static String URL_PGR_LOCLHOST = "jdbc:postgresql://localhost:5432/pdv";
	
	/** The Constant URL_PGR_PDV_7. */
	public final static String URL_PGR_PDV_7 = "digestdebug:jdbc:postgresql://10.240.110.7:5432/pdv";
	
	
	public final static String URL_SOFIA_MERGE3 = "jdbc:postgresql://10.240.44.184:5432/sofiamerge3";
	
	public static long ONE_DAY_MILLIS = 24*3600*1000;
	
	//file statuses
	public static String CREATED = "CREATED";
	public static String FILLED = "FILLED";
	public static String PROCESSED = "PROCESSED";
	
	
	public static  ConnectionProperties [] dBases = {
			new ConnectionProperties(ORCL_DRIVER, URL_ORC_SF_129, "brc", "brc", "orlc_brc_129"),  //0
			new ConnectionProperties(ORCL_DRIVER, URL_ORC_SF_129, "krp", "krp", "orlc_krp_129"),  //1
			new ConnectionProperties(ORCL_DRIVER, URL_ORC_SF_146, "brc", "brc", "orlc_brc_146"),  //2
			new ConnectionProperties(ORCL_DRIVER, URL_ORC_SF_146, "sdk", "sdk", "orlc_sdk_146"),  //3
			new ConnectionProperties(PGR_DRIVER, URL_PGR_BRC, "mdt", "mdt", "pgr_brc_129"),       //4
			new ConnectionProperties(ORCL_DRIVER, URL_LOCAL, "brc", "brc", "pgr_brc_129") ,       //5
			new ConnectionProperties(ORCL_DRIVER, URL_PGR_MDT, "mdt", "mdt", "pgr_mdt_129"),      //6
			
			new ConnectionProperties(PGR_DRIVER_DIGEST, URL_PGR_149, "bnk", "Bnk12345", "pgr_bnk_149") , //7  bankq 149 postgre
			new ConnectionProperties(PGR_DRIVER, URL_PGR_LOCLHOST, "pdv1", "pdv1", "localhost_pgr"),  //8   postgre  localhost
			new ConnectionProperties(PGR_DRIVER_DIGEST, URL_LOCAL, "pdv1", "pdv1", "pdv10.240.110.7") , //9   postgres  localhost
			
			new ConnectionProperties(PGR_DRIVER, URL_SOFIA_MERGE3, "sofiamerge3", "12345", "sofiamerge3"),  //10
			new ConnectionProperties(PGR_DRIVER,"jdbc:postgresql://10.0.10.143:5432/asn", "asn", "asn", ""), //11
			new ConnectionProperties(PGR_DRIVER,"jdbc:postgresql://10.0.10.131:5432/krl", "krl", "krl", ""),  //12
			new ConnectionProperties(PGR_DRIVER,"jdbc:postgresql://10.240.110.70:5432/pdv", "pdv", "pdv", "")  //13
	};
	
	//call services for configuration name
	private static IWsServiceMethods cs;
	private static final String B_UI = "BUI_";
	private static final String E_UI = "_EUI";
	static GraoManualProcessingInfo info = new GraoManualProcessingInfo();
	
	public static void main(String [] args) throws UnsupportedEncodingException, IOException {
		//init client configuration
		cs = init();
		
		//init db config
		ConnectionProperties pr = dBases[13];
		initDbConfig(pr);
		
		//create db executor
		DBExecutor ex = new DBExecutor(DBConfig.getConnectionFactory());
		
		graoMassiveUpdate(ex, "graoClientConfiguration");
	}
	
	
	/***
	 * Returns the file content as single utf8 string!
	 * @param pathTofile
	 * @return
	 * @throws IOException
	 */
	private static List<String> readFileAsUtf8StringList(byte [] fileData) throws IOException {
		List<String> result = new ArrayList<String>();
		BufferedReader reader = null;
		
		ByteArrayInputStream is = new ByteArrayInputStream(fileData);
		try{
			reader = new BufferedReader(new InputStreamReader( is, "UTF8"));
			String s = reader.readLine();
			while(s!=null){
				result.add(s);
				//result.append("\n");
				s = reader.readLine();
			}
		}finally {
			if(reader!=null) reader.close();
		}
		return result;
	}
	
	/***
	 * Returns info about processing progress...
	 * @return
	 */
	public static  String getInfo() {
		return info.getInfo();
	}
	
	/***
	 * Processes a manually uploaded file with eins from grao.
	 * @param ex 
	 * @param fileContent
	 * @throws IOException 
	 */
	public static void processUploadedFileContent(DBExecutor ex, byte [] fileData, long flags, boolean spawnProc) throws IOException {
		ElapsedTimer timer = new ElapsedTimer();
		timer.start();
		long starTime = timer.getStartTime();
		info.clearInfo();
		
		info.addInfo("Start processing uploaded file... Start Time " + new Date());
		
		//create uid
		String uid = UUID.randomUUID().toString();
		GraoFileDescription fd = new GraoFileDescription();
		fd.setFname("uploadedemptyFile");
		fd.setStatus(CREATED); fd.setUid(uid);
		
		//insert new file
		ex.execute(new GraoFileDescriptionInsert(fd));
		info.addInfo("New File descriptor created...");
		
		//get created file
		GraoFileDescription lastCreatedFile = getLastDescriptorForStatus(ex, CREATED);
		info.addInfo("File descriptorId = " + fd.getFileId() + " UID = " + fd.getUid());
		
		info.addInfo("Start reading file lines as  UTF8 String....");
		timer.start();
		List<String> flines = readFileAsUtf8StringList(fileData);
		timer.stop();
		info.addInfo("Reading file Lines finished in " + timer.getDuration() + " ms " + ", number of lines " + flines.size());
		
		//create DB saver for file lines & save file lines
		List<DBStatement> statements = new ArrayList<DBStatement>();
		IGraoDbFileSaver dbSaver =  SaveDBUtils.createFileLinesSaver(flines, uid, statements);
		
		info.addInfo("Saving file Lines to DataBase...");
		timer.start();
		fd = new GraoFileDescription(); fd.setFileId(lastCreatedFile.getFileId());
		fd.setStatus(FILLED);
		GraoFileDescriptionUpdate upd = new GraoFileDescriptionUpdate(fd);
		statements.add(upd);
		dbSaver.savetoDataBase(ex);
		timer.getEndTime();
		info.addInfo("File saved to  DataBase for "  + timer.getDuration() + " millisecods..");
		
		if(spawnProc) {
			timer.start();
			info.addInfo("Calling processing procedure with flags  = " + flags );
			callProcessFileProc(ex, fd.getFileId(), flags);
			timer.stop();
			info.addInfo("Processing procedure finnished for " + timer.getDuration() + " millisecods...");
		}
		info.addInfo("Processing finished...Duration = " + (timer.getEndTime() - starTime) + " millis");
	}
	
	
	//spawns grao massive update right now
	public static void graoMassiveUpdateNow(DBExecutor ex, String configName, long flags, long waitTimeMinutes, boolean callProc) throws UnsupportedEncodingException, IOException, InterruptedException {
		ElapsedTimer timer = new ElapsedTimer();
		timer.start();
		long starTime = timer.getStartTime();
		info.clearInfo();
		
		info.addInfo("Massive Grao upadate started manually... Start Time " + new Date());
		info.addInfo("Grao configuration name = " + configName);
		info.addInfo("Grao endpoint = " + CONTEXTPARAMS.GRAO_ENDPOINT.getValue());
		
		//get call services instance
		cs = GraoCallServices.getCallServicesForConfigurationName(configName);
		
		//create a new file save its descriptor to database & upload it
		//create uid
		String uid = UUID.randomUUID().toString();
		GraoFileDescription fd = new GraoFileDescription();
		fd.setFname("uploadedemptyFile");
		fd.setStatus(CREATED); fd.setUid(uid);
		
		//upload empty file descriptor
		uploadEmptyFile(uid);
		
		//insert new file
		ex.execute(new GraoFileDescriptionInsert(fd));
		timer.stop();
		
		info.addInfo("New graoFiledescriptor created  for UID " + uid);
		
		//get created file
		GraoFileDescription lastCreatedFile = getLastDescriptorForStatus(ex, CREATED);
		info.addInfo("File id  " + lastCreatedFile.getFileId() );
		info.addInfo("Waiting for files to be processed from web service for maximum of " + waitTimeMinutes + " minutes..");
		
		timer.start();
		//wait at most waitTimeMinutes minutes to finish processing at the other side
		long waitTimemillis = waitTimeMinutes*60*1000;
		Set<String> processedFiles = null;
		
		long start = new Date().getTime();
		while(start + waitTimemillis >  new Date().getTime()) {
			try {
				processedFiles = cs.getProcessedFiles(uid);
				if(processedFiles != null && !processedFiles.isEmpty()) break; //if no exception - files at the other side must be processed
			}catch (Exception e) {
			}
			Thread.sleep(60*1000);   //sleep one minute
		}
		if(processedFiles == null || processedFiles.isEmpty()) throw new RuntimeException("Coudn't process files at WS for " + waitTimeMinutes + " minutes...");
		timer.stop();
		info.addInfo("The following files are generated from GraoComService  for "  + timer.getDuration() + " millis..." );
		for(String s: processedFiles) {
			info.addInfo(s);
		}
		//here we must have files add the other side processed
		
		//download files and update status to filled on success
		fd = new GraoFileDescription(); fd.setFileId(lastCreatedFile.getFileId());
		fd.setStatus(FILLED);
		GraoFileDescriptionUpdate upd = new GraoFileDescriptionUpdate(fd);
		List<DBStatement> l = new ArrayList<DBStatement>(); l.add(upd);
		downloadFiles(ex, lastCreatedFile.getUid(), l);
		
		//System.out.println("Calling grao.processfile for file with file id =  " + fd.getFileId() +    " and flags = " );
		if(callProc) {
			timer.start();
			info.addInfo("Calling processing procedure with flags  = " + flags );
			callProcessFileProc(ex, fd.getFileId(), flags);
			timer.stop();
			info.addInfo("Processing procedure finished for " + timer.getDuration() + " millisecods...");
		}
		timer.stop();
		info.addInfo("All processing done for " + (new Date().getTime() - starTime) + " millis...");
	}
	
	//spawn massive update
	public static void graoMassiveUpdate(DBExecutor ex, String configName) throws UnsupportedEncodingException, IOException {
		
		GraoConfig c = getConfig(ex);
		
		cs = GraoCallServices.getCallServicesForConfigurationName(configName);
		
		long lastUpdateTime = (c == null || c.getLastUpdateTime() == null) ? 0 : c.getLastUpdateTime().getTime();
		if(lastUpdateTime == 0) lastUpdateTime = new Date().getTime();
		
		//update intervals in days
		int updateIntervalIndays = c == null || c.getMindaysBeaforeUpd() <=0 ? 20 :  c.getMindaysBeaforeUpd();
		
		//validityFiles in days 
		int validityIntervalsIndays = c == null || c.getMaxDaysFileValidity() <=0 ? 2 :  c.getMaxDaysFileValidity();
		long now = new Date().getTime();
		
		if(now - lastUpdateTime  < dayToMillis(updateIntervalIndays) ) return;
		
		//get filled file
		GraoFileDescription lastFIlled = getLastDescriptorForStatus(ex, FILLED);
		if(lastFIlled !=null && lastFIlled.getCreateTime()!=null && (lastFIlled.getCreateTime().getTime() + dayToMillis(validityIntervalsIndays) > now) ) {
			//spawn update proc for file id & update file status to processed & update config with current time
			System.out.println("Calling grao.processfile for file with file id =  " + lastFIlled.getFileId() +    " and flags = " +  c.getFlags());
			callProcessFileProc(ex, lastFIlled.getFileId(), c.getFlags());
			return;
		}
		
		//get created file
		GraoFileDescription lastCreatedFile = getLastDescriptorForStatus(ex, CREATED);
		if(lastCreatedFile !=null && lastCreatedFile.getCreateTime()!=null && 
				(lastCreatedFile.getCreateTime().getTime() + dayToMillis(validityIntervalsIndays) > now) ){
			//download files and update status to filled on success
			GraoFileDescription fd = new GraoFileDescription(); fd.setFileId(lastCreatedFile.getFileId());
			fd.setStatus(FILLED);
			GraoFileDescriptionUpdate upd = new GraoFileDescriptionUpdate(fd);
			List<DBStatement> l = new ArrayList<DBStatement>(); l.add(upd);
			downloadFiles(ex, lastCreatedFile.getUid(), l);
		}
		else {//create a new file save its descriptor to database & upload it
			//create uid
			String uid = UUID.randomUUID().toString();
			GraoFileDescription fd = new GraoFileDescription();
			fd.setFname("uploadedemptyFile");
			fd.setStatus(CREATED); fd.setUid(uid);
			
			//upload empty file
			uploadEmptyFile(uid);
			ex.execute(new GraoFileDescriptionInsert(fd));
		}
	}
	
	
	
	
	private static GraoConfig getConfig(DBExecutor ex) {
		GraoConfigSelect csel = new GraoConfigSelect();
		ex.execute(csel);
		return  csel.getConfig();
	}
	
	/***
	 * Calls procedure for processing files!
	 * @param ex
	 * @param fileId
	 * @param flags
	 */
	private static void callProcessFileProc(DBExecutor ex, long fileId, long flags) {
		GraoSpMassive p = new GraoSpMassive(fileId, flags);
		GraoFileDescription fd = new GraoFileDescription(); fd.setFileId(fileId);
		fd.setStatus(PROCESSED);
		GraoFileDescriptionUpdate upd = new GraoFileDescriptionUpdate(fd);
		UpdateLastUpdateTimeInConfigWithCurrentTime cTimeUpd = new UpdateLastUpdateTimeInConfigWithCurrentTime();
		List<DBStatement>  stList = new ArrayList<DBStatement>();
		stList.add(p); stList.add(upd);stList.add(cTimeUpd);
		ex.execute((stList.toArray(new DBStatement[0])));
		return;
	}
	
	private static GraoFileDescription getLastDescriptorForStatus(DBExecutor ex,String status) {
		GetLastFileDescriptorForStatus sel = new GetLastFileDescriptorForStatus(status);
		ex.execute(sel);
		return sel.getFileDescriptor();
	}
	
	
	private static long dayToMillis(int days) {
		return days * ONE_DAY_MILLIS;
	}
	
	
	/**Downloads processed files & inserts them to data base for further processing
	 * @throws IOException */
	public static void downloadFiles(DBExecutor ex, String uid, List<DBStatement> statments) throws IOException {
		info.addInfo("Starting downloading files...");
		ElapsedTimer timer = new ElapsedTimer();
		timer.start();
		createDbSaverForUidFromWs(uid, statments).savetoDataBase(ex);
		timer.stop();
		info.addInfo("Saving files to database finished for " + timer.getDuration() + " millisecods...");
	}
	
	
	
	/***
	 * Downloads from  WS files under unique identifier!
	 * @param uid
	 * @return
	 * @throws IOException
	 */
	private static  IGraoDbFileSaver createDbSaverForUidFromWs(String uid, List<DBStatement> statements) throws IOException {
		//get the names of processed files 
		Set<String> procesedFiles =	cs.getProcessedFiles(uid);
		
		List<String> flines  = new ArrayList<String>();
		ElapsedTimer timer = new ElapsedTimer();
		//download the content of each file 
		if(procesedFiles != null) {
			for(String filename : procesedFiles) {
				timer.start();
				//get file content for file
				String fileContent = cs.getProcessedFileCnt(uid, filename);
				//read string line by line & add file lines
				flines.addAll( Utils.lineStringReader(fileContent));
				timer.stop();
				info.addInfo("File " + filename + " downloaded and parsed for " + timer.getDuration() + " millisecods...");
			}
		}
		//create DB saver for file lines
		IGraoDbFileSaver dbSaver =  SaveDBUtils.createFileLinesSaver(flines, uid, statements);
		info.addInfo(" IGraoDbFileSaver created for all downloaded files... ");
		return dbSaver;
	}
	
	
	
	
	/**Uploads a list with eins!*/
	public static UploadFileData uploadEinList(List<String> einList, String uid) throws UnsupportedEncodingException, IOException {
		//create Upload File data from ein list
		UploadFileData fdata = getUploadFileData(einList, uid);
		
		//upload  file data to Grao Service
		cs.uploadFile(fdata);
		
		//on successful upload save fileDescriptor to data base
		return fdata;
	}
	
	/**Uploads an empty file to WS! In this case all eins are retrieved from grao*/
	public static UploadFileData uploadEmptyFile(String uid) throws UnsupportedEncodingException, IOException {
		//create Upload File data from ein list
		UploadFileData fdata = getUploadFileData(null, uid);
		
		//upload  file data to Grao Service
	    String res = 	cs.uploadEmptyFile(uid);
	    if(res.equals("PROCESSING_RUNNING")) throw new RuntimeException("PROCESSING FILES AT GraoComService is still RUNNING");
	    
	    return fdata;
	}
	
	
	/***
	 * Gets ein list for dbresourcename!
	 * @param dbResourceName
	 * @return
	 */
	private static List<String> getEinList(String dbResourceName){
		DBExecutor ex = new DBExecutor(DBConfig.getConnectionFactory());
		SelectEin sel = new SelectEin();
		ex.execute(sel, dbResourceName, DBExecutor.DEFAULT_TRANSACTION_ISOLATION_LEVEL);
		return sel.getList();
	}
	
	private static List<String> getEinList(ConnectionProperties pr){
		return getEinList(new FcWrapper(new DataSourceConnectionFactoryDrManager(pr)));
	}
	
	
	private static void initDbConfig(ConnectionProperties pr) {
		DBConfig.initDBConfig(new LogFactorySystemOut(),new IVisitFactory() {
			@Override
			public IVisit getVist() {
				return new  VisitEmpty();
			}
		}, new FcWrapper(new DataSourceConnectionFactoryDrManager(pr)), new IElaplsedTimerFactory() {
			@Override
			public IElaplsedTimer getElapsedTimer() {
				return new ElapsedTimer();
			}
		}, null);
	}
	
	/***
	 * Get ein list for connection factory!
	 * @param fc
	 * @return
	 */
	private static List<String> getEinList(IConnectionFactoryX fc){
		DBExecutor ex = new DBExecutor(fc);
		SelectEin sel = new SelectEin();
		ex.execute(sel,  DBExecutor.DEFAULT_TRANSACTION_ISOLATION_LEVEL);
		return sel.getList();
	}
	

	//init client configuration
	private static IWsServiceMethods init() {
		net.is_bg.ltf.grao.ltf.massive.client.GraoParamsConfig.GraoParamsConfigBuilder cf = null;
		if(true) {
			cf = new GraoParamsConfig.GraoParamsConfigBuilder();
			cf.setGraoClientConfiguration("graoClientConfiguration");
			//cf.setGraoEndpoint("http://10.240.110.230:80/GraoComService");
			cf.setGraoEndpoint("http://10.240.110.70:8180/GraoComService");
			GraoCallServices.init(cf.build());
		}
		
		return GraoCallServices.getCallServicesForConfigurationName(cf.graoClientConfiguration);
	}
	
	/**Creates upload file data from ein list passed to method
	 * @throws IOException 
	 * @throws UnsupportedEncodingException */
	private static  UploadFileData getUploadFileData(List<String> einslist, String uid) throws UnsupportedEncodingException, IOException {
		UploadFileData fdata = new UploadFileData();
		
		byte [] newLine = "\n".getBytes("UTF-8");
		
		ByteArrayOutputStream os = null;
		
		//create byte array from ein list if ein list is not null or empty
		if(einslist != null && einslist.size() > 0) {
			os = new ByteArrayOutputStream();
			for(String ein : einslist) {
				if(ein == null) continue;
				os.write(ein.getBytes("UTF-8"));
				os.write(newLine);
			}
			os.flush();
			os.close();
		}
		
		//set file content
		fdata.fileContent = (os == null ? null : os.toByteArray());
		long now = new Date().getTime();
		
		//add create time to file name
		fdata.setFileName(B_UI + uid + E_UI + "CT"+ "" + now + "ECT" + "einlist.txt");
		
		return fdata;
	}
	
	
}

class FcWrapper implements IConnectionFactoryX{
	
	IConnectionFactory cf;
	public FcWrapper(IConnectionFactory cf) {
		this.cf = cf;
	}

	@Override
	public Connection getConnection() {
		return cf.getConnection();
	}

	@Override
	public Connection getConnection(String arg0) {
		return cf.getConnection();
	}
	
}
