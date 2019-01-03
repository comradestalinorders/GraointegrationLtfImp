package net.is_bg.ltf.grao.ltf;

import java.util.Locale;
import java.util.ResourceBundle;

class Bundles {

	static ResourceBundle provinceMap;              //provinces ekatte codes
	static ResourceBundle unitedAdminRegions;       //united regions names in SOFIA
	static ResourceBundle cityUnknownId;			//id of unknown city
	static ResourceBundle admRegionCities;          //cities that have adminregion
	static ResourceBundle twoWordProvinces;         //provinces V.tarnovo & ST. ZAGORA
	
	static void load(){
		Locale bg =  new Locale("bg");
		provinceMap = ResourceBundle.getBundle("grao.provincemap", bg);
		unitedAdminRegions = ResourceBundle.getBundle("grao.unitedadminregions", bg);
		cityUnknownId = ResourceBundle.getBundle("grao.cityunknownid", bg);
		admRegionCities = ResourceBundle.getBundle("grao.admregioncities", bg);
		twoWordProvinces = ResourceBundle.getBundle("grao.twowordprovince", bg);
	}
	
}
