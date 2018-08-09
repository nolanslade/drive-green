package model;

/**
 * This part of the model represents the current user's preferences
 * and custom settings
 * Users will supply their desired form factor of vehicle, model year, fuel type
 * as well as their driving type (highway, city, combined)
 * 
 * @author Nolan Slade		001401079
 * @version 1.3
 */
public class UserData {
	private String 	preferredFormFactor;
	private String 	preferredFuelType;
	private String 	driverType;
	private String	preferredModelYear;
	private String 	geoRegion;
	
	/**
	 * Constructor will set the new user's initial preferences
	 * 
	 * @param ff The form factor of vehicle that the user is interested in
	 * @param ft The fuel type of the vehicle that the user is interested in
	 * @param dt The driver type of the user
	 * @param my The preferred model year of the user
	 * @param gr The geographic region of the user
	 */
	protected UserData(String ff, String ft, String dt, String my, String gr) {
		preferredFormFactor = ff;
		preferredFuelType 	= ft;
		driverType 			= dt;
		preferredModelYear 	= my;
		geoRegion 			= gr;
	}
	
	
	/**
	 * Will set the form factor of vehicle to a new value
	 * 
	 * @param ff The new desired form factor
	 */
	protected void setFormFactor(String ff) {
		preferredFormFactor = ff;
	}
	
	
	/**
	 * Will set the fuel type preference to a new value
	 * 
	 * @param ft The new desired fuel type
	 */
	protected void setFuelType(String ft) {
		preferredFuelType = ft;
	}
	
	
	/**
	 * Will set the model year preference to a new value
	 * 
	 * @param my The new desired model year
	 */
	protected void setModelYear(String my) {
		preferredModelYear = my;
	}
	
	
	/**
	 * Will set the driver type of the user to a new value
	 * 
	 * @param dt The driver type of the user
	 */
	protected void setDriverType(String dt) {
		driverType = dt;
	}
	
	
	/**
	 * Will set the driver geographic region to a new value
	 * 
	 * @param gr The geographic region of the user
	 */
	protected void setGeoRegion(String gr) {
		geoRegion = gr;
	}
	
	
	/**
	 * Getter function to return the user's desired form factor
	 * 
	 * @return The form factor of vehicle that the user is interested in
	 */
	protected String getFormFactor() {
		return preferredFormFactor;
	}
	
	
	/**
	 * Getter function to return the user's desired fuel type
	 * 
	 * @return The fuel type that the user desires
	 */
	protected String getFuelType() {
		return preferredFuelType;
	}
	
	
	/**
	 * Getter function to return the user's desired model year
	 * 
	 * @return The model year that the user desires
	 */
	protected String getModelYear() {
		return preferredModelYear;
	}
	
	
	/**
	 * Getter function to return the user's typical driving habits
	 * 
	 * @return The driver type of the user
	 */
	protected String getDriverType() {
		return driverType;
	}
	
	
	/**
	 * Getter function to return the user's geographic region
	 * 
	 * @return The geographic region of the user
	 */
	protected String getGeoRegion() {
		return geoRegion;
	}
}
