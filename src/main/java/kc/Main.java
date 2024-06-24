package kc;

import java.net.http.HttpClient;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        HttpClient client = HttpClient.newHttpClient();
        APIHandler apiHandler = new APIHandler(client);
        List<Station> stations = apiHandler.getAllStations();

        if (stations.isEmpty()) {
            System.out.println("No stations available at the moment.");
            return;
        }

        for (Station station : stations) {
            System.out.println(station);
        }
    }
}