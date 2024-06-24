package kc;

import java.util.List;

public class Station {
    private int id;
    private String name;
    private List<Installation> installations;

    public Station(int id, String name, List<Installation> installations) {
        this.id = id;
        this.name = name;
        this.installations = installations;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Installation> getInstallations() {
        return installations;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Station #").append(id).append(" (").append(name).append("):\n");
        for (Installation installation : installations) {
            sb.append(installation).append("\n");
        }
        return sb.toString();
    }

}