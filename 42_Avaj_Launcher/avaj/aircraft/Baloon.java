package avaj.aircraft;

import avaj.Flyable;
import avaj.WeatherTower;
import avaj.Coordinates;

public class Baloon extends Aircraft implements Flyable {
	private WeatherTower  weatherTower;

	Baloon(String name, Coordinates coordinates) {
		super(name, coordinates);
	}

	@Override
	public void updateConditions() {
		String weather = this.weatherTower.getWeather(this.coordinates);
		System.out.print("Baloon#" + this.name + "(" + this.id + "): ");
		
		switch(weather) {
		case "SUN":
			this.coordinates.update(2, 0, 4);
			System.out.println("Let's enjoy the good weather and take some pics.");
			break;
		case "RAIN":
			this.coordinates.update(0, 0, -5);
			System.out.println("Damn you rain! You messed up my baloon.");
			break;
		case "FOG":
			this.coordinates.update(0 , 0, -3);
			System.out.println("I must go in, the fog is rising.");
			break;
		default:
			this.coordinates.update(0, 0, -15);
			System.out.println("It's snowing. We're gonna crash.");
		}
		
		if (this.coordinates.getHeight() <= 0 ) {
			this.weatherTower.unregister(this);
			System.out.println("Baloon#" + this.name + "(" + this.id + ") landing.");
			System.out.println("Tower says: Baloon#" + this.name + "(" + this.id + ") unregistered to weather tower.");
		}
	}

	@Override
	public void registerTower(WeatherTower weatherTower) {
		this.weatherTower = weatherTower;
		this.weatherTower.register(this);
		System.out.println("Tower says: Baloon#" + this.name + "(" + this.id + ") registered to weather tower.");
	}

}
