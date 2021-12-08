package pl.app.projektio.assets;

import java.util.ArrayList;

public class InventoryList {
    public ArrayList<Inventory> allInventories;
    public ArrayList<Inventory> openInventories;
    public ArrayList<Inventory> finishedInventories;
    public Inventory activeInventory;

    public InventoryList(ArrayList<Inventory> allInventories) {
        this.allInventories = allInventories;
        openInventories = new ArrayList<>();
        finishedInventories = new ArrayList<>();

        for (Inventory i: this.allInventories) {
            if (i.getStatus() == 0) openInventories.add(i);
            else finishedInventories.add(i);
        }
    }

    public int getNextId(){
        int nextId = 0;

        for (Inventory i : allInventories) {
            if (i.getId() > nextId) nextId = i.getId();
        }
        return nextId + 1;
    }
}
