package pl.app.projektio.dialogs;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import pl.app.projektio.DashboardActivity;
import pl.app.projektio.R;
import pl.app.projektio.assets.Floor;
import pl.app.projektio.assets.Room;
import pl.app.projektio.fragments.ManagerUISearchOptionsFragment;

public class EditRoomFragment extends Fragment {
    private EditText editTextRoomName;
    private Button editButton;
    private Button cancelButton;
    private DashboardActivity parentActivity;
    private ManagerUISearchOptionsFragment parentFragment;

    public EditRoomFragment() { super(R.layout.dialog_manager_ui_edit_room); }

    @Override
    public void onStart() {
        super.onStart();
        parentActivity = (DashboardActivity) getActivity();
        parentFragment = (ManagerUISearchOptionsFragment) getParentFragment();
        editTextRoomName = getView().findViewById(R.id.editTextEditRoomFragment);
        editButton = getView().findViewById(R.id.buttonEditEditRoomFragment);
        cancelButton = getView().findViewById(R.id.buttonCancelEditRoomFragment);

        parentActivity.findViewById(R.id.linearLayoutDashManActivity)
                .setBackgroundResource(R.drawable.activity_manager_buttonlayout_db_button_active);

        editButton.setOnClickListener(v -> {
            Room chosenRoom = parentActivity.rooms.getChosenRoom();
            String newName = editTextRoomName.getText().toString();
            if (chosenRoom != null) {
                if (parentActivity.rooms.getRoomByName(newName) != null)
                    parentActivity.showToast(R.string.toastFloorExists, Toast.LENGTH_SHORT);
                else {
                    chosenRoom.setName(newName);
                    parentActivity.service.updateRoom(chosenRoom);
                    parentFragment.updateRooms();
                    parentFragment.getChildFragmentManager().popBackStack();
                    parentActivity.findViewById(R.id.linearLayoutDashManActivity)
                            .setBackgroundResource(R.drawable.dialog_filter);
                }
            } else parentActivity.showToast(R.string.toastElementNotChosen, Toast.LENGTH_SHORT);
        });

        cancelButton.setOnClickListener(v -> {
            getParentFragmentManager().popBackStack();

            parentActivity.findViewById(R.id.linearLayoutDashManActivity)
                    .setBackgroundResource(R.drawable.dialog_filter);
        });
    }
}
