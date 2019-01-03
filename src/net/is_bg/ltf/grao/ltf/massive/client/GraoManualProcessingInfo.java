package net.is_bg.ltf.grao.ltf.massive.client;

/**
 * Information about the status of the running manual update!!!
 * @author Lubo
 *
 */
class GraoManualProcessingInfo {

	private String info = ""; 
	private String newLine = "\n";
	
	GraoManualProcessingInfo(){
		
	}
	
	GraoManualProcessingInfo(String newLine){
		this.newLine = newLine;
	}
	
	public synchronized String getInfo() {
		return info;
	}
	
	public synchronized void addInfo(String s) {
		info+=(s + newLine);
	}
	
	public synchronized void clearInfo() {
		info = "";
	}
}
