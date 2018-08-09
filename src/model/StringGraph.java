package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import edu.princeton.cs.algs4.ST;

/**
 * StringGraph converts a graph based text file that has been generated into a Bipartite Graph object using Symbol Tables.
 * 
 * @author Vyome Kishore 
 */
public class StringGraph {
	private String[] keyValues;
	private ST<String, Integer> symbolTable;
	private Bigraph undirectedGraph;

	/**
	 * Takes in a file and converts it into a graph
	 * 
	 * @param file The auto-generated graph based text file
	 * @throws IOException
	 */
	public StringGraph(String file) throws IOException {
		symbolTable = new ST<String, Integer>();
		String line;
		BufferedReader inOne = new BufferedReader(new FileReader(new File(file)));
		int i = 0;
		while ((line = inOne.readLine()) != null) {

			String[] a = line.split(",");
			if (a[0].equals("2")) {
				if (!symbolTable.contains(a[1])) {
					symbolTable.put(a[1], symbolTable.size());
				}
				if (!symbolTable.contains(a[2])) {
					symbolTable.put(a[2], symbolTable.size());
				}

				symbolTable.put(a[3] + i, symbolTable.size());
			} else {
				if (!symbolTable.contains(a[1])) {
					symbolTable.put(a[1], symbolTable.size());
				}
				symbolTable.put(a[2] + i, symbolTable.size());
			}
			i++;
		}
		inOne.close();

		// inverted index to get string keyValues in an array
		keyValues = new String[symbolTable.size()];
		for (String name : symbolTable.keys()) {
			keyValues[symbolTable.get(name)] = name;
		}

		// second pass builds the graph by connecting first vertex on each
		// line to all others
		undirectedGraph = new Bigraph(symbolTable.size());
		BufferedReader inTwo = new BufferedReader(new FileReader(new File(file)));
		int p = 0;
		while ((line = inTwo.readLine()) != null) {
			String[] a = line.split(",");

			if (a[0].equals("1")) {
				int v = symbolTable.get(a[1]);
				int w = symbolTable.get(a[2] + p);
				undirectedGraph.addTo(v, w);
			} else {
				int v1 = symbolTable.get(a[1]);
				int v2 = symbolTable.get(a[2]);
				int w = symbolTable.get(a[3] + p);
				undirectedGraph.addTo(v1, w);
				undirectedGraph.addTo(v2, w);
			}
			p++;
		}
		inTwo.close();
	}

	/**
	 * Gets index of a company name
	 * 
	 * @param companyName The name to be searched in the ST
	 * @return index of the company name
	 */
	public int getIndex(String companyName) {
		return symbolTable.get(companyName);
	}

	/**
	 * Gets company name at an index
	 * 
	 * @param index index to get the company name from
	 * @return company name at the index
	 */
	public String getName(int index) {
		return keyValues[index];
	}

	/**
	 * Lets one access the bipartite graph
	 * 
	 * @return the bipartite graph associated with the object
	 */
	public Bigraph getBigraph() {
		return undirectedGraph;
	}

	/**
	 * Gets rating of the company name provided
	 * 
	 * @param graph Graph to be searched in
	 * @param name Name of the company to get the rating of
	 * @return rating of the company from 0-1
	 */
	public static double getRating(StringGraph graph, String name) {
		double goodCounter = 0;
		double badCounter = 0;
		ArrayList<String> ratings = new ArrayList<String>();
		for (int i : graph.getBigraph().adj(graph.getIndex(name))) {
			ratings.add(graph.getName(i));
		}

		for (int o = 0; o < ratings.size(); o++) {
			if (ratings.get(o).substring(0, 4).equals("good")) {
				goodCounter++;
			}
			if (ratings.get(o).substring(0, 3).equals("bad")) {
				badCounter++;
			}
		}
		return (goodCounter) / (goodCounter + badCounter);
	}
}
