package pl.app.projektio.dialogs;

import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import pl.app.projektio.DashboardActivity;
import pl.app.projektio.fragments.ManagerUISearchOptionsFragment;
import pl.app.projektio.R;

public class DeleteBuildingFragment extends Fragment {
    private DashboardActivity parentActivity;
    private ManagerUISearchOptionsFragment parentFragment;
    private TextView textViewDeleteMsg;
    private Button deleteButton;
    private Button cancelButton;

    public DeleteBuildingFragment() { super(R.layout.dialog_manager_ui_delete_building); }

    @Override
    public void onStart() {
        super.onStart();
        parentActivity = (DashboardActivity) getActivity();
        parentFragment = (ManagerUISearchOptionsFragment) getParentFragment();
        textViewDeleteMsg = getView().findViewById(R.id.textViewDeleteBuildingFragment);
        deleteButton = getView().findViewById(R.id.buttonDeleteDeleteBuildingFragment);
        cancelButton = getView().findViewById(R.id.buttonCancelDeleteBuildingFragment);

        parentActivity.findViewById(R.id.linearLayoutDashManActivity)
                .setBackgroundResource(R.drawable.activity_manager_buttonlayout_db_button_active);

        String textViewText = getString(R.string.managerUIDeleteMsg) +
                " " + parentActivity.buildings.getChosenBuilding().getName() + "?\n" + getString(R.string.managerUIDeleteWarning);
        textViewDeleteMsg.setText(textViewText);

        deleteButton.setOnClickListener(v -> {
            parentActivity.service.deleteBuilding(parentActivity.buildings.getChosenBuilding());
            parentFragment.updateBuildings();
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
