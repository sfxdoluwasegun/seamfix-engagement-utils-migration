/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ng.autotopup.engagement_utils;

import java.io.File;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ng.autotopup.engagement.hsdp.SmsMtnService;
import ng.autotopup.engagement_utils.model.DelayedSchedule;

@Stateless(name = "EngUtils")
public class Utils {

	@EJB(lookup = "java:global/hsdp-engagement/SmsMtnService")
	private SmsMtnService smsMtnService ;
	
	@Inject
	private ApplicationBean appbean ;
	
	@Inject
	private PropertiesManager props ;
	
	@PersistenceContext
	private EntityManager entityManager ;
	
	/**
	 * Determine if message should be forwarded at this time or scheduled for later.
	 * 
	 * @param message message to be sent via SMS
	 * @param MSISDN subscriber unique MSISDN
	 */
	@Asynchronous
	public void handleEngagement(String message, String MSISDN){
		
		if (appbean.isEngaging())
			smsMtnService.sendSms(message, MSISDN);
		else
			scheduleMessageForLater(props.getLong("engagement-delay-hours", 11L), message, MSISDN);
	}
	
	/**
	 * Determine if message should be forwarded at this time or not at all.
	 * Used particularly for status update notifications.
	 * 
	 * @param message message to be sent via SMS
	 * @param MSISDN subscriber unique MSISDN
	 */
	@Asynchronous
	public void handleReportEngagement(String message, String MSISDN){
		
		if (appbean.isEngaging())
			smsMtnService.sendSms(message, MSISDN);
	}

	/**
	 * Create postponed schedule for engagement.
	 * 
	 * @param increment number of hours to shift back engagement
	 * @param message engagement message
	 * @param MSISDN subscriber to be engaged
	 */
	private void scheduleMessageForLater(long increment, String message, String MSISDN) {
		// TODO Auto-generated method stub
		
		DelayedSchedule delayedSchedule = new DelayedSchedule();
		delayedSchedule.setMessage(message);
		delayedSchedule.setMsisdn(MSISDN);
		delayedSchedule.setTimestamp(Timestamp.valueOf((LocalDateTime.now().plusHours(increment)).truncatedTo(ChronoUnit.HOURS)));
		
		entityManager.persist(delayedSchedule);
	}
	
	/**
	 * Store current DateModified property of file.
	 */
	public void cachePropetiesFileTimeStamp(){
		
		LocalDateTime localDateTime = getPropertiesFileCurrentModificationDate();
		appbean.setLastModifed(localDateTime);
	}
	
	/**
	 * Retrieved current Date Modified property of file.
	 * 
	 * @return {@link LocalDateTime} representing time-stamp of lastModified property of file
	 */
	private LocalDateTime getPropertiesFileCurrentModificationDate() {
		// TODO Auto-generated method stub
		
		File file = new File(System.getProperty("jboss.home.dir") + "/app.properties");
		long milliseconds = 0;
		
		if (file.exists())
			milliseconds = file.lastModified();
		
		if (milliseconds == 0)
			return LocalDateTime.now();
		
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(milliseconds), ZoneId.systemDefault());
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void checkPropertiesFile() {
		// TODO Auto-generated method stub
		
		LocalDateTime lastModified = appbean.getLastModifed();
		LocalDateTime currentTimestamp = getPropertiesFileCurrentModificationDate();
		
		if (currentTimestamp.isAfter(lastModified)){
			appbean.setLastModifed(currentTimestamp);
			props.loadProperties();
		}
	}

}