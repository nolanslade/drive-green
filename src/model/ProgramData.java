package model;

/**
 * Will represent the state of the program amongst other information
 * Will be used to tell the user exactly what the program is doing, etc
 * 
 * @author Nolan Slade		001401079
 * @version 1.0
 */
public class ProgramData {
	private String programState;
	private boolean resultState;
	
	
	/**
	 * Constructor will set the initial program state and task
	 * 
	 * @param ps The program state
	 */
	public ProgramData(String ps) {
		programState = ps;
		resultState = false;
	}
	
	
	/**
	 * Getter function to get the state of the program
	 * 
	 * @return The state of the program
	 */
	public String getProgramState() {
		return programState;
	}
	
	
	/**
	 * Will set the current program state to a new value
	 * 
	 * @param ps The new program state
	 */
	public void setProgramState(String ps) {
		programState = ps;
	}
	
	
	/**
	 * Will return the whether or not results have been shown
	 * 
	 * @return True if results have been shown, false otherwise
	 */
	public boolean getResultsShown() {
		return resultState;
	}
	
	
	/**
	 * Will set whether or not results have been shown
	 * 
	 * @param nv The new value
	 */
	public void setResultsShown(boolean nv) {
		resultState = nv;
	}
}
