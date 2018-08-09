package testing;

import static org.junit.Assert.*;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import model.*;


public class TestModel {
	
	@Before
	public void setUp() throws Exception {
		//TODO
	}

	@After
	public void tearDown() throws Exception {
		//TODO
	}
		
	@Test
	public void testRegion() throws IOException {
		DataAccessor da = new DataAccessor() ;
		String [] temp = {"temp1", "temp2" ,"temp3","temp4","temp5"};
		da.setToUser(temp);
		String [] possibleRegions = {"Alberta","British Columbia", "Manitoba", "New Brunswick", "Newfoundland and Labrador", "Nova Scotia", "Ontario", "Prince Edward Island", "Quebec", "Saskatchewan", "Northwest Territories", "Nunavut", "Yukon"};
		for(int i = 0; i < possibleRegions.length; i++){
			da.setGeoRegion(possibleRegions[i]);
			assertTrue(da.getGeoRegion().equals(possibleRegions[i]));
		}
		
			
		
	}
	
	@Test
	public void testDriverTypes() throws IOException{
		DataAccessor da = new DataAccessor() ;
		String [] temp = {"temp1", "temp2" ,"temp3","temp4","temp5"};
		da.setToUser(temp);
		String [] possibleDriverTypes = {"City", "Highway","Combined" };

		for(int i = 0; i < possibleDriverTypes.length; i++){
			da.setDriverType(possibleDriverTypes[i]);
			assertTrue(da.getDriverType().equals(possibleDriverTypes[i]));
		}
		
	}
	
	@Test
	public void testFuelTypes() throws IOException{
		DataAccessor da = new DataAccessor() ;
		String [] temp = {"temp1", "temp2" ,"temp3","temp4","temp5"};
		da.setToUser(temp);
		String [] possibleFuelTypes = {"Regular gasoline", "Premium gasoline", "Diesal", "Ethanol", "Natural gas"};

		for(int i = 0; i < possibleFuelTypes.length; i++){
			da.setDriverType(possibleFuelTypes[i]);
			assertTrue(da.getDriverType().equals(possibleFuelTypes[i]));
		}
	}
	
	@Test
	public void testFormFactors() throws IOException{
		DataAccessor da = new DataAccessor() ;
		String [] temp = {"temp1", "temp2" ,"temp3","temp4","temp5"};
		da.setToUser(temp);
		String [] possibleFormFactors = {"COMPACT", "MID-SIZE", "SUBCOMPACT", "STATION WAGON - MID-SIZE", "MINICOMPACT", "TWO-SEATER", "STATION WAGON - SMALL", "FULL-SIZE", "SUV", "VAN - CARGO", "VAN - PASSENGER", "PICKUP TRUCK - STANDARD", "PICKUP TRUCK - SMALL", "MINIVAN", "SUV - STANDARD", "SPECIAL PURPOSE VEHICLE", "SUV - SMALL"};

		for(int i = 0; i < possibleFormFactors.length; i++){
			da.setDriverType(possibleFormFactors[i]);
			assertTrue(da.getDriverType().equals(possibleFormFactors[i]));
		}
	}
	
	@Test
	public void testYears() throws IOException{
		DataAccessor da = new DataAccessor() ;
		
		String [] temp = {"temp1", "temp2" ,"temp3","temp4","temp5"};
		da.setToUser(temp);
		String [] possibleYears = {"2001","2002","2003","2004","2005","2006","2007","2008","2009","2010","2011","2012","2013","2014"};

		for(int i = 0; i < possibleYears.length; i++){
			da.setDriverType(possibleYears[i]);
			assertTrue(da.getDriverType().equals(possibleYears[i]));
		}
	}
	
	@Test
	public void testTopFive() throws IOException{
		DataAccessor da = new DataAccessor();
		VehicleData vd = new VehicleData();
		String [] fuelTypes = {"Regular gasoline", "Premium gasoline", "Diesal", "Ethanol", "Natural gas"};
		String [] formFactors = {"COMPACT", "MID-SIZE", "SUBCOMPACT", "STATION WAGON - MID-SIZE", "MINICOMPACT", "TWO-SEATER", "STATION WAGON - SMALL", "FULL-SIZE", "SUV", "VAN - CARGO", "VAN - PASSENGER", "PICKUP TRUCK - STANDARD", "PICKUP TRUCK - SMALL", "MINIVAN", "SUV - STANDARD", "SPECIAL PURPOSE VEHICLE", "SUV - SMALL"};
		String [] years = {"2001","2002","2003","2004","2005","2006","2007","2008","2009","2010","2011","2012","2013","2014"};

		
		for(int i = 0; i < fuelTypes.length; i++){
			for(int j = 0; j < years.length; j++){
				for(int k = 0; k < formFactors.length; k++){
					vd.trimData(years[i], fuelTypes[i], formFactors[i]);
					vd.computeTopFive();
					ArrayList<String[]> top5 = vd.getTopFive();
					assertTrue(top5.size()==5 || top5.size() == 0);
				}
			}
		}
	}
}
