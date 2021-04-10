# Summary
This is a simple web application that displays the weather at Elysium Planitia on Mars.  
The Data is obtained through the NASA InSight Weather API. You can get a free API-Key at https://api.nasa.gov/  
Once you have your key, add it to the URL defined in the properties file

```
insighturl = https://api.nasa.gov/insight_weather/?api_key=<YOUR_API_KEY_HERE>&feedtype=json&ver=1.0
```

Live demo: https://weather-on-mars.herokuapp.com/   
Deployed using heroku (https://heroku.com/)

# How to
1. clone the repo and import it to your IDE as an existing Maven project
2. run the app using the Application class
3. navigate to ```localhost:8080/```

# Screenshots
## Title page
![title page](screenshots/marsWeather01.png) 


## Plot
![line plot1](screenshots/MarsWeatherPlot1.png) 


![line plot2](screenshots/MarsWeatherPlot2.png)

## Acknowledgements
Huge thanks to the creators and contributors of the following third party libraries and frameworks which were used in this project  
+ [Vaadin Framework](https://vaadin.com/)  
+ [Google gson](https://github.com/google/gson)  
+ [Caffeine](https://github.com/ben-manes/caffeine)


