package pl.app.projektio.dialogs;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pl.app.projektio.DashboardActivity;
import pl.app.projektio.R;
import pl.app.projektio.ScannerActivity;
import pl.app.projektio.assets.BuildingList;
import pl.app.projektio.assets.FloorList;
import pl.app.projektio.assets.Item;
import pl.app.projektio.assets.Room;
import pl.app.projektio.fragments.ManagerUIViewDatabaseFragment;

public class EditItemFragment extends Fragment {
    private DashboardActivity parentActivity;
    private ManagerUIViewDatabaseFragment parentFragment;

    private Spinner roomsSpinner;
    private ArrayAdapter<String> roomsAdapter;
    private HashMap<Integer, String> roomMap; // pomocniczna mapa do identyfikowania pokoi

    private EditText statusET;
    public EditText codeET;
    private EditText categoryET;
    private EditText nameET;

    private Button scanButton;
    private Button deleteButton;
    private Button backButton;

    private FloatingActionButton saveButton;

    private int selectedRoomId;

    public EditItemFragment() { super(R.layout.fragment_manager_ui_edit_item); }

    @Override
    public void onStart() {
        super.onStart();

        parentActivity = (DashboardActivity) getActivity();
        parentFragment = (ManagerUIViewDatabaseFragment) getParentFragment();
        selectedRoomId = parentActivity.items.getChosenItem().getRoomId();

        ArrayList<Room> allRooms = parentActivity.service.getRooms();
        parentActivity.floors = new FloorList(parentActivity.service.getFloors(), -1);
        parentActivity.buildings = new BuildingList(parentActivity.service.getBuildings());
        ArrayList<String> allRoomsString = new ArrayList<>();
        roomMap = new HashMap<>();
        String entry;

        for (Room r : allRooms) {
            entry = r.getName() + " (" + parentActivity.floors.getFloorById(r.getFloorId()).getName() + ", "
                    + parentActivity.buildings.getBuildingById(parentActivity.floors.getFloorById(r.getFloorId()).getBuildingId()).getName() + ")";
            allRoomsString.add(entry);
            roomMap.put(r.getId(), entry);
        }

        allRoomsString.sort(String::compareToIgnoreCase);

        roomsSpinner = getView().findViewById(R.id.spinnerRoomsEditItemFragment);
        roomsAdapter = new ArrayAdapter<>(this.requireContext(), R.layout.view_manager_ui_spinner_edit_item, allRoomsString);
        roomsSpinner.setAdapter(roomsAdapter);
        roomsSpinner.setSelection(getSpinnerIndex(roomsSpinner, roomMap.get(parentActivity.items.getChosenItem().getRoomId())));

        roomsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String chosenRoom = parent.getItemAtPosition(position).toString();

                for(Map.Entry<Integer, String> e : roomMap.entrySet()) {
                    if (e.getValue().equals(chosenRoom)) {
                        selectedRoomId = e.getKey();
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        statusET = getView().findViewById(R.id.editTextStatusEditItemFragment);
        codeET = getView().findViewById(R.id.editTextCodeEditItemFragment);
        categoryET = getView().findViewById(R.id.editTextCategoryEditItemFragment);
        nameET = getView().findViewById(R.id.editTextNameEditItemFragment);

        statusET.setText(parentActivity.items.getChosenItem().getStatus());
        if (!codeET.getText().toString().equals(parentActivity.scanResult))
            codeET.setText(parentActivity.items.getChosenItem().getCode());
        categoryET.setText(parentActivity.items.getChosenItem().getCategory());
        nameET.setText(parentActivity.items.getChosenItem().getName());

        scanButton = getView().findViewById(R.id.buttonScanEditItemFragment);
        deleteButton = getView().findViewById(R.id.buttonDeleteEditItemFragment);
        saveButton = getView().findViewById(R.id.floatingActionButtonManagerUIEditItemFragment);
        backButton = getView().findViewById(R.id.buttonBackEditItemFragment);

        scanButton.setOnClickListener(v -> {
            IntentIntegrator intentIntegrator = new IntentIntegrator(parentActivity);

            intentIntegrator.setPrompt("Użyj przycisku podgłaśniania, aby włączyć latarkę.");
            intentIntegrator.setBeepEnabled(true);
            intentIntegrator.setOrientationLocked(true);
            intentIntegrator.setCaptureActivity(ScannerActivity.class);
            intentIntegrator.initiateScan();
        });

        deleteButton.setOnClickListener(v -> {
            getChildFragmentManager().beginTransaction()
                    .add(R.id.fragmentContainerEditItemFragment, DeleteItemFragment.class, null)
                    .setReorderingAllowed(true)
                    .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                    .addToBackStack(null)
                    .commit();

        });

        saveButton.setOnClickListener(v -> {
            Item chosenItem = parentActivity.items.getChosenItem();

            chosenItem.setRoomId(selectedRoomId);
            chosenItem.setStatus(statusET.getText().toString());
            chosenItem.setCode(codeET.getText().toString());
            chosenItem.setCategory(categoryET.getText().toString());
            chosenItem.setName(nameET.getText().toString());

            parentActivity.service.updateItem(chosenItem);
            parentActivity.items.setChosenItem(null);

            parentActivity.onBackPressed();
        });

        backButton.setOnClickListener(v -> parentActivity.onBackPressed());

    }

    private int getSpinnerIndex(Spinner spinner, String value) {
        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++)
            if (spinner.getItemAtPosition(i).equals(value))
                index = i;

        return index;
    }
}
