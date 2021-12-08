package pl.app.projektio.dialogs;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import pl.app.projektio.InventoryDashboardActivity;
import pl.app.projektio.R;
import pl.app.projektio.assets.BuildingList;
import pl.app.projektio.assets.Floor;
import pl.app.projektio.assets.FloorList;
import pl.app.projektio.assets.Inventory;
import pl.app.projektio.assets.InventoryList;
import pl.app.projektio.fragments.InventoryUIInventory;

public class NewInventoryFragment extends Fragment {
    private InventoryDashboardActivity parentActivity;
    private HashMap<Integer, String> floorsMap;
    private Spinner floorsSpinner;
    private Button startButton;
    private Button cancelButton;
    private FloorList floors;
    private BuildingList buildings;
    private int selectedFloorId;

    public NewInventoryFragment() { super(R.layout.dialog_inventory_ui_new_inventory); }

    @Override
    public void onStart() {
        super.onStart();
        parentActivity = (InventoryDashboardActivity) getActivity();
        floorsSpinner = (Spinner) getView().findViewById(R.id.spinnerInventoryNewInventory);
        startButton = (Button) getView().findViewById(R.id.buttonNewInventoryStartNew);
        cancelButton = (Button) getView().findViewById(R.id.buttonNewInventoryCancel);
        floorsMap = new HashMap<>();
        selectedFloorId = 0;

        this.floors = new FloorList(parentActivity.service.getFloors(), -1);
        this.buildings = new BuildingList(parentActivity.service.getBuildings());
        ArrayList<String> floorsString = new ArrayList<>();
        for (Floor f : this.floors.allFloors) {
            String floorItem = f.getName() + ", " + buildings.getBuildingById(f.getBuildingId()).getName();
            floorsString.add(floorItem);
            floorsMap.put(f.getId(), floorItem);
        }
        ArrayAdapter<String> floorsAdapter = new ArrayAdapter<>(this.getContext(),R.layout.view_inventory_ui_spinner_new_inventory, floorsString);
        floorsSpinner.setAdapter(floorsAdapter);

        floorsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for(Map.Entry<Integer, String> e : floorsMap.entrySet()) {
                    if (e.getValue().equals(parent.getItemAtPosition(position).toString())) {
                        selectedFloorId = e.getKey();
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        startButton.setOnClickListener(v -> {
            if (parentActivity.inventories == null)
                parentActivity.inventories = new InventoryList(parentActivity.service.getInventories());

            parentActivity.inventories.activeInventory = new Inventory(parentActivity.inventories.getNextId(), selectedFloorId, new Date(), 0);
            parentActivity.service.createInventory(parentActivity.inventories.activeInventory);
            parentActivity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerInventoryDashboardActivity, InventoryUIInventory.class, null)
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit();
        });



        cancelButton.setOnClickListener(v -> {
            parentActivity.getSupportFragmentManager().popBackStack();
        });
    }

}
