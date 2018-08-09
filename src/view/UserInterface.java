package view;

/*
 * 
 * Need to add text boxes for program state and another for program task
 * probably at the top somewhere
 * 
 * 
 */


import controller.MainController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The graphical user interface to receive user input, and display program output
 * The user interface will take in mouse clicks, and will notify controller modules 
 * in order to take appropriate action
 * 
 * @author Areeb Malik
 * @author Nolan Slade
 * @author Vyome Kishore
 * @version 1.0
 */
@SuppressWarnings("unused")
public class UserInterface {
	private static JFrame frame;
	private static String programStateString = MainController.getProgramState();
	private static final String[] formArr = {"COMPACT", "MID-SIZE", "SUBCOMPACT", "STATION WAGON - MID-SIZE", "MINICOMPACT", "TWO-SEATER", "STATION WAGON - SMALL", "FULL-SIZE", "SUV", "VAN - CARGO", "VAN - PASSENGER", "PICKUP TRUCK - STANDARD", "PICKUP TRUCK - SMALL", "MINIVAN", "SUV - STANDARD", "SPECIAL PURPOSE VEHICLE", "SUV - SMALL"};
	private static final String[] fuelArr = {"Regular gasoline", "Premium gasoline", "Diesel", "Ethanol", "Natural gas"};
	private static final String[] driverArr = {"City", "Highway", "Combined"};
	private static final String[] regionArr = {"Alberta", "British Columbia", "Manitoba", "New Brunswick", "Newfoundland and Labrador", "Nova Scotia", "Ontario", "Prince Edward Island", "Quebec","Saskatchewan", "Northwest Territories", "Nunavut", "Yukon"};
	private static final String[] yearArr = {"2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008","2009","2010","2011","2012","2013","2014"};
	private static int taskX;
	private static int taskY;
	private ResultIcon resultOne;
	private ResultIcon resultTwo;
	private ResultIcon resultThree;
	private ResultIcon resultFour;
	private ResultIcon resultFive;
	private JLabel progState;
	
	@SuppressWarnings("serial")
	private static JPanel panel = new JPanel() {
		@Override
		public void paintComponent(Graphics g) {
			BufferedImage image = null;
			try {
				image = ImageIO.read(new File("finalBackground.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		    super.paintComponent(g); // paint the background image and scale it to fill the entire space
		    g.drawImage(image, 0, 0, null);
		}
	};
	
	
	/**
	 * Defines and constructs components of the view to be seen by the user
	 * Also implements button listeners for functionality of the program
	 * 
	 */
	public UserInterface() {
		//JFrame Layout
		frame = new JFrame("Drive Green");
		frame.setVisible(true);
		frame.setSize(1000, 900);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		

		int height = frame.getHeight();
		int width = frame.getWidth();
		
		//JPanel Layout
		//JPanel panel = new JPanel(new GridBagLayout());
		//panel= new JPanel(new GridBagLayout());
		panel.setLayout(null);
		frame.add(panel);
		
		
		int cursor = 50;
		
		//Title
//		JLabel titleLabel = new JLabel("Drive Green");
//		titleLabel.setFont(new Font("Serif",Font.PLAIN,60));
//		titleLabel.setBounds(width/2-300/2,cursor,300,60);
//		panel.add(titleLabel);
		cursor+=80;

		//*****LINES****
		DrawLines draw = new DrawLines();
		panel.add(draw);
		
		//*****TITLES*****
		
		// ~~~~~~PREFERENCES~~~~~~
		//Preference Title
//		JLabel preferenceLabel = new JLabel("Enter Preferences:");
//		preferenceLabel.setFont(new Font("Serif",Font.PLAIN,30));
//		preferenceLabel.setBounds(width/2-280/2,cursor,280,40);
//		panel.add(preferenceLabel);
		cursor+=40;
		
		//Location and spacing criteria for sub-sub titles
		int sTSpacing = (width-20)/5;
		int sTXlocation = 10+sTSpacing/5;
		int sTYlocation = cursor;
		
		//Model Year
		JLabel subTitleLabel1 = new JLabel("Model Year");
		subTitleLabel1.setFont(new Font("Serif",Font.PLAIN,22));
		subTitleLabel1.setBounds(sTXlocation,sTYlocation,150,22);
		panel.add(subTitleLabel1);
		
		//Form Factor
		JLabel subTitleLabel2 = new JLabel("Form Factor");
		subTitleLabel2.setFont(new Font("Serif",Font.PLAIN,22));
		sTXlocation += sTSpacing;
		subTitleLabel2.setBounds(sTXlocation,sTYlocation,150,22);
		panel.add(subTitleLabel2);
		
		//Fuel Type
		JLabel subTitleLabel3 = new JLabel("Fuel Type");
		subTitleLabel3.setVisible(true);
		subTitleLabel3.setFont(new Font("Serif",Font.PLAIN,22));
		sTXlocation += sTSpacing;
		subTitleLabel3.setBounds(sTXlocation,sTYlocation,150,30);
		panel.add(subTitleLabel3);
		
		//Driver Type
		JLabel subTitleLabel4 = new JLabel("Driver Type");
		subTitleLabel4.setFont(new Font("Serif",Font.PLAIN,22));
		sTXlocation += sTSpacing;
		subTitleLabel4.setBounds(sTXlocation,sTYlocation,150,30);
		panel.add(subTitleLabel4);
		
		//Province
		JLabel subTitleLabel5 = new JLabel("Region");
		subTitleLabel5.setFont(new Font("Serif",Font.PLAIN,22));
		sTXlocation += sTSpacing;
		subTitleLabel5.setBounds(sTXlocation,sTYlocation,150,30);
		panel.add(subTitleLabel5);
		
		// ~~~~~~~RESULTS~~~~~~~
		// Title
		cursor+=220;
//		JLabel resultsLabel = new JLabel("Results");
//		resultsLabel.setVisible(true);
//		resultsLabel.setFont(new Font("Serif",Font.PLAIN,30));
//		resultsLabel.setBounds(width/2-100/2,cursor,100,60);
//		panel.add(resultsLabel);
		cursor+= 70;
		
//		// Ordered Output
//		JLabel[] numberLabel = new JLabel[5];
//		sTXlocation = 10+sTSpacing/3;
//		for (int i = 0;i<5;i++){
//		numberLabel[i] = new JLabel(Integer.toString(i+1));
//		numberLabel[i].setFont(new Font("Serif",Font.PLAIN,22));
//		numberLabel[i].setBounds(sTXlocation,cursor,150,22);
//		panel.add(numberLabel[i]);
//		sTXlocation += sTSpacing;
//		}
		
		cursor-=290;
		
		//*****TEXTBOX******
		sTXlocation = 10+sTSpacing/5;
		
		
		//*****COMBO BOXES*****
	
		//First
		//sTXlocation += sTSpacing;
		final JComboBox<String> modelYear = new JComboBox<String>(yearArr);
		modelYear.setVisible(true);
		modelYear.setBackground(Color.WHITE);
		modelYear.setBounds(sTXlocation,sTYlocation+35,100,20);
		modelYear.doLayout();
		panel.add(modelYear);
		
		//First
		sTXlocation += sTSpacing;
		final JComboBox<String> formFactor = new JComboBox<String>(formArr);
		formFactor.setVisible(true);
		formFactor.setBackground(Color.WHITE);
		formFactor.setBounds(sTXlocation,sTYlocation+35,100,20);
		formFactor.doLayout();
		panel.add(formFactor);
		
		//Second
		sTXlocation += sTSpacing;
		final JComboBox<String> fuelType = new JComboBox<String>(fuelArr);
		fuelType.setVisible(true);
		fuelType.setBackground(Color.WHITE);
		fuelType.setBounds(sTXlocation,sTYlocation+35,100,20);
		fuelType.doLayout();
		panel.add(fuelType);
		
		//Third
		sTXlocation += sTSpacing;
		final JComboBox<String> driverType = new JComboBox<String>(driverArr);
		driverType.setVisible(true);
		driverType.setBackground(Color.WHITE);
		driverType.setBounds(sTXlocation,sTYlocation+35,100,20);
		driverType.doLayout();
		panel.add(driverType);
		
		//Fourth
		sTXlocation += sTSpacing;
		final JComboBox<String> province = new JComboBox<String>(regionArr);
		province.setVisible(true);
		province.setBackground(Color.WHITE);
		province.setBounds(sTXlocation,sTYlocation+35,100,20);
		province.doLayout();
		panel.add(province);
		
		cursor+=20;
		
		//*****PROGRAM TASK AND STATE TEXT BOXES*****
		
		//Program Task
		//taskX=sTXlocation;
		//taskY=sTSpacing;
		//Task_State(panel,sTXlocation,sTSpacing);
				
		
		//*****BUTTONS*****
		
		//Update Preferences
		cursor+=50;
		JButton updatePref = new JButton("Update Preferences");
		updatePref.setVisible(true);
		updatePref.setBounds(width/2-200/2,cursor,200,40);
		panel.add(updatePref);
		cursor += 50;
		
		//Restart
		cursor+=512;
		JButton restart = new JButton("Restart");
		restart.setVisible(true);
		restart.setBounds(width/2-200/2,cursor,200,40);
		panel.add(restart);
		cursor-=450;
		
		// Repaint -> fix for stuff not showing up initially
		panel.validate();
		panel.repaint();
		
		// Add listener to the updatePref button in order to transfer appropriate commands to the
		// controller
		updatePref.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// Only to be used when results have not been shown
				if (!MainController.getResultsShown()) {
					// needs to be {form factor, fuel type, driver type, model year,province}
					String[] updatedPreferences = {formFactor.getItemAt(formFactor.getSelectedIndex()),
							fuelType.getItemAt(fuelType.getSelectedIndex()),driverType.getItemAt(driverType.getSelectedIndex()),
							modelYear.getItemAt(modelYear.getSelectedIndex()),province.getItemAt(province.getSelectedIndex())};
					
					// Call for the controller to set the changes into the model and start computation
					MainController.setUserData(updatedPreferences);
					//panel.remove(progState);
					//Task_State(panel,taskX,taskY);		
					
					// Just need to add the proper stuff into the strings below
					ArrayList<String[]> toDisplay = MainController.getVehicleResults();
					
					resultOne = new ResultIcon(panel, toDisplay.get(0)[0] + " " + toDisplay.get(0)[1], toDisplay.get(0)[2], toDisplay.get(0)[3], toDisplay.get(0)[4], 20, 440);
					resultTwo = new ResultIcon(panel, toDisplay.get(1)[0] + " " + toDisplay.get(1)[1], toDisplay.get(1)[2], toDisplay.get(1)[3], toDisplay.get(1)[4], 206, 440);
					resultThree = new ResultIcon(panel, toDisplay.get(2)[0] + " " + toDisplay.get(2)[1], toDisplay.get(2)[2], toDisplay.get(2)[3], toDisplay.get(2)[4], 392, 440);
					resultFour = new ResultIcon(panel, toDisplay.get(3)[0] + " " + toDisplay.get(3)[1], toDisplay.get(3)[2], toDisplay.get(3)[3], toDisplay.get(3)[4], 577, 440);
					resultFive = new ResultIcon(panel, toDisplay.get(4)[0] + " " + toDisplay.get(4)[1], toDisplay.get(4)[2], toDisplay.get(4)[3], toDisplay.get(4)[4], 765, 440);
	
					// Update to show results
					MainController.setResultsShown(true);
					
					panel.repaint();
				}
			}
		});
		
		// Add listener to the updatePref button in order to transfer appropriate commands to the
		// controller
		restart.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// Only to be used if results have been shown
				if (MainController.getResultsShown()) {
					// Erase the results
					resultOne.erase(panel); resultTwo.erase(panel); resultThree.erase(panel); resultFour.erase(panel); resultFive.erase(panel);
					
					// Null for garbage collection
					resultOne = null; resultTwo = null; resultThree = null; resultFour = null; resultFive = null;
					
					// Allow the user to set their new preferences
					MainController.setResultsShown(false);
					panel.repaint();
				}
			}
		});
	}
	@SuppressWarnings("serial")
	public static class DrawLines extends JPanel {
		@Override
		protected void paintComponent(Graphics g) {
			Dimension d = this.getSize();
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(Color.BLACK);
			g2d.setStroke(new BasicStroke(3));
			g2d.drawLine(0, 100, 1000, 100);	
		}
	}
	
//	public void Task_State(JPanel p,int sTX,int sTS){
//		progState= new JLabel(programStateString);;
//		progState.setFont(new Font("Serif",Font.PLAIN,30));
//		sTX += sTS;
//		progState.setBounds(425,250,250 ,30);
//		p.add(progState);
//	}
}
