package pl.app.projektio.dialogs;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import pl.app.projektio.DashboardActivity;
import pl.app.projektio.fragments.ManagerUISearchOptionsFragment;
import pl.app.projektio.R;
import pl.app.projektio.assets.Building;

public class AddBuildingFragment extends Fragment {
    private EditText editTextBuildingName;
    private Button addButton;
    private Button cancelButton;
    private DashboardActivity parentActivity;
    private ManagerUISearchOptionsFragment parentFragment;

    public AddBuildingFragment() { super(R.layout.dialog_manager_ui_add_building); }

    @Override
    public void onStart() {
        super.onStart();
        parentActivity = (DashboardActivity) getActivity();
        parentFragment = (ManagerUISearchOptionsFragment) getParentFragment();
        editTextBuildingName = getView().findViewById(R.id.editTextAddBuildingFragment);
        addButton = getView().findViewById(R.id.buttonAddAddBuildingFragment);
        cancelButton = getView().findViewById(R.id.buttonCancelAddBuildingFragment);

        parentActivity.findViewById(R.id.linearLayoutDashManActivity)
                .setBackgroundResource(R.drawable.activity_manager_buttonlayout_db_button_active);

        addButton.setOnClickListener(v -> {
            String newBuildingName = editTextBuildingName.getText().toString();
            if (parentActivity.buildings.getBuildingByName(newBuildingName) == null) {
                parentActivity.service.createBuilding(new Building(parentActivity.buildings.getNextId(), newBuildingName));
                parentFragment.updateBuildings();
                parentFragment.getChildFragmentManager().popBackStack();
                parentActivity.findViewById(R.id.linearLayoutDashManActivity)
                        .setBackgroundResource(R.drawable.dialog_filter);
            } else parentActivity.showToast(R.string.toastBuildingExists, Toast.LENGTH_SHORT);
        });

        cancelButton.setOnClickListener(v -> {
            getParentFragmentManager().popBackStack();

            parentActivity.findViewById(R.id.linearLayoutDashManActivity)
                    .setBackgroundResource(R.drawable.dialog_filter);
        });
    }
}
