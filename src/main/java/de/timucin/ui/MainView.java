package de.timucin.ui;

import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.Configuration;
import com.vaadin.flow.component.charts.model.Crosshair;
import com.vaadin.flow.component.charts.model.ListSeries;
import com.vaadin.flow.component.charts.model.Tooltip;
import com.vaadin.flow.component.charts.model.XAxis;
import com.vaadin.flow.component.charts.model.YAxis;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import de.timucin.backend.insightapi.WeatherDataRetriever;
import de.timucin.backend.model.SolTemperatureData;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Route
public class MainView extends VerticalLayout {

    /**
     * Construct a new Vaadin view.
     * <p>
     * Build the initial UI state for the user accessing the application.
     */
    public MainView() {
	H1 title = new H1("Weather on Mars");
	WeatherDataRetriever retriever = new WeatherDataRetriever();
	List<SolTemperatureData> temperatureData = retriever.fetchWeatherData();

	if (temperatureData.size() < 3) {
	    H2 noDataMessage = new H2(
		    "No new data available.\nData below is made up was created to show the functionality of the application. It is not lifted from official NASA sources. For official data, see https://mars.nasa.gov/insight/weather/");
	    add(title, noDataMessage);
	    temperatureData = SolTemperatureData.fakeData();
	}

	// create the grid
	Grid<SolTemperatureData> gridT = new Grid<>(SolTemperatureData.class);
	gridT.setItems(temperatureData);
	gridT.setColumns("sol", "firstUTC", "avgTempF", "maxTempF", "minTempF");
	gridT.getColumnByKey("firstUTC").setHeader("Date (UTC)");
	gridT.getColumnByKey("avgTempF").setHeader("Average Temperature (° F)");
	gridT.getColumnByKey("maxTempF").setHeader("Maximum Temperature (° F)");
	gridT.getColumnByKey("minTempF").setHeader("Minimum Temperature (° F)");

	Grid<SolTemperatureData> gridWS = new Grid<>(SolTemperatureData.class);
	gridWS.setItems(temperatureData);
	gridWS.setColumns("sol", "firstUTC", "avgWindSpeedMS", "maxWindSpeedMS", "minWindSpeedMS", "mostCommonWD");
	gridWS.getColumnByKey("firstUTC").setHeader("Date (UTC)");
	gridWS.getColumnByKey("avgWindSpeedMS").setHeader("Average Wind Speed (m/s)");
	gridWS.getColumnByKey("maxWindSpeedMS").setHeader("Maximum Wind Speed (m/s)");
	gridWS.getColumnByKey("minWindSpeedMS").setHeader("Minimum Wind Speed (m/s)");
	gridWS.getColumnByKey("mostCommonWD").setHeader("Wind Direction (most common)");

	// create the column plot for the temperature plots
	Chart chartTemp = new Chart();
	Configuration configTemp = chartTemp.getConfiguration();
	configTemp.setTitle("Weather data for the last " + temperatureData.size() + " Sol");
	configTemp.setSubTitle("Source: NASA InSight");
	chartTemp.getConfiguration().getChart().setType(ChartType.LINE);

	configTemp.addSeries(new ListSeries("Average Temp. (° F)",
		temperatureData.stream().map(s -> s.getAvgTempF()).collect(Collectors.toList())));
	configTemp.addSeries(new ListSeries("Max Temp. (° F)",
		temperatureData.stream().map(s -> s.getMaxTempF()).collect(Collectors.toList())));
	configTemp.addSeries(new ListSeries("Min Temp. (° F)",
		temperatureData.stream().map(s -> s.getMinTempF()).collect(Collectors.toList())));

	XAxis x = new XAxis();
	x.setCrosshair(new Crosshair());
	x.setCategories(temperatureData.stream().map(s -> new String(s.getSol() + "")).toArray(String[]::new));
	x.setTitle("Sol");
	configTemp.addxAxis(x);

	Stream<Double> avgStream = temperatureData.stream().mapToDouble(s -> s.getAvgTempF()).boxed();
	Stream<Double> maxStream = temperatureData.stream().mapToDouble(s -> s.getMaxTempF()).boxed();
	Stream<Double> minStream = temperatureData.stream().mapToDouble(s -> s.getMinTempF()).boxed();

	double minTemp = Stream.concat(Stream.concat(avgStream, maxStream), minStream).mapToDouble(s -> s).min()
		.getAsDouble();

	YAxis y = new YAxis();
	y.setMin((int) (minTemp - 20));
	y.setTitle("Temperature (° F)");
	configTemp.addyAxis(y);

	Tooltip tooltip = new Tooltip();
	tooltip.setShared(true);
	configTemp.setTooltip(tooltip);

	// create the colum plot for the wind speed plot
	Chart chartWS = new Chart();
	Configuration configWS = chartWS.getConfiguration();
	configWS.setTitle("Wind speeds for the last " + temperatureData.size() + " Sol");
	configWS.setSubTitle("Source: NASA InSight");
	chartWS.getConfiguration().getChart().setType(ChartType.LINE);

	configWS.addSeries(new ListSeries("Average Wind Speed (m/s)",
		temperatureData.stream().map(s -> s.getAvgWindSpeedMS()).collect(Collectors.toList())));
	configWS.addSeries(new ListSeries("Maximum Wind Speed (m/s)",
		temperatureData.stream().map(s -> s.getMaxWindSpeedMS()).collect(Collectors.toList())));
	configWS.addSeries(new ListSeries("Minimum Wind Speed (m/s)",
		temperatureData.stream().map(s -> s.getMinWindSpeedMS()).collect(Collectors.toList())));
	// we can reuse the same x-axis as above
	configWS.addxAxis(x);

	YAxis yWS = new YAxis();
	yWS.setMin(0);
	yWS.setTitle("Wind Speed (m/s)");
	configWS.addyAxis(yWS);
	configWS.setTooltip(tooltip);

	add(gridT, gridWS, chartTemp, chartWS);

    }
}
