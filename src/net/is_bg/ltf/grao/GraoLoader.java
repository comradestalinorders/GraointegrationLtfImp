package net.is_bg.ltf.grao;

import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import grao.integration.ltf.address.structure.IAddress;
import grao.integration.ltf.taxsubject.structure.IPerson;
import grao.integration.ltf.taxsubject.structure.ITaxSubject;
import grao.integration.structure.iface.IAddressResolver;
import grao.integration.structure.iface.IGraoPersonInfo;
import grao.integration.structure.impl.GraoUtils;
import net.is_bg.ltf.AbstractManagedBean;
import net.is_bg.ltf.AppConstants;
import net.is_bg.ltf.AppUtil;
import net.is_bg.ltf.ModalDialogEx;
import net.is_bg.ltf.businessmodels.addresses.Address;
import net.is_bg.ltf.businessmodels.addresses.AddressBean;
import net.is_bg.ltf.businessmodels.cities.City;
import net.is_bg.ltf.businessmodels.municipalities.Municipality;
import net.is_bg.ltf.businessmodels.person.Person;
import net.is_bg.ltf.businessmodels.streets.Street;
import net.is_bg.ltf.businessmodels.taxsubjects.TaxSubjectsBean;
import net.is_bg.ltf.grao.ltf.AddressResolverFactory;
import net.is_bg.ltf.grao.ltf.PublicUtils;
import net.is_bg.ltf.grao.ltf.massive.client.GraoCallServices;
import net.is_bg.ltf.init.ContextParamLoader.CONTEXTPARAMS;

/***
 * Loader class used to load data from grao by user idn!
 * @author Lubo
 *
 */
public class GraoLoader extends   AbstractManagedBean implements IGraoLoader {
	/**
	 * 
	 */
    private static final long serialVersionUID = -257700147081564221L;
	private ModalDialogEx graoModal = new ModalDialogEx();
    private IGraoPersonInfo pInfo;
    private IAddress pAddress;
    private IAddress cAddress;
    private static IAddressResolver addressResolver;
    private static final ResourceBundle MSG_GRAO = ResourceBundle.getBundle(AppConstants.MSG_GRAO);
    private String lastError;
    
    public GraoLoader() {
    	graoModal.setMovable(true);
    	graoModal.setHeight("450");
    	graoModal.setWidth("500");
    	graoModal.setStyle("padding:0px; margin:0px;");
    	graoModal.setTitle(MSG_GRAO.getString("graodata"));
    	graoModal.setPathToInclude("/pages/taxsubject/grao/include/modalinclude.xhtml");
	}

    public void loadGraoData(String idn) {
    	try {
	    	initAddressResolver(); //GraoDatasourceUtils.getGraoPersonInfo(getTaxSubjectIdn());
	    	pInfo = null;
	    	pInfo =  GraoCallServices.getCallServicesForConfigurationName((String)CONTEXTPARAMS.GRAO_CLIENT_CONFIGURATION_NAME.getValue()).getGraoPersonInfo(idn); 
	    	if(pInfo == null) return;
	    	
	    	//resolve addresses
	    	cAddress = addressResolver.resolveAddress(pInfo.getCurrentAddress());
	    	pAddress = addressResolver.resolveAddress(pInfo.getPermanentAddress());
	    	
	    	System.out.println("grao data loaded...");
    	}catch (Exception e) {
    		lastError = AppUtil.exceptionToString(e);
    		throw new RuntimeException(e);
    	}
    }
    
    private static void initAddressResolver() {
    	if(addressResolver!=null) return;
    	synchronized (TaxSubjectsBean.class) {
    		if(addressResolver == null) addressResolver = new AddressResolverFactory().getAddressResolver();
		}
    }
    
   /* public static void refreshAddressResolver() {
    	addressResolver = new AddressResolverFactory().getAddressResolver();
    }*/
    
   public void loadPersonDataFromGraoData(Person p, AddressBean addressBean){
	   ITaxSubject ts = PublicUtils.getTaxsubject(pInfo);
	   if(p == null) return;
	   //s Person p = getRtnObject().getPerson();
	   IPerson ip = ts.getPerson();
	   
	   p.setFirstName(ip.getFirstName());
	   p.setSirName(ip.getSirName());
	   p.setFamilyName(ip.getFamilyName()); 
	   
	   p.setIdentDocNo(ip.getIdentDocNo());
	   p.setDeadDate(ip.getDeadDate());
	   p.setIdentDocPublisher(ip.getIdentDocPublisher());
	   p.setEmissionDate(GraoUtils.parseDate(ip.getEmissionDate(), new SimpleDateFormat("yyyymmdd")));
	   p.setRdvrId(ip.getRdvrId());
	   p.setKindIDN(10);
	   
	  //clearAddresses();
	   
	   //load addresses
	   addressBean.setPresAddress(loadAddressFromGrao(new Address(), cAddress));
	   addressBean.setPermAddress(loadAddressFromGrao(new Address(), pAddress));
	   addressBean.setPostAddress(loadAddressFromGrao(new Address(), pAddress));
	   
	   graoModal.setShowModal(false);
   }
   
   
   private void loadStreeNumbers(Address adr, IAddress graddress) {
	   adr.setNo(GraoUtils.leadingZerosTrim(graddress.getNo()));
	   adr.setBlock(GraoUtils.leadingZerosTrim(graddress.getBlock()));
	   adr.setEntry(GraoUtils.leadingZerosTrim(graddress.getEntry()));
	   adr.setFloor(GraoUtils.leadingZerosTrim(graddress.getFloor()));
	   adr.setApartment(GraoUtils.leadingZerosTrim(graddress.getApartment()));
	   fillAdmRegion(adr, graddress);
   }
   
   private void fillAdmRegion(Address adr, IAddress graddress) {
	   adr.getAdmRegion().setId(graddress.getAdmRegion().getId());
   }
   
   private Address loadAddressFromGrao(Address adr, IAddress graddress){
	   long id = graddress.getStreet().getId();
	   
	   if(id > 0) {
		   adr.setStreetEx((Street)getServiceLocator().getStreetsDao().getStreets(graddress.getStreet().getId()).get(0));
		   loadStreeNumbers(adr, graddress);
		   return adr;
	   }
	   id = graddress.getCity().getId();
	   if( id > 0) {
		   adr.setCityEx((City)getServiceLocator().getCitiesDao().getCities(id).get(0));
		   loadStreeNumbers(adr, graddress);
		   return adr;
	   }
	   
	   id = graddress.getMunicipality().getId();
	   if(graddress.getMunicipality().getId() > 0) {
		   adr.setMunicipalityEx((Municipality)getServiceLocator().getMunicipalitiesDao().getMunicipalities(id).get(0));
		   loadStreeNumbers(adr, graddress);
		   return adr;
	   }
	   id = graddress.getProvince().getId();
	   if(graddress.getProvince().getId() > 0) {
		   adr.setProvinceEx(graddress.getProvince().getId());
		   loadStreeNumbers(adr, graddress);
		   return adr;
	   }
	   
	   return adr;
   }

	public ModalDialogEx getGraoModal() {
		return graoModal;
	}
	
	
	public IGraoPersonInfo getpInfo() {
		return pInfo;
	}
	
	public IAddress getcAddress() {
		return cAddress;
	}
	
	public IAddress getpAddress() {
		return pAddress;
	}

	public String getLastError() {
		return lastError;
	}
	
}
