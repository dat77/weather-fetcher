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
public class CityData {
  @JsonProperty
  String name;
  @JsonProperty
  String country;
  @JsonProperty
  Double latitude;
  @JsonProperty
  Double longitude;
  @JsonProperty
  String timezone;
}
