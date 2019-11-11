package net.is_bg.ltf.grao.ltf;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.imageio.stream.FileImageInputStream;

import net.is_bg.ltf.db.common.ConnectionProperties;
import net.is_bg.ltf.db.common.DBConfig;
import net.is_bg.ltf.db.common.DBExecutor;
import net.is_bg.ltf.db.common.impl.DataSourceConnectionFactoryDrManager;
import net.is_bg.ltf.db.common.impl.logging.LogFactorySystemOut;
import net.is_bg.ltf.db.common.impl.timer.ElapsedTimer;
import net.is_bg.ltf.db.common.impl.visit.VisitEmpty;
import net.is_bg.ltf.db.common.interfaces.IConnectionFactory;
import net.is_bg.ltf.db.common.interfaces.IConnectionFactoryX;
import net.is_bg.ltf.db.common.interfaces.timer.IElaplsedTimer;
import net.is_bg.ltf.db.common.interfaces.timer.IElaplsedTimerFactory;
import net.is_bg.ltf.db.common.interfaces.visit.IVisit;
import net.is_bg.ltf.db.common.interfaces.visit.IVisitFactory;
import net.is_bg.ltf.grao.ltf.massive.client.GraoMassiveModeClient;

 class AppMain {

	public static void main(String [] args) throws IOException {
		//init db config
		ConnectionProperties pr = GraoMassiveModeClient.dBases[14];
		
		initDbConfig(pr);
		
		//create db executor
		DBExecutor ex = new DBExecutor(DBConfig.getConnectionFactory());
		byte [] fileData = loadFile(new File("D:\\grao05_0.grao"));
		GraoMassiveModeClient.processUploadedFileContent(ex, fileData, 0, false);
	}
	
	
	private static void initDbConfig(ConnectionProperties pr) {
		DBConfig.initDBConfig(new LogFactorySystemOut(),new IVisitFactory() {
			@Override
			public IVisit getVist() {
				return new  VisitEmpty();
			}
		}, new FcWrapper(new DataSourceConnectionFactoryDrManager(pr)), new IElaplsedTimerFactory() {
			@Override
			public IElaplsedTimer getElapsedTimer() {
				return new ElapsedTimer();
			}
		}, null);
	}
	
	private static byte[] loadFile(File file) {
		return loadFile(file, (int)file.length());
	}
	
	private static byte[] loadFile(File file, int bufferSize) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		byte [] buffer = new  byte[bufferSize];
		try {
			FileImageInputStream is = new FileImageInputStream(file);
			int len = 0;
			while ((len = is.read(buffer)) > 0) {
				os.write(buffer, 0, len);
			}
			is.close();
			return os.toByteArray();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
		}
	}
	
	
	private static class FcWrapper implements IConnectionFactoryX{
		
		IConnectionFactory cf;
		public FcWrapper(IConnectionFactory cf) {
			this.cf = cf;
		}

		@Override
		public Connection getConnection() {
			return cf.getConnection();
		}

		@Override
		public Connection getConnection(String arg0) {
			return cf.getConnection();
		}
		
	}
}
