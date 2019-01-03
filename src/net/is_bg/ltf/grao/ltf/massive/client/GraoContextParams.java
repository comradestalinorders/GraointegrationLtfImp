package net.is_bg.ltf.grao.ltf.massive.client;
import com.cc.rest.client.enumerators.IPARAM;
import com.cc.rest.client.enumerators.IREST_PATH;

class GraoContextParams {
	 //grao constants
    public final static String GRAO_CLIENT_CONFIGURATION_NAME = "graoClientConfiguration";
	public static final String CONTEXT_GRAO_ENDPOINT = "graoEndpoint";
	public static final String CONTEXT_GRAO_KEYSTORE_TYPE = "graoKeystoreType";
	public static final String CONTEXT_GRAO_KEYSTORE_PASSWORD = "graoKeystorePassword";
	public static final String CONTEXT_GRAO_KEYSTORE_FILE = "graoKeystoreFile";
	public static final String CONTEXT_GRAO_PRIVATE_KEY_PASSWORD = "graoPrivateKeyPassword";
	public static final String CONTEXT_GRAO_PRIVATE_KEY_ALIAS = "graoPrivateKeyAlias";
	public static final String FILE_NAME = "filename";
	
	//===================== PRIVATE CONSTANTS ==================
	private static final String PERSON_INFO = "personinfo";
	private static final String VERSION_INFO = "versioninfo";
	private static final String FORM_DATA = "formdata";
	private static final String FILE_UPLOAD = "fileupload";
	private static final String PROCESSED_FILES_NAMES = "processedFilesName";
	private static final String PROCESSED_FILE_PATH = "processedFilePath";
	private static final String PROCESSED_FILE_CNT = "processedFileCnt";
	private static final String GRAO_REGIX_TEST = "grao/regix/test";
	private static final String JQUERY = "jquery";
	private static final String HELP = "help";
	private static final String WIN_ERROR = "winErrors";
	private static final String EMPTY_FILE = "emptyfile";
	private static final String PRCESSED_FILE_PATH = "processedFilePath";
	    //================= END OF CONSTANTS ================
	
	/**
	 * The path param person info
	 * @author lubo
	 *
	 */
	enum GRAO_PATH implements IREST_PATH{
		PERSON_INFO(GraoContextParams.PERSON_INFO),
		FILE_UPLOAD(GraoContextParams.FILE_UPLOAD),
		PROCESSED_FILE_PATH(GraoContextParams.PROCESSED_FILE_PATH),
		PROCESSED_FILE_CNT(GraoContextParams.PROCESSED_FILE_CNT),
		EMPTY_FILE_UPLOAD(GraoContextParams.EMPTY_FILE),
		PROCESSED_FILES_NAMES(GraoContextParams.PROCESSED_FILES_NAMES);
		
		
		String value;
		
		GRAO_PATH(String s){
			this.value =s;
		}

		@Override
		public String getPath() {
			return  value;
		}
	}
	
	/***
	 * Creates IREST_PATH path from path & subpath!
	 * @author Lubo
	 *
	 */
	static class PATH_SUBPATH implements IREST_PATH{
		String path;
		String subPath;
		
		PATH_SUBPATH(String path, String subPath){
			this.path = path;
			this.subPath = subPath;
		}
		

		@Override
		public String getPath() {
			return path + subPath;
		}
		
	}
	
	/***/
	public static IREST_PATH  getSubPath(GRAO_PATH graoPath, String subPath) {
		return new PATH_SUBPATH(graoPath.value, subPath);
	}
	
	
	/***
	 * Query param idn
	 * @author lubo
	 *
	 */
	enum GRAO_PARAM implements IPARAM{
		
		IDN("idn"),
		FLAGS("flags"),
		FILE_NAME("filename");
		
		String value;
		
		GRAO_PARAM(String v){
			this.value = v;
		}
		
		@Override
		public String getStringValue() {
			return value;
		}
		
	}
}
