package net.is_bg.ltf.grao.ltf.massive.client;

/*
import java.sql.Connection;
import net.is_bg.ltf.db.common.ConnectionProperties;
import net.is_bg.ltf.db.common.DBConfig;
import net.is_bg.ltf.db.common.impl.DataSourceConnectionFactoryDrManager;
import net.is_bg.ltf.db.common.impl.logging.LogFactorySystemOut;
import net.is_bg.ltf.db.common.impl.timer.ElapsedTimer;
import net.is_bg.ltf.db.common.impl.visit.VisitEmpty;
import net.is_bg.ltf.db.common.interfaces.IConnectionFactoryX;
import net.is_bg.ltf.db.common.interfaces.timer.IElaplsedTimer;
import net.is_bg.ltf.db.common.interfaces.timer.IElaplsedTimerFactory;
import net.is_bg.ltf.db.common.interfaces.visit.IVisit;
import net.is_bg.ltf.db.common.interfaces.visit.IVisitFactory;

public class ConnectionHelper {
    private final static String ORCL_DRIVER = "oracle.jdbc.OracleDriver";
    private final static String PGR_DRIVER = "org.postgresql.Driver";
    public final static String URL_PGR_BRC = "digest:jdbc:postgresql://10.240.110.120:5432/brc";
    public final static String URL_PGR_MDT = "digest:jdbc:postgresql://10.240.44.129:5432/mdt";
    public final static String URL_ORC_SF_129 = "jdbc:oracle:thin:@10.240.44.129:1521:ORCL";
    public final static String URL_ORC_SF_146 = "jdbc:oracle:thin:@10.240.44.146:1521:orcl";
    public final static String URL_LOCAL = "jdbc:oracle:thin:@localhost:1521:ltf";
    public final static String URL_PGR_149 = "jdbc:postgresql://10.240.44.149:5432/bnk11";
    public final static String URL_PGR_LOCLHOST = "jdbc:postgresql://localhost:5432/bnk";
    public final static String URL_PGR_PDV_70 = "jdbc:postgresql://10.240.110.70:5432/pdv";
    public final static String URL_PGR_PDV_BANITE = "jdbc:postgresql://10.240.110.70:5432/banite";
    public final static String URL_PGR_SOF_184 = "jdbc:postgresql://10.240.44.184:5432/sofiamerge3";
    public final static String URL_PGR_SOF_244 = "jdbc:postgresql://10.240.44.244:5432/sofiamerge3";
    public final static String URL_PGR_LOVETCH = "jdbc:postgresql://10.240.1.81:5432/lovech";
    
    public static ConnectionProperties[] dBases = { 
    	new ConnectionProperties(ORCL_DRIVER, URL_ORC_SF_129, "brc", "brc", "orlc_brc_129"), // 0
	    new ConnectionProperties(ORCL_DRIVER, URL_ORC_SF_129, "krp", "krp", "orlc_krp_129"), // 1
	    new ConnectionProperties(ORCL_DRIVER, URL_ORC_SF_146, "brc", "brc", "orlc_brc_146"), // 2
	    new ConnectionProperties(ORCL_DRIVER, URL_ORC_SF_146, "sdk", "sdk", "orlc_sdk_146"), // 3
	    new ConnectionProperties(PGR_DRIVER, URL_PGR_BRC, "mdt", "mdt", "pgr_brc_129"), // 4
	    new ConnectionProperties(ORCL_DRIVER, URL_LOCAL, "brc", "brc", "pgr_brc_129"), // 5
	    new ConnectionProperties(ORCL_DRIVER, URL_PGR_MDT, "mdt", "mdt", "pgr_mdt_129"), // 6
	    new ConnectionProperties(PGR_DRIVER, URL_PGR_149, "bnk", "Bnk12345", "pgr_bnk_149"), // 7
	    new ConnectionProperties(PGR_DRIVER, URL_PGR_LOCLHOST, "bnk", "Bnk12345", "localhost_pgr"), // 8
	    new ConnectionProperties(PGR_DRIVER, URL_PGR_PDV_70, "pdv", "pdv", "pdv10.240.110.7"), // 9
	    new ConnectionProperties(PGR_DRIVER, URL_PGR_SOF_184, "sofia2013", "12345", "sofia184"),//10
	    new ConnectionProperties(PGR_DRIVER, URL_PGR_PDV_70, "pdv", "pdv", "banite10.240.110.7"),  //11
	    new ConnectionProperties(PGR_DRIVER, URL_PGR_SOF_244, "sofia2013", "12345", "sofiamerge3_244"),  //12
	    new ConnectionProperties(PGR_DRIVER, URL_PGR_LOVETCH, "sofia2013", "12345", "lovech"),  //13
    };
    
    
    public static void initDb() {
    	DBConfig.initDBConfig(new LogFactorySystemOut(), new IVisitFactory() {
			@Override
			public IVisit getVist() {
				return new VisitEmpty();
			}
		}, new IConnectionFactoryX() {
			@Override
			public Connection getConnection() {
				return new DataSourceConnectionFactoryDrManager(dBases[11]).getConnection();
			}
			
			@Override
			public Connection getConnection(String arg0) {
				return this.getConnection();
			}
		}, new IElaplsedTimerFactory() {
			public IElaplsedTimer getElapsedTimer() {
				return new ElapsedTimer();
			}
		
		}, null);
    }
    
}*/
