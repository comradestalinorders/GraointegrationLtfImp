package net.is_bg.ltf.grao.ltf;

//class that contains provinces codes such as ekatte, 3 letter digit code, old code ..so on...
class ProvinceCodes{
	private String latinCode;  //3 letter latin code
	private String code;       //two digit code
	private String name;       //province name
	
	public ProvinceCodes(String name, String code, String latincode) {
		this.latinCode = latincode;
		this.code = code;
		this.name = name;
	}
	
	public String getLatinCode() {
		return latinCode;
	}
	public void setLatinCode(String latinCode) {
		this.latinCode = latinCode;
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	/***
	 * Creates provinces codes from comma separated String value in propery file
	 * @param p
	 * @return
	 */
	static ProvinceCodes fromPropertyString(String p){
		String a [] = p.split(",");
		return new ProvinceCodes(a[0], a[1], a[2]);
	}
	
	@Override
	public String toString() {
		return "code:"+ code +  ",name:"+ name +  ",latinCode:" + latinCode ;
	}
}
