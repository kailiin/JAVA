package avaj.aircraft;

import avaj.Flyable;
import avaj.Coordinates;
import avaj.MyException;

public class AircraftFactory {
	
	public static Flyable newAircraft(String type, String name, int longitude, int latitude, int height) throws MyException {
		
		if (longitude < 0 || latitude < 0 || height < 0)
			throw new MyException("Coordinates are negative numbers!");
		
		if (height > 100)
			height = 100;
			
		Coordinates coord = new Coordinates(longitude, latitude, height);
		
		switch (type) {
		case ("JetPlane"):
			return new JetPlane(name, coord);
		case ("Helicopter"):
			return new Helicopter(name, coord);
		case ("Baloon"):
			return new Baloon(name, coord);
		default:
			throw new MyException("Aircraft type does not exist!");
		}
		
	}

}
