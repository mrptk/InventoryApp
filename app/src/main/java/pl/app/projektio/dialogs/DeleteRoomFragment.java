package pl.app.projektio.dialogs;

import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import pl.app.projektio.DashboardActivity;
import pl.app.projektio.fragments.ManagerUISearchOptionsFragment;
import pl.app.projektio.R;

public class DeleteRoomFragment extends Fragment {
    private DashboardActivity parentActivity;
    private ManagerUISearchOptionsFragment parentFragment;
    private TextView textViewDeleteMsg;
    private Button deleteButton;
    private Button cancelButton;

    public DeleteRoomFragment() { super(R.layout.dialog_manager_ui_delete_room); }

    @Override
    public void onStart() {
        super.onStart();
        parentActivity = (DashboardActivity) getActivity();
        parentFragment = (ManagerUISearchOptionsFragment) getParentFragment();
        textViewDeleteMsg = getView().findViewById(R.id.textViewDeleteRoomFragment);
        deleteButton = getView().findViewById(R.id.buttonDeleteDeleteRoomFragment);
        cancelButton = getView().findViewById(R.id.buttonCancelDeleteRoomFragment);

        parentActivity.findViewById(R.id.linearLayoutDashManActivity)
                .setBackgroundResource(R.drawable.activity_manager_buttonlayout_db_button_active);

        String textViewText = getString(R.string.managerUIDeleteMsg) +
                " " + parentActivity.rooms.getChosenRoom().getName() + "?\n" + getString(R.string.managerUIDeleteWarning);
        textViewDeleteMsg.setText(textViewText);

        deleteButton.setOnClickListener(v -> {
            parentActivity.service.deleteRoom(parentActivity.rooms.getChosenRoom());
            parentFragment.updateRooms();
            parentFragment.getChildFragmentManager().popBackStack();
            parentActivity.findViewById(R.id.linearLayoutDashManActivity)
                    .setBackgroundResource(R.drawable.dialog_filter);
        });

        cancelButton.setOnClickListener(v -> {
            parentFragment.getChildFragmentManager().popBackStack();
            parentActivity.findViewById(R.id.linearLayoutDashManActivity)
                    .setBackgroundResource(R.drawable.dialog_filter);
        });
    }
}
