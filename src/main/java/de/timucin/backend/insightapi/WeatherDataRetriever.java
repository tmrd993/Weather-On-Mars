package de.timucin.backend.insightapi;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import de.timucin.backend.constants.ApiConstants;
import de.timucin.backend.model.SolTemperatureData;

public class WeatherDataRetriever {

    private static final Cache<String, List<SolTemperatureData>> cache;
    private static final String solDataList = "SOL_DATA_LIST";

    static {
	cache = Caffeine.newBuilder().expireAfterWrite(24, TimeUnit.HOURS).maximumSize(50).build();
    }

    public List<SolTemperatureData> fetchWeatherData() {
	
	List<SolTemperatureData> weatherData = cache.getIfPresent(solDataList);
	if(weatherData != null) {
	    return weatherData;
	}
	
	weatherData = new ArrayList<>();

	try {
	    URL requestURL = new URL(ApiConstants.REQUEST_URL_STR);
	    HttpURLConnection httpconnection = (HttpURLConnection) requestURL.openConnection();
	    httpconnection.connect();

	    InputStreamReader isr = new InputStreamReader((InputStream) httpconnection.getContent());
	    JsonElement rootElement = JsonParser.parseReader(isr);
	    JsonObject rootObject = rootElement.getAsJsonObject();

	    List<String> solKeys = new ArrayList<>();
	    for (JsonElement el : rootObject.get("sol_keys").getAsJsonArray()) {
		solKeys.add(el.getAsString());
	    }

	    for (String sol : solKeys) {
		JsonElement atmosphericTempElement = rootObject.get(sol).getAsJsonObject().get("AT");
		double avgT = atmosphericTempElement.getAsJsonObject().get("av").getAsDouble();
		double maxT = atmosphericTempElement.getAsJsonObject().get("mx").getAsDouble();
		double minT = atmosphericTempElement.getAsJsonObject().get("mn").getAsDouble();
		String firstUTCStr = rootObject.get(sol).getAsJsonObject().get("First_UTC").getAsString();
		LocalDate date = LocalDate.parse(firstUTCStr.substring(0, firstUTCStr.indexOf("T")));

		JsonElement horizontalWindSpeedElement = rootObject.get(sol).getAsJsonObject().get("HWS");
		double avgWs = horizontalWindSpeedElement.getAsJsonObject().get("av").getAsDouble();
		double maxWs = horizontalWindSpeedElement.getAsJsonObject().get("mx").getAsDouble();
		double minWs = horizontalWindSpeedElement.getAsJsonObject().get("mn").getAsDouble();

		JsonElement directionElement = rootObject.get(sol).getAsJsonObject().get("WD");
		String mostCommonWD = directionElement.getAsJsonObject().get("most_common").getAsJsonObject()
			.get("compass_point").getAsString();

		boolean temperatureDataPresent = rootObject.get("validity_checks").getAsJsonObject().get(sol)
			.getAsJsonObject().get("AT").getAsJsonObject().get("valid").getAsBoolean();
		boolean windSpeedDataPresent = rootObject.get("validity_checks").getAsJsonObject().get(sol)
			.getAsJsonObject().get("HWS").getAsJsonObject().get("valid").getAsBoolean();
		boolean directionPresent = rootObject.get("validity_checks").getAsJsonObject().get(sol)
			.getAsJsonObject().get("WD").getAsJsonObject().get("valid").getAsBoolean();

		// create new temperature data object if validity checks are true
		if (temperatureDataPresent && windSpeedDataPresent && directionPresent) {
		    weatherData.add(new SolTemperatureData(Integer.parseInt(sol), date, maxT, minT, avgT, avgWs, maxWs,
			    minWs, mostCommonWD));
		}

	    }

	} catch (IOException e) {
	    e.printStackTrace();
	}
	cache.put(solDataList, weatherData);
	return weatherData;
    }
}
