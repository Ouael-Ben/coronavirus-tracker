package io.covid.coronavirustracker.service;

import io.covid.coronavirustracker.models.LocationState;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoronaVirusDataService {

    private static final String URL_VIRUS_DATA = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Confirmed.csv";
    private List<LocationState> locationStateList = new ArrayList<>();

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void fetchVirusData() throws IOException {
        List<LocationState> newStats = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(URL_VIRUS_DATA, String.class);
        StringReader csvBodyReader = new StringReader(response.getBody());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        for (CSVRecord record : records) {
            LocationState locationState = new LocationState();
            locationState.setState(record.get("Province/State"));
            locationState.setCountry(record.get("Country/Region"));
            locationState.setLatitude(Double.parseDouble(record.get("Lat")));
            locationState.setLongitude(Double.parseDouble(record.get("Long")));
            locationState.setLatestTotalCases(Integer.parseInt(record.get(record.size() - 1)));
            System.out.println(locationState.toString());
            newStats.add(locationState);
        }
        this.locationStateList = newStats;
    }

    public List<LocationState> getLocationStateList() {
        return locationStateList;
    }
}
