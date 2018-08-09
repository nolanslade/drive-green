package auxiliary;

import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.io.FileReader;


/**
 * Can read a given data set and retrieve all distinct values in a column.
 * For programmer's use only.
 * 
 * Assumes no commas in columns, if there are, adjust N by number of commas present
 * elsewhere
 * 
 * @author Nolan Slade	001401079
 * @version 1.0
 */
public class RetrieveColumnData {
	public static void main(String[] args) throws Exception {
		ArrayList<String> theList = new ArrayList<String>();
		int n = 4; // Get data in the N column

		BufferedReader in = new BufferedReader(new FileReader(new File("data/gasPrices.csv")));
		String line;
		while ((line = in.readLine()) != null) {
			String[] row = line.split(",");
			String currentThing = row[n];
			if (!theList.contains(currentThing)) {
				theList.add(currentThing);
			}
		}
		in.close();	
		
		// Print in "one", "two" .... format
		for (int i = 0; i < theList.size(); i++) {
			if (i == 0) {
				System.out.print("\""+theList.get(i)+"\"");
			} else {
				System.out.print(", " + "\"" +theList.get(i)+"\"");
			}
		}
	}
}
