/** 
 * @author Dimitri Gallos <dimitrios.gallos@mail.mcgill.ca>
 * @version 2013.10.06
 * Unit Testing Temperature class
 */

import org.junit.* ;

import static org.junit.Assert.* ;

public class TemperatureTest {
	
	private static final double error = 1e-6;
	/*//Testing if getUnits returns the right units for every possibility of units
	 */
	@Test 
	public void testGetUnits() {
		Temperature fahrenheit = new Temperature (20, Temperature.Units.FAHRENHEIT);
		Temperature celcius = new Temperature (20, Temperature.Units.CELSIUS);
		Temperature kelvin = new Temperature (20, Temperature.Units.KELVIN);
		

		assertSame("Units should be FAHRENHEIT", Temperature.Units.FAHRENHEIT, fahrenheit.getUnits());
		assertSame("Units should be CELSIUS", Temperature.Units.CELSIUS, celcius.getUnits());
		assertSame("Units should be KELVIN", Temperature.Units.KELVIN, kelvin.getUnits());	
	}
	
	/* Testing the getValue function of the Temperature class. 
	 * It should return the numerical value
	 * of the given temperature at the correct units. 
	 * The method getValue() of all 3 possible units will be tested. 
	 * The following test data will be used: 
	 * belowMinimumTemperature: will use this to see if it passes exceptions correctly when input is unacceptable value
	 * minimumTemperature: will use this to see how the program performs at the boundary
	 * randomMediumTemperature: will use this to see how program handles a random medium temperature
	 * randomHighTemperature: will use this to see how program handles a random high temperature
	 * A private function generateRandomTemperature ( double min, double max) that returns a random 
	 * number between a range of specified values is used to get random temperature values. 
	 */
	@Test (expected = Exception.class) 
	public void testGetValue() {
		
		double randomMediumTemperature = generateRandomTemperature ( 100, 1000);
		double randomHighTemperature = generateRandomTemperature (10e6, 10e8);
		
		//Testing getValue for Celcius
		Temperature belowMinimumTemperature = new Temperature (-280, Temperature.Units.CELSIUS);
		Temperature minimumTemperature = new Temperature (-273.15, Temperature.Units.CELSIUS);
		Temperature mediumTemperature = new Temperature ( randomMediumTemperature, Temperature.Units.CELSIUS);
		Temperature highTemperature = new Temperature ( randomHighTemperature, Temperature.Units.CELSIUS);
		
		assertFalse ("Out of range", belowMinimumTemperature.getValue() < -273.15); 
		assertEquals (" getValue() did not return the right temperature for the minimum temperature",
				                                                -273.15, minimumTemperature.getValue(), error);
		assertEquals (" getValue() did not return the right temperature for the medium temperature",
				                                                 randomMediumTemperature, mediumTemperature.getValue(), error);
		assertEquals (" getValue() did not return the right temperature for the high temperature",
                                                                  randomHighTemperature, highTemperature.getValue(), error);
		//Testing getValue() for Kelvin
		belowMinimumTemperature = new Temperature (-280, Temperature.Units.KELVIN);
		minimumTemperature = new Temperature (0, Temperature.Units.KELVIN);
		mediumTemperature = new Temperature ( randomMediumTemperature, Temperature.Units.KELVIN);
		highTemperature = new Temperature ( randomHighTemperature, Temperature.Units.KELVIN);
		
		assertFalse ("Out of range", belowMinimumTemperature.getValue() < 0); 
		assertEquals (" getValue() did not return the right temperature for the minimum temperature",
				                                                  0, minimumTemperature.getValue(), error);
		assertEquals (" getValue() did not return the right temperature for the medium temperature",
				                                                 randomMediumTemperature, mediumTemperature.getValue(), error);
		assertEquals (" getValue() did not return the right temperature for the high temperature",
                                                                  randomHighTemperature, highTemperature.getValue(), error);
		
		//Testing getValue() for Fahrenheit 
		belowMinimumTemperature = new Temperature (-600, Temperature.Units.FAHRENHEIT);
		minimumTemperature = new Temperature (-459.67, Temperature.Units.FAHRENHEIT);
		mediumTemperature = new Temperature ( randomMediumTemperature, Temperature.Units.FAHRENHEIT);
		highTemperature = new Temperature ( randomHighTemperature, Temperature.Units.FAHRENHEIT);
		
		assertFalse ("Out of range", belowMinimumTemperature.getValue() < -459.67); 
		assertEquals (" getValue() did not return the right temperature for the minimum temperature",
				                                                  -459.67, minimumTemperature.getValue(), error);
		assertEquals (" getValue() did not return the right temperature for the medium temperature",
				                                                 randomMediumTemperature, mediumTemperature.getValue(), error);
		assertEquals (" getValue() did not return the right temperature for the high temperature",
                                                                  randomHighTemperature, highTemperature.getValue(), error);
		
	}
	
	@Test
	/*Testing the changeUnits() function for all six possible conversions
	 * between Celsius, Fahrenheit and Kelvin for a minimum temperature, a
	 * medium temperature and a high temperature. Changing the units should change the
	 * value of the temperature in a consistent manner. 
	 */
	public void testChangeUnits() {
		double minimumTemperature, mediumTemperature, highTemperature;
		
		//Testing converting from Fahrenheit to other two units
		minimumTemperature = -459.67; //-273.15 in Celsius
		mediumTemperature = 392; //200 in Celsius
		highTemperature = 1800000032; //10e8 in Celsius
		Temperature minimumFahrenheit = new Temperature (minimumTemperature, Temperature.Units.FAHRENHEIT);
		Temperature mediumFahrenheit = new Temperature (mediumTemperature, Temperature.Units.FAHRENHEIT);
		Temperature highFahrenheit = new Temperature (highTemperature, Temperature.Units.FAHRENHEIT);
		
		//Testing Fahreneheit to Celcius conversion for all three values
		minimumFahrenheit.changeUnits(Temperature.Units.CELSIUS);
		mediumFahrenheit.changeUnits(Temperature.Units.CELSIUS);
		highFahrenheit.changeUnits(Temperature.Units.CELSIUS);
		
		assertEquals ("Failed to convert minimum Fahrenheit temperature to Celcius", -273.15, minimumFahrenheit.getValue(), error);
		assertEquals ("Failed to convert medium Fahrenheit temperature to Celsius", 200, mediumFahrenheit.getValue(), error);
		assertEquals ("Failed to convert high Fahrenheit temperature to Celsius", 10e8, highFahrenheit.getValue(), error);
		
		// Testing Fahrenheit to Kelvin conversion
		//reverting test cases to fahrenheit 
		minimumFahrenheit = new Temperature(minimumTemperature, Temperature.Units.FAHRENHEIT); 
		mediumFahrenheit = new Temperature (mediumTemperature, Temperature.Units.FAHRENHEIT);
		highFahrenheit = new Temperature (highTemperature, Temperature.Units.FAHRENHEIT);
		
		minimumFahrenheit.changeUnits(Temperature.Units.KELVIN);
		mediumFahrenheit.changeUnits(Temperature.Units.KELVIN);
		highFahrenheit.changeUnits(Temperature.Units.KELVIN);
		
		assertEquals ("Failed to convert minimum Fahrenheit temperature to Kelvin", 0, minimumFahrenheit.getValue(), error);
		assertEquals ("Failed to convert medium Fahrenheit temperature to Kelvin", 200 + 273.15, mediumFahrenheit.getValue(), error);
		assertEquals ("Failed to convert high Fahrenheit temperature to Kelvin", 10e8 + 273.15, highFahrenheit.getValue(), error);
		
		//Testing Celsius to other two units
		minimumTemperature = -273.15;
		mediumTemperature = 200; 
		highTemperature = 10e8; 
		Temperature minimumCelsius = new Temperature (minimumTemperature, Temperature.Units.CELSIUS);
		Temperature mediumCelsius = new Temperature (mediumTemperature, Temperature.Units.CELSIUS);
		Temperature highCelsius = new Temperature (highTemperature, Temperature.Units.CELSIUS);
		
		//Celsius to Fahrenheit 
		minimumCelsius.changeUnits(Temperature.Units.FAHRENHEIT);
		mediumCelsius.changeUnits(Temperature.Units.FAHRENHEIT);
		highCelsius.changeUnits(Temperature.Units.FAHRENHEIT);
		
		assertEquals ("Failed to convert minimum Celsius temperature to Fahrenheit", -459.67, minimumCelsius.getValue(), error);
		assertEquals ("Failed to convert medium Celsius temperature to Fahrenheit", 392, mediumCelsius.getValue(), error);
		assertEquals ("Failed to convert high Celsius temperature to Fahrenheit", 1800000032, highCelsius.getValue(), error);
		
		//Celsius to Kelvin 
		//revert temperatures to Celsius
		minimumCelsius = new Temperature (minimumTemperature, Temperature.Units.CELSIUS);
		mediumCelsius = new Temperature (mediumTemperature, Temperature.Units.CELSIUS);
		highCelsius = new Temperature (highTemperature, Temperature.Units.CELSIUS);
		
		minimumCelsius.changeUnits(Temperature.Units.KELVIN);
		mediumCelsius.changeUnits(Temperature.Units.KELVIN);
		highCelsius.changeUnits(Temperature.Units.KELVIN);
		
		assertEquals ("Failed to convert minimum Celsius temperature to Kelvin", 0, minimumCelsius.getValue(), error);
		assertEquals ("Failed to convert medium Celsius temperature to Kelvin", 200 + 273.15, mediumCelsius.getValue(), error);
		assertEquals ("Failed to convert high Celsius temperature to Kelvin", 10E8 + 273.15, highCelsius.getValue(), error);
		
		// Convert Kelvin to other two units
		minimumTemperature = 0;
		mediumTemperature = 200 + 273.15;
		highTemperature = 10E8 + 273.15; 
		
		Temperature minimumKelvin = new Temperature (minimumTemperature, Temperature.Units.KELVIN);
		Temperature mediumKelvin = new Temperature (mediumTemperature, Temperature.Units.KELVIN);
		Temperature highKelvin = new Temperature (highTemperature, Temperature.Units.KELVIN);
		
		//Kelvin to Celsius
		minimumKelvin.changeUnits(Temperature.Units.CELSIUS);
		mediumKelvin.changeUnits(Temperature.Units.CELSIUS);
		highKelvin.changeUnits(Temperature.Units.CELSIUS);
		
		assertEquals ("Failed to convert minimum Kelvin temperature to Celsius", -273.15, minimumKelvin.getValue(), error);
		assertEquals ("Failed to convert medium Kelvin temperature to Celsius", 200, mediumKelvin.getValue(), error);
		assertEquals ("Failed to convert high Kelvin temperature to Celsius", 10E8, highKelvin.getValue(), error);
		
		//Kelvin to Fahrenheit 
		//revert to Kelvin
		minimumKelvin = new Temperature (minimumTemperature, Temperature.Units.KELVIN);
		mediumKelvin = new Temperature (mediumTemperature, Temperature.Units.KELVIN);
		highKelvin = new Temperature (highTemperature, Temperature.Units.KELVIN);
		
		minimumKelvin.changeUnits(Temperature.Units.FAHRENHEIT);
		mediumKelvin.changeUnits(Temperature.Units.FAHRENHEIT);
		highKelvin.changeUnits(Temperature.Units.FAHRENHEIT);
		
		assertEquals ("Failed to convert minimum Kelvin temperature to Fahrenheit", -459.67, minimumKelvin.getValue(), error);
		assertEquals ("Failed to convert medium Kelvin temperature to Fahrenheit", 392, mediumKelvin.getValue(), error);
		assertEquals ("Failed to convert high Kelvin temperature to Fahrenheit", 1800000032, highKelvin.getValue(), error);	
	}
	
	//returns a random  number between a range of specified values 
	private double generateRandomTemperature (double min, double max) {
		return min + Math.random() * ((max-min) + 1);
	}
	
}
