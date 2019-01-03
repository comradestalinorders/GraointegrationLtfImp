package net.is_bg.ltf.grao;


import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import org.joda.time.DateTime;

import net.is_bg.ltf.AppUtil;
import net.is_bg.ltf.DataBaseInfo;
import net.is_bg.ltf.db.common.DBExecutor;
import net.is_bg.ltf.grao.ltf.massive.client.GraoCallServices;
import net.is_bg.ltf.grao.ltf.massive.client.GraoConfigBean;
import net.is_bg.ltf.grao.ltf.massive.client.GraoDao;
import net.is_bg.ltf.grao.ltf.massive.client.GraoMassiveModeClient;
import net.is_bg.ltf.grao.ltf.massive.client.GraoParamsConfig;
import net.is_bg.ltf.init.ContextParamLoader.CONTEXTPARAMS;
import net.is_bg.ltf.init.ScheduledTaskCreatorBase;
import net.is_bg.ltf.update.register.dao.DataSourceServiceConnectionFactory;
import net.is_bg.ltf.util.ContextUtils;
import taskscheduler.Hour;
import taskscheduler.ITaskInfoGetter;
import taskscheduler.ITaskNotifier;
import taskscheduler.ITaskProgressSetter;
import taskscheduler.Interval;
import taskscheduler.ScheduledTask;
import taskscheduler.ScheduledTask.ScheduledTaskBuilder;
import taskscheduler.TaskEnums.DAY_OF_WEEK;
import taskscheduler.TaskUtils;
import taskscheduler.ThreadPoolTaskScheduler;
import taskscheduler.TimeInterval;

public class InitGraoClientLib implements IInitGraoClient {
	
	private final static String GRAO_CLIENT_CONFIGURATION_NAME = (String)CONTEXTPARAMS.GRAO_CLIENT_CONFIGURATION_NAME.getValue();
    public static  final String GRAO_MASSIVE_MODE = "GRAO_MASSIVE_MODE";
    //map with references to ScheduledTask for each data base- the key is datasourcename
    private static Map<String, ScheduledTask> shceduledTasksForDatabases = new HashMap<String, ScheduledTask>();
	 
	static Collection<DataBaseInfo> dbases = null;
	static {
			try {
				dbases = ContextUtils.getDbConnections().values();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (NamingException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	
	public static ScheduledTask getScheduledTaskbyDsName(String dsName) {
		return shceduledTasksForDatabases.get(dsName);
	}
	
	    
    /***
     * Initialize Grao COM client using parameters in server xml!
     */
    private static void init() {
    	        GraoCallServices.init(new GraoParamsConfig.GraoParamsConfigBuilder()
    			.setGraoClientConfiguration((String)CONTEXTPARAMS.GRAO_CLIENT_CONFIGURATION_NAME.getValue())
    			.setGraoEndpoint((String)CONTEXTPARAMS.GRAO_ENDPOINT.getValue())
    			.setGraoKeystoreFile((String)CONTEXTPARAMS.GRAO_KEYSTORE_FILE.getValue())
    			.setGraoKeystorePassword((String)CONTEXTPARAMS.GRAO_KEYSTORE_PASSWORD.getValue())
    			.setGraoKeystoreType((String)CONTEXTPARAMS.GRAO_KEYSTORE_TYPE.getValue())
    			.setGraoPrivateKeyAlias((String)CONTEXTPARAMS.GRAO_PRIVATEKEY_ALIAS.getValue())
    			.setGraoPrivateKeyPassword((String)CONTEXTPARAMS.GRAO_PRIVATEKEY_PASSWORD.getValue())
    			.build());
    }
    
   static class GraoScheduledTaskCreator extends ScheduledTaskCreatorBase{
		private    GraoDao dao;
		
		private GraoScheduledTaskCreator(DataBaseInfo info){
			dao = new GraoDao(info);
			this.info  = info;
			updateInterval = 30;      //2 min
			startFrom = new  Hour(0, 0, 0, 0);
			startTo = new  Hour(2, 59, 0, 0);
			reg = dao.getGraoUpdaterReg(GRAO_MASSIVE_MODE);
			if(reg == null) reg = getInitReg();
		}
		
		public void doWork(ITaskProgressSetter taskProgressSetter,
				ITaskInfoGetter taskinfoGetter, ITaskNotifier notifier) {
			synchronized(GraoConfigBean.class) {
				if(GraoConfigBean.getSemaphore().isSignalled()) throw new RuntimeException("Massive update has been started manually..");
			}
			
			DBExecutor ex = new DBExecutor(new DataSourceServiceConnectionFactory(info));
			
			try {
				GraoMassiveModeClient.graoMassiveUpdate(ex, GRAO_CLIENT_CONFIGURATION_NAME);
			} catch (IOException e) {
				String m = AppUtil.exceptionToString(e);//e.printStackTrace();
				throw new RuntimeException(m);
			}
		}
		

		public void OnCrash(ITaskInfoGetter taskinfoGetter, ITaskNotifier notifier, Exception e) {
			//dao.log(getErrorInitLog(taskinfoGetter.getTaskIno().getProgress().getStartTime(), "ERROR", AppUtil.exceptionToString(e), "graomassivemode.processfile"));
		}
		
		@Override
		public void OnCompleteSuccessfuly(ITaskInfoGetter arg0, ITaskNotifier arg1) {
			//super.OnCompleteSuccessfuly(arg0, arg1);
			
		}

		
		/**Create grao update task for one database!!!*/
		public static ScheduledTask createGraoScheduledTask(DataBaseInfo info){
			GraoScheduledTaskCreator cb =	new GraoScheduledTaskCreator(info);
			DateTime from = cb.reg.getStartFrom();
			DateTime to = cb.reg.getStartTo();
			List<Interval> in = new ArrayList<Interval>();
			Hour startFrom = new Hour(from.getHourOfDay(), from.getMinuteOfHour(), from.getSecondOfMinute(), 0);
			Hour startTo = new  Hour(to.getHourOfDay(), to.getMinuteOfHour(), to.getSecondOfMinute(), 0);
			TimeInterval.normalizeInterval(new TimeInterval(startFrom, startTo), in);
			ScheduledTaskBuilder bd = ScheduledTask.getScheduledTaskBuilder();
			bd.setTaskScheduleDays(DAY_OF_WEEK.EVERY_DAY.getDay()).setMinTimeOutSincelastRun(cb.delayUnit.toMillis(cb.updateInterval)).
			setIntervals(in).setName("Grao ScheduledTask - " + info.getTns());
			ScheduledTask t =  bd.build(cb);
			TaskUtils.printTaskConfiguration(t);
			return t;
		}
		
		/**Create grao update tasks for all databases!!!*/
		public static List<ScheduledTask> createGraoScheduledTasks(Collection<DataBaseInfo> dbic){
			List<ScheduledTask> tasks = new  ArrayList<ScheduledTask>();
			Iterator<DataBaseInfo> it = dbic.iterator();
			while(it.hasNext()){
				DataBaseInfo  ii =	 it.next();
				ScheduledTask t = createGraoScheduledTask(ii);
				tasks.add(t);
				shceduledTasksForDatabases.put(ii.getDefDbCon(), t);
			}
			return tasks;
		}
	}
	
	
	//initialize Grao client and add grao tasks
	private  void initGraoClient(ThreadPoolTaskScheduler sch) {
		try {
			init();
			
			ThreadPoolTaskScheduler ts = sch;// InitTaskScheduler.getThreadPoolTaskScheduler();
			if(ts!=null) {
				List<ScheduledTask> t = GraoScheduledTaskCreator.createGraoScheduledTasks(dbases);
				for(ScheduledTask st:t) {
					ts.submitTask(st);
					System.out.println("GRAO SCHEDULED TASK " + st.getTaskName() + " added to scheduler..." );
				}
			}
			
		}catch (Exception e) {
			System.out.println("=========== GRAO MASSIVE MODE INIT FAILED ========");
			e.printStackTrace();
		}
	}
	
	@Override
	public void init(ThreadPoolTaskScheduler sc) {
		initGraoClient(sc);
	}

}
