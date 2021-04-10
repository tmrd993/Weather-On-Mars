package org.timucin.backend.service;

import java.util.List;

import de.timucin.backend.model.SolTemperatureData;

public interface DataRetrieverService {
	public List<SolTemperatureData> fetchCurrentWeatherData();
	public String dataSourceIdentifier();
	public String apiURL();

}
