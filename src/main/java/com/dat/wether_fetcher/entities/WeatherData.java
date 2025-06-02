package com.dat.wether_fetcher.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class WeatherData {
  @JsonProperty
  private String city;
  @JsonProperty
  private String temperature;
  @JsonProperty
  private String weatherCondition;
  @JsonProperty
  private String windSpeed;
}
