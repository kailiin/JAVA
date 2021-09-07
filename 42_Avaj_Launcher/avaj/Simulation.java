package avaj;

import avaj.aircraft.AircraftFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

public class Simulation {

	public static void main(String[] args) throws MyException, FileNotFoundException {
		int				simulationTime = 0;
		String []		aircraftInfo;
		WeatherTower	weatherTower = new WeatherTower();
		PrintStream		consoleOut = System.out;
		
		try {
			File file = new File(args[0]);
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line;
			
			if ((line = br.readLine()) != null)
				simulationTime = Integer.parseInt(line);
			if (simulationTime <= 0)	
				throw new MyException("Simulation time < 1 !");
			
			File			simulationFile = new File("simulation.txt");
			PrintStream		stream = new PrintStream(simulationFile);
			System.setOut(stream);
			
			while ((line = br.readLine()) != null) {
				aircraftInfo = line.split(" ");
				if (aircraftInfo.length != 5)
					throw new MyException("Aircraft describe format error!");
				AircraftFactory.newAircraft(aircraftInfo[0], aircraftInfo[1], Integer.parseInt(aircraftInfo[2]),
						Integer.parseInt(aircraftInfo[3]), Integer.parseInt(aircraftInfo[4])).registerTower(weatherTower);
			}
			br.close();
			
			while (simulationTime > 0) {
				System.out.println("----------------");
				simulationTime --;
				weatherTower.changeWeather();
			}
			
		} catch (IOException | MyException | NumberFormatException e) {
			System.setOut(consoleOut);
			System.out.println("Error: " + e.getMessage());
		} 

	}

}

// compile to Java 7
// find * -name "*.java" > sources.txt
// javac --release 7 @sources.txt
// java avaj.Simulation avaj/testFile/scenario1.txt