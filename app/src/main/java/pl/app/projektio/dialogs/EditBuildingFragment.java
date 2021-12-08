package pl.app.projektio.dialogs;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import pl.app.projektio.DashboardActivity;
import pl.app.projektio.fragments.ManagerUISearchOptionsFragment;
import pl.app.projektio.R;
import pl.app.projektio.assets.Building;

public class EditBuildingFragment extends Fragment {
    private EditText editTextBuildingName;
    private Button editButton;
    private Button cancelButton;
    private DashboardActivity parentActivity;
    private ManagerUISearchOptionsFragment parentFragment;

    public EditBuildingFragment() { super(R.layout.dialog_manager_ui_edit_building); }

    @Override
    public void onStart() {
        super.onStart();
        parentActivity = (DashboardActivity) getActivity();
        parentFragment = (ManagerUISearchOptionsFragment) getParentFragment();
        editTextBuildingName = getView().findViewById(R.id.editTextEditBuildingFragment);
        editButton = getView().findViewById(R.id.buttonEditEditBuildingFragment);
        cancelButton = getView().findViewById(R.id.buttonCancelEditBuildingFragment);

        parentActivity.findViewById(R.id.linearLayoutDashManActivity)
                .setBackgroundResource(R.drawable.activity_manager_buttonlayout_db_button_active);

        editButton.setOnClickListener(v -> {
            Building chosenBuilding = parentActivity.buildings.getChosenBuilding();
            String newName = editTextBuildingName.getText().toString();
            if (chosenBuilding != null) {
                if (parentActivity.buildings.getBuildingByName(newName) != null)
                    parentActivity.showToast(R.string.toastBuildingExists, Toast.LENGTH_SHORT);
                else {
                    chosenBuilding.setName(newName);
                    parentActivity.service.updateBuilding(chosenBuilding);
                    parentFragment.updateBuildings();
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
