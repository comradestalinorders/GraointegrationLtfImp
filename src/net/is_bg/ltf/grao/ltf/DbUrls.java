package net.is_bg.ltf.grao.ltf;

import net.is_bg.ltf.db.common.ConnectionProperties;

class DbUrls {
	 // FIELDS OF INTEREST

    /** drivers. */
    private final static String ORCL_DRIVER = "oracle.jdbc.OracleDriver";

    /** The Constant PGR_DRIVER. */
    private final static String PGR_DRIVER = "org.postgresql.Driver";

    /** The Constant PGR_DRIVER_DIGEST. */
    private final static String PGR_DRIVER_DIGEST = "com.is.util.db.driver.digestdriver.DigestDriver";

    /** data bases url's. */
    private final static String URL_PGR_BRC = "digest:jdbc:postgresql://10.240.110.120:5432/brc";

    /** The Constant URL_PGR_MDT. */
    private final static String URL_PGR_MDT = "digest:jdbc:postgresql://10.240.44.129:5432/mdt";

    /** The Constant URL_ORC_SF_129. */
    private final static String URL_ORC_SF_129 = "jdbc:oracle:thin:@10.240.44.129:1521:ORCL";

    /** The Constant URL_ORC_SF_146. */
    private final static String URL_ORC_SF_146 = "jdbc:oracle:thin:@10.240.44.146:1521:orcl";

    /** The Constant URL_LOCAL. */
    private final static String URL_LOCAL = "jdbc:oracle:thin:@localhost:1521:ltf";

    /** УРЛ. */
    private final static String URL_PGR_149 = /* digest: */"jdbc:postgresql://10.240.44.149:5432/bnk11";

    /** The Constant URL_PGR_LOCLHOST. */
    private final static String URL_PGR_LOCLHOST = "jdbc:postgresql://localhost:5432/bnk";

    /** The Constant URL_PGR_PDV_7. */
    private final static String URL_PGR_PDV_7 = "jdbc:postgresql://10.240.110.70:5432/pdv";
    private final static String URL_PGR_PDV_BANITE = "jdbc:postgresql://10.240.110.70:5432/banite";
    

    private final static String URL_PGR_SOF_184 = "jdbc:postgresql://10.240.44.184:5432/sofiamerge3";
    
    private final static String URL_PGR_SOF_244 = "jdbc:postgresql://10.240.44.244:5432/sofiamerge3";

    // .public final static String URL_PGR_LOCLHOST =
    // "digest:jdbc:postgresql://localhost:5432/postgres";

    /** SOME PREDEFINED CONNECTIONS. */
    /** data base connections */
    public static ConnectionProperties[] dBases = { 
    	new ConnectionProperties(ORCL_DRIVER, URL_ORC_SF_129, "brc", "brc", "orlc_brc_129"), // 0
	    new ConnectionProperties(ORCL_DRIVER, URL_ORC_SF_129, "krp", "krp", "orlc_krp_129"), // 1
	    new ConnectionProperties(ORCL_DRIVER, URL_ORC_SF_146, "brc", "brc", "orlc_brc_146"), // 2
	    new ConnectionProperties(ORCL_DRIVER, URL_ORC_SF_146, "sdk", "sdk", "orlc_sdk_146"), // 3
	    new ConnectionProperties(PGR_DRIVER, URL_PGR_BRC, "mdt", "mdt", "pgr_brc_129"), // 4
	    new ConnectionProperties(ORCL_DRIVER, URL_LOCAL, "brc", "brc", "pgr_brc_129"), // 5
	    new ConnectionProperties(ORCL_DRIVER, URL_PGR_MDT, "mdt", "mdt", "pgr_mdt_129"), // 6

	    new ConnectionProperties(PGR_DRIVER_DIGEST, URL_PGR_149, "bnk", "Bnk12345", "pgr_bnk_149"), // 7
													// bankq
													// 149
													// postgre
	    new ConnectionProperties(PGR_DRIVER_DIGEST, URL_PGR_LOCLHOST, "bnk", "Bnk12345", "localhost_pgr"), // 8
													       // postgre
													       // localhost
	    new ConnectionProperties(PGR_DRIVER, URL_PGR_PDV_7, "pdv", "pdv", "pdv10.240.110.70"), // 9
													 // postgre
													 // localhost
	    new ConnectionProperties(PGR_DRIVER, URL_PGR_SOF_184, "sofia2013", "12345", "sofia184"),//10
	    
	    
	    new ConnectionProperties(PGR_DRIVER, URL_PGR_PDV_7, "pdv", "pdv", "banite10.240.110.7"),  //11
	    new ConnectionProperties(PGR_DRIVER, URL_PGR_SOF_244, "sofia2013", "12345", "sofiamerge3_244"),  //12
    };
}
