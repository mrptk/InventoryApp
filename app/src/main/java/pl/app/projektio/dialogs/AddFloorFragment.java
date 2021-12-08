package pl.app.projektio.dialogs;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import pl.app.projektio.DashboardActivity;
import pl.app.projektio.fragments.ManagerUISearchOptionsFragment;
import pl.app.projektio.R;
import pl.app.projektio.assets.Floor;

public class AddFloorFragment extends Fragment {
    private DashboardActivity parentActivity;
    private ManagerUISearchOptionsFragment parentFragment;
    private EditText editTextAddFloor;
    private Button addFloorButton;
    private Button cancelButton;

    public AddFloorFragment() { super(R.layout.dialog_manager_ui_add_floor); }

    @Override
    public void onStart() {
        super.onStart();
        parentActivity = (DashboardActivity) getActivity();
        parentFragment = (ManagerUISearchOptionsFragment) getParentFragment();
        editTextAddFloor = getView().findViewById(R.id.editTextAddFloorFragment);
        addFloorButton = getView().findViewById(R.id.buttonAddAddFloorFragment);
        cancelButton = getView().findViewById(R.id.buttonCancelAddFloorFragment);

        parentActivity.findViewById(R.id.linearLayoutDashManActivity)
                .setBackgroundResource(R.drawable.activity_manager_buttonlayout_db_button_active);

        addFloorButton.setOnClickListener(v -> {
            String newFloorName = editTextAddFloor.getText().toString();
            if (parentActivity.floors.getFloorByName(newFloorName) == null) {
                parentActivity.service
                        .createFloor(new Floor(parentActivity.floors.getNextId(),
                                parentActivity.buildings.getChosenBuilding().getId(),
                                newFloorName));
                parentFragment.updateFloors();
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
