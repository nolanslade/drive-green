package model;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Will deal with the data of the vehicles to be presented to the user
 * Initially, the class will store all data from vehicle databases; then
 * proceed to narrow down the data set to only relevant vehicles through a
 * series of sort and search operations. The vehicles will be ranked
 * according to fuel consumption in one reduced set, and emissions in another.
 * The positions will be averaged to give a top 5 list of combined emissions
 * and consumptions.
 * 
 * @author Nolan Slade      001401079
 * @author Vyome Kishore    001406201
 * @version 1.6
 */
public class VehicleData {
	private ArrayList<String[]> mainDataSet; 			// The full data set
	private ArrayList<String[]> reducedDataConsumption; // The reduced data set which will contain only relevant fuel consumption data
	private ArrayList<String[]> reducedDataEmissions; 	// The reduced data set which will contain only relevant vehicle emissions data
	private ArrayList<String[]> finalFive;				// Used to store the information of the top five vehicles to be presented to user
	
	// Used for symbol matching between data set and UI e.g. X = regular gasoline in the vehicle data set, etc.
	private static final String[][] fuelArr = {{"Regular gasoline", "X"}, {"Premium gasoline", "Z"}, {"Diesel", "D"}, {"Ethanol", "E"}, {"Natural gas","N"}};

	
	/**
	 * Will call for the initialization of the data reading process
	 * The data read from the fuelConsumption.csv file will be stored
	 * into the main data set
	 * 
	 */
	public VehicleData () throws IOException {
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
		BufferedReader in = new BufferedReader(new FileReader(new File("data/fuelConsumption.csv")));
		String line;
		while ((line = in.readLine()) != null) {

			// use comma as separator
			String[] row = line.split(",");
			mainDataSet.add(row);
		}
		in.close();
	}
	
	
	/**
	 * Re-sorts the vehicles by their combined environmental standing, based on emissions and consumption
	 * After this process, the top five vehicles may be determined by their standing in this list
	 * 
	 */
	public void computeTopFive() {
		combinedStandingSort();
	}
	
	private void combinedStandingSort() {
		finalFive = null;
		finalFive = new ArrayList<String[]>();
		assert(reducedDataConsumption.size() == reducedDataEmissions.size());	// Just to make sure nothing has gone wrong
		
		// Contains the brands and names of the vehicles, sorted by consumption and emissions - gets rid of duplicates
		ArrayList<String[]> resultListC = new ArrayList<String[]>();	// Consumption
		ArrayList<String[]> resultListE = new ArrayList<String[]>();	// Emissions
		String currentVehicle = "";										// For list construction
		String[] currentVehicleArray = currentVehicle.split(",");		// For list construction
		
		
		/*
		 * Possible data mismatch -> what if the version with lowest consumption != the version with lowest emissions?
		 * can't think of a way to fix other than to include all the other information, so maybe do that later
		 */
		
		for (int i = 0; i < reducedDataConsumption.size(); i++) {
			currentVehicle = reducedDataConsumption.get(i)[1].trim() + "," + reducedDataConsumption.get(i)[2].trim() + "," + reducedDataConsumption.get(i)[8].trim() + "," + reducedDataConsumption.get(i)[9].trim() + "," + reducedDataConsumption.get(i)[10].trim() + "," + reducedDataConsumption.get(i)[12].trim();
			currentVehicleArray = currentVehicle.split(",");
			
			// Use an equivalent to .contains() to see if a given ArrayList<String[]>
			// contains a given array's elements
			if (!contains(resultListC, currentVehicleArray)) {
				resultListC.add(currentVehicleArray);
			}
		} 
		
		for (int i = 0; i < reducedDataEmissions.size(); i++) {
			currentVehicle = reducedDataEmissions.get(i)[1].trim() + "," + reducedDataEmissions.get(i)[2].trim() + "," + reducedDataEmissions.get(i)[8].trim() + "," + reducedDataEmissions.get(i)[9].trim() + "," + reducedDataEmissions.get(i)[10].trim() + "," + reducedDataEmissions.get(i)[12].trim();
			currentVehicleArray = currentVehicle.split(",");
			
			// Use an equivalent to .contains() to see if a given ArrayList<String[]>
			// contains a given array's elements
			if (!contains(resultListE, currentVehicleArray)) {
				resultListE.add(currentVehicleArray);
			}
		}
		
		// Take average position of a given item to get final top 5 list
		assert(resultListE.size() == resultListC.size());
		double[][] positions = new double[resultListC.size()][3]; // [][0] = index in consumption, [][1] = emission, [][2] = combined average
		
		// First get the indices in both lists for a given vehicle
		for (int i = 0; i < positions.length; i++) {
			positions[i][0] = i; 	// The consumption position is the index, no searching required

			// Get emissions indices
			for (int j = 0; j < resultListE.size(); j++) {
				if (resultListE.get(j)[0].equals(resultListC.get(i)[0]) && resultListE.get(j)[1].equals(resultListC.get(i)[1]) 
						&& resultListE.get(j)[2].equals(resultListC.get(i)[2]) && resultListE.get(j)[3].equals(resultListC.get(i)[3])
						&& resultListE.get(j)[4].equals(resultListC.get(i)[4]) && resultListE.get(j)[5].equals(resultListC.get(i)[5])) {			// Might need to fix this
					positions[i][1] = j;	// Emission position	
					break;
				}
			}
		}
		
		// Average positions in the two lists so that we get the most accurate representation
		// of a vehicle's environmental friendliness (emissions and fuel consumption)
		
		// First get an average position for each vehicle and assign it to [i][2] index
		for (int i = 0; i < positions.length; i++) {
			positions[i][2] = (positions[i][0] + positions[i][1])/2;
		}
		
		// Put the vehicles in order according to this combined position for the final result
		for (int i = 0; i < positions.length; i++) {
			double min = positions[i][2];
			int minIndex = i;
			
			for (int j = 0; j < positions.length; j++) {
				if (positions[j][2] < min) {
					min = positions[j][2];
					minIndex = j;
				}
			}
			
			finalFive.add(resultListC.get(minIndex)); 		// Need to fix this, wrong data
			positions[minIndex][2] = Integer.MAX_VALUE;		// So that we don't duplicately add stuff
			
			if (i == 4) {
				break;	// We have our top five vehicles
			}
		}	
	}
	
	
	/**
	 * Will reduce the full data set into only those that the user is concerned with
	 * Model year, fuel type, form factor 
	 */
	public void trimData(String my, String ft, String ff) {
		reduceData(my, ft, ff);
	}
	
	private void reduceData(String my, String ft, String ff) {
		String symbolFT = ""; // For symbol matching
		
		reducedDataConsumption = null;
		reducedDataEmissions = null;
		reducedDataConsumption = new ArrayList<String[]>();
		reducedDataEmissions = new ArrayList<String[]>();
		
		// Narrow data sets based on user preferences by symbol matching to data set fuel types
		for (int i = 0; i < fuelArr.length; i++) {
			if (ft.equals(fuelArr[i][0])) {
				symbolFT = fuelArr[i][1]; // Set to match the types represented in the data set
			}
		}
		
		// In data set: index 0-model year, 3-form factor, 7-fuel type
		for (int i = 0; i < mainDataSet.size(); i++) {
			if (my.equals(mainDataSet.get(i)[0]) && symbolFT.equals(mainDataSet.get(i)[7])
					&& ff.equals(mainDataSet.get(i)[3])) {
				reducedDataConsumption.add(mainDataSet.get(i));
				reducedDataEmissions.add(mainDataSet.get(i));
			}
		}
	}
	
	
	/**
	 * Returns the information pertaining to the top five vehicles
	 * Used for outputting results
	 * 
	 * 
	 */
	public ArrayList<String[]> getTopFive() {
		return finalFive;
	}
	
	
	/**
	 * Will call for the sorting of the data according to emissions and consumption
	 * Driver type used for consumption sort in order to accurately rank vehicles
	 * 
	 * @param type The driver type of the user
	 */
	public void sortData(String type) {
		consumptionSort(type, reducedDataConsumption, reducedDataConsumption.size());
		emissionSort(reducedDataEmissions, reducedDataEmissions.size());
	}
	
	// i think its missing the first row?
	private void consumptionSort(String type, ArrayList<String[]> x, int n) {
		int index;
		if (type.toUpperCase().contains("CITY")) {
			index = 8;
		} else if (type.toUpperCase().contains("HIGHWAY")) {
			index = 9;
		} else {
			index = 10;
		}
		
		if (n < 2) {
			return;
		}

		// Declare necessary right and left hand side components to merge sort
		// efficiently
		int halfwayPoint = n / 2;
		int elementsOnRight = n - halfwayPoint;
		int elementsOnLeft = halfwayPoint;
		ArrayList<String[]> rightSide = new ArrayList<String[]>(elementsOnRight);
		ArrayList<String[]> leftSide = new ArrayList<String[]>(elementsOnLeft);

		// Fill in the arrays of the left and right hand sides
		for (int i = 0; i < halfwayPoint; i++) {
			leftSide.add(x.get(i));
		}
		
		for (int i = halfwayPoint; i < n; i++) {
			rightSide.add(x.get(i));
		}

		// Recurse through the right and left hand sides
		consumptionSort(type, leftSide, halfwayPoint);
		consumptionSort(type, rightSide, n - halfwayPoint);

		// Counters for merging
		int counterOne = 0;
		int counterTwo = 0;
		int counterThree = 0;

		// Merge together to form the sorted, and combined array
		while (counterOne < leftSide.size() && counterTwo < rightSide.size()) {
			if (Double.parseDouble(leftSide.get(counterOne)[index]) <= Double
					.parseDouble(rightSide.get(counterTwo)[index])) {
				x.set(counterThree, leftSide.get(counterOne));
				counterOne++;
				counterThree++;
			} else {
				x.set(counterThree, rightSide.get(counterTwo));
				counterThree++;
				counterTwo++;
			}
		}

		// Merge in proper order
		for (; counterOne < leftSide.size(); counterOne++) {
			x.set(counterThree, leftSide.get(counterOne));
			counterThree++;
		}
		for (; counterTwo < leftSide.size(); counterTwo++) {
			x.set(counterThree, rightSide.get(counterTwo));
			counterThree++;
		}
	}

	
	// Merge sort for emissions
	private void emissionSort(ArrayList<String[]> x, int n) {
		if (n < 2) {
			return;
		}

		// Declare necessary right and left hand side components to merge sort
		// efficiently
		int halfwayPoint = n / 2;
		int elementsOnRight = n - halfwayPoint;
		int elementsOnLeft = halfwayPoint;
		ArrayList<String[]> rightSide = new ArrayList<String[]>(elementsOnRight);
		ArrayList<String[]> leftSide = new ArrayList<String[]>(elementsOnLeft);

		// Fill in the arrays of the left and right hand sides
		for (int i = 0; i < halfwayPoint; i++) {
			leftSide.add(x.get(i));
		}
		for (int i = halfwayPoint; i < n; i++) {
			rightSide.add(x.get(i));
		}

		// Recurse through the right and left hand sides
		emissionSort(leftSide, halfwayPoint);
		emissionSort(rightSide, n - halfwayPoint);

		// Counters for merging
		int counterOne = 0;
		int counterTwo = 0;
		int counterThree = 0;

		// Merge together to form the sorted, and combined array
		while (counterOne < leftSide.size() && counterTwo < rightSide.size()) {
			if (Double.parseDouble(leftSide.get(counterOne)[12]) <= Double.parseDouble(rightSide.get(counterTwo)[12])) {
				x.set(counterThree, leftSide.get(counterOne));
				counterOne++;
				counterThree++;
			} else {
				x.set(counterThree, rightSide.get(counterTwo));
				counterThree++;
				counterTwo++;
			}
		}

		// Merge in proper order
		for (; counterOne < leftSide.size(); counterOne++) {
			x.set(counterThree, leftSide.get(counterOne));
			counterThree++;
		}
		for (; counterTwo < leftSide.size(); counterTwo++) {
			x.set(counterThree, rightSide.get(counterTwo));
			counterThree++;
		}
	}
	
	
	// Used to check if an arraylist contains an array (only comparing the make & model = first two indeces)
	private static boolean contains(ArrayList<String[]> list, String[] item) {
		// Check for the exact array in the arrayList (first two indeces)
		for (int j = 0; j < list.size(); j++) {
			if (list.get(j)[0].equals(item[0]) && list.get(j)[1].equals(item[1])) {
				return true;
			}
		}
		return false;
	}
}
