package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Will hold the data pertaining to fuel types and prices
 * Will also be responsible for analysis of fuel prices
 * resulting in averages, predicted spending, etc
 * 
 * @author Nolan Slade		001401079
 * @author Vyome Kishore	001406201
 * @version 1.4
 */
public class FuelData {
	private ArrayList<String[]> mainDataSet; 		// The full data set, read directly from file containing all fuel data
	private ArrayList<String[]> reducedDataSet; 	// The reduced data set which will contain only relevant fuel data
	private final String CURRENTYEAR = "16";		// Our current year
	private double predictedRate; 					// We'll use this for each car in the top 5 to show predicted fuel spending
	private double[] drivingAmount = {15000.00, 25000.00, 20000.00}; 	// A hard-coded estimation on how much a given driver will drive in a year
																		// Index 0 = city driver, 1 = highway driver, 2 = combined driver
																		// Found by researching online, and giving a rough estimate
	
	// Used to match relevant data in the data set to user preferences
	private String[][] fuelArr = {
									{"Regular gasoline","Regular unleaded gasoline at full service filling stations", "Regular unleaded gasoline at self service filling stations"},
									{"Premium gasoline","Premium unleaded gasoline at full service filling stations","Premium unleaded gasoline at self service filling stations"},
									{"Diesel", "Diesel fuel at full service filling stations", "Diesel fuel at self service filling stations"},
									{"Ethanol", "Not Applicable -> Add into dataset manually"},
									{"Natural Gas", "Not applicable -> Add into dataset manually"}
								};
	
	/**
	 * Will call for the initialization of the data reading process
	 * The data read from the gasPrices.csv file will be stored
	 * into the main data set
	 * 
	 */
	public FuelData () throws IOException {
		mainDataSet = null;
		mainDataSet = new ArrayList<String[]>();
		initialize();
	}
	
	
	/**
	 * Initializes the filling of the data sets
	 * Run at the start of the program during initialization
	 * 
	 */
	private void initialize() throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(new File("data/gasPrices.csv")));
		String line;
		while ((line = in.readLine()) != null) {

			// use comma as separator
			String[] row = line.split(",");
			mainDataSet.add(row);
		}

		in.close();
	}
	
	
	/**
	 * Reduce our data set to only relevant data
	 * First, the data set will be narrowed down to only those with valid region, then narrowed
	 * again based on fuel type. 
	 * 
	 * @param ft The fuel type to include in the reduced data set
	 * @param rg The geographical region of the user
	 */
	public void trimData(String ft, String rg) {
		reduceData(ft, rg);
	}
	
	private void reduceData(String ft, String rg) {
		String symbolFT[] = {""}; // For matching
		
		reducedDataSet = null;
		reducedDataSet = new ArrayList<String[]>();
		
		// Narrow data sets based on user preferences by symbol matching to data set fuel types
		for (int i = 0; i < fuelArr.length; i++) {
			if (ft.equals(fuelArr[i][0])) {
				symbolFT = null;
				symbolFT = fuelArr[i]; // Set to match the types represented in the data set
			}
		}
		
		// Run through the data set and add using linear search and comparisons for region, fuel
		for (int i = 0; i < mainDataSet.size(); i++) {
			// Check region first
			if (mainDataSet.get(i)[2].contains(rg)) {
				for (int j = 0; j < symbolFT.length; j++) {
					// PEI has weird formatting in data set, so account for
					// extra comma
					if (rg.contains("Prince Edward Island")) {
						if (!mainDataSet.get(i)[5].contains(symbolFT[j])) {
							continue; // Continue to next string in symbolFT
						} else {
							reducedDataSet.add(mainDataSet.get(i));
							break; // Ensure we don't add things twice
						}
					}

					else {
						if (!mainDataSet.get(i)[4].contains(symbolFT[j])) {
							continue; // Continue to next string in symbolFT
						} else {
							reducedDataSet.add(mainDataSet.get(i));
							break; // Ensure we don't add things twice
						}
					}
				}
			}
		}
	}
	
	
	/**
	 * Perform statistical analysis on reduced set of fuel prices
	 * Note that as the data set is already narrowed to fuel type and
	 * region of the user, no parameters need to be passed
	 * 
	 */
	public void analyzeData() {
		double numerator, slope, b, y;
		int denominator;
		double m = 0;
		int index = 0;

		String[] years = { "10", "11", "12", "13", "14", "15" };
		ArrayList<Double> avgPrice = new ArrayList<Double>();

		for (int i = 0; i < years.length; i++) {
			avgPrice.add(dataAnalyze(years[i]));
		}

		for (int i = 0; i < years.length - 1; i++) {
			numerator = avgPrice.get(i + 1) - avgPrice.get(i);
			denominator = Integer.parseInt(years[i + 1]) - Integer.parseInt(years[i]);
			m += (numerator / denominator);
		}
		
		slope = m / (years.length - 1);
		double min = avgPrice.get(0);

		for (int i = 1; i < avgPrice.size(); i++) {
			if (min > avgPrice.get(i)) {
				min = avgPrice.get(i);
				index = i;

			}
		}
		
		b = min - slope * Integer.parseInt(years[index]);
		y = slope * Integer.parseInt(CURRENTYEAR) + b;
		
		predictedRate = Math.round(y*100.0)/100.0;
	}

	private double dataAnalyze(String year) {
		int countMonths = 0;
		double totalFuelPrice = 0;
		int len = 0;

		for (int i = 0; i < reducedDataSet.size(); i++) {

			if (reducedDataSet.get(i)[0].contains(year)) {
				len = reducedDataSet.get(i).length;
				countMonths++;
				totalFuelPrice += Double.parseDouble(reducedDataSet.get(i)[len - 1]);
			}
		}
		return Math.round(totalFuelPrice / countMonths * 100.0) / 100.0;
	}
	
	
	/**
	 * Gives 5 annual fuel spending estimates (dollars) to go along with the top vehicles
	 * Uses the average amount driven by a given driver type, in combination with
	 * the consumption ratings of the top 5 vehicles to generate a result
	 * 
	 * @param fuelConsumptions The fuel consumption ratings of the top five vehicles
	 * @param type The driver type of the user
	 * @return An array of double floating point values representing the expected annual spending on fuel for the five vehicles
	 */
	public double[] getRates(double[] fuelConsumptions, String type) {
		assert(fuelConsumptions.length == 5);
		return rateGet(fuelConsumptions, type);
	}
	
	private double[] rateGet(double[] fuelConsumptions, String type) {
		double[] result = { 0.00, 0.00, 0.00, 0.00, 0.00 };

		// dollars = xxxxx km * xxx L * xxx c * 1 dollar
		//                     100 km    1 L    100 cents

		// Provide result based on driver type
		if (type.toUpperCase().equals("CITY")) {
			// Go through the fuel consumptions and assign a result for each vehicle
			for (int i = 0; i < fuelConsumptions.length; i++) {
				result[i] = drivingAmount[0] * (fuelConsumptions[i] / 100) * predictedRate / 100;
			}
		} else if (type.toUpperCase().equals("HIGHWAY")) {
			for (int i = 0; i < fuelConsumptions.length; i++) {
				result[i] = drivingAmount[1] * (fuelConsumptions[i] / 100) * predictedRate / 100;
			}
		} else {
			for (int i = 0; i < fuelConsumptions.length; i++) {
				result[i] = drivingAmount[2] * (fuelConsumptions[i] / 100) * predictedRate / 100;
			}
		}
		return result;
	}
}
