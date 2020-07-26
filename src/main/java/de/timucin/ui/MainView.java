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
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import de.timucin.backend.insightapi.WeatherDataRetriever;
import de.timucin.backend.model.SolTemperatureData;

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

	// create the grid
	Grid<SolTemperatureData> grid = new Grid<>(SolTemperatureData.class);
	grid.setItems(temperatureData);
	grid.setColumns("sol", "lastUTC", "avgTempF", "maxTempF", "minTempF");
	grid.getColumnByKey("lastUTC").setHeader("Date (UTC)");
	grid.getColumnByKey("avgTempF").setHeader("Average Temperature (° F)");
	grid.getColumnByKey("maxTempF").setHeader("Maximum Temperature (° F)");
	grid.getColumnByKey("minTempF").setHeader("Minimum Temperature (° F)");
	
	//create the column plot
	Chart chart = new Chart();
	Configuration config = chart.getConfiguration();
	config.setTitle("Weather data for the last " + temperatureData.size() + " Sol");
	chart.getConfiguration().getChart().setType(ChartType.COLUMN);
	
	config.addSeries(new ListSeries("Average Temp. (° F)", temperatureData.stream().map(s -> s.getAvgTempF()).collect(Collectors.toList())));
	config.addSeries(new ListSeries("Max Temp. (° F)", temperatureData.stream().map(s -> s.getMaxTempF()).collect(Collectors.toList())));
	config.addSeries(new ListSeries("Min Temp. (° F)", temperatureData.stream().map(s -> s.getMinTempF()).collect(Collectors.toList())));
	
	XAxis x = new XAxis();
	x.setCrosshair(new Crosshair());
	x.setCategories(temperatureData.stream().map(s -> new String(s.getSol() + "")).toArray(String[]::new));
	x.setTitle("Sol");
	config.addxAxis(x);
	
	Stream<Double> avgStream = temperatureData.stream().mapToDouble(s -> s.getAvgTempF()).boxed();
	Stream<Double> maxStream = temperatureData.stream().mapToDouble(s -> s.getMaxTempF()).boxed();
	Stream<Double> minStream = temperatureData.stream().mapToDouble(s -> s.getMinTempF()).boxed();
	
	double minTemp = Stream.concat(Stream.concat(avgStream, maxStream), minStream).mapToDouble(s -> s).min().getAsDouble();
	
	YAxis y = new YAxis();
	y.setMin((int) (minTemp - 20));
	y.setTitle("Temperature (° F)");
	config.addyAxis(y);
	

        Tooltip tooltip = new Tooltip();
        tooltip.setShared(true);
        config.setTooltip(tooltip);

	add(title, grid, chart);
    }

}
