package model;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * To maintain low coupling, the controller will access model components through
 * this class. Through this class, fuel, program, user, satisfaction, and vehicle
 * data will be initialized, retrieved and modified, all while preserving
 * encapsulation.
 * 
 * @author Nolan Slade
 * @version 1.5
 */
public class DataAccessor {
	private FuelData fuelData;					// Data components are hidden from the controller
	private ProgramData programData;			// and are instead accessed from this class
	private UserData userData;
	private SatisfactionData satisfactionData;
	private VehicleData vehicleData;
	private ArrayList<String[]> vehicleResults;
	
	
	/**
	 * Constructor for the data accessor
	 * Creates objects which do not require user preferences, including programData, vehicleData
	 * and fuelData and initiates their sub routines
	 * 
	 * @throws IOException If an error occurs during object creation, likely from file reading
	 */
	public DataAccessor() throws IOException {
		// These can happen now because they don't require initial user input/preferences
		programData = new ProgramData("Awaiting User");
		vehicleData = new VehicleData();
		fuelData = new FuelData();
	}

	/**
	 * Will customize the application given the user's preferences
	 * Specified format of parameter is form factor, fuel type, driver type,
	 * model year, geographic region. The function will then call all necessary
	 * procedures on the data to achieve a result for the user.
	 * 
	 * @param userPref An array of strings holding user preferences in specified format
	 */
	public void setToUser(String[] userPref) {
		try {
			setUserData(userPref);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("\nAn exception has occurred during computation.");
		}
	}
	
	private void setUserData(String[] userPref) throws IOException {
		userData = null;
		assert(userPref.length == 5);
		userData = new UserData(userPref[0], userPref[1], userPref[2], userPref[3], userPref[4]);

		// Move to next step, where we narrow our data
		vehicleData.trimData(getModelYear(), getFuelType(), getFormFactor());
		fuelData.trimData(getFuelType(), getGeoRegion());
		
		// Sort the data before we can analyze it
		vehicleData.sortData(getDriverType());
		
		// Compute a value for each vehicle in the two sorted lists in order to represent its combined
		// Environmental standing, and use this to produce the top five vehicles
		vehicleData.computeTopFive();
		ArrayList<String[]> results = vehicleData.getTopFive();
		
		// Use the top five vehicle information to construct estimates on fuel spending for each of the vehicles according to driver type
		String[] automakers = new String[5];
		double[] fuelConsumps = new double[5];
		
		fuelData.analyzeData();
		
		int index;
		
		// Match user's driver type in order to determine which data we are to output
		if (getDriverType().toUpperCase().equals("CITY")) {
			index = 2;
		} else if (getDriverType().toUpperCase().equals("HIGHWAY")) {
			index = 3;
		} else {
			index = 4;
		}
		
		for (int i = 0; i < fuelConsumps.length; i++) {
			if (results.size() == 0) {
				fuelConsumps[i] = 0.0;
			} else if (results.get(i) == null) {
				fuelConsumps[i] = 0.0;
			} else {
				fuelConsumps[i] = Double.parseDouble(results.get(i)[index]);
			}
		}
		
		double[] fuelSpending = fuelData.getRates(fuelConsumps, getDriverType());
	
		// Use the top five vehicle information to construct a list of the top automakers, which will be analyzed for consumer satisfaction
		assert(results.size() == 5);
		for (int i = 0; i < results.size(); i++) {
			automakers[i] = results.get(i)[0].toUpperCase();
		}
		
		// Compute the satisfaction data for the automakers
		satisfactionData = new SatisfactionData(automakers);
		double[] ratings = satisfactionData.getRatings();
		
		// Construct results using all data, then end
		vehicleResults = null;
		vehicleResults = new ArrayList<String[]>();
		DecimalFormat df = new DecimalFormat("#.00"); 
		
		// Add our final results to the ArrayList and catch possible null entries to avoid errors
		for (int i = 0; i < 5; i++) {
			if (automakers[i] == null) {
				String[] temp = {"", "", "0.00", "0.00", "0.00"};
				vehicleResults.add(temp);
			} else {
				//				Make			Model			Fuel Consumption			Annual Fuel Cost			Satisfaction
				String[] temp = {automakers[i], results.get(i)[1], results.get(i)[index], df.format(fuelSpending[i]), df.format(ratings[i]*100)};
				vehicleResults.add(temp);
			}
		}	
		programData.setProgramState("Results Found");
	}
	
	
	/**
	 * Will return the vehicle results of program execution for display by the view
	 * 
	 * @return An array list of string arrays containing the top 5 vehicle information (make/model/fuel consump/fuel cost/satisfaction)
	 */
	public ArrayList<String[]> getVehicleResults() {
		return vehicleResults;
	}
	
	// ********************************************************************************************
	// ---------
	// User data
	// ----------

	/**
	 * Will set the form factor of vehicle to a new value
	 * 
	 * @param ff The new desired form factor
	 */
	public void setFormFactor(String ff) {
		userData.setFormFactor(ff);
	}

	/**
	 * Will set the fuel type preference to a new value
	 * 
	 * @param ft The new desired fuel type
	 */
	public void setFuelType(String ft) {
		userData.setFuelType(ft);
	}

	/**
	 * Will set the model year preference to a new value
	 * 
	 * @param my The new desired model year
	 */
	public void setModelYear(String my) {
		userData.setModelYear(my);
	}

	/**
	 * Will set the driver type of the user to a new value
	 * 
	 * @param dt The driver type of the user
	 */
	public void setDriverType(String dt) {
		userData.setDriverType(dt);
	}

	/**
	 * Will set the driver geographic region to a new value
	 * 
	 * @param gr The geographic region of the user
	 */
	public void setGeoRegion(String gr) {
		userData.setGeoRegion(gr);
	}

	/**
	 * Getter function to return the user's desired form factor
	 * 
	 * @return The form factor of vehicle that the user is interested in
	 */
	public String getFormFactor() {
		return userData.getFormFactor();
	}

	/**
	 * Getter function to return the user's desired fuel type
	 * 
	 * @return The fuel type that the user desires
	 */
	public String getFuelType() {
		return userData.getFuelType();
	}

	/**
	 * Getter function to return the user's desired model year
	 * 
	 * @return The model year that the user desires
	 */
	public String getModelYear() {
		return userData.getModelYear();
	}

	/**
	 * Getter function to return the user's typical driving habits
	 * 
	 * @return The driver type of the user
	 */
	public String getDriverType() {
		return userData.getDriverType();
	}

	/**
	 * Getter function to return the user's geographic region
	 * 
	 * @return The geographic region of the user
	 */
	public String getGeoRegion() {
		return userData.getGeoRegion();
	}

	// ------------
	// Program Data
	// ------------

	/**
	 * Getter function to get the state of the program
	 * 
	 * @return The state of the program
	 */
	public String getProgramState() {
		return programData.getProgramState();
	}


	/**
	 * Will set the current program state to a new value
	 * 
	 * @param ps The new program state
	 */
	public void setProgramState(String ps) {
		programData.setProgramState(ps);
	}
	
	
	/**
	 * Will return the whether or not results have been shown
	 * 
	 * @return True if results have been shown, false otherwise
	 */
	public boolean getResultsShown() {
		return programData.getResultsShown();
	}
	
	
	/**
	 * Will set whether or not results have been shown
	 * 
	 * @param nv The new value
	 */
	public void setResultsShown(boolean nv) {
		programData.setResultsShown(nv);
	}
}
