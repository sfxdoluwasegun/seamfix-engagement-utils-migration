package ng.autotopup.engagement_utils;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import javax.ejb.AccessTimeout;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;

@Lock(LockType.WRITE)
@Singleton(name = "GlobalBean")
@AccessTimeout(unit = TimeUnit.MINUTES, value = 30)
public class ApplicationBean {
	
	private String currentDate ;
	
	private boolean engaging ;
	
	private LocalDateTime lastModifed ;

	public String getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}

	public boolean isEngaging() {
		return engaging;
	}

	public void setEngaging(boolean engaging) {
		this.engaging = engaging;
	}

	@Lock(LockType.READ)
	public LocalDateTime getLastModifed() {
		return lastModifed;
	}

	public void setLastModifed(LocalDateTime lastModifed) {
		this.lastModifed = lastModifed;
	}

}