package net.is_bg.ltf.grao.ltf;

import java.io.IOException;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

//import grao.integration.CheckPersonsFromFile;
//import grao.integration.graodatasource.GraoDatasourceUtils;
import grao.integration.ltf.taxsubject.structure.ITaxSubject;
import grao.integration.structure.iface.IAddressResolver;
import grao.integration.structure.iface.IGraoPersonInfo;
import grao.integration.structure.impl.GraoUtils;
import grao.integration.structure.impl.GraoUtils.ADDRESS_EQUALITY_STRENGTH;
import net.is_bg.ltf.db.common.DBConfig;
import net.is_bg.ltf.db.common.impl.DataSourceConnectionFactoryDrManager;
import net.is_bg.ltf.db.common.impl.logging.LogFactorySystemOut;
import net.is_bg.ltf.db.common.impl.timer.ElapsedTimer;
import net.is_bg.ltf.db.common.impl.visit.VisitEmpty;
import net.is_bg.ltf.db.common.interfaces.IConnectionFactoryX;
import net.is_bg.ltf.db.common.interfaces.timer.IElaplsedTimer;
import net.is_bg.ltf.db.common.interfaces.timer.IElaplsedTimerFactory;
import net.is_bg.ltf.db.common.interfaces.visit.IVisit;
import net.is_bg.ltf.db.common.interfaces.visit.IVisitFactory;

class Application {
	
	
	static SimpleDateFormat df = new SimpleDateFormat("yyyymmdd");
	
	public static class DesktopAppConnectionFactory implements IConnectionFactoryX {
		public Connection getConnection() {
		    return new DataSourceConnectionFactoryDrManager(DbUrls.dBases[9]).getConnection();
		}

		@Override
		public Connection getConnection(String name) {
			return null;
		}
	 }
	    
    public static void initDbConfig(){
    	DBConfig.initDBConfig(new LogFactorySystemOut(), new IVisitFactory() {
    	    public IVisit getVist() {
    		return new VisitEmpty();
    	    }
    	}, new DesktopAppConnectionFactory(), new IElaplsedTimerFactory() {
    	    public IElaplsedTimer getElapsedTimer() {
    		return new ElapsedTimer();
    	    }
    	}, null);
    }
	
	
	public static void main(String [] args) throws ParseException, IOException{
		
		initDbConfig();
		
		//Date d =  df.parse("20111102");
		IAddressResolver atree = AddressTree.getAddressResolver();
		
		//load address trees from data base
		//atree.loadAddressTree();
		
		//MatchingLevelResult resa = atree.fidCityByCityName("Р“Р .РџР�Р Р”РћРџ");
		
		String ress = LoadingUtils.loadCityStreetIds("Р“Р .Р‘РђРќРЇ", "%", true);
		//MatchingLevelResult resa =  atree.resolveCountry("РџРћР›РЁ");
		
		//System.out.println("res = " + resa);
    	List<String> res = null;// CheckPersonsFromFile.readFileContent("einlist.txt");
    	//String line = "3401237358";
    	
    	for(String line :res){
    		//if(line.equals("")) continue;
    		IGraoPersonInfo pinfo = null;// GraoDatasourceUtils.getGraoPersonInfo(line);
    		//if(pinfo == null) continue;
    		
    	    ITaxSubject ts = PublicUtils.getTaxsubject(pinfo);
    	    System.out.println(ts);
    		
    		//compare permanent to current address to see if equal
    		ADDRESS_EQUALITY_STRENGTH st = GraoUtils.equals(pinfo.getPermanentAddress(), pinfo.getCurrentAddress());
    		System.out.println("Addresses equality strength for ein " + pinfo.getPersonalDetails().getEgn() +  " is  "  +  st);
    		atree.resolveAddress(pinfo.getPermanentAddress());
    		atree.resolveAddress(pinfo.getCurrentAddress());
    		//if(st.getValue() > )
    		//atree.resolveCityStreet(graoCityName, graoStreetName)
    		//IAddress ltfadr = atree.resolveAddress(pinfo.getPermanentAddress());
    		//System.out.println(LtfAddress.toStrinProvinceMunCityAdmr(ltfadr));
    	}
		//atree.resolveAddress(graoAddress);
	}
	
}
