package com.ideas2it.logger;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Logger to manage logs in the application
 *
 * @author Sembiyan
 * @version 1.0  May 09
 */
public class LoggerClass {

	private Logger logger;
	
	public LoggerClass(Class<?> classType) {
		logger = LogManager.getLogger(classType);
	}
	
	/**
	 * Logs the details about debug
	 * @param        
	 */
	public void debugLogger(String message) {
		logger.debug(message);
	}
	
	/**
	 * Logs the infos to the logger
	 * @param        
	 */
	public void infoLogger(String message) {
		logger.info(message);
	}
	
	/**
	 * Logs the warnings to the logger
	 * @param        
	 */
	public void warnLogger(String message) {
		logger.warn(message);
	}
	
	/**
	 * Logs the errors to the logger
	 * @param        
	 */
	public void errorLogger(String message) {
		logger.error(message);
	}
	
	/**
	 * Logs the fatal error to the logger
	 * @param        
	 */
	public void fatalLogger(String message) {
		logger.fatal(message);
	}
}
