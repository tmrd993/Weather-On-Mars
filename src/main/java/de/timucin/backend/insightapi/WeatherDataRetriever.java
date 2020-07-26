package de.timucin.backend.insightapi;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import de.timucin.backend.constants.ApiConstants;
import de.timucin.backend.model.SolTemperatureData;

public class WeatherDataRetriever {

    public List<SolTemperatureData> fetchWeatherData() {
	List<SolTemperatureData> weatherData = new ArrayList<>();

	try {
	    URL requestURL = new URL(ApiConstants.REQUEST_URL_STR);
	    HttpURLConnection httpconnection= (HttpURLConnection) requestURL.openConnection();
	    httpconnection.connect();
	    
	    InputStreamReader isr = new InputStreamReader((InputStream) httpconnection.getContent());
	    JsonElement rootElement = JsonParser.parseReader(isr);
	    JsonObject rootObject = rootElement.getAsJsonObject();
	    
	   
	    List<String> solKeys = new ArrayList<>();
	    for(JsonElement el : rootObject.get("sol_keys").getAsJsonArray()) {
		solKeys.add(el.getAsString());
	    }
	    
	    for(String sol : solKeys) {
		JsonElement atmosphericTempElement = rootObject.get(sol).getAsJsonObject().get("AT");
		double avg = atmosphericTempElement.getAsJsonObject().get("av").getAsDouble();
		double max = atmosphericTempElement.getAsJsonObject().get("mx").getAsDouble();
		double min = atmosphericTempElement.getAsJsonObject().get("mn").getAsDouble();
		String lastUTCStr = rootObject.get(sol).getAsJsonObject().get("Last_UTC").getAsString();
		LocalDate date = LocalDate.parse(lastUTCStr.substring(0, lastUTCStr.indexOf("T")));
		
		weatherData.add(new SolTemperatureData(Integer.parseInt(sol), date, max, min, avg));
	    }

	} catch (IOException e) {
	    e.printStackTrace();
	}

	return weatherData;
    }
}
