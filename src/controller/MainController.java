package controller;

import java.io.IOException;
import java.util.ArrayList;
import model.DataAccessor;
import view.UserInterface;

/**
 * Updates the model based on the input received by the view
 * Will call the DataController to make changes to relevant components
 * of the model
 * 
 * @author Nolan Slade
 * @version 1.4
 */
@SuppressWarnings("unused")
public class MainController {
	public static MainController mainController;
	private static DataAccessor dataAccessor;
	private static UserInterface applicationView;
	
	/**
	 * The initiator of Drive Green
	 * Will create the main controller, which will in turn initiate other processes to commence 
	 * program 
	 * 
	 * @param args --
	 * @throws IOException If an error occurs during program initiation with data set reading
	 */
	public static void main(String[] args) {
		try {
			mainController = new MainController();
		} catch (IOException e) {
			System.out.println("An error has occurred during program initiation.");
		}
	}
	
	
	/**
	 * Called on program initiation to set up all necessary components of the application
	 * Will create the view, and instantiate the data accessor.
	 * 
	 * @throws IOException If an error occurs with dataAccessor's functions (data set reading)
	 */
	public MainController () throws IOException {
		// Create the view, instantiate the dataAccessor
		dataAccessor = new DataAccessor();
		applicationView = new UserInterface();
	}
	
	
	/**
	 * Calls the data accessor to update data in the model, then view is told what to show
	 * User data is updated based on new preferences; then results are retrieved from the model
	 * 
	 * @param data The preferences of the user to be set
	 */
	public static void setUserData(String[] data) {
		dataAccessor.setToUser(data);
	}
	
	
	/**
	 * Will return the vehicle results of program execution for display by the view
	 * 
	 * @return An array list of string arrays containing the top 5 vehicle information (make/model/fuel consump/fuel cost/satisfaction)
	 */
	public static ArrayList<String[]> getVehicleResults() {
		return dataAccessor.getVehicleResults();
	}
	

	// ---------
	// User data
	// ----------

	/**
	 * Will set the form factor of vehicle to a new value
	 * 
	 * @param ff The new desired form factor
	 */
	public void setFormFactor(String ff) {
		dataAccessor.setFormFactor(ff);
	}

	/**
	 * Will set the fuel type preference to a new value
	 * 
	 * @param ft The new desired fuel type
	 */
	public void setFuelType(String ft) {
		dataAccessor.setFuelType(ft);
	}

	/**
	 * Will set the model year preference to a new value
	 * 
	 * @param my The new desired model year
	 */
	public void setModelYear(String my) {
		dataAccessor.setModelYear(my);
	}

	/**
	 * Will set the driver type of the user to a new value
	 * 
	 * @param dt The driver type of the user
	 */
	public void setDriverType(String dt) {
		dataAccessor.setDriverType(dt);
	}

	/**
	 * Will set the driver geographic region to a new value
	 * 
	 * @param gr The geographic region of the user
	 */
	public void setGeoRegion(String gr) {
		dataAccessor.setGeoRegion(gr);
	}

	/**
	 * Getter function to return the user's desired form factor
	 * 
	 * @return The form factor of vehicle that the user is interested in
	 */
	public String getFormFactor() {
		return dataAccessor.getFormFactor();
	}

	/**
	 * Getter function to return the user's desired fuel type
	 * 
	 * @return The fuel type that the user desires
	 */
	public String getFuelType() {
		return dataAccessor.getFuelType();
	}

	/**
	 * Getter function to return the user's desired model year
	 * 
	 * @return The model year that the user desires
	 */
	public String getModelYear() {
		return dataAccessor.getModelYear();
	}

	/**
	 * Getter function to return the user's typical driving habits
	 * 
	 * @return The driver type of the user
	 */
	public String getDriverType() {
		return dataAccessor.getDriverType();
	}

	/**
	 * Getter function to return the user's geographic region
	 * 
	 * @return The geographic region of the user
	 */
	public String getGeoRegion() {
		return dataAccessor.getGeoRegion();
	}

	// ------------
	// Program Data
	// ------------

	/**
	 * Getter function to get the state of the program
	 * 
	 * @return The state of the program
	 */
	public static String getProgramState() {
		return dataAccessor.getProgramState();
	}

	/**
	 * Will set the current program state to a new value
	 * 
	 * @param ps The new program state
	 */
	public static void setProgramState(String ps) {
		dataAccessor.setProgramState(ps);
	}
	
	
	/**
	 * Will return the whether or not results have been shown
	 * 
	 * @return True if results have been shown, false otherwise
	 */
	public static boolean getResultsShown() {
		return dataAccessor.getResultsShown();
	}
	
	
	/**
	 * Will set whether or not results have been shown
	 * 
	 * @param nv The new value
	 */
	public static void setResultsShown(boolean nv) {
		dataAccessor.setResultsShown(nv);
	}
}
