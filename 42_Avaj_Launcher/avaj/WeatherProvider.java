package avaj;

public class WeatherProvider {
	private static WeatherProvider weatherprovider = new WeatherProvider();
	private static String weather[] = {"RAIN", "FOG", "SUN", "SNOW"};
	
	private WeatherProvider() {}

	public static WeatherProvider getProvider() {
		return weatherprovider;
	}

	public String getCurrentWeather(Coordinates coordinates) {
		int i = coordinates.getHeight() + coordinates.getLatitude() + coordinates.getLongitude();
		return weather[i % 4];
	}

}
