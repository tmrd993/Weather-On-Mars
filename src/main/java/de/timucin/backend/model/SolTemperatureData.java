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
    // time of last datum (UTC) for any sensor at Elysium Planitia
    private final LocalDate lastUTC;
    // max recorded temperature
    private final double maxTempF;
    // min recorded temperature
    private final double minTempF;
    // avg recorded temperature
    private final double avgTempF;

    public SolTemperatureData(int sol, LocalDate lastUtc, double maxTemp, double minTemp, double avgTemp) {
	this.sol = sol;
	this.lastUTC = lastUtc;
	this.maxTempF = maxTemp;
	this.minTempF = minTemp;
	this.avgTempF = avgTemp;
    }

    public int getSol() {
	return sol;
    }

    public LocalDate getLastUTC() {
	return lastUTC;
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

    @Override
    public String toString() {
	return "Sol: " + sol + "\n" + "Datum of last sensor: " + lastUTC.toString() + "\n" + "Temperature:" + "\n\t"
		+ "Average: " + avgTempF + "° F\n\t" + "Max: " + maxTempF + "° F\n\t" + "Min: " + minTempF + "° F\n";
    }

}
