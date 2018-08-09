package auxiliary;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * Will generate a bipartite graph for our third dataset
 * The graph will have a random-given rating "good" or "bad"
 * and an associated automaker. This will be used to simulate 
 * sentimental analysis on twitter posts. Note that there may be
 * two automakers in a single social media post, so we have done
 * that with random numbers (sometimes two automakers are assigned)
 * 
 * This only needs to be run once in the lifetime of the project, by theory.
 * 
 * @author Nolan Slade : 001401079
 * @version 1.0
 */
public class GraphGenerator {
	private static String[] automakers = { "ACURA", "AUDI", "BMW", "BUICK", "CADILLAC", "CHEVROLET", "CHRYSLER",
			"DAEWOO", "DODGE", "FERRARI", "FORD", "GMC", "HONDA", "HYUNDAI", "INFINITI", "ISUZU", "JAGUAR", "JEEP",
			"KIA", "LAND ROVER", "LEXUS", "LINCOLN", "MAZDA", "MERCEDES-BENZ", "NISSAN", "OLDSMOBILE", "PLYMOUTH",
			"PONTIAC", "PORSCHE", "SAAB", "SATURN", "SUBARU", "SUZUKI", "TOYOTA", "VOLKSWAGEN", "VOLVO", "BENTLEY",
			"ROLLS-ROYCE", "MASERATI", "MINI", "MITSUBISHI", "SMART", "HUMMER", "ASTON MARTIN", "LAMBORGHINI",
			"BUGATTI", "SCION", "FIAT", "RAM", "SRT", "ALFA ROMEO" };

	private static String[] ratings = { "good", "bad" };
	private static int nodeCount = 0;
	
	/**
	 * Will initiate the construction of the graph to a specified directory and display node count
	 * Directory is tailored to work for Mac OS X
	 * 
	 * @param args None expected
	 */
	public static void main(String[] args) {
		// Change the following forward slashes if you're on windows
		String directory = "data/automakerGraph.txt";
		generateGraph(directory);
		assert(nodeCount >= 50000);
		System.out.println("Graph generated. Nodes: " + nodeCount);
	}
	
	
	/*
	 * 
	 * Have to change this around so that it actually writes a valid bipartite graph
	 *  - see the princeton bipartite shit from the online algorithms website
	 * 
	 */
	
	
	/**
	 * Will generate random nodes and write the final graph to a file
	 * Automaker ratings are randomly generated using Math.random(), and
	 * approximately 1200 are assigned to each manufacturer in order to 
	 * meet the 50000 node minimum requirement. The data will be represented
	 * as "automaker,rating"
	 * 
	 * @exception IOException If there is a file writing error
	 * @exception Exception If there is unexpected exception
	 */
	private static void generateGraph(String dir) {
		try {
			// Initialize things
			String toFile = "";
			String currentAutomaker;
			String secondaryAutomaker;
			double randomOne;
			double randomTwo;
			double pivotPoint;
			int index;
			Writer wr = null;
			
			// Go through each auto maker and assign randomized ratings
			for (int i = 0; i < automakers.length; i++) {
				currentAutomaker = automakers[i];
				pivotPoint = Math.random();
				
				// Produce at least 50 000 nodes
				for (int j = 0; j < 1220; j++) {
					// Randomize user opinion and add to running string
					randomOne = Math.random();
					randomTwo = Math.random();

					// Just one automaker will be rated
					if (randomOne > randomTwo) {
						if (randomOne < pivotPoint) {
							toFile += "1" + "," + currentAutomaker + "," + ratings[0] + "\n";
						} else {
							toFile += "1" + "," + currentAutomaker + "," + ratings[1] + "\n";
						}
						nodeCount++;
					}

					// Two automakers will be rated
					else {
						if (i < 5) {
							index = i + 5;
							secondaryAutomaker = automakers[index];
						} else {
							index = i - 5;
							secondaryAutomaker = automakers[index];
						}

						if (randomOne < pivotPoint) {
							toFile += "2" + "," + currentAutomaker + "," + secondaryAutomaker + "," + ratings[0] + "\n";
						} else {
							toFile += "2" + "," + currentAutomaker + "," + secondaryAutomaker + "," + ratings[1] + "\n";
						}
						nodeCount++;
					}
				}
			}
			// Write to the file
			File f = new File(dir);
			wr = new BufferedWriter(new FileWriter(f));
			wr.write(toFile);
			wr.close();		
			
		// Catch exceptions
		} catch (IOException e) {
			System.out.println("An IO exception has occurred.");
		} catch (Exception e) {
			System.out.println("An unexpected exception has occurred.");
		}
	}
}
