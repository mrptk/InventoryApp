package pl.app.projektio.assets;

public class Room {
    private int id;
    private int buildingId;
    private int floorId;
    private String name;

    public Room() {}

    public Room(int id, int buildingId, int floorId, String name) {
        this.id = id;
        this.buildingId = buildingId;
        this.floorId = floorId;
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

    public int getFloorId() {
        return floorId;
    }

    public void setFloorId(int roomId) {
        this.floorId = roomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}