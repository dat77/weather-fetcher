package com.dat.wether_fetcher.services;

import com.dat.wether_fetcher.entities.CityData;
import com.dat.wether_fetcher.exceptions.DataConversionException;
import com.dat.wether_fetcher.exceptions.UnableRetrieveDataException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;

public class CityDataService {

  private static final String GEOCODING_API_URL = "https://geocoding-api.open-meteo.com/v1/search?name=%s&count=1&language=en&format=json";
  private ObjectMapper objectMapper = new ObjectMapper();

  public CityData getCityData(String city) {
    try {
      Client client = ClientBuilder.newClient();
      WebTarget webTarget = client.target(String.format(GEOCODING_API_URL, city));
      String response = webTarget.request().get(String.class);
      JsonNode jsonNode = objectMapper.readTree(response);
      JsonNode resultsNode = jsonNode.get("results").get(0);
      CityData cityData = new CityData().builder()
          .name(resultsNode.get("name").asText())
          .country(resultsNode.get("country").asText())
          .latitude(resultsNode.get("latitude").asDouble())
          .longitude(resultsNode.get("longitude").asDouble())
          .timezone(resultsNode.get("timezone").asText())
          .build();
      return cityData;
    } catch (JsonProcessingException e) {
      throw new DataConversionException(e.getMessage());
    } catch (Exception e) {
      throw new UnableRetrieveDataException(e.getMessage());
    }
  }


}
