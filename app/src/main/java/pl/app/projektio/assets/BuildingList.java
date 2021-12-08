package pl.app.projektio.assets;

import java.util.ArrayList;

public class BuildingList {
    public ArrayList<Building> buildings;
    public ArrayList<String> buildingsStringArray;
    private Building chosenBuilding;

    public BuildingList(ArrayList<Building> buildings) {
        this.buildings = buildings;
        updateStringArray();
    }

    private void updateStringArray() {
        buildingsStringArray = new ArrayList<>();
        for (Building b : buildings) buildingsStringArray.add(b.getName());
        buildingsStringArray.sort(String::compareToIgnoreCase);
        buildingsStringArray.add(0, "Wszystkie");
    }

    public Building getBuildingByName(String buildingName) {
        Building match = null;

        for (int i = 0; i < buildings.size() && match == null; i++)
            if (buildings.get(i).getName().equals(buildingName))
                match = buildings.get(i);


        return match;
    }
    public Building getBuildingById(int buildingId) {
        Building match = null;

        for (int i = 0; i < buildings.size() && match == null; i++)
            if (buildingId == buildings.get(i).getId())
                match = buildings.get(i);

        return match;
    }

    public Building getChosenBuilding() {
        return chosenBuilding;
    }

    public void setChosenBuilding(Building chosenBuilding) {
        this.chosenBuilding = chosenBuilding;
    }

    public int getNextId(){
        int nextId = 0;

        for (Building b : buildings) {
            if (b.getId() > nextId) nextId = b.getId();
        }
        return nextId + 1;
    }

}
