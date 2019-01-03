package net.is_bg.ltf.grao.ltf.massive.client;
public class GraoParamsConfig {

	String  graoClientConfiguration;
	String  graoEndpoint;
	String  graoKeystoreType;
	String  graoKeystorePassword;
	String  graoKeystoreFile;
	String  graoPrivateKeyPassword;
	String  graoPrivateKeyAlias;
	
	
	public String getGraoClientConfiguration() {
		return graoClientConfiguration;
	}


	public String getGraoEndpoint() {
		return graoEndpoint;
	}


	public String getGraoKeystoreType() {
		return graoKeystoreType;
	}


	public String getGraoKeystorePassword() {
		return graoKeystorePassword;
	}


	public String getGraoKeystoreFile() {
		return graoKeystoreFile;
	}


	public String getGraoPrivateKeyPassword() {
		return graoPrivateKeyPassword;
	}


	public String getGraoPrivateKeyAlias() {
		return graoPrivateKeyAlias;
	}
	
	@Override
	public String toString() {
		return  "graoClientConfiguration:" + graoClientConfiguration+ "\n"+
				"graoEndpoint:" + graoEndpoint+"\n"+
				"graoKeystoreType:" + graoKeystoreType+"\n"+
				"graoKeystorePassword:" + graoKeystorePassword+"\n"+
				"graoKeystoreFile:" + graoKeystoreFile+"\n"+
				"graoPrivateKeyPassword:" + graoPrivateKeyPassword+"\n"+
				"graoPrivateKeyAlias:" + graoPrivateKeyAlias;
	}


	private GraoParamsConfig(String graoClientConfiguration, String graoEndpoint, String graoKeystoreType,
			String graoKeystorePassword, String graoKeystoreFile, String graoPrivateKeyPassword,
			String graoPrivateKeyAlias) {
		super();
		this.graoClientConfiguration = graoClientConfiguration;
		this.graoEndpoint = graoEndpoint;
		this.graoKeystoreType = graoKeystoreType;
		this.graoKeystorePassword = graoKeystorePassword;
		this.graoKeystoreFile = graoKeystoreFile;
		this.graoPrivateKeyPassword = graoPrivateKeyPassword;
		this.graoPrivateKeyAlias = graoPrivateKeyAlias;
	}


	public static class GraoParamsConfigBuilder{
		String  graoClientConfiguration;
		String  graoEndpoint;
		String  graoKeystoreType;
		String  graoKeystorePassword;
		String  graoKeystoreFile;
		String  graoPrivateKeyPassword;
		String  graoPrivateKeyAlias;
		
		public GraoParamsConfigBuilder setGraoClientConfiguration(String graoClientConfiguration) {
			this.graoClientConfiguration = graoClientConfiguration;
			return this;
		}
		public GraoParamsConfigBuilder setGraoEndpoint(String graoEndpoint) {
			this.graoEndpoint = graoEndpoint;
			return this;
		}
		public GraoParamsConfigBuilder setGraoKeystoreType(String graoKeystoreType) {
			this.graoKeystoreType = graoKeystoreType;
			return this;
		}
		public GraoParamsConfigBuilder setGraoKeystorePassword(String graoKeystorePassword) {
			this.graoKeystorePassword = graoKeystorePassword;
			return this;
		}
		public GraoParamsConfigBuilder setGraoKeystoreFile(String graoKeystoreFile) {
			this.graoKeystoreFile = graoKeystoreFile;
			return this;
		}
		public GraoParamsConfigBuilder setGraoPrivateKeyPassword(String graoPrivateKeyPassword) {
			this.graoPrivateKeyPassword = graoPrivateKeyPassword;
			return this;
		}
		public GraoParamsConfigBuilder setGraoPrivateKeyAlias(String graoPrivateKeyAlias) {
			this.graoPrivateKeyAlias = graoPrivateKeyAlias;
			return this;
		}
		
		public GraoParamsConfig build(){
			return new GraoParamsConfig(graoClientConfiguration, graoEndpoint, graoKeystoreType, graoKeystorePassword, 
					graoKeystoreFile, graoPrivateKeyPassword, graoPrivateKeyAlias);
		}
		
	}
}
