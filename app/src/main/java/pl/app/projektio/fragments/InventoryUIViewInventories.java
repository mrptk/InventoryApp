package pl.app.projektio.fragments;

import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import pl.app.projektio.InventoryDashboardActivity;
import pl.app.projektio.R;
import pl.app.projektio.assets.BuildingList;
import pl.app.projektio.assets.FloorList;
import pl.app.projektio.assets.Inventory;
import pl.app.projektio.assets.InventoryList;

public class InventoryUIViewInventories extends Fragment {
    private InventoryDashboardActivity parentActivity;
    private LinearLayout linearLayout;
    private FloorList floors;
    private BuildingList buildings;


    public InventoryUIViewInventories() { super(R.layout.fragment_inventory_ui_view_inventories); }

    @Override
    public void onStart() {
        super.onStart();

        parentActivity = (InventoryDashboardActivity) getActivity();
        floors = new FloorList(parentActivity.service.getFloors(), -1);
        buildings = new BuildingList(parentActivity.service.getBuildings());
        parentActivity.inventories = new InventoryList(parentActivity.service.getInventories());
        linearLayout = getView().findViewById(R.id.linearLayoutScrollViewInventoryUIViewInventories);
        linearLayout.removeAllViews();

        for (Inventory i: parentActivity.inventories.openInventories) {
            String iStartDate = i.getStartDate();
            String inventoryDescription = "Inwentaryzacja: \n" + floors.getFloorById(i.getFloorId()).getName()
                    + ", " + buildings.getBuildingById(floors.getFloorById(i.getFloorId()).getBuildingId()).getName() + "\n"
                    + iStartDate.substring(0,iStartDate.indexOf("T")) + "\n"
                    + iStartDate.substring(iStartDate.indexOf("T") + 1);

            linearLayout.addView(i.getInventoryLayout(this.getContext(), inventoryDescription, v -> {
                parentActivity.inventories.activeInventory = i;
                parentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerInventoryDashboardActivity, InventoryUIInventory.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
            }));
        }
    }
}
