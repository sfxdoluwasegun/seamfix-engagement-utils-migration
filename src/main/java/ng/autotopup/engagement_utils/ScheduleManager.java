package ng.autotopup.engagement_utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.ejb.AccessTimeout;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Startup
@Singleton
@AccessTimeout(unit = TimeUnit.MINUTES, value = 15)
public class ScheduleManager {
	
	@Inject
	private ApplicationBean appbean  ;
	
	@Inject
	private Utils utils ;
	
	private DateTimeFormatter dateformatter ;
	
	@PostConstruct
	public void init(){
		
		dateformatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		
		appbean.setCurrentDate(LocalDate.now().format(dateformatter));
		utils.cachePropetiesFileTimeStamp();
		utils.checkPropertiesFile();
	}
	
	@Schedule(hour = "0")
	public void resetDate(){
		appbean.setCurrentDate(LocalDate.now().format(dateformatter));
	}
	
	/**
	 * Quarter hour schedule for the following:
	 * 1. Refresh application cached setting entries
	 */
	@Schedule(hour = "*", minute = "*/15", persistent = false)
	public void refreshCachedSettings(){
		
		utils.checkPropertiesFile();
	}

}