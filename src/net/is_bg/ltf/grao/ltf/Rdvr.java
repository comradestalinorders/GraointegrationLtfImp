package net.is_bg.ltf.grao.ltf;

// rdvr model
class Rdvr extends NameIdParent{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8131828009528741657L;
	
	private String code;
	
	long getRdvrId(){
		return id;
	}
	
	void setRdvrId(long id){
		this.id = id;
	}
	
	void setCityId(long cityId){
		this.parentId = cityId;
	}
	
	long getCityId(){
		return parentId;
	}
	
	@Override
	public String getName() {
		return super.getName();
	}

	void setName(String name){
		this.name = name;
	}
	
	void setCode(String code) {
		this.code = code;
	}
	
	String getCode() {
		return code;
	}
	
	@Override
	public String toString() {
		return "id:" + id +", code: "+ code +  ",name:"+ name +  ",city_id:" + parentId ;
	}
}
