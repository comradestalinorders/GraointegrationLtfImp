package net.is_bg.ltf.grao.ltf.massive.client;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import grao.integration.GraoFlagConstants;
import grao.integration.IPersonInfoProperties;
import grao.integration.structure.iface.IAddressResolver;
import grao.integration.structure.iface.IGraoPersonInfo;
import grao.integration.structure.impl.GraoUtils;
import grao.integration.structure.ws.iface.IWsServiceMethods;
import net.is_bg.ltf.grao.ltf.AddressResolverFactory;
import net.is_bg.ltf.grao.ltf.massive.client.GraoParamsConfig.GraoParamsConfigBuilder;

class GraoTest {

	private static IWsServiceMethods cs;
	public static void main(String [] args) throws UnrecoverableKeyException, KeyManagementException, KeyStoreException, NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException{
		
		//init DB
		//ConnectionHelper.initDb();
		
		//initialize client
		cs = init();
		
		//get person info as JSON String
		String jsonPersonInfo = cs.getPersonInfoAsString("9310155820", GraoFlagConstants.JSON_NOT_NULL_PROPERTIES);
		System.out.println(jsonPersonInfo);
		
		//Convert JSON String to IPersonInfoProperties data structure
	    IPersonInfoProperties pp = GraoPersonInfoToPersonProp.getPersonInfoProperties(jsonPersonInfo);
	    
	    //convert to IGraoPersonInfo data structure 
	    IGraoPersonInfo pInfo = GraoUtils.getGraoPersonInfo(pp);
	    
	    System.out.println(pInfo);
	    
	    IAddressResolver r =  new AddressResolverFactory().getAddressResolver();
	    r.resolveAddress(pInfo.getCurrentAddress());
	    r.resolveAddress(pInfo.getPermanentAddress());
	    
		/*//upload list a file with ein list
		GraoCallServices.uploadFile(getUploadFileData(getEinsFromDatabase(null)));*/
	}
	
	
	private static IWsServiceMethods init() {
		net.is_bg.ltf.grao.ltf.massive.client.GraoParamsConfig.GraoParamsConfigBuilder cf = null;
		if(true) {
			cf = new GraoParamsConfig.GraoParamsConfigBuilder();
			cf.setGraoClientConfiguration("graoClientConfiguration");
			//cf.setGraoEndpoint("http://10.240.110.230:80/GraoComService");
			cf.setGraoEndpoint("http://10.240.110.90:9999/GraoComService");
			GraoCallServices.init(cf.build());
		}
		
		return GraoCallServices.getCallServicesForConfigurationName(cf.graoClientConfiguration);
	}
	
	
	
	public static IGraoPersonInfo getGraoPersonInfo(String ein) throws JsonParseException, JsonMappingException, IOException {
		System.out.println("Grao calling service test");
		GraoParamsConfigBuilder cf = null;
		init();
		
		UploadFileData fdata = new UploadFileData();
		fdata.setFileName("test");
		fdata.fileContent = new byte[256];
		for(int i = 0; i < 256; i++) fdata.fileContent[i] = (byte)(i - 128);
		
		//simulate file upload
		cs.uploadFile(fdata);
		
		//get person info as JSON String
		String jsonPersonInfo = cs.getPersonInfoAsString(ein, GraoFlagConstants.JSON_NOT_NULL_PROPERTIES);
		System.out.println(jsonPersonInfo);
		
		//Convert JSON String to IPersonInfoProperties data structure
	    IPersonInfoProperties pp = GraoPersonInfoToPersonProp.getPersonInfoProperties(jsonPersonInfo);
	    
	    //convert to IGraoPersonInfo data structure 
	    IGraoPersonInfo pInfo = GraoUtils.getGraoPersonInfo(pp);
	    return pInfo;
	}
}
