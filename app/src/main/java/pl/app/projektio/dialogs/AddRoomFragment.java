package pl.app.projektio.dialogs;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import pl.app.projektio.DashboardActivity;
import pl.app.projektio.fragments.ManagerUISearchOptionsFragment;
import pl.app.projektio.R;
import pl.app.projektio.assets.Room;

public class AddRoomFragment extends Fragment {
    private DashboardActivity parentActivity;
    private ManagerUISearchOptionsFragment parentFragment;
    private EditText editTextAddRoom;
    private Button addRoomButton;
    private Button cancelButton;

    public AddRoomFragment() { super(R.layout.dialog_manager_ui_add_room); }

    @Override
    public void onStart() {
        super.onStart();
        parentActivity = (DashboardActivity) getActivity();
        parentFragment = (ManagerUISearchOptionsFragment) getParentFragment();
        editTextAddRoom = getView().findViewById(R.id.editTextAddRoomFragment);
        addRoomButton = getView().findViewById(R.id.buttonAddAddRoomFragment);
        cancelButton = getView().findViewById(R.id.buttonCancelAddRoomFragment);

        parentActivity.findViewById(R.id.linearLayoutDashManActivity)
                .setBackgroundResource(R.drawable.activity_manager_buttonlayout_db_button_active);

        addRoomButton.setOnClickListener(v -> {
            String newRoomName = editTextAddRoom.getText().toString();
            if (parentActivity.rooms.getRoomByName(newRoomName) == null) {
                parentActivity.service
                        .createRoom(new Room(parentActivity.rooms.getNextId(),
                                        parentActivity.buildings.getChosenBuilding().getId(),
                                        parentActivity.floors.getChosenFloor().getId(),
                                        newRoomName));
                parentFragment.updateRooms();
                parentFragment.getChildFragmentManager().popBackStack();

                parentActivity.findViewById(R.id.linearLayoutDashManActivity)
                        .setBackgroundResource(R.drawable.dialog_filter);
            } else parentActivity.showToast(R.string.toastFloorExists, Toast.LENGTH_SHORT);
        });

        cancelButton.setOnClickListener(v -> {
            parentFragment.getChildFragmentManager().popBackStack();

            parentActivity.findViewById(R.id.linearLayoutDashManActivity)
                    .setBackgroundResource(R.drawable.dialog_filter);
        });
    }
}
