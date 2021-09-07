package avaj.aircraft;

import avaj.Flyable;
import avaj.WeatherTower;
import avaj.Coordinates;

public class Helicopter extends Aircraft implements Flyable {
	private WeatherTower  weatherTower;
	
	Helicopter(String name, Coordinates coordinates) {
		super(name, coordinates);
	}

	@Override
	public void updateConditions() {
		String weather = this.weatherTower.getWeather(this.coordinates);
		System.out.print("Helicopter#" + this.name + "(" + this.id + "): ");
		
		switch(weather) {
		case "SUN":
			this.coordinates.update(10, 0, 2);
			System.out.println("This is hot.");
			break;
		case "RAIN":
			this.coordinates.update(0, 0, 5);
			System.out.println("Keep calm and love rainy days.");
			break;
		case "FOG":
			this.coordinates.update(0 , 0, 1);
			System.out.println("Oh no, the fog is rising.");
			break;
		default:
			this.coordinates.update(0, 0, -12);
			System.out.println("My rotor is going to freeze!");
		}
		
		if (this.coordinates.getHeight() <= 0 ) {
			this.weatherTower.unregister(this);
			System.out.println("Helicopter#" + this.name + "(" + this.id + ") landing.");
			System.out.println("Tower says: Helicopter#" + this.name + "(" + this.id + ") unregistered to weather tower.");
		}
	}

	@Override
	public void registerTower(WeatherTower weatherTower) {
		this.weatherTower = weatherTower;
		this.weatherTower.register(this);
		System.out.println("Tower says: Helicopter#" + this.name + "(" + this.id + ") registered to weather tower.");
	}

}
