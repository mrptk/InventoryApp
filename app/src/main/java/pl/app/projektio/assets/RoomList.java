package pl.app.projektio.assets;

import java.util.ArrayList;

public class RoomList {
    public ArrayList<Room> allRooms;
    public ArrayList<Room> chosenRooms;
    public ArrayList<String> chosenRoomsStringArray;
    private Room chosenRoom;

    public RoomList(ArrayList<Room> allRooms, int floorId) {
        this.allRooms = allRooms;
        this.chosenRooms = new ArrayList<>();
        this.chosenRoomsStringArray = new ArrayList<>();

        if (floorId != -1)  {
            for (Room r : allRooms) if (r.getFloorId() == floorId) {
                chosenRooms.add(r);
                chosenRoomsStringArray.add(r.getName());
            }
            chosenRoomsStringArray.sort(String::compareToIgnoreCase);
        }

        chosenRoomsStringArray.add(0, "Wszystkie");
    }

    public Room getChosenRoom() {
        return chosenRoom;
    }

    public void setChosenRoom(Room chosenRoom) {
        this.chosenRoom = chosenRoom;
    }

    public Room getRoomByName(String roomName) {
        Room match = null;

        for (int i = 0; i < chosenRooms.size() && match == null; i++)
            if (roomName.equals(chosenRooms.get(i).getName()))
                match = chosenRooms.get(i);

        return match;
    }

    public Room getRoomById(int roomId) {
        Room match = null;

        for (int i = 0; i < allRooms.size() && match == null; i++)
            if (roomId == allRooms.get(i).getId())
                match = allRooms.get(i);

        return match;
    }

    public int getNextId(){
        int nextId = 0;

        for (Room r : allRooms) {
            if (r.getId() > nextId) nextId = r.getId();
        }
        return nextId + 1;
    }
}
