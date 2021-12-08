package pl.app.projektio.fragments;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


import pl.app.projektio.DashboardActivity;
import pl.app.projektio.R;
import pl.app.projektio.assets.*;
import pl.app.projektio.dialogs.*;

public class ManagerUISearchOptionsFragment extends Fragment {
    public DashboardActivity parentActivity;
    private Button backButton;

    private Spinner buildingsSpinner;
    private ArrayAdapter<String> adapterBuildings;
    private FloatingActionButton addBuildingButton;
    private FloatingActionButton editBuildingButton;
    private FloatingActionButton deleteBuildingButton;

    private Spinner floorsSpinner;
    private ArrayAdapter<String> adapterFloors;
    private FloatingActionButton addFloorButton;
    private FloatingActionButton editFloorButton;
    private FloatingActionButton deleteFloorButton;

    private Spinner roomsSpinner;
    private ArrayAdapter<String> adapterRooms;
    private FloatingActionButton addRoomButton;
    private FloatingActionButton editRoomButton;
    private FloatingActionButton deleteRoomButton;

    public ManagerUISearchOptionsFragment() { super(R.layout.fragment_manager_ui_search_options); }

    @Override
    public void onStart() {
        super.onStart();
        parentActivity = (DashboardActivity) getActivity();

        parentActivity.findViewById(R.id.linearLayoutDashManActivity)
                .setBackgroundResource(R.drawable.dialog_filter);

        // Spinnery
        buildingsSpinner = getView().findViewById(R.id.spinnerBuildingsSearchOptionsFragment);
        floorsSpinner = getView().findViewById(R.id.spinnerFloorsSearchOptionsFragment);
        roomsSpinner = getView().findViewById(R.id.spinnerRoomsSearchOptionsFragment);

        updateBuildings();
        updateFloors();
        updateRooms();

        // BUDYNKI *********************
        buildingsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String chosenItem = parent.getItemAtPosition(position).toString();

                if (!chosenItem.equals("Wszystkie")) {
                    parentActivity.buildings.setChosenBuilding(parentActivity.buildings.getBuildingByName(chosenItem));
                }
                else parentActivity.buildings.setChosenBuilding(null);

                updateFloors();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        addBuildingButton = getView().findViewById(R.id.addBuildingFloatingButtonSearchOptionsFragment);
        addBuildingButton.setOnClickListener(v -> {
            AddBuildingFragment addBuildingFragment = new AddBuildingFragment();
            getChildFragmentManager().beginTransaction()
                    .add(R.id.fragmentContainerSearchOptionsFragment, addBuildingFragment)
                    .setReorderingAllowed(true)
                    .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                    .addToBackStack(null)
                    .commit();
        });

        editBuildingButton = getView().findViewById(R.id.editBuildingFloatingButtonSearchOptionsFragment);
        editBuildingButton.setOnClickListener(v -> {
            if (parentActivity.buildings.getChosenBuilding() != null) {
                EditBuildingFragment editBuildingFragment = new EditBuildingFragment();
                getChildFragmentManager().beginTransaction()
                        .add(R.id.fragmentContainerSearchOptionsFragment, editBuildingFragment)
                        .setReorderingAllowed(true)
                        .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .addToBackStack(null)
                        .commit();
            } else parentActivity.showToast(R.string.toastElementNotChosen, Toast.LENGTH_SHORT);
        });

        deleteBuildingButton = getView().findViewById(R.id.deleteBuildingFloatingButtonSearchOptionsFragment);
        deleteBuildingButton.setOnClickListener(v -> {
            if (parentActivity.buildings.getChosenBuilding() != null) {
                DeleteBuildingFragment deleteBuildingFragment = new DeleteBuildingFragment();
                getChildFragmentManager().beginTransaction()
                        .add(R.id.fragmentContainerSearchOptionsFragment, deleteBuildingFragment)
                        .setReorderingAllowed(true)
                        .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .addToBackStack(null)
                        .commit();
            } else parentActivity.showToast(R.string.toastElementNotChosen, Toast.LENGTH_SHORT);
        });

        // PIETRA ******************************
        floorsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String chosenItem = parent.getItemAtPosition(position).toString();

                if (!chosenItem.equals("Wszystkie")) {
                    parentActivity.floors.setChosenFloor(parentActivity.floors.getFloorByName(chosenItem));
                }
                else parentActivity.floors.setChosenFloor(null);

                updateRooms();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        addFloorButton = getView().findViewById(R.id.addFloorFloatingButtonSearchOptionsFragment);
        addFloorButton.setOnClickListener(v -> {
            if (getChildFragmentManager().getFragments().size() > 0)
                getChildFragmentManager().popBackStack();
            if (parentActivity.buildings.getChosenBuilding() != null) {
                AddFloorFragment addFloorFragment = new AddFloorFragment();
                getChildFragmentManager().beginTransaction()
                        .add(R.id.fragmentContainerSearchOptionsFragment, addFloorFragment)
                        .setReorderingAllowed(true)
                        .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .addToBackStack(null)
                        .commit();
            } else parentActivity.showToast(R.string.toastElementNotChosen, Toast.LENGTH_SHORT);
        });

        editFloorButton = getView().findViewById(R.id.editFloorFloatingButtonSearchOptionsFragment);
        editFloorButton.setOnClickListener(v -> {
            if (getChildFragmentManager().getFragments().size() > 0)
                getChildFragmentManager().popBackStack();
            if (parentActivity.floors.getChosenFloor() != null) {
                EditFloorFragment editFloorFragment = new EditFloorFragment();
                getChildFragmentManager().beginTransaction()
                        .add(R.id.fragmentContainerSearchOptionsFragment, editFloorFragment)
                        .setReorderingAllowed(true)
                        .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .addToBackStack(null)
                        .commit();
            } else parentActivity.showToast(R.string.toastElementNotChosen, Toast.LENGTH_SHORT);
        });

        deleteFloorButton = getView().findViewById(R.id.deleteFloorFloatingButtonSearchOptionsFragment);
        deleteFloorButton.setOnClickListener(v -> {
            if (getChildFragmentManager().getFragments().size() > 0)
                getChildFragmentManager().popBackStack();
            if (parentActivity.floors.getChosenFloor() != null) {
                DeleteFloorFragment deleteFloorFragment = new DeleteFloorFragment();
                getChildFragmentManager().beginTransaction()
                        .add(R.id.fragmentContainerSearchOptionsFragment, deleteFloorFragment)
                        .setReorderingAllowed(true)
                        .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .addToBackStack(null)
                        .commit();
            } else parentActivity.showToast(R.string.toastElementNotChosen, Toast.LENGTH_SHORT);
        });

        // POKOJE ******************************************************
        roomsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String chosenItem = parent.getItemAtPosition(position).toString();

                if (!chosenItem.equals("Wszystkie")) {
                    parentActivity.rooms.setChosenRoom(parentActivity.rooms.getRoomByName(chosenItem));
                }
                else parentActivity.rooms.setChosenRoom(null);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addRoomButton = getView().findViewById(R.id.addRoomFloatingButtonSearchOptionsFragment);
        addRoomButton.setOnClickListener(v -> {
            if (getChildFragmentManager().getFragments().size() > 0)
                getChildFragmentManager().popBackStack();
            if(parentActivity.floors.getChosenFloor() != null) {
                AddRoomFragment addRoomFragment = new AddRoomFragment();
                getChildFragmentManager().beginTransaction()
                        .add(R.id.fragmentContainerSearchOptionsFragment, addRoomFragment)
                        .setReorderingAllowed(true)
                        .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .addToBackStack(null)
                        .commit();
            } else parentActivity.showToast(R.string.toastElementNotChosen, Toast.LENGTH_SHORT);
        });

        editRoomButton = getView().findViewById(R.id.editRoomFloatingButtonSearchOptionsFragment);
        editRoomButton.setOnClickListener(v -> {
            if (getChildFragmentManager().getFragments().size() > 0)
                getChildFragmentManager().popBackStack();
            if (parentActivity.rooms.getChosenRoom() != null) {
                EditRoomFragment editRoomFragment = new EditRoomFragment();
                getChildFragmentManager().beginTransaction()
                        .add(R.id.fragmentContainerSearchOptionsFragment, editRoomFragment)
                        .setReorderingAllowed(true)
                        .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .addToBackStack(null)
                        .commit();
            } else parentActivity.showToast(R.string.toastElementNotChosen, Toast.LENGTH_SHORT);
        });

        deleteRoomButton = getView().findViewById(R.id.deleteRoomFloatingButtonSearchOptionsFragment);
        deleteRoomButton.setOnClickListener(v -> {
            if (getChildFragmentManager().getFragments().size() > 0)
                getChildFragmentManager().popBackStack();
            if (parentActivity.rooms.getChosenRoom() != null) {
                DeleteRoomFragment deleteRoomFragment = new DeleteRoomFragment();
                getChildFragmentManager().beginTransaction()
                        .add(R.id.fragmentContainerSearchOptionsFragment, deleteRoomFragment)
                        .setReorderingAllowed(true)
                        .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .addToBackStack(null)
                        .commit();
            } else parentActivity.showToast(R.string.toastElementNotChosen, Toast.LENGTH_SHORT);
        });

        backButton = getView().findViewById(R.id.buttonBackSearchOptionsFragment);
        backButton.setOnClickListener(v -> getParentFragmentManager().popBackStack());

        updateBuildings();
    }

    public void updateBuildings() {
        parentActivity.buildings = new BuildingList(parentActivity.service.getBuildings());
        adapterBuildings = new ArrayAdapter<>(this.requireContext(), R.layout.view_manager_ui_spinner_add_item, parentActivity.buildings.buildingsStringArray);
        buildingsSpinner.setAdapter(adapterBuildings);
    }

    public void updateFloors() {
        if (parentActivity.buildings.getChosenBuilding() == null)
            parentActivity.floors = new FloorList(parentActivity.service.getFloors(), -1);
        else
            parentActivity.floors = new FloorList(parentActivity.service.getFloors(), parentActivity.buildings.getChosenBuilding().getId());
        adapterFloors = new ArrayAdapter<>(this.requireContext(), R.layout.view_manager_ui_spinner_add_item, parentActivity.floors.chosenFloorsStringArray);
        floorsSpinner.setAdapter(adapterFloors);
    }

    public void updateRooms() {
        if (parentActivity.floors.getChosenFloor() == null)
            parentActivity.rooms = new RoomList(parentActivity.service.getRooms(), -1);
        else
            parentActivity.rooms = new RoomList(parentActivity.service.getRooms(), parentActivity.floors.getChosenFloor().getId());
        adapterRooms = new ArrayAdapter<>(this.requireContext(), R.layout.view_manager_ui_spinner_add_item, parentActivity.rooms.chosenRoomsStringArray);
        roomsSpinner.setAdapter(adapterRooms);
    }

    private int getSpinnerIndex(Spinner spinner, String value) {
        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++)
            if (spinner.getItemAtPosition(i).equals(value))
                index = i;

        return index;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
