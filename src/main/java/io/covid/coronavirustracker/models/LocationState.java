package io.covid.coronavirustracker.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class LocationState {

    private String state;
    private String country;
    private double longitude;
    private double latitude;
    private int latestTotalCases;

}
