package pl.app.projektio.dialogs;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import pl.app.projektio.DashboardActivity;
import pl.app.projektio.fragments.ManagerUISearchOptionsFragment;
import pl.app.projektio.R;
import pl.app.projektio.assets.Floor;

public class EditFloorFragment extends Fragment {
    private EditText editTextFloorName;
    private Button editButton;
    private Button cancelButton;
    private DashboardActivity parentActivity;
    private ManagerUISearchOptionsFragment parentFragment;

    public EditFloorFragment() { super(R.layout.dialog_manager_ui_edit_floor); }

    @Override
    public void onStart() {
        super.onStart();
        parentActivity = (DashboardActivity) getActivity();
        parentFragment = (ManagerUISearchOptionsFragment) getParentFragment();
        editTextFloorName = getView().findViewById(R.id.editTextEditFloorFragment);
        editButton = getView().findViewById(R.id.buttonEditEditFloorFragment);
        cancelButton = getView().findViewById(R.id.buttonCancelEditFloorFragment);

        parentActivity.findViewById(R.id.linearLayoutDashManActivity)
                .setBackgroundResource(R.drawable.activity_manager_buttonlayout_db_button_active);

        editButton.setOnClickListener(v -> {
            Floor chosenFloor = parentActivity.floors.getChosenFloor();
            String newName = editTextFloorName.getText().toString();
            if (chosenFloor != null) {
                if (parentActivity.floors.getFloorByName(newName) != null)
                    parentActivity.showToast(R.string.toastFloorExists, Toast.LENGTH_SHORT);
                else {
                    chosenFloor.setName(newName);
                    parentActivity.service.updateFloor(chosenFloor);
                    parentFragment.updateFloors();
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
