package com.dat.wether_fetcher.controllers;

import com.dat.wether_fetcher.entities.CityData;
import com.dat.wether_fetcher.exceptions.DataConversionException;
import com.dat.wether_fetcher.services.CityDataService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/city")
public class CityDataController {

  private ObjectMapper objectMapper = new ObjectMapper();
  private CityDataService cityDataService = new CityDataService();

  @GET
  @Path("/{city}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getSystemHealth(@PathParam("city") String city) {
    CityData cityData = cityDataService.getCityData(city);
    try {
      return Response.ok(objectMapper.writeValueAsString(cityData)).build();
    } catch (JsonProcessingException e) {
      throw new DataConversionException(e.getMessage());
    }
  }

}
