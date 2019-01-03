package net.is_bg.ltf.grao.ltf;

import java.text.SimpleDateFormat;

import grao.integration.ltf.taxsubject.structure.ITaxSubject;
import grao.integration.structure.iface.IGraoPersonDoc;
import grao.integration.structure.iface.IGraoPersonInfo;
import grao.integration.structure.iface.IGraoPersonalDetails;
import grao.integration.structure.impl.GraoUtils;

public class PublicUtils {
	static SimpleDateFormat df = new SimpleDateFormat("yyyymmdd");
	
	public static ITaxSubject getTaxsubject(IGraoPersonInfo graoPersoninfo){
		TaxSubject ts = fillMainProperties(graoPersoninfo);
		return ts;
	}
	
	static TaxSubject fillMainProperties(IGraoPersonInfo graoPersoninfo){
		TaxSubject ts = new TaxSubject();
		Person p = ts.person;
		IGraoPersonDoc doc = graoPersoninfo.getPersonDoc();
		IGraoPersonalDetails pd =  graoPersoninfo.getPersonalDetails();
		
		//
		p.docNo = doc.getDocNumber();
		p.emmisionDate = doc.getDocDate();
		p.docPublisher = doc.getDocRPU();
		
		
		p.firtsName = pd.getFirstName();
		p.sirnName = pd.getMiddleName();
		p.familyName = pd.getLastName();
		p.deadDate = GraoUtils.parseDate(pd.getDeathDate(), df);
		ts.idn = pd.getEgn();
		
		return ts;
	}
}
