package ng.autotopup.engagement_utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.annotation.PostConstruct;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Startup
@Singleton
public class ScheduleManager {
	
	@Inject
	private ApplicationBean appbean  ;
	
	@Inject
	private PropertiesManager props ;
	
	@Inject
	private Utils utils ;
	
	private DateTimeFormatter formatter ;
	private DateTimeFormatter dateformatter ;
	
	@PostConstruct
	public void init(){
		
		formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		dateformatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		
		appbean.setCurrentDate(LocalDate.now().format(dateformatter));
	}
	
	@Schedule(hour = "*")
	public void hourlyTasks(){
		
		String startTime = props.getProperty("engagement-start-time", "06:59:59");
		String stopTime = props.getProperty("engagement-end-time", "19:59:59");
		
		String formattedDate = appbean.getCurrentDate();
		
		LocalDateTime start = LocalDateTime.parse(new StringBuffer(formattedDate).append(startTime).toString(), formatter);
		LocalDateTime stop = LocalDateTime.parse(new StringBuffer(formattedDate).append(stopTime).toString(), formatter);
		
		LocalDateTime currentTime = LocalDateTime.now();
		
		if (currentTime.isAfter(start) && currentTime.isBefore(stop))
			appbean.setEngaging(true);
		else
			appbean.setEngaging(false);
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