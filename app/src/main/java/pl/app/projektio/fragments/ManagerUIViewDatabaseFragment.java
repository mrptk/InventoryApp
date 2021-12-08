package pl.app.projektio.fragments;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import pl.app.projektio.DashboardActivity;
import pl.app.projektio.R;
import pl.app.projektio.assets.*;
import pl.app.projektio.dialogs.AddItemFragment;
import pl.app.projektio.dialogs.EditItemFragment;

public class ManagerUIViewDatabaseFragment extends Fragment {
    private DashboardActivity parentActivity;
    private Button searchOptionsButton;
    private Button addItemButton;
    private LinearLayout linearLayout;


    public ManagerUIViewDatabaseFragment() {
        super(R.layout.fragment_manager_ui_view_database);
    }

    @Override
    public void onStart() {
        super.onStart();

        searchOptionsButton = getView().findViewById(R.id.buttonSearchOptionsViewDBFragment);
        searchOptionsButton.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerDashManActivity, ManagerUISearchOptionsFragment.class, null)
                    .setReorderingAllowed(true)
                    .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                    .addToBackStack(null)
                    .commit();
        });

        addItemButton = getView().findViewById(R.id.buttonAddItemViewDBFragment);
        addItemButton.setOnClickListener(v -> {
            List<Fragment> fragmentList = getChildFragmentManager().getFragments();
            if (parentActivity.rooms != null && parentActivity.rooms.getChosenRoom() != null) {
                if (fragmentList.size() == 0) {
                    getChildFragmentManager().beginTransaction()
                            .add(R.id.fragmentContainerViewDBFragment, AddItemFragment.class, null, "add_item")
                            .setReorderingAllowed(true)
                            .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                            .addToBackStack(null)
                            .commit();
                } else {
                    AddItemFragment addItemFragment = (AddItemFragment) fragmentList.get(0);

                    parentActivity.service.createItem(addItemFragment.scrapItem());
                    getChildFragmentManager().popBackStack();
                    updateItems();
                }
            } else {
                parentActivity.showToast(R.string.toastRoomNotChosen, Toast.LENGTH_SHORT);
            }
        });

        linearLayout = getView().findViewById(R.id.linearLayoutScrollViewManagerUIViewReportsFragment);
    }

    @Override
    public void onResume() {
        super.onResume();
        parentActivity = (DashboardActivity) getActivity();
        parentActivity.findViewById(R.id.linearLayoutDashManActivity)
                .setBackgroundResource(R.drawable.activity_manager_buttonlayout_db_button_active);
        updateItems();

    }

    public void updateItems() {
        ArrayList<Room> rooms = parentActivity.service.getRooms();
        ArrayList<Floor> floors = parentActivity.service.getFloors();
        ArrayList<Building> buildings = parentActivity.service.getBuildings();

        parentActivity.items = new ItemList(parentActivity.service.getItems());
        linearLayout.removeAllViews();
        for (Item i : parentActivity.items.allItems) {
            i.getLayout(linearLayout.getContext());
            i.display.setOnClickListener(v -> {
                parentActivity.items.setChosenItem(i);
                parentActivity.service.chosenItem = i;
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerDashManActivity, EditItemFragment.class, null, "edit_item")
                        .setReorderingAllowed(true)
                        .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .addToBackStack(null)
                        .commit();
            });

            if (parentActivity.rooms != null && parentActivity.rooms.getChosenRoom() != null) {
                int chosenRoomId = parentActivity.rooms.getChosenRoom().getId();
                if (i.getRoomId() == chosenRoomId)
                    linearLayout.addView(i.display);
            }
            else if (parentActivity.floors != null && parentActivity.floors.getChosenFloor() != null) {
                int chosenFloorId = parentActivity.floors.getChosenFloor().getId();

                for (Room r: rooms)
                    if (i.getRoomId() == r.getId() && r.getFloorId() == chosenFloorId)
                        linearLayout.addView(i.display);
            }
            else if (parentActivity.buildings != null && parentActivity.buildings.getChosenBuilding() != null) {
                int chosenBuildingId = parentActivity.buildings.getChosenBuilding().getId();

                for (Floor f: floors)
                    for (Room r: rooms)
                        if (i.getRoomId() == r.getId() && r.getFloorId() == f.getId() && f.getBuildingId() == chosenBuildingId)
                            linearLayout.addView(i.display);
            }
            else linearLayout.addView(i.display);
        }
    }
}
