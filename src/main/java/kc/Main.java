package kc;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        APIHandler apiHandler = new APIHandler();
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