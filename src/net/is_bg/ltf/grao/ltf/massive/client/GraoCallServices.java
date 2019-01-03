package net.is_bg.ltf.grao.ltf.massive.client;


import java.io.IOException;
import java.util.Set;

import com.cc.rest.client.ClientConfigurator;
import com.cc.rest.client.ClientConfigurator.SOCKET_PROTOCOL;
import com.cc.rest.client.ClientConfigurator.STORE_TYPE;
import com.cc.rest.client.Requester;
import com.cc.rest.client.Requester.MEDIA_TYPE;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import grao.integration.GraoFlagConstants;
import grao.integration.IPersonInfoProperties;
import grao.integration.structure.iface.IGraoPersonInfo;
import grao.integration.structure.impl.GraoUtils;
import grao.integration.structure.ws.iface.IFileData;
import grao.integration.structure.ws.iface.IWsServiceMethods;
import net.is_bg.ltf.grao.ltf.massive.client.GraoContextParams.GRAO_PARAM;

public class GraoCallServices implements IWsServiceMethods{
	
	//static boolean initted = false;
	static String graoinitError = null;
	private String configName;
	
	public static IWsServiceMethods getCallServicesForConfigurationName(String configurationName) {
	  GraoCallServices cs = new GraoCallServices() ; 
	  cs.configName = configurationName;
	  return cs;
	}
	
	/***
	 * Initializes grao COM service client using configuration parameters!
	 * @param pconfig
	 */
    public static void init(GraoParamsConfig pconfig) {
			//if(initted) return;
			try{
	    	    if (pconfig.getGraoEndpoint() != null) {
				    if (pconfig.getGraoKeystoreType() != null)
					ClientConfigurator.configure(pconfig.getGraoClientConfiguration())
							  .targetEndpoint(pconfig.getGraoEndpoint())
							  .readTimeout(100
								  * 60)
							  .protocol(SOCKET_PROTOCOL.SSL)
							  .keystore(STORE_TYPE.valueOf(pconfig.getGraoKeystoreType()),
								    pconfig.getGraoKeystoreFile(),
								    pconfig.getGraoKeystorePassword())
							  .privateKey(pconfig.getGraoPrivateKeyAlias(),pconfig.getGraoPrivateKeyPassword())
							  .trustAllCerts()
							  .complete();
				    else
					ClientConfigurator.configure(pconfig.getGraoClientConfiguration()).targetEndpoint(pconfig.getGraoEndpoint()).readTimeout(100
						* 60).noSSL().complete();
				    //initted = true;
				}
			}catch(Exception e){
				//graoinitError = AppUtil.exceptionToString(e);
				throw new RuntimeException(e);
	    	}
	    }
	
    
    /***
     * Retrieves person info from graoComService as String!
     * @param idn
     * @return
     */
    public String getPersonInfoAsString(String idn, long flags) {
		//if(!initted) throw new RuntimeException("Grao Com service Client is not initialized...");
		try {
		   return  Requester.request(configName)
						    .path(GraoContextParams.GRAO_PATH.PERSON_INFO)
						    .queryParam(GraoContextParams.GRAO_PARAM.IDN, idn)
						    .queryParam(GraoContextParams.GRAO_PARAM.FLAGS, flags)
						    .get(MEDIA_TYPE.TEXT_PLAIN)
						    .getResponseAsObject(String.class);
		} catch (Exception e) {
		    e.printStackTrace();
		    throw new RuntimeException(e);
		}
	}
    
    /***
     * Uploads ein file to GraoComservice!!!
     * @param fileData
     */
    public void uploadFile(IFileData fileData) {
    	//if(!initted) throw new RuntimeException("Grao Com service Client is not initialized...");
		try {
		     Requester.request(configName)
						    .path(GraoContextParams.GRAO_PATH.FILE_UPLOAD)
						    .post(fileData, MEDIA_TYPE.JSON,  MEDIA_TYPE.JSON);
		} catch (Exception e) {
		    e.printStackTrace();
		    throw new RuntimeException(e);
		}
    }
    
    
    @SuppressWarnings("unchecked")
   	public String uploadEmptyFile(String uid) {
   		try {
   		    return  Requester.request(configName)
   						    .path(GraoContextParams.getSubPath(GraoContextParams.GRAO_PATH.EMPTY_FILE_UPLOAD, "/"+ uid))
   						    .queryParam(GraoContextParams.GRAO_PARAM.FILE_NAME, uid)
   						    .get(MEDIA_TYPE.JSON).getResponseAsObject(String.class);
   		} catch (Exception e) {
   		    e.printStackTrace();
   		    throw new RuntimeException(e);
   		}
    }
       
    
    /****
     * Get a set  with the names of the output files  after successful processing!
     * @param createTimeMillis
     * @return
     */
    @SuppressWarnings("unchecked")
	public
	Set<String> getProcessedFiles(String uid) {
    	//if(!initted) throw new RuntimeException("Grao Com service Client is not initialized...");
		try {
		    return  (Set<String>)Requester.request(configName)
						    .path(GraoContextParams.getSubPath(GraoContextParams.GRAO_PATH.PROCESSED_FILES_NAMES, "/"+ uid))
						    .get(MEDIA_TYPE.JSON).getResponseAsObject(Set.class);
		} catch (Exception e) {
		    e.printStackTrace();
		    throw new RuntimeException(e);
		}
    }
    
    
    
    /***
     * Get the content of file by file create time  & file name
     * @param createTimeMillis
     * @param filename
     * @return
     */
    public String getProcessedFileCnt(String uid, String filename) {
    	//if(!initted) throw new RuntimeException("Grao Com service Client is not initialized...");
		try {
		    return  Requester.request(configName)
						    .path(GraoContextParams.getSubPath(GraoContextParams.GRAO_PATH.PROCESSED_FILE_CNT, "/"+ uid))
						    .queryParam(GRAO_PARAM.FILE_NAME, filename)
						    .get(MEDIA_TYPE.JSON).getResponseAsObject(String.class);
		} catch (Exception e) {
		    e.printStackTrace();
		    throw new RuntimeException(e);
		}
    }
    
    
    
    
    
    /**
     * Retrieves info from Grao transforming it to IGraoPersonInfo data Structure!
     * @param idn
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    public  IGraoPersonInfo getGraoPersonInfo(String idn) throws JsonParseException, JsonMappingException, IOException{
    	//get person info as JSON String
  		String jsonPersonInfo = getPersonInfoAsString(idn, GraoFlagConstants.JSON | GraoFlagConstants.SKIP_NULL);
  		
  		//Convert JSON String to IPersonInfoProperties data structure
  	    IPersonInfoProperties pp = GraoPersonInfoToPersonProp.getPersonInfoProperties(jsonPersonInfo);
  	    
  	    //convert to IGraoPersonInfo data structure 
  	    IGraoPersonInfo pInfo = GraoUtils.getGraoPersonInfo(pp);
		return pInfo;
    }
    
    /*public static IGraoPersonInfo getGraoPersonInfo(String ein) throws JsonParseException, JsonMappingException, IOException {
		IWsServiceMethods callservices = GraoCallServices.getCallServicesForConfigurationName((String)CONTEXTPARAMS.GRAO_CLIENT_CONFIGURATION_NAME.getName());
		
		System.out.println("Grao calling service test");
		GraoParamsConfigBuilder cf = null;
		if(!GraoCallServices.isInitted()) {
			cf = new GraoParamsConfig.GraoParamsConfigBuilder();
			cf.setGraoClientConfiguration("graoClientConfiguration");
			//cf.setGraoEndpoint("http://10.240.110.230:80/GraoComService");
			cf.setGraoEndpoint("http://localhost:8080/GraoComService");
			GraoCallServices.init(cf.build());
		}
		
		UploadFileData fdata = new UploadFileData();
		fdata.setFileName("test");
		fdata.fileContent = new byte[256];
		for(int i = 0; i < 256; i++) fdata.fileContent[i] = (byte)(i - 128);
		
		//simulate file upload
		GraoCallServices.uploadFile(fdata);
		
		//get person info as JSON String
		String jsonPersonInfo = callservices.getPersonInfoAsString(ein, GraoFlagConstants.JSON_NOT_NULL_PROPERTIES);
		System.out.println(jsonPersonInfo);
		
		//Convert JSON String to IPersonInfoProperties data structure
	    IPersonInfoProperties pp = GraoPersonInfoToPersonProp.getPersonInfoProperties(jsonPersonInfo);
	    
	    //convert to IGraoPersonInfo data structure 
	    IGraoPersonInfo pInfo = GraoUtils.getGraoPersonInfo(pp);
	    return pInfo;
	}*/
    
  
    
    
    public  String getGraoinitError(){
    	return graoinitError;
    }


	
   /* public static boolean isInitted() {
		return initted;
	}*/
	
}
