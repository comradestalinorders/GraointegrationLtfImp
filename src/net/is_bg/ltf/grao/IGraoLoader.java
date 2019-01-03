package net.is_bg.ltf.grao;

import grao.integration.ltf.address.structure.IAddress;
import grao.integration.structure.iface.IGraoPersonInfo;
import net.is_bg.ltf.ModalDialogEx;
import net.is_bg.ltf.businessmodels.addresses.AddressBean;
import net.is_bg.ltf.businessmodels.person.Person;

public interface IGraoLoader {

	public ModalDialogEx getGraoModal();
	
	public IGraoPersonInfo getpInfo();
	
	public IAddress getcAddress();
	
	public IAddress getpAddress();

	public String getLastError();
	
	public void loadGraoData(String idn);
	
	public void loadPersonDataFromGraoData(Person p, AddressBean addressBean);
}
