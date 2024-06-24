
import kc.APIHandler;
import kc.Installation;
import kc.Station;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class APIHandlerTest {
    @Mock
    private HttpClient client;

    @Mock
    private HttpResponse<String> response;

    private APIHandler apiHandler;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        apiHandler = new APIHandler(client);
    }

    @Test
    public void getAllStationsReturnsExpectedStations() throws Exception {
        when(client.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(response);
        when(response.body()).thenReturn("[{\"id\":1,\"stationName\":\"Station1\"},{\"id\":2,\"stationName\":\"Station2\"}]");

        List<Station> stations = apiHandler.getAllStations();

        assertEquals(2, stations.size());
        assertEquals("Station1", stations.get(0).getName());
        assertEquals("Station2", stations.get(1).getName());
    }

    @Test
    public void getAllStationsReturnsEmptyListOnException() throws Exception {
        when(client.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenThrow(new RuntimeException());

        List<Station> stations = apiHandler.getAllStations();

        assertTrue(stations.isEmpty());
    }

    @Test
    public void getInstallationsForStationReturnsExpectedInstallations() throws Exception {
        when(client.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(response);
        when(response.body()).thenReturn("[{\"id\":1,\"param\":{\"paramCode\":\"Code1\"}},{\"id\":2,\"param\":{\"paramCode\":\"Code2\"}}]");

        List<Installation> installations = apiHandler.getInstallationsForStation(1);

        assertEquals(2, installations.size());
        assertEquals("Code1", installations.get(0).getParamCode());
        assertEquals("Code2", installations.get(1).getParamCode());
    }

    @Test
    public void getInstallationsForStationReturnsEmptyListOnException() throws Exception {
        when(client.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenThrow(new RuntimeException());

        List<Installation> installations = apiHandler.getInstallationsForStation(1);

        assertTrue(installations.isEmpty());
    }
}