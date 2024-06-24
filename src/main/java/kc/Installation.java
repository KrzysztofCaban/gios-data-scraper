package kc;

public class Installation {
    private int id;
    private String paramCode;

    public Installation(int id, String paramCode) {
        this.id = id;
        this.paramCode = paramCode;
    }

    public int getId() {
        return id;
    }

    public String getParamCode() {
        return paramCode;
    }

    @Override
    public String toString() {
        return "installation #" + id + ": '" + paramCode + "'";
    }
}