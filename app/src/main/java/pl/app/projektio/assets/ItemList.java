package pl.app.projektio.assets;

import java.util.ArrayList;

public class ItemList {
    public ArrayList<Item> allItems;
    public ArrayList<Item> chosenItems;
    private Item chosenItem;

    public ItemList(ArrayList<Item> allItems) {
        this.allItems = allItems;
    }

    public int getNextId(){
        int nextId = 0;

        for (Item i : allItems) {
            if (i.getId() > nextId) nextId = i.getId();
        }
        return nextId + 1;
    }

    public Item getItemById(int itemId) {
        Item match = null;

        for (int i = 0; i < allItems.size() && match == null; i++)
            if (itemId == allItems.get(i).getId())
                match = allItems.get(i);

        return match;
    }
    public Item getItemByCode(String itemCode) {
        Item match = null;

        for (int i = 0; i < allItems.size() && match == null; i++)
            if (itemCode.equals(allItems.get(i).getCode()))
                match = allItems.get(i);

        return match;
    }

    public Item getChosenItem() {
        return chosenItem;
    }

    public void setChosenItem(Item chosenItem) {
        this.chosenItem = chosenItem;
    }
}
