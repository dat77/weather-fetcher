package com.dat.wether_fetcher.controllers;

import com.dat.wether_fetcher.entities.WeatherData;
import com.dat.wether_fetcher.exceptions.DataConversionException;
import com.dat.wether_fetcher.services.WeatherFetcherService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/weather")
public class WeatherFetcherController {

  private ObjectMapper objectMapper = new ObjectMapper();
  private WeatherFetcherService weatherFetcherService = new WeatherFetcherService();

  @GET
  @Path("/health")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getSystemHealth(){
    ObjectNode jsonNodes = objectMapper.createObjectNode();
    jsonNodes.put("healthcheck", "OK");
    return Response.ok(jsonNodes.toPrettyString()).build();
  }

  @POST
  @Path("/city")
  @Consumes(MediaType.TEXT_PLAIN)
  @Produces(MediaType.APPLICATION_JSON)
  public Response getWeatherInTheCity(String inputData){
    System.out.println("Input: " + inputData);
    WeatherData weatherData = weatherFetcherService.getWeatherInTheCity(inputData);
    try {
      return Response.ok(objectMapper.writeValueAsString(weatherData)).build();
    } catch (JsonProcessingException e) {
      throw new DataConversionException(e.getMessage());
    }
  }

}
