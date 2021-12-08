package pl.app.projektio.assets;

import java.util.ArrayList;

public class FloorList {
    public ArrayList<Floor> allFloors;
    public ArrayList<Floor> chosenFloors;
    public ArrayList<String>  chosenFloorsStringArray;
    private Floor chosenFloor;

    public FloorList(ArrayList<Floor> allFloors, int buildingId) {
        this.allFloors = allFloors;
        this.chosenFloors = new ArrayList<>();
        chosenFloorsStringArray = new ArrayList<>();

        if (buildingId != -1)  {
            for (Floor f : allFloors) if (f.getBuildingId() == buildingId) {
                chosenFloors.add(f);
                chosenFloorsStringArray.add(f.getName());
            }
            chosenFloorsStringArray.sort(String::compareToIgnoreCase);
        }

        chosenFloorsStringArray.add(0, "Wszystkie");
    }

    public Floor getChosenFloor() {
        return chosenFloor;
    }

    public void setChosenFloor(Floor chosenFloor) {
        this.chosenFloor = chosenFloor;
    }

    public Floor getFloorByName(String floorName) {
        Floor match = null;

        for (int i = 0; i < chosenFloors.size() && match == null; i++)
            if (floorName.equals(chosenFloors.get(i).getName()))
                match = chosenFloors.get(i);

        return match;
    }

    public Floor getFloorById(int floorId) {
        Floor match = null;

        for (int i = 0; i < allFloors.size() && match == null; i++)
            if (floorId == allFloors.get(i).getId())
                match = allFloors.get(i);

        return match;
    }

    public int getNextId(){
        int nextId = 0;

        for (Floor f : allFloors) {
            if (f.getId() > nextId) nextId = f.getId();
        }
        return nextId + 1;
    }
}
