package de.timucin.backend.model;

import java.time.LocalDate;

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

}
