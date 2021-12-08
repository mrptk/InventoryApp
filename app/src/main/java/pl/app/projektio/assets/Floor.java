package pl.app.projektio.assets;

public class Floor {
    private int id;
    private int buildingId;
    private String name;

    public Floor() {}

    public Floor(int id, int buildingId, String name) {
        this.id = id;
        this.buildingId = buildingId;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}