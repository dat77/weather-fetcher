package com.dat.wether_fetcher.services;

import com.dat.wether_fetcher.entities.CityData;
import com.dat.wether_fetcher.entities.WeatherData;
import com.dat.wether_fetcher.exceptions.DataConversionException;
import com.dat.wether_fetcher.exceptions.UnableRetrieveDataException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;

public class WeatherFetcherService {

  private static final String OPEN_METEO_API_URL = "https://api.open-meteo.com/v1/forecast?latitude=%s&longitude=%s&current_weather=true&timezone=%s";
  private ObjectMapper objectMapper = new ObjectMapper();
  private CityDataService cityDataService = new CityDataService();

  public WeatherData getWeatherInTheCity(String city) {
    try {
      CityData cityData = cityDataService.getCityData(city.split("\\W")[0]);
      Client client = ClientBuilder.newClient();
      WebTarget webTarget = client.target(String.format(OPEN_METEO_API_URL,
          cityData.getLatitude().toString(), cityData.getLongitude().toString(),
          cityData.getTimezone()));
      String response = webTarget.request().get(String.class);
      JsonNode jsonNode = objectMapper.readTree(response);
      JsonNode units = jsonNode.get("current_weather_units");
      JsonNode weather = jsonNode.get("current_weather");
      WeatherData weatherData = new WeatherData().builder()
          .city(city)
          .temperature(weather.get("temperature").asText() + units.get("temperature").asText())
          .weatherCondition(getWeatherConditions(weather.get("weathercode").asInt()))
          .windSpeed(weather.get("windspeed").asText() + units.get("windspeed").asText())
          .build();
      return weatherData;

    } catch (JsonProcessingException e) {
      throw new DataConversionException(e.getMessage());
    } catch (Exception e) {
      throw new UnableRetrieveDataException(e.getMessage());
    }
  }

  private String getWeatherConditions(int weathercode) {
    switch (weathercode) {
      case 0:
        return "Clear sky";
      case 1, 2, 3:
        return "Mainly clear, partly cloudy, and overcast";
      case 45, 48:
        return "Fog and depositing rime fog";
      case 51, 53, 55:
        return "Drizzle: Light, moderate, and dense intensity";
      case 56, 57:
        return "Freezing Drizzle: Light and dense intensity";
      case 61, 63, 65:
        return "Rain: Slight, moderate and heavy intensity";
      case 66, 67:
        return "Freezing Rain:Light and heavy intensity";
      case 71, 73, 75:
        return "Snow fall: Slight, moderate, and heavy intensity";
      case 77:
        return "Snow grains";
      case 80, 81, 82:
        return "Rain showers:Slight, moderate, and violent";
      case 85, 86:
        return "Snow showers slight and heavy";
      case 95:
        return "Thunderstorm: Slight or moderate";
      case 96, 99:
        return "Thunderstorm with slight and heavy hail";
    }
    return "Undefined";
  }
}

