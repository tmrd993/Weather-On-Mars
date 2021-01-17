package de.timucin.backend.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Model representing weather data for a single sol (solar day) at Elysium
 * Planitia
 * 
 * @author Timucin Merdin
 *
 */

public class SolTemperatureData {
    // current NASA InSight solar day
    private final int sol;
    // time of first datum (UTC) for any sensor at Elysium Planitia
    private final LocalDate firstUTC;
    // max recorded temperature
    private final double maxTempF;
    // min recorded temperature
    private final double minTempF;
    // avg recorded temperature
    private final double avgTempF;

    private final double avgWindSpeedMS;
    private final double maxWindSpeedMS;
    private final double minWindSpeedMS;

    private final String mostCommonWD;

    public SolTemperatureData(int sol, LocalDate firstUtc, double maxTemp, double minTemp, double avgTemp,
	    double avgWindSpeedMS, double maxWindSpeedMS, double minWindSpeedMS, String mostCommonWD) {
	this.sol = sol;
	this.firstUTC = firstUtc;
	this.maxTempF = Math.floor(maxTemp * 100) / 100;
	this.minTempF = Math.floor(minTemp * 100) / 100;
	this.avgTempF = Math.floor(avgTemp * 100) / 100;
	this.avgWindSpeedMS = Math.floor(avgWindSpeedMS * 100) / 100;
	this.maxWindSpeedMS = Math.floor(maxWindSpeedMS * 100) / 100;
	this.minWindSpeedMS = Math.floor(minWindSpeedMS * 100) / 100;
	this.mostCommonWD = mostCommonWD;
    }

    public int getSol() {
	return sol;
    }

    public LocalDate getFirstUTC() {
	return firstUTC;
    }

    public double getMaxTempF() {
	return maxTempF;
    }

    public double getMinTempF() {
	return minTempF;
    }

    public double getAvgTempF() {
	return avgTempF;
    }
    
    public double getAvgWindSpeedMS() {
	return avgWindSpeedMS;
    }
    
    public double getMaxWindSpeedMS() {
	return maxWindSpeedMS;
    }
    
    public double getMinWindSpeedMS() {
	return minWindSpeedMS;
    }
    
    public String getMostCommonWD() {
	return mostCommonWD;
    }

    @Override
    public String toString() {
	return "Sol: " + sol + "\n" + "Datum of last sensor: " + firstUTC.toString() + "\n" + "Temperature:" + "\n\t"
		+ "Average: " + avgTempF + "° F\n\t" + "Max: " + maxTempF + "° F\n\t" + "Min: " + minTempF + "° F\n";
    }
    
    // static factory method for testing purposes
    public static List<SolTemperatureData> fakeData() {
	List<SolTemperatureData> fakeData = new ArrayList<>();
	fakeData.add(new SolTemperatureData(589, LocalDate.of(2020, 7, 23), -13.111, -90.183, -57.947, 16.061, 0.2, 5.621, "WNW"));
	fakeData.add(new SolTemperatureData(590, LocalDate.of(2020, 7, 23), -11.875, -90.12, -60.247,31.061, 9.5, 0.621, "SW"));
	fakeData.add(new SolTemperatureData(591, LocalDate.of(2020, 7, 24), -12.71, -89.83, -59.547, 13.061, 0.1, 4.521, "WNW"));
	fakeData.add(new SolTemperatureData(592, LocalDate.of(2020, 7, 25), -11.151, -91.163, -57.947, 33.61, 9.4, 0.61, "SW"));
	fakeData.add(new SolTemperatureData(593, LocalDate.of(2020, 7, 26), -10.871, -92.183, -57.947, 19.061, 0.2, 5.721, "WNW"));
	fakeData.add(new SolTemperatureData(594, LocalDate.of(2020, 7, 27), -9.11, -88.183, -57.947, 14.061, 0.2, 5.221, "WNW"));
	fakeData.add(new SolTemperatureData(595, LocalDate.of(2020, 7, 28), -12.111, -87.23, -57.947, 11.061, 0.1, 4.921, "WNW"));
	
	return fakeData;
    }

}
