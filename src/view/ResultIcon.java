package view;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The program will display top five automobiles using this icon as a graphic
 * The icon contains relevant data to the vehicle, formatted in the desired
 * fashion; the data are given from the model.
 * 
 * @author ...
 * @version 1.0
 */
public class ResultIcon {
	private JLabel MM;
	private JLabel FC;
	private JLabel FC1;
	private JLabel PA;
	private JLabel PA1;
	private JLabel CS;
	private JLabel CS1;
	
	/* 
	 * Looks something like this, four dynamic text regions
	 * and immutable texts
	 * 
	 *  - - - - - - - - - - - - - - -
	 * |    Vehicle Make & Model     |
	 * |- - - - - - - - - - - - - - -|
	 * |							 |
	 * |       Fuel Consumption:     |
	 * |		xxxxxx L/100km       |  
	 * |                             |
	 * |  Predicted Annual Fuel Cost:|
	 * |			xxxx $  		 |
	 * |							 |
	 * |	Consumer Satisfaction:	 |
	 * |			xxx %			 |
	 * |							 |
	 * |							 |
	 *  - - - - - - - - - - - - - - -
	 */
	
	/**
	 * Takes four string inputs and generates an icon for the vehicle to be displayed on UI
	 * 
	 * @param p The JPanel we are utilizing
	 * @param makeModel The vehicle's make and model
	 * @param fuelConsumption The vehicles L/100km fuel consumption (according to driver type)
	 * @param fuelSpending The predicted annual fuel spending (according to region, fuel type and driver type)
	 * @param consumerSatisfaction The computed satisfaction with the vehicle's manufacturer
	 * @param x The x coordinate to offset
	 * @param y The y coordinate to offset
	 * 
	 * @return The icon corresponding to the given information
	 */
	public ResultIcon (JPanel p, String makeModel, String fuelConsumption, String fuelSpending, String consumerSatisfaction, int x, int y) {
		MM= new JLabel(makeModel);
		MM.setFont(new Font("Serif",Font.PLAIN,15));
		MM.setBounds(25+x,0+y,250 ,30);
		p.add(MM);
		
		FC= new JLabel("Fuel Consumption:");
		FC.setFont(new Font("Serif",Font.PLAIN,15));
		FC.setBounds(40+x,50+y,250 ,30);
		p.add(FC);
		
		FC1= new JLabel(fuelConsumption+" L/100km");
		FC1.setFont(new Font("Serif",Font.PLAIN,15));
		FC1.setBounds(60+x,75+y,250 ,30);
		p.add(FC1);
		
		PA= new JLabel("Annual Fuel Cost:");
		PA.setFont(new Font("Serif",Font.PLAIN,15));
		PA.setBounds(40+x,150+y,250 ,30);
		p.add(PA);
		
		PA1= new JLabel("$ "+fuelSpending);
		PA1.setFont(new Font("Serif",Font.PLAIN,15));
		PA1.setBounds(50+x,175+y,250 ,30);
		p.add(PA1);
		
		CS= new JLabel("Consumer Satisfaction:");
		CS.setFont(new Font("Serif",Font.PLAIN,15));
		CS.setBounds(25+x,250+y,250 ,30);
		p.add(CS);
		
		CS1= new JLabel(consumerSatisfaction+" %");
		CS1.setFont(new Font("Serif",Font.PLAIN,15));
		CS1.setBounds(60+x,275+y,250 ,30);
		p.add(CS1);
	}
	
	
	/**
	 * Used to remove a given icon from the panel, so that we may restart the computation
	 * 
	 */
	public void erase(JPanel p) {
		p.remove(MM);
		p.remove(FC);
		p.remove(FC1);
		p.remove(PA);
		p.remove(PA1);
		p.remove(CS);
		p.remove(CS1);
	}
}
