package net.is_bg.ltf.grao.ltf.massive.client;

import grao.integration.structure.ws.iface.IFileData;

public class UploadFileData implements IFileData {
	String fileName;
	byte [] fileContent;
	
	public UploadFileData() {
		
	}
	
	public UploadFileData(String fileName, byte[] fileContent) {
		super();
		this.fileName = fileName;
		this.fileContent = fileContent;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getFileContent() {
		return fileContent;
	}

	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}

	
}
