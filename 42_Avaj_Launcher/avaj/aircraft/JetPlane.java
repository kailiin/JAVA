package avaj.aircraft;

import avaj.Flyable;
import avaj.WeatherTower;
import avaj.Coordinates;

public class JetPlane extends Aircraft implements Flyable {
	private WeatherTower  weatherTower;

	JetPlane(String name, Coordinates coordinates) {
		super(name, coordinates);
	}

	@Override
	public void updateConditions() {
		String weather = this.weatherTower.getWeather(this.coordinates);
		System.out.print("JetPlace#" + this.name + "(" + this.id + "): ");
		
		switch(weather) {
		case "SUN":
			this.coordinates.update(10, 0, 2);
			System.out.println("Sunny days are my favorite days.");
			break;
		case "RAIN":
			this.coordinates.update(0, 0, 5);
			System.out.println("It's raining. Better watch out for lightings.");
			break;
		case "FOG":
			this.coordinates.update(0 , 0, 1);
			System.out.println("I'm not afraid of fog!");
			break;
		default:
			this.coordinates.update(0, 0, -7);
			System.out.println("OMG! Winter is coming!");
		}
		
		if (this.coordinates.getHeight() <= 0 ) {
			this.weatherTower.unregister(this);
			System.out.println("JetPlane#" + this.name + "(" + this.id + ") landing.");
			System.out.println("Tower says: JetPlane#" + this.name + "(" + this.id + ") unregistered to weather tower.");
		}
	}

	@Override
	public void registerTower(WeatherTower weatherTower) {
		this.weatherTower = weatherTower;
		this.weatherTower.register(this);
		System.out.println("Tower says: JetPlane#" + this.name + "(" + this.id + ") registered to weather tower.");
	}

}
