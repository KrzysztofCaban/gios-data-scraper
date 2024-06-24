package kc;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class APIHandler {
    private static final String STATIONS_ENDPOINT = "http://api.gios.gov.pl/pjp-api/rest/station/findAll";
    private static final String INSTALLATIONS_ENDPOINT = "http://api.gios.gov.pl/pjp-api/rest/station/sensors/";
    private static final String ID_KEY = "id";
    private static final String STATION_NAME_KEY = "stationName";
    private static final String PARAM_KEY = "param";
    private static final String PARAM_CODE_KEY = "paramCode";

    private static final int MAX_RETRIES = 3;
    private static final long RETRY_DELAY_MS = 3000;
    private HttpClient client;

    public APIHandler(HttpClient client) {
        this.client = client;
    }

    public List<Station> getAllStations() {
        try {
            HttpRequest request = createRequest(STATIONS_ENDPOINT);
            JSONArray stations = sendRequestAndGetResponse(request);
            return createStationsFromJson(stations);
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private List<Station> createStationsFromJson(JSONArray stationsArray) {
        List<Station> stationList = new ArrayList<>();
        for (int i = 0; i < stationsArray.length(); i++) {
            createStationFromJson(stationsArray.getJSONObject(i)).ifPresent(stationList::add);
        }
        return stationList;
    }

    private Optional<Station> createStationFromJson(JSONObject stationObject) {
        try {
            int id = stationObject.getInt(ID_KEY);
            String name = stationObject.getString(STATION_NAME_KEY);
            List<Installation> installations = getInstallationsForStation(id);
            return Optional.of(new Station(id, name, installations));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public List<Installation> getInstallationsForStation(int stationId) {
        try {
            HttpRequest request = createRequest(INSTALLATIONS_ENDPOINT + stationId);
            JSONArray installations = sendRequestAndGetResponse(request);
            return createInstallationsFromJson(installations);
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private List<Installation> createInstallationsFromJson(JSONArray installationsArray) {
        List<Installation> installationList = new ArrayList<>();
        for (int i = 0; i < installationsArray.length(); i++) {
            createInstallationFromJson(installationsArray.getJSONObject(i)).ifPresent(installationList::add);
        }
        return installationList;
    }

    private Optional<Installation> createInstallationFromJson(JSONObject installationObject) {
        try {
            int id = installationObject.getInt(ID_KEY);
            String paramCode = installationObject.getJSONObject(PARAM_KEY).getString(PARAM_CODE_KEY);
            return Optional.of(new Installation(id, paramCode));
        } catch (JSONException e) {
            return Optional.empty();
        }
    }

    private HttpRequest createRequest(String endpoint) {
        return HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .build();
    }

    private JSONArray sendRequestAndGetResponse(HttpRequest request) throws IOException, InterruptedException, JSONException {
        int retries = 0;
        while (true) {
            try {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                return new JSONArray(response.body());
            } catch (IOException | InterruptedException | JSONException e) {
                if (++retries == MAX_RETRIES) {
                    throw e;
                }
                Thread.sleep(RETRY_DELAY_MS);
            }
        }
    }
}